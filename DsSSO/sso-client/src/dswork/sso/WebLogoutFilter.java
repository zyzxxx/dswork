package dswork.sso;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebLogoutFilter implements Filter
{
	static Logger log = LoggerFactory.getLogger("dswork.sso.logout");

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		try
		{
			HttpSession session = request.getSession();
			session.setAttribute(WebFilter.LOGINER, "");// 只清空sso的session信息
			String jsoncallback  = request.getParameter("jsoncallback");
			PrintWriter out = response.getWriter();
			out.print(jsoncallback + "([])");
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
			ex.printStackTrace();
		}
		return;
	}

	public void init(FilterConfig config) throws ServletException
	{
	}

	public void destroy()
	{
	}
}
