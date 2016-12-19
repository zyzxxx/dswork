package common.auto;

@SuppressWarnings("serial")
public class AutoServlet extends javax.servlet.http.HttpServlet
{
	static
	{
		AutoTimerLucene pj = new AutoTimerLucene();
		pj.start();//启动程序
	}
}
