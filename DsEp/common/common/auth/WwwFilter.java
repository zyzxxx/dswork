package common.auth;

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

import common.auth.Auth;
import common.auth.AuthUtil;

/**
 * 仅用于集成dswork.sso单点登录认证过滤器
 * @author skey
 */
public class WwwFilter implements Filter
{
	public void init(FilterConfig config) throws ServletException
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
			// 取得当前用户账号
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
						auth.setLogintype(Auth.ADMIN).setId(m.getId()).setAccount(userAccount).setName(m.getName());
						req.getSession().setAttribute(AuthUtil.SessionName_LoginUser, auth);
						chain.doFilter(requestWrapper, responseWraper);
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
}
