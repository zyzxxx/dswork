import dswork.ee.MyTomcat;

public class Tomcat
{
	public static void main(String[] args) throws Exception
	{
		MyTomcat.class.newInstance().setPort(8888).setBaseDir("/WorkServer/TomcatEmbed").addWebapp().addWebapp("/portal", MyTomcat.getProjectPath() + "/../portal/web").start();
	}
}
