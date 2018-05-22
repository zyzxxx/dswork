package dswork.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Builder
{
	static
	{
		String[] drivers = {
			"oracle.jdbc.driver.OracleDriver"
			,"oracle.jdbc.OracleDriver"
			,"com.mysql.jc.jdbc.Driver"// mysql j 6.x for jdk8
			,"com.mysql.jdbc.Driver"// mysql j 5.x及之前
			,"com.microsoft.jdbc.sqlserver.SQLServerDriver"// MS driver for Sql Server 2000
			,"com.microsoft.sqlserver.jdbc.SQLServerDriver"// MS driver for Sql Server 2005
			//,"com.ibm.db2.jcc.DB2Driver"// 增加了DB2支持
			//,"org.sqlite.JDBC"// 增加了sqlite支持
			//,"org.postgresql.Driver"
			//,"com.sybase.jdbc2.jdbc.SybDriver"
			//,"net.sourceforge.jtds.jdbc.Driver"// sqlServer
			//,"weblogic.jdbc.sqlserver.SQLServerDriver"// sqlServer
			//,"com.informix.jdbc.IfxDriver"
			//,"org.apache.derby.jdbc.ClientDriver"
			//,"org.apache.derby.jdbc.EmbeddedDriver"
			//,"org.hsqldb.jdbcDriver"
			//,"org.h2.Driver"
			//,"dm.jdbc.driver.DmDriver"//达梦
			//,"com.gbase.jdbc.Driver"//gbase
		};
		for(String driver : drivers)
		{
			try
			{
				Class.forName(driver);
			}
			catch(ClassNotFoundException e)
			{
			}
		}
	}
	
	public static void build(BuilderConfig config)
	{
		Dao dao = null;
		try
		{
			Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
			conf.setDefaultEncoding(config.charset.text);
			conf.setDirectoryForTemplateLoading(new File(config.templates.path));
			String url = config.builds.url;
			if(url.startsWith("jdbc:mysql"))
			{
				dao = DaoMySql.class.newInstance();
			}
			else if(url.startsWith("jdbc:sqlserver"))
			{
				dao = DaoMSSQL.class.newInstance();
			}
			else if(url.startsWith("jdbc:oracle"))
			{
				//dao = DaoMySql.class.newInstance();
			}
			if(dao != null)
			{
				dao.connect(url);
				String errMsg = "";
				System.out.println("==============================开始执行代码生成操作=============================");
				for(BuilderConfig.Build m : config.builds.build)
				{
					System.out.println("生成build模块：表格：" + m.table + "，模型：" + m.model);
					Table table = dao.query(m.table);
					Map<String, Object> param = new HashMap<String, Object>();
					param.putAll(config.params);
					// param.put("params", config.params);
					// param.put("developer", config.params.get("developer"));
					// param.put("frame", config.params.get("frame"));
					String namespace = m.namespace;
					if(namespace.startsWith("/"))
					{
						namespace = namespace.substring(1, namespace.length() - 1);
					}
					param.put("namespace", namespace.replace('/', '.'));
					param.put("model", m.model);
					param.put("module", m.module);
					param.put("columnList", table.column);
					param.put("table", table);
					
					for(BuilderConfig.Template tpl : config.templates.template)
					{
						if(!tpl.name.equals(config.builds.templatename))
						{
							continue;
						}
						if(tpl.comment != null && !"".equals(tpl.comment) && !m.comment.equals(tpl.comment))
						{
							continue;
						}
						String viewpath = tpl.viewpath;
						String path = config.builds.rootpath + tpl.path
							.replace("{src}", config.builds.src)
							.replace("{web}", config.builds.web)
							.replace("{model}", m.model)
							.replace("{module}", m.module)
							.replace("{namespace}", m.namespace)
							.replace("//", "/");
						String x = "    " + printf(tpl.id, config.templates.max) + "生成";
						try
						{
							Template template = conf.getTemplate(viewpath);
							File file = createNewFile(path);
							template.process(param, new FileWriter(file));
							System.out.println(x + "成功：" + path);
						}
						catch(Exception e)
						{
							
							System.err.println(x + "失败：" + path);
							errMsg += x + "失败：" + path + "，" + e.getMessage() + "\n";
						}
					}
				}
				System.out.println("==============================结束执行代码生成操作=============================");
				if(errMsg.length() > 0)
				{
					System.err.print(errMsg);
					System.out.println("==============================================================================");
				}
			}
			else
			{
				System.out.println("生成失败：目前系统只支持mysql");
			}
		}
		catch(Exception e)
		{
			System.out.println("生成失败：" + e.getMessage());
		}
		finally
		{
			if(dao != null)
			{
				dao.close();
			}
		}
	}
	
	private static String printf(String x, int max)
	{
		if(x.length() < max)
		{
			x = "                    ".substring(x.length(), max) + x;
		}
		return x + " ";
	}

	private static File createNewFile(String path) throws IOException
	{
		File file = new File(path);
		if(file.exists())
		{
			return file;
		}
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}

	public static String readTextFile(String filePath)
	{
		File file = new File(filePath);
		StringBuilder sb = new StringBuilder();
		if(file.isFile())
		{
			try
			{
				FileInputStream fin = new FileInputStream(file);
				BufferedReader in = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
				String line = "";
				while((line = in.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				in.close();
				fin.close();
			}
			catch(Exception e)
			{
			}
		}
		return sb.toString();
	}

	public static String getLocation(String name)
	{
		String location = Builder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(location.endsWith(".jar"))
		{
			location = location.substring(0, location.lastIndexOf("/") + 1);
		}
		return location + name;
	}
	
	public static String lowerCamel(String name)
	{
		String[] ss = name.toLowerCase().split("_");
		String str = ss[0];
		for(int i = 1; i < ss.length; i++)
		{
			str += ss[i].substring(0, 1).toUpperCase() + ss[i].substring(1);
		}
		return str;
	}

	public static String upperCamel(String name)
	{
		String[] ss = name.toLowerCase().split("_");
		String str = "";
		for(String s : ss)
		{
			str += s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return str;
	}
	
	public static String getToPath(String path)
	{
		path = path.replace('.', '/').replace('\\', '/').replace("//", "/");
		if(path.endsWith("/"))
		{
			path = path.substring(0, path.length() - 1);
		}
		if (path.length() > 0 && !path.startsWith("/"))
		{
			path = "/" + path;
		}
		return path;
	}
	
	public static void main(String[] args)
	{
		String configPath = Builder.getLocation("builder.xml");
		if(args.length > 0)
		{
			configPath = System.getProperty("user.dir") + "/" + args[0];
			configPath = configPath.replace("//", "/");
		}
		System.out.println(configPath);
		Builder.build((new BuilderParser().parse(Builder.readTextFile(configPath))));
	}
}
