package testwork.cs;

import java.util.HashMap;
import java.util.List;

//import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import testwork.model.Demo;
import testwork.service.demo.DemoService;

/**
 * cs 系统启动
 */
public class Start
{
	public static void main(String[] args)
	{
		//PropertyConfigurator.configure("bin/config/log4j.properties");
		ApplicationContext context=new FileSystemXmlApplicationContext  
		( 
		    new String[] {  
		    	"/src/config/applicationContext-datasource.xml",
		    	"/src/config/applicationContext-mybatis.xml",
		    	"/src/config/applicationContext-project.xml"	    	
		    }  
		 );
		DemoService service = (DemoService)context.getBean("demoService");
		Demo demo = new Demo();
		demo.setContent("Content");
		demo.setTitle("Title");
		demo.setFoundtime("2010-01-01");
		service.save(demo);
		List<Demo> csList = service.queryList(new HashMap<Object, Object>());
		System.out.println("Total:"+csList.size());
	    ((FileSystemXmlApplicationContext)context).close();      
	}
}
