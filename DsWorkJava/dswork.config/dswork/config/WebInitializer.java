package dswork.config;

import java.io.File;
import java.io.FileFilter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;

import dswork.core.util.EnvironmentUtil;

public class WebInitializer implements dswork.web.MyWebInitializer
{
	@Override
	public void onStartup(ServletContext context) throws ServletException
	{
		String active = EnvironmentUtil.getToString("dswork.active", "");
		String log4j2 = "/WEB-INF/classes/config/log4j2.xml";
		String dswork = "classpath*:/config/config.properties";
		String dsworkSSO = "/config/sso.properties";
		
		String spring = ",/WEB-INF/classes/config/spring-*.xml";
		String springmvc = ",classpath*:/config/springmvc-*.xml";
		
		if(active.length() > 0)
		{
			String pathWeb = context.getRealPath("/");
			String pathDev = "/WEB-INF/classes/config/" + active + "/";
			File devfiles = new File(pathWeb + pathDev);
			if(devfiles.isDirectory())
			{
				String pathUse = "/WEB-INF/classes/config/";
				File usefiles = new File(pathWeb + pathUse);
				File[] files;
				StringBuilder v = new StringBuilder(256);
				java.util.Map<String, String> map = new java.util.HashMap<String, String>();
				
				files = devfiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("spring-")){return true;}return false;}});
				for(File f : files){map.put(f.getName(), "1");v.append(",").append(pathDev).append(f.getName());}
				files = usefiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("spring-")){return true;}return false;}});
				for(File f : files){if(map.get(f.getName()) == null){v.append(",").append(pathUse).append(f.getName());}}
				if(v.length() > 0) {spring = v.toString();}
				map.clear();
				v.setLength(0);

				files = devfiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("springmvc-")){return true;}return false;}});
				for(File f : files){map.put(f.getName(), "1");v.append(",").append(pathDev).append(f.getName());}
				files = usefiles.listFiles(new FileFilter(){public boolean accept(File f){if(f.isFile() && f.getName().startsWith("springmvc-")){return true;}return false;}});
				for(File f : files){if(map.get(f.getName()) == null){v.append(",").append(pathUse).append(f.getName());}}
				if(v.length() > 0) {springmvc = v.toString();}
				map.clear();
				v.setLength(0);
				
				if((new File(pathWeb + "/WEB-INF/classes/config/" + active + "/config.properties")).isFile())
				{
					dswork = "classpath*:/config/" + active + "/config.properties";
					EnvironmentUtil.setSystemProperties("/config/" + active + "/config.properties");
				}
				
				String log4jFile = (new File(pathWeb + "/WEB-INF/classes/config/" + active + "/log4j2.xml")).isFile() ? "/" + active: "";
				log4j2 = "/WEB-INF/classes/config" + log4jFile + "/log4j2.xml";
				
				String ssoFile = (new File(pathWeb + "/WEB-INF/classes/config/" + active + "/sso.properties")).isFile() ? "/" + active : "";
				dsworkSSO = "/config" + ssoFile + "/sso.properties";
				
				System.out.println("dsworkConfiguration=" + dswork);
				System.out.println("dsworkSSOConfiguration=" + dsworkSSO);
				System.out.println("contextConfigLocation=" + spring);
			}
		}

		String jdbcDialect = EnvironmentUtil.getToString("jdbc.dialect", "");
		String dsworkDialect = "mysql";
		String mybatisDialect = "dswork.core.mybatis.dialect.LimitOffsetDialect";
		String hibernateDialect = "";
		if(jdbcDialect.length() > 0)
		{
			if("mysql".equals(jdbcDialect) || "gbase".equals(jdbcDialect))
			{
			}
			else if("oracle".equals(jdbcDialect))
			{
				dsworkDialect = "null";
				mybatisDialect = "dswork.core.mybatis.dialect.OracleDialect";
				hibernateDialect = "org.hibernate.dialect.OracleDialect";
			}
			else if("db2".equals(jdbcDialect))
			{
				dsworkDialect = "null";
				mybatisDialect = "dswork.core.mybatis.dialect.DB2Dialect";
				hibernateDialect = "org.hibernate.dialect.DB2Dialect";
			}
			else if("sqlite".equals(jdbcDialect))
			{
				dsworkDialect = "sqlite";
				hibernateDialect = "org.hibernate.dialect.SQLiteDialect";
			}
			else if(jdbcDialect.startsWith("mssql"))
			{
				dsworkDialect = "mssql";
				if("mssql2000".equals(jdbcDialect))
				{
					mybatisDialect = "dswork.core.mybatis.dialect.SQLServer2000Dialect";
					hibernateDialect = "org.hibernate.dialect.SQLServer2000Dialect";
				}
				else if("mssql2005".equals(jdbcDialect))
				{
					mybatisDialect = "dswork.core.mybatis.dialect.SQLServer2005Dialect";
					hibernateDialect = "org.hibernate.dialect.SQLServer2005Dialect";
				}
				else if("mssql2008".equals(jdbcDialect))
				{
					mybatisDialect = "dswork.core.mybatis.dialect.SQLServer2008Dialect";
					hibernateDialect = "org.hibernate.dialect.SQLServer2008Dialect";
				}
				else if("mssql2012".equals(jdbcDialect))
				{
					mybatisDialect = "dswork.core.mybatis.dialect.SQLServer2012Dialect";
					hibernateDialect = "org.hibernate.dialect.SQLServer2012Dialect";
				}
				else
				{
					mybatisDialect = "dswork.core.mybatis.dialect.SQLServerDialect";
					hibernateDialect = "org.hibernate.dialect.SQLServerDialect";
				}
			}
			else
			{
				dsworkDialect = "null";
			}
		}
		context.setInitParameter("dswork.dialect", dsworkDialect);
		context.setInitParameter("jdbc.dialect.mybatis", mybatisDialect);
		context.setInitParameter("jdbc.dialect.hibernate", hibernateDialect);
		
		String dsworkBasePackage = EnvironmentUtil.getToString("dswork.base-package", "");
		if(dsworkBasePackage.length() > 0)
		{
			context.setInitParameter("dswork.base-package", dsworkBasePackage);
			String[] basePackages = dsworkBasePackage.split(",");
			int i = 1;
			for(String p : basePackages)
			{
				if(p.length() > 0)
				{
					context.setInitParameter("dswork.p" + i, p);/*旧版本的spring，如4.0.9的mvc不支持多个扫描包的配置，有bug，这是兼容模式*/
					context.setInitParameter("dswork.m" + i, "/" + p.replace('.', '/'));
					i++;
				}
			}
		}
		context.setInitParameter("dsworkConfiguration", dswork);
		context.setInitParameter("dsworkSSOConfiguration", dsworkSSO);
		context.setInitParameter("contextConfigLocation", "classpath*:/dswork/config/spring/*.xml" + spring);
		try
		{
			if((new File(context.getRealPath("/") + log4j2)).isFile())
			{
				org.apache.logging.log4j.core.LoggerContext c = (org.apache.logging.log4j.core.LoggerContext)LogManager.getContext(false);
				c.setConfigLocation(context.getResource(log4j2).toURI());
				c.reconfigure();
			}
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

		javax.servlet.ServletRegistration.Dynamic springmvcServlet = context.addServlet("springmvcServlet", "org.springframework.web.servlet.DispatcherServlet");
		springmvcServlet.setLoadOnStartup(1);
		springmvcServlet.setInitParameter("contextConfigLocation", "classpath*:/dswork/config/mvc/springmvc-servlet.xml" + springmvc);
		springmvcServlet.addMapping("*.htm");
		
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
// context.setInitParameter("log4jConfiguration", "/WEB-INF/classes/config/log4j2.xml");