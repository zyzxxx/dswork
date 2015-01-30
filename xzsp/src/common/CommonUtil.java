package common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CommonUtil
{
	public static String getHtml(String htmlurl) throws IOException
	{
		return getHtml(htmlurl, "UTF-8");
	}

	// 读取一个网页内容
	public static String getHtml(String htmlurl, String charsetName) throws IOException
	{
		URL url;
		String temp;
		StringBuilder sb = new StringBuilder();
		try
		{
			url = new URL(htmlurl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charsetName));// 读取网页内容
			while((temp = in.readLine()) != null)
			{
				sb.append(temp);
			}
			in.close();
		}
		catch(final MalformedURLException me)
		{
			System.out.println("你输入的URL格式有问题！" + htmlurl);
			me.getMessage();
			throw me;
		}
		catch(final IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		return sb.toString().trim();
	}
}
