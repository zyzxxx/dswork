package dswork.cs;

import java.io.File;
import java.io.FileFilter;

/**
 * 本地方式启动应用
 */
public class MyStart
{
	static
	{
		MyConfigLoad();
	}
	private static void MyConfigLoad()
	{
		try
		{
			String active = "";
			boolean isEnvironmentUtil = false;
			try
			{
				Class.forName("dswork.core.util.EnvironmentUtil");
				isEnvironmentUtil = true;
				active = dswork.core.util.EnvironmentUtil.getToString("dswork.active", "");
			}
			catch(Exception ee)
			{
			}
			String log4j2 = "/config/log4j2_local.xml";
			String dsworkconfig = "classpath:/config/config.properties";
			String spring = ",classpath*:/config/spring-*.xml";
			
			if(active.length() > 0)
			{
				String pathRoot = MyStart.class.getResource("/").getPath();
				System.out.println("Root=" + pathRoot);
				File devfiles = new File(pathRoot + "/config/" + active + "/");
				if(devfiles.isDirectory())
				{
					File usefiles = new File(pathRoot + "/config/");
					File[] files;
					StringBuilder v = new StringBuilder(256);
					java.util.Map<String, String> map = new java.util.HashMap<String, String>();
					String pathDev = "classpath*:/config/" + active + "/";
					String pathUse = "classpath*:/config/";

					files = devfiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("spring-")){return true;}return false;}});
					for(File f : files){map.put(f.getName(), "1");v.append(",").append(pathDev).append(f.getName());}
					files = usefiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("spring-")){return true;}return false;}});
					for(File f : files){if(map.get(f.getName()) == null){v.append(",").append(pathUse).append(f.getName());}}
					if(v.length() > 0) {spring = v.toString();}
					map.clear();
					v.setLength(0);
					
					if((new File(pathRoot + "/config/" + active + "/config.properties")).isFile())
					{
						dsworkconfig = "classpath:/config/" + active + "/config.properties";
						if(isEnvironmentUtil)
						{
							dswork.core.util.EnvironmentUtil.setSystemProperties("/config/" + active + "/config.properties");
						}
					}
					
					String log4jFile = (new File(pathRoot + "/config/" + active + "/log4j2_local.xml")).isFile() ? "/" + active: "";
					log4j2 = "/config" + log4jFile + "/log4j2_local.xml";
				}
			}
			if(isEnvironmentUtil)
			{
				String dsworkBasePackage = dswork.core.util.EnvironmentUtil.getToString("dswork.base-package", "");
				if(dsworkBasePackage.length() > 0)
				{
					System.setProperty("dswork.base-package", dsworkBasePackage);
					String[] basePackages = dsworkBasePackage.split(",");
					int i = 1;
					for(String p : basePackages)
					{
						if(p.length() > 0)
						{
							System.setProperty("dswork.p" + i, p);/*旧版本的spring，如4.0.9的mvc不支持多个扫描包的配置，有bug，这是兼容模式*/
							System.setProperty("dswork.m" + i, "/" + p.replace('.', '/'));
							i++;
						}
					}
				}
			}
			System.setProperty("log4jConfiguration", log4j2);
			System.setProperty("dsworkConfiguration", dsworkconfig);
			System.setProperty("contextConfigLocation", "classpath*:/dswork/config/spring/*.xml" + spring);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void MyLog4jLoad()
	{
		// log4j 1.x
		//org.apache.log4j.PropertyConfigurator.configure(System.getProperty("log4jConfiguration"));
		try
		{
			//log4j 2.x
			org.apache.logging.log4j.core.config.ConfigurationSource source;
			// 方法一
			//source = new ConfigurationSource(new java.io.FileInputStream("D:\\log4j2.xml"));
			// 方法二
			java.net.URL url = MyStart.class.getResource(System.getProperty("log4jConfiguration"));
			source = new org.apache.logging.log4j.core.config.ConfigurationSource(new java.io.FileInputStream(new java.io.File(url.getPath())), url);
			org.apache.logging.log4j.core.config.Configurator.initialize(null, source);
			System.out.println("Log4j load completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void MySpringLoad()
	{
		try
		{
			// ********初始化spring容器********
			org.springframework.context.support.GenericXmlApplicationContext context =  new org.springframework.context.support.GenericXmlApplicationContext();
			context.setValidating(false);
			context.load(System.getProperty("contextConfigLocation"));
			context.refresh();
			try
			{
				Class.forName("dswork.spring.BeanFactory");
				dswork.spring.BeanFactory.setApplicationContext(context);// 非web应用时，需要使用该方法设置spring容器
			}
			catch(Exception ee)
			{
			}
			System.out.println("Springframework ApplicationContext load completed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void waitForQuit()
	{
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