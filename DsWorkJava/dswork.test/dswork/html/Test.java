package dswork.html;

import java.io.IOException;
import dswork.html.nodes.Document;
import dswork.html.nodes.Element;
import dswork.http.HttpUtil;
public class Test
{
	public static void main(String[] argv) throws IOException
	{
		String cssQuery = ".container";
		String url = "";
		//url = "http://www.gzly.gov.cn/zw/zwclient/jzxx/jzxx.html";
		url = "http://www.gzly.gov.cn/a/tzgsgg/2319.html";
		url = "http://127.0.0.1/x/a.html";
		String html = new HttpUtil().create(url).connect();
		//System.out.println(html);
		Document doc = dswork.html.HtmlUtil.parse(html, false);
		doc.prettyPrint(true);
		doc.outline(true);
		doc.charset("GB18030");
		System.out.println("------------------------------------");
		
		for(Element e : doc.getElementsByTag("script"))
		{
			e.remove();
		}
		System.out.println("------------------------------------");
		System.out.println(doc.html());
		System.out.println("------------------------------------");
		System.out.println(doc.selectText(cssQuery));
		
		
		doc =  dswork.html.HtmlUtil.parse(html, false);
		doc.prettyPrint(false);
		doc.outline(false);
		System.out.println("------------------------------------");
		System.out.println(doc.html());
		
		
//		
//		url = "http://192.168.1.8/a/gzdt2/2298.html";
//		html = new HttpUtil().create(url).connect();
//		doc = SearchUtil.getHtmlDocument(html);
//		System.out.println(SearchUtil.getHtmlText(doc, cssQuery));
	}
}
