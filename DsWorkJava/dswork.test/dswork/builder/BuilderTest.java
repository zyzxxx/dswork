package dswork.builder;

public class BuilderTest
{
	public static void main(String[] args)
	{
		java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("");
		// if(url == null)
		// {
		// 	url = Builder.class.getProtectionDomain().getCodeSource().getLocation();
		// }
		String loadpath = url == null ? System.getProperty("user.dir") + "/" : url.getPath();
		loadpath = String.valueOf(loadpath).replace('\\', '/').replace("//", "/");
		System.out.println(loadpath);
		String templatepath, configPath;
		// String configPath = Builder.getLocation("builder.xml");
		if(args.length > 0)
		{
			configPath = loadpath + args[0].replace('\\', '/').replace("//", "/");
			templatepath = loadpath + "template";
			if(args.length > 1)
			{
				templatepath = loadpath + args[1].replace('\\', '/').replace("//", "/");
			}
		}
		else
		{
			configPath = loadpath + "your.xml";
			templatepath = loadpath + "template";
		}
		Builder.build(templatepath, (new BuilderParser().parse(Builder.readTextFile(configPath))));
	}
}
