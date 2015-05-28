package auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import auth.action.AuthLoginAction;

public class AuthFilter implements Filter 
{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException 
	{
		try
		{
		}
		catch(Exception e)
		{
		}
		System.out.println("AuthFilter initialization");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(req);
		HttpServletResponseWrapper responseWraper = new HttpServletResponseWrapper(res);
		//String currentUrl = req.getRequestURI().replaceAll(req.getContextPath(), "");// 取得当前路径
		try
		{
//			Auth model = AuthLogin.getLoginUser(req, res);
//			// 没有登录
//			if(model == null || model.getAccount() == null)
//			{
//				if("XMLHttpRequest".equals(String.valueOf(req.getHeader("X-Requested-With"))))
//				{
//					res.getWriter().print("0:登录超时！");
//					return;
//				}
//				res.sendRedirect(req.getContextPath() + "/login.html");
//				return;
//			}
			Auth model = (Auth) req.getSession().getAttribute(AuthLoginAction.SessionName_LoginUser);
			//没有登录
			if (model == null || model.getAccount() == null)
			{
				res.sendRedirect(req.getContextPath() + "/login.jsp");
				return;
			}
			chain.doFilter(requestWrapper, responseWraper);
		}
		catch(Exception e)
		{
		}
	}

	@Override
	public void destroy() 
	{
	}
}
