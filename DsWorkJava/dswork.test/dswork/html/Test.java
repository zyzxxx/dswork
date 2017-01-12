package dswork.html;

import java.io.IOException;
import dswork.html.nodes.Document;
import dswork.html.nodes.Element;
import dswork.http.HttpUtil;
public class Test
{
	public static void ss()
	{
		String[] arr = {
				"AMP=38"
				,"COPY=169"
				,"GT=62"
				,"LT=60"
				,"QUOT=34"
				,"REG=174"
				,"acute=180"
				,"amp=38"
				,"copy=169"
				,"curren=164"
				,"deg=176"
				,"divide=247"
				,"frac12=189"
				,"frac14=188"
				,"frac34=190"
				,"gt=62"
				,"laquo=171"
				,"lt=60"
				,"middot=183"
				,"nbsp=160"
				,"para=182"
				,"plusmn=177"
				,"pound=163"
				,"quot=34"
				,"raquo=187"
				,"reg=174"
				,"sect=167"
				,"shy=173"
				,"sup1=185"
				,"sup2=178"
				,"sup3=179"
				,"szlig=223"
				,"thorn=254"
				,"times=215"
				,"uml=168"
				,"yen=165"
		};
		for(String s : arr)
		{
			String[] ss = s.split("=", -1);
			System.out.println(",\"" + ss[0] + "=" + Integer.parseInt(ss[1], 10) + "\"");
		}
	}
	
	public static void main(String[] argv) throws IOException
	{
		String cssQuery = "title";
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
