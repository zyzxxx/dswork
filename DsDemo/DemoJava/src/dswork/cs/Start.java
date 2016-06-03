package dswork.cs;

/**
 * 本地方式启动应用
 */
public class Start
{
	public static void main(String[] args)
	{
		// ********初始化log4j配置********
		try
		{
			// log4j 1.x
			//org.apache.log4j.PropertyConfigurator.configure("/config/log4j.properties");
			
			//log4j 2.x
			org.apache.logging.log4j.core.config.ConfigurationSource source;
			// 方法一
			//source = new ConfigurationSource(new java.io.FileInputStream("D:\\log4j2.xml"));
			// 方法二
			java.net.URL url = Start.class.getResource("/config/log4j2_local.xml");
			System.out.println(url.getPath());
			source = new org.apache.logging.log4j.core.config.ConfigurationSource(new java.io.FileInputStream(new java.io.File(url.getPath())), url);
			org.apache.logging.log4j.core.config.Configurator.initialize(null, source);
			
			System.out.println("Log4j load completed");
			
			// ********初始化spring容器********
			org.springframework.context.support.GenericXmlApplicationContext context =  new org.springframework.context.support.GenericXmlApplicationContext();
			context.setValidating(false);
			context.load("classpath*:/config/applicationContext*.xml");
			context.refresh();
			dswork.spring.BeanFactory.setApplicationContext(context);// 非web应用时，需要使用该方法设置spring容器
			System.out.println("Springframework ApplicationContext load completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		// 执行需要的应用程序
		ExecuteProject.execute(args);

		
		
		// 以下代码用于防止main进程执行后结束，可用于捕获监听输入，输入quit或exit可退出
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
