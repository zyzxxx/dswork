package common.web.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import common.auth.Auth;
import common.auth.AuthUtil;

@WebFilter("/AuthOwnSSOFilter")
public class AuthOwnSSOFilter implements Filter
{
	public AuthOwnSSOFilter()
	{
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
		try
		{
			String userAccount = dswork.sso.WebFilter.getAccount(req.getSession());
			if(userAccount.length() == 0)
			{
				return;
			}
			Auth auth = AuthUtil.getLoginUser(req);
			if(auth != null && !auth.getAccount().equals(userAccount))
			{
				auth = null;
			}
			if(auth == null)
			{
				auth = new Auth();
				try
				{
					dswork.sso.model.IUser m = dswork.sso.AuthFactory.getUser(userAccount);
					if(m.getStatus() != 0)
					{
						AuthOwnUtil.setUser(req, m.getId().toString(), m.getAccount(), m.getName(), "admin" + m.getAccount());
						chain.doFilter(requestWrapper, responseWraper);
						return;
					}
				}
				catch(Exception xx)
				{
				}
			}
		}
		catch(Exception e)
		{
		}
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
	}
}
