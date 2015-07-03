package dswork.cas.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dswork.cas.service.CasFactoryService;
import dswork.cas.service.TicketService;

public class SessionListener implements HttpSessionListener
{
	public final static String COOKIETICKET = "__CookieTicket__";
	public void sessionCreated(HttpSessionEvent event)
	{
	}

	public void sessionDestroyed(HttpSessionEvent event)
	{
		try
		{
			HttpSession session = event.getSession();
			String ticket = String.valueOf(session.getAttribute(SessionListener.COOKIETICKET));
			if(!ticket.equals("null") && ticket.length() > 0)
			{
				((CasFactoryService)dswork.spring.BeanFactory.getBean(CasFactoryService.class)).saveLogLogout(ticket, true, false);
				TicketService.removeSession(ticket);// 应该是超时
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
