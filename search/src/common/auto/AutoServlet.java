package common.auto;

import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(name="autoload", loadOnStartup=1, urlPatterns={"/AutoServlet"})
public class AutoServlet extends javax.servlet.http.HttpServlet
{
	static
	{
		AutoTimerLucene pj = new AutoTimerLucene();
		pj.start();// 启动程序
	}
}
