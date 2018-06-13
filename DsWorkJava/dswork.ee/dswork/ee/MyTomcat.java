package dswork.ee;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class MyTomcat
{
	private Tomcat tomcat = new Tomcat();
	private int port = 8080;
	private String hostname = "localhost";
	public MyTomcat()
	{
	}
	public MyTomcat(int port)
	{
		this.port = port;
	}
	public MyTomcat(String hostname, int port)
	{
		this.hostname = hostname;
		this.port = port;
	}
	private static String loaderPath = "";
	private static java.io.File loaderFile = null;
	static
	{
		java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String loadpath = url == null ? System.getProperty("user.dir") + "/" : url.getPath();
		loadpath = String.valueOf(loadpath).replace('\\', '/').replace("//", "/");
		if(loadpath.endsWith("/"))
		{
			loadpath = loadpath.substring(0, loadpath.length() - 1);
		}
		loaderPath = loadpath;
		loaderFile = new java.io.File(loaderPath);
		System.out.println(loaderPath);
	}
	
	public static String getLoaderPath()
	{
		return loaderPath;
	}
	
	public static String getProjectPath()
	{
		String path;
		if(loaderPath.endsWith("/WEB-INF/classes"))
		{
			path = loaderFile.getParentFile().getParentFile().getParentFile().getPath().replace('\\', '/');
		}
		else if(loaderPath.endsWith("/bin"))
		{
			path = loaderFile.getParentFile().getPath().replace('\\', '/');
		}
		else
		{
			path = loaderFile.getPath().replace('\\', '/');
		}
		return path;
	}
	
	public static String getProjectName()
	{
		File f = new File(getProjectPath());
		System.out.println("getProjectName=" + f.getName());
		return f.getName();
	}
	
	public static String getWebPath()
	{
		if(!loaderPath.endsWith("/WEB-INF/classes"))
		{
			System.err.println("请确保你的项目编译输出目录为\"/WEB-INF/classes\"");
		}
		File f = new File(getProjectPath());
		String path = f.getPath().replace('\\', '/') + "/web";
		if(!(new java.io.File(path).isDirectory()))
		{
			path = f.getPath().replace('\\', '/') + "/WebContent";
			if(!(new java.io.File(path).isDirectory()))
			{
				path = f.getPath().replace('\\', '/') + "/WebRoot";
				if(!(new java.io.File(path).isDirectory()))
				{
					path = f.getPath().replace('\\', '/');
				}
			}
		}
		System.out.println("getWebPath=" + path);
		return path;
	}
	
	public MyTomcat addWebapp() throws Exception
	{
		tomcat.addWebapp("/" + getProjectName(), getWebPath());
		return this;
	}
	
	public MyTomcat addWebapp(String projectName) throws Exception
	{
		tomcat.addWebapp(projectName, getWebPath());
		return this;
	}
	
	public MyTomcat addWebapp(String projectName, String projectPath) throws Exception
	{
		tomcat.addWebapp(projectName, projectPath);
		return this;
	}
	
	public MyTomcat start() throws Exception
	{
		tomcat.setBaseDir("/WorkServer/TomcatBase");
		tomcat.setHostname(hostname);
		tomcat.setPort(port);
		tomcat.start();
		tomcat.getServer().await();
		return this;
	}
}
