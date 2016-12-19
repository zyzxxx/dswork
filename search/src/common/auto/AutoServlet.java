package common.auto;

@SuppressWarnings("serial")
public class AutoServlet extends javax.servlet.http.HttpServlet
{
	static
	{
		common.auto.AutoTimerLucene.toStart();
	}
}
