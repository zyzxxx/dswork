import dswork.ee.MyTomcat;

public class Tomcat
{
	public static void main(String[] args) throws Exception
	{
		MyTomcat.class.newInstance().setPort(8080).setBaseDir("/WorkServer/TomcatEmbed").addWebapp().start();
	}
}
