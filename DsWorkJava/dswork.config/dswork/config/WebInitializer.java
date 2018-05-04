package dswork.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import dswork.core.util.EnvironmentUtil;

public class WebInitializer implements dswork.web.MyWebInitializer
{

	@Override
	public void onStartup(ServletContext context) throws ServletException
	{
		String dsworkActive = EnvironmentUtil.getToString("dswork.active", "");
		if(dsworkActive.length() > 0)
		{
			context.setInitParameter("log4jConfiguration", "/WEB-INF/classes/config/" + dsworkActive + "/log4j2.xml");
			context.setInitParameter("contextConfigLocation", "/WEB-INF/classes/config/" + dsworkActive + "/applicationContext*.xml");
			context.setInitParameter("dsworkSSOConfiguration", "/WEB-INF/classes/config/" + dsworkActive + "/sso.properties");
		}
		else
		{
			context.setInitParameter("log4jConfiguration", "/WEB-INF/classes/config/log4j2.xml");
			context.setInitParameter("contextConfigLocation", "/WEB-INF/classes/config/applicationContext*.xml");
			context.setInitParameter("dsworkSSOConfiguration", "/WEB-INF/classes/config/sso.properties");
		}
		context.addListener("org.apache.logging.log4j.web.Log4jServletContextListener");
		
		try
		{
			Class.forName("org.springframework.web.util.IntrospectorCleanupListener", false, null);
			context.addListener("org.springframework.web.util.IntrospectorCleanupListener");
			context.addListener("org.springframework.web.context.ContextLoaderListener");
		}
		catch(Throwable c)
		{
		}
		
		javax.servlet.FilterRegistration.Dynamic encodingFilter = context.addFilter("encodingFilter", "org.springframework.web.filter.CharacterEncodingFilter");
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");// false指最优先加载

	}
}
