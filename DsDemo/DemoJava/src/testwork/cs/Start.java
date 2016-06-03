package testwork.cs;

import java.util.HashMap;
import java.util.List;
//log4j 1.x
//import org.apache.log4j.PropertyConfigurator;

//log4j 2.x
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.apache.logging.log4j.core.config.ConfigurationSource;  
import org.apache.logging.log4j.core.config.Configurator;  

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import dswork.spring.BeanFactory;
import testwork.model.Demo;
import testwork.service.ManageDemoService;

/**
 * cs 系统启动
 */
public class Start
{
	public static void main(String[] args)
	{
		// log4j 1.x
		//PropertyConfigurator.configure("bin/config/log4j.properties");
		
		try
		{
			//log4j 2.x
			ConfigurationSource source;
			// 方法一
			//source = new ConfigurationSource(new java.io.FileInputStream("D:\\log4j2.xml"));
			// 方法二
			java.net.URL url = Start.class.getResource("/config/log4j2_local.xml");
			System.out.println(url.getPath());
			source = new ConfigurationSource(new java.io.FileInputStream(new java.io.File(url.getPath())), url);
			Configurator.initialize(null, source);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
//		ApplicationContext context = new FileSystemXmlApplicationContext
//		(
//		    new String[]{
//		    	"/config/applicationContext-datasource.xml",
//		    	"/config/applicationContext-mybatis.xml",
//		    	"/config/applicationContext-hibernate.xml",
//		    	"/config/applicationContext-project.xml"
//		    }
//		 );
		org.springframework.context.support.GenericXmlApplicationContext context =  new org.springframework.context.support.GenericXmlApplicationContext();
		context.setValidating(false);
		context.load("classpath*:/config/applicationContext*.xml");
		context.refresh();
		System.out.println("context is loaded;");
		BeanFactory.setApplicationContext(context);// 非web应用时，需要使用该方法设置spring容器
		ManageDemoService service = (ManageDemoService)BeanFactory.getBean("manageDemoService");
		Demo demo = new Demo();
		demo.setContent("Content");
		demo.setTitle("Title");
		demo.setFoundtime("2010-01-01");
		service.save(demo);
		List<Demo> csList = service.queryList(new HashMap<Object, Object>());
		System.out.println("Total:"+csList.size());
	    //((FileSystemXmlApplicationContext)context).close();

	    try
		{
	    	java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
	    	for(;;)
	    	{
		    	String msg = reader.readLine();
		    	if(msg != null)
		    	{
		    		if(msg.equals("quit") || msg.equals("exit"))
		    		{
		    			break;
		    		}
		    	}
	    	}
		}
		catch(Exception e)
		{
			// TODO: handle exception
		}
	}
}
