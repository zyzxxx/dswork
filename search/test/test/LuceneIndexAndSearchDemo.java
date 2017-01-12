/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

import dswork.analyzer.BaseAnalyzer;

/**
 * 使用IKAnalyzer进行Lucene索引和查询的演示 2012-3-2
 * 以下是结合Lucene4.0 API的写法
 */
public class LuceneIndexAndSearchDemo
{
	/**
	 * 模拟： 创建一个单条记录的索引，并对其进行搜索
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new BaseAnalyzer(true);
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try
		{
			// 建立内存索引对象
			directory = new RAMDirectory();
			// directory = org.apache.lucene.store.FSDirectory.open(java.nio.file.FileSystems.getDefault().getPath("./LuceneDemo/index")); // 1.创建Directory（JDK
			
			// 配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			iwriter.deleteAll();// 清除以前的index
			// 写入索引
			// 检索内容
			String text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
			for(int i = 0; i<10; i++)
			{
				Document doc = new Document();
				doc.add(new StringField("ID", (10000+i)+"", Field.Store.YES));
				doc.add(new TextField("text", text, Field.Store.YES));
				iwriter.addDocument(doc);
			}
			if(true)
			{
				File dFile = new File("./web/META-INF"); // 设置搜索的File路径
				File[] files = dFile.listFiles();
				for(File file : files)
				{
					Document document = new Document(); // 3.创建Document对象
					// 4.为Document添加Field; 第三个参数是FieldType,但是定义在TextField中作为静态变量,看API也不好知道怎么写
					document.add(new Field("content", new FileReader(file), TextField.TYPE_NOT_STORED));
					document.add(new Field("name", file.getName(), TextField.TYPE_STORED));
					document.add(new Field("path", file.getAbsolutePath(), TextField.TYPE_STORED));
					iwriter.addDocument(document); // 5.通过IndexWriter添加文档到索引中
				}
			}
			
			iwriter.close();
			
			
			
			// 搜索过程**********************************
			// 实例化搜索器
			ireader = DirectoryReader.open(directory);
			isearcher = new IndexSearcher(ireader);
			String keyword = "中文分词工具包2";
			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser("text", analyzer);
			qp.setDefaultOperator(QueryParser.OR_OPERATOR);
			Query query = qp.parse(keyword);
			System.out.println("Query = " + query);
			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 5);
			System.out.println("命中：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for(int i = 0; i < scoreDocs.length; i++)
			{
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("内容：" + targetDoc.toString());
			}
		}
		catch(CorruptIndexException e)
		{
			e.printStackTrace();
		}
		catch(LockObtainFailedException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(ireader != null)
			{
				try
				{
					ireader.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			if(directory != null)
			{
				try
				{
					directory.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
