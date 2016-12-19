package common.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
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



import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import dswork.analyzer.BaseAnalyzer;
import dswork.core.page.Page;
import dswork.core.util.FileUtil;
import dswork.html.HtmlUtil;

public class LuceneUtil
{
	private static final String SEARCH_PATH = dswork.core.util.EnvironmentUtil.getToString("dswork.lucene.search", "");
	private static final String INDEX_PATH = dswork.core.util.EnvironmentUtil.getToString("dswork.lucene.index", "");
	private static final String Domain = dswork.core.util.EnvironmentUtil.getToString("dswork.lucene.domain", "");
	private static final int Size = new Long(dswork.core.util.EnvironmentUtil.getToLong("dswork.lucene.size", 100)).intValue();
	public static final long Refreshtime = dswork.core.util.EnvironmentUtil.getToLong("dswork.lucene.refreshtime", 86400000);
	
	
	private static Formatter formatter = new SimpleHTMLFormatter("<span class='keyvalue'>", "</span>");// 关键字增加前后缀
	private static Analyzer analyzer = new BaseAnalyzer(false);
	private static Directory directory = null;
	
	private static String SearchKey = "search";
	private static String SearchName = "name";
	private static String SearchMsg = "msg";
	private static String SearchUri = "uri";
	private static Document getLuceneDocument(long seq, String uri, String name, String msg)
	{
		Document doc = new Document();
		doc.add(new NumericDocValuesField("seq", seq));
		FieldType fieldType = new FieldType();
		fieldType.setIndexOptions(IndexOptions.NONE);
		fieldType.setStored(true);
		fieldType.setTokenized(false);
		doc.add(new Field(SearchUri, uri, fieldType));
		fieldType = new FieldType();
		fieldType.setIndexOptions(IndexOptions.NONE);
		fieldType.setStored(true);
		fieldType.setTokenized(false);
		doc.add(new Field(SearchName, name, fieldType));
		fieldType = new FieldType();
		fieldType.setIndexOptions(IndexOptions.NONE);
		fieldType.setStored(true);
		fieldType.setTokenized(false);
		doc.add(new Field(SearchMsg, msg, fieldType));
		
		// 空间换时间，用于搜索的field
		fieldType = new FieldType();
		fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		fieldType.setStored(true);
		fieldType.setTokenized(true);
		doc.add(new Field(SearchKey, name+msg, fieldType));
		return doc;
	}
	private static void initFile(IndexWriter iwriter, String rootpath, File files) throws IOException
	{
		if(files.isFile())
		{
			if(files.getName().endsWith(".html") && !files.getName().startsWith("index"))
			{
				long id = 1L;
				try
				{
					id = Long.parseLong(files.getName().substring(0, files.getName().length() - 5));
				}
				catch(NumberFormatException e)
				{
					e.printStackTrace();
				}
				String url = files.getPath().replaceAll("\\\\", "/").replaceFirst(rootpath, "");
				dswork.html.nodes.Document document = HtmlUtil.parse(FileUtil.readFile(files.getPath(), "UTF-8"));
				if(document == null)
				{
					System.out.println("---------------------\nerror:");
					return;
				}
				// ----------------------------------------
				// 此处读取的信息应该根据不同的项目，截取不同的文档信息
				// ----------------------------------------
				String title = document.selectOwnText(".title").trim();
				String content = document.selectText(".content").trim().replaceAll("&nbsp;", "");
				if(title.length() > 0 && content.length() > 0)
				{
					count++;
					iwriter.addDocument(LuceneUtil.getLuceneDocument(id, url, title, content));
				}
			}
		}
		else if(files.isDirectory())
		{
			for(File file : files.listFiles())
			{
				initFile(iwriter, rootpath, file);
			}
		}
	}
	private static int count = 0;
	private static void initIndex()
	{
		IndexWriter iwriter = null;
		try
		{
			if(SEARCH_PATH.length() == 0)
			{
				directory = new org.apache.lucene.store.RAMDirectory();
			}
			else
			{
				directory = org.apache.lucene.store.FSDirectory.open(java.nio.file.FileSystems.getDefault().getPath(INDEX_PATH));
			}
			IndexWriterConfig iwConfig = new IndexWriterConfig(new BaseAnalyzer(false));
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory, iwConfig);
			
			File files = new File(SEARCH_PATH);
			String path = files.getPath().replaceAll("\\\\", "/");
			if(files.isDirectory())
			{
				iwriter.deleteAll();
				initFile(iwriter, path, files);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(iwriter != null)
				{
					iwriter.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static int MaxLength = 300;
	
	public static Page<MyDocument> search(String keyword, int page, int pagesize)
	{
		// 太多了，处理一下
		if(pagesize > 100)
		{
			pagesize = 10;
		}
		System.out.println("搜索关键字：" + keyword);
		IndexReader ireader = null;
		Page<MyDocument> pageModel = null;
		try
		{
			if(directory == null)
			{
				directory = org.apache.lucene.store.FSDirectory.open(java.nio.file.FileSystems.getDefault().getPath(INDEX_PATH));
			}
			ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			QueryParser qp = new QueryParser(SearchKey, analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);
			System.out.println("Query = " + query);
			TopDocs topDocs = isearcher.search(query, Size);
			System.out.println("命中：" + topDocs.totalHits);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			pageModel = new Page<MyDocument>(page, pagesize, scoreDocs.length);

			Scorer scorer = new QueryScorer(query);// 封装关键字
			Highlighter highlighter = new Highlighter(formatter, scorer);
			// 文档切片
			Fragmenter fragmenter = new SimpleFragmenter(MaxLength);
			highlighter.setTextFragmenter(fragmenter);
			
			List<MyDocument> ls = new ArrayList<MyDocument>();
			for(int i = pageModel.getFirstResultIndex(); i < scoreDocs.length; i++)
			{
				ScoreDoc scoreDoc = scoreDocs[i];
				float score = scoreDoc.score;
				Document targetDoc = isearcher.doc(scoreDoc.doc);
				String name = targetDoc.get(SearchName);
				String content = targetDoc.get(SearchMsg);
				String uri = targetDoc.get(SearchUri);
				String title = highlighter.getBestFragment(analyzer, SearchName, name);
				String summary = highlighter.getBestFragment(analyzer, SearchMsg, content);
				if(title == null)
				{
					title = name;
				}
				if(summary == null)
				{
					summary = (content.length() > MaxLength) ? content.substring(0, 300): content;
				}
				MyDocument doc = new MyDocument();
				//doc.setName(name).setMsg(content);
				doc.setScore(score).setTitle(title).setSummary(summary).setUrl(Domain + uri);
				ls.add(doc);
			}
			pageModel.setResult(ls);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
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
		}
		if(pageModel == null)
		{
			pageModel = new Page<MyDocument>(1, pagesize, 0, new ArrayList<MyDocument>());
		}
		return pageModel;
	}
	
	public static synchronized void reload()
	{
		System.out.println("------------------");
		try
		{
			System.out.println("索引初始化开始");
			System.out.println("------------------");
			count = 0;
			initIndex();
			System.out.println("索引初始化文件个数为：" + count);
			System.out.println("索引初始化结束");
		}
		catch(Exception e)
		{
			System.out.println("***索引初始化失败***");
			e.printStackTrace();
		}
		System.out.println("------------------");
	}
}
