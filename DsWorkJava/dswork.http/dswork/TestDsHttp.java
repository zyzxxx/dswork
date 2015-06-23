package dswork;

import java.util.Date;

import dswork.http.HttpUtil;

public class TestDsHttp
{

	public static void main(String[] args)
	{
		java.util.Date d = new Date(2147483647);
		System.out.println(d);
		if(d != null)
		{
			//return;
		}
		HttpUtil http = new HttpUtil();
		System.out.println(http.create("https://www.baidu.com/", false).connect());
		System.out.println(http.clearCookies().create("https://fp.gdltax.gov.cn/fpzx/", true).connect());
		System.out.println(
			http.clearCookies().create("http://127.0.0.1:8070/DemoJava/manage/demo/test.htm")
			.setUseCaches(false)
			.connect()
		);
		System.out.println(
			http.create("http://127.0.0.1:8070/DemoJava/manage/demo/addDemo1.htm")
			.setUseCaches(false)
			.connect()
		);
		System.out.println(
			http.create("http://127.0.0.1:8070/DemoJava/manage/demo/addDemo2.htm")
			.addForm("title", "mytest中文")
			.addForm("content", "!@#$%^&*()_+|{}[]:\";'<>?,./ 4")
			.addForm("foundtime", "2000-01-02")
			.setUseCaches(false)
			.connect()
		);
	}
}
