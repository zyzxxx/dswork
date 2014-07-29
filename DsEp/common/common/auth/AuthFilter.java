package common.auth;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 认证过滤器
 * @author skey
 */
public class AuthFilter implements Filter
{
	public void init(FilterConfig config) throws ServletException
	{
		try
		{
		}
		catch(Exception e)
		{
		}
		System.out.println("AuthFilter initialization");
	}

	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(req);
		HttpServletResponseWrapper responseWraper = new HttpServletResponseWrapper(res);
		// 取得当前路径
		String currentUrl = req.getRequestURI().replaceAll(req.getContextPath(), "");
		try
		{
			Auth model = AuthLogin.getLoginUser(req, res);
			// 没有登录
			if(model == null || model.getAccount() == null)
			{
				res.sendRedirect(req.getContextPath() + "/login.html");
				return;
			}
			// 超级管理员
			if(!model.isSuperAdmin() && currentUrl.startsWith("/manage/info"))
			{
				res.sendRedirect(req.getContextPath() + "/login.html");
				return;
			}
			// 管理员
			if(!model.isAdmin() && currentUrl.startsWith("/manage/user"))
			{
				res.sendRedirect(req.getContextPath() + "/login.html");
				return;
			}
			if(!model.isUser() && currentUrl.startsWith("/manage/msg"))
			{
				res.sendRedirect(req.getContextPath() + "/login.html");
				return;
			}
			chain.doFilter(requestWrapper, responseWraper);
		}
		catch(Exception e)
		{
		}
	}
}
