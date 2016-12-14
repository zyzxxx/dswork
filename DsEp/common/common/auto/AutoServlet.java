package common.auto;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class AutoServlet extends HttpServlet
{
	static
	{
		AutoTimerExecute.toStart();
		AutoThreadExecute.toStart();
	}
}
