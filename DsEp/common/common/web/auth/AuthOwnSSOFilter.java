package common.web.auth;

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

public class AuthOwnSSOFilter implements Filter
{
	public AuthOwnSSOFilter()
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
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
			AuthOwn authOwn = AuthOwnUtil.getUser(req, res);
			if(authOwn != null && !authOwn.getAccount().equals(userAccount))
			{
				authOwn = null;
			}
			if(authOwn == null)
			{
				try
				{
					dswork.sso.model.IUser m = dswork.sso.AuthFactory.getUser(userAccount);
					if(m.getStatus() != 0)
					{
						AuthOwnUtil.setUser(req, res, m.getId().toString(), m.getAccount(), m.getName(), "admin");
						chain.doFilter(requestWrapper, responseWraper);
						return;
					}
				}
				catch(Exception xx)
				{
				}
			}
			else
			{
				chain.doFilter(requestWrapper, responseWraper);
			}
		}
		catch(Exception e)
		{
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException
	{
	}
}
