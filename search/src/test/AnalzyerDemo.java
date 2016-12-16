/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
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

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import dswork.analyzer.BaseAnalyzer;

/**
 * 使用IKAnalyzer进行分词的演示
 * 2012-10-22
 */
public class AnalzyerDemo
{
	public static void main(String[] args)
	{
		Analyzer analyzer = new BaseAnalyzer();
		// Analyzer analyzer = new org.apache.lucene.analysis.cjk.CJKAnalyzer();
		// 获取Lucene的TokenStream对象
		TokenStream ts = null;
		try
		{
			ts = analyzer.tokenStream("myfield", new StringReader("领队猪中华人民共和国中国中华人民一万零三百四十五个分钟一二两三四五六七八一二两三四五六七九一二两三四五六七十零壹贰叁肆伍陆柒捌玖拾百千万亿拾佰仟萬億兆卅廿这是一个中文分词的例子，2小时，3分钟none，ok你可以直接运行它！BaseAnalyer can analysis english text too"));
			// 获取词元位置属性
			OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 获取词元文本属性
			TypeAttribute type = ts.addAttribute(TypeAttribute.class);
			// 重置TokenStream（重置StringReader）
			ts.reset();
			// 迭代获取分词结果
			while(ts.incrementToken())
			{
				System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
			}
			// 关闭TokenStream（关闭StringReader）
			ts.end(); // Perform end-of-stream operations, e.g. set the final offset.
		}
		catch(IOException e)
		{
			e.printStackTrace();
			analyzer.close();
		}
		finally
		{
			// 释放TokenStream的所有资源
			if(ts != null)
			{
				try
				{
					ts.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
