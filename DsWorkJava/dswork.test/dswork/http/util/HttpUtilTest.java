package dswork.http.util;

import java.io.InputStream;

import dswork.core.util.FileUtil;
import dswork.http.HttpUtil;

public class HttpUtilTest
{

	public static void main(String[] args)
	{
		HttpUtil http = new HttpUtil();
		
		
		String f = "";
		for(int i = 1; i < 250; i++)
		{
			if(i < 10)
			{
				f = "My-wife_03_00" + i + ".jpg";
			}
			else if(i < 100)
			{
				f = "My-wife_03_0" + i + ".jpg";
			}
			else
			{
				f = "My-wife_03_" + i + ".jpg";
			}
			try
			{

				//String url = "http://images.dmzj.com/w/我妻同学是我的老婆/第03卷/" + f;
				http.create("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", false);
				http.addCookie("", "1461836533").addCookie("Hm_lpvt_645dcc265dc58142b6dbfea748247f02", "1461836543");
				http.setRequestMethod("GET");
				InputStream is = http.connectStream();
				if(is != null)
				{
					FileUtil.writeFile("C:/Users/skey/Desktop/mh/03/" + f, is, true);
				}
				else
				{
					System.out.println(f + "读取失败");
				}
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(f);
			}
		}
		


		http.create("http://127.0.0.1:8888/CasServer/cookie.jsp?ticket=a92dfcdf-a84e-43ab-8383-f3916b379ef41440155191204", false);
		http.addCookie("JSESSIONID", "05E9FD37E514F5662636576B3B9118FF")
		.addCookie("a", "111")
		.addCookie("b", "222")
		.addForm("title", "mytest中文")
		.addForm("content", "!@#$%^&*()_+|{}[]:\";'<>?,./ 4")
		.addForm("foundtime", "2000-01-02");
		System.out.println(http.connect());
		/*
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
		*/
	}
}
