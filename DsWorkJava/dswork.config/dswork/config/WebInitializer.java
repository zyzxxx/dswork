package dswork.config;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;

import dswork.core.util.EnvironmentUtil;

public class WebInitializer implements dswork.web.MyWebInitializer
{
	@Override
	public void onStartup(ServletContext context) throws ServletException
	{
		String dsworkActive = EnvironmentUtil.getToString("dswork.active", "");
		String log4j2 = "/WEB-INF/classes/config/log4j2.xml";
		if(dsworkActive.length() > 0)
		{
			
			String webRoot = context.getRealPath("/");
			String springTest = "/WEB-INF/classes/config/" + dsworkActive + "/";
			File file = new File(webRoot + springTest);
			if(file.isDirectory())
			{
				String springRoot = "/WEB-INF/classes/config/";
				File[] files = file.listFiles(new java.io.FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("applicationContext")){return true;}return false;}});
				java.util.Map<String, String> map = new java.util.HashMap<String, String>();
				String paths = "";
				for(File f : files)
				{
					map.put(f.getName(), "1");
					paths += (paths.length()==0 ? "" : ",") + springTest + f.getName();
				}
				
				file = new File(webRoot + springRoot);
				files = file.listFiles(new java.io.FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("applicationContext")){return true;}return false;}});
				for(File f : files)
				{
					if(map.get(f.getName()) == null)
					{
						paths += (paths.length()==0 ? "" : ",") + springRoot + f.getName();
					}
				}
				context.setInitParameter("contextConfigLocation", paths);
				boolean configFile = (new File(webRoot + "/WEB-INF/classes/config/" + dsworkActive + "/config.properties")).isFile();
				if(configFile)
				{
					EnvironmentUtil.setSystemProperties("/config/" + dsworkActive + "/config.properties");
					System.out.println("config=" + "/config/" + dsworkActive + "/config.properties");
				}
				else
				{
					System.out.println("config=" + "/config/config.properties");
				}
				String log4jFile = (new File(webRoot + "/WEB-INF/classes/config/" + dsworkActive + "/log4j2.xml")).isFile() ? "/" + dsworkActive: "";
				// context.setInitParameter("log4jConfiguration", "/WEB-INF/classes/config" + log4jFile + "/log4j2.xml");
				log4j2 = "/WEB-INF/classes/config" + log4jFile + "/log4j2.xml";
				String ssoFile = (new File(webRoot + "/WEB-INF/classes/config/" + dsworkActive + "/sso.properties")).isFile() ? "/" + dsworkActive : "";
				context.setInitParameter("dsworkSSOConfiguration", "/config" + ssoFile + "/sso.properties");

				System.out.println("contextConfigLocation=" + context.getInitParameter("contextConfigLocation"));
				System.out.println("log4jConfiguration=" + context.getInitParameter("log4jConfiguration"));
				System.out.println("dsworkSSOConfiguration=" + context.getInitParameter("dsworkSSOConfiguration"));
			}
			else
			{
				context.setInitParameter("contextConfigLocation", "/WEB-INF/classes/config/applicationContext*.xml");
				context.setInitParameter("dsworkSSOConfiguration", "/config/sso.properties");
			}
		}
		else
		{
			// context.setInitParameter("log4jConfiguration", "/WEB-INF/classes/config/log4j2.xml");
			context.setInitParameter("contextConfigLocation", "/WEB-INF/classes/config/applicationContext*.xml");
			context.setInitParameter("dsworkSSOConfiguration", "/config/sso.properties");
		}
		try
		{
			org.apache.logging.log4j.core.LoggerContext c = (org.apache.logging.log4j.core.LoggerContext)LogManager.getContext(false);
			c.setConfigLocation(context.getResource(log4j2).toURI());
			c.reconfigure();
		}
		catch(Exception e)
		{
		}
		
		context.addListener("org.springframework.web.util.IntrospectorCleanupListener");
		context.addListener("org.springframework.web.context.ContextLoaderListener");
		javax.servlet.FilterRegistration.Dynamic encodingFilter = context.addFilter("encodingFilter", "org.springframework.web.filter.CharacterEncodingFilter");
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");// false指最优先加载
		
		try
		{
			javax.servlet.ServletRegistration.Dynamic authcodeServlet = context.addServlet("AuthcodeServlet", "dswork.web.MyAuthCodeServlet");
			authcodeServlet.addMapping("/authcode");
		}
		catch(Exception e)
		{
		}
	}
}
