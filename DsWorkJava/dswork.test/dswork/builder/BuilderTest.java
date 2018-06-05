package dswork.builder;

public class BuilderTest
{
	public static void main(String[] args)
	{
		String configPath = Builder.getLocation("your.xml");
		System.out.println(configPath);
		Builder.build((new BuilderParser().parse(Builder.readTextFile(configPath))));
	}
}
