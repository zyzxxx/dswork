package common.automatic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AutoListener implements ServletContextListener
{
	public AutoListener()
	{
	}

	public void contextInitialized(ServletContextEvent arg0)
	{
		AutomaticExecuteXzsp.toStart();
	}

	public void contextDestroyed(ServletContextEvent arg0)
	{
	}
}
