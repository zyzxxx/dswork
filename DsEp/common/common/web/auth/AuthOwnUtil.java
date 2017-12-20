package common.web.auth;

import javax.servlet.http.HttpServletRequest;

public class AuthOwnUtil
{
	private static final String SESSION_WWW_LOGIN_USER = "WWW_LOGIN_USER";

	public static void setUser(HttpServletRequest request, String id, String account, String name, String own)
	{
		request.getSession().setAttribute(SESSION_WWW_LOGIN_USER, new AuthOwn(id, account, name, own));
	}

	public static AuthOwn getUser(HttpServletRequest request)
	{
		return (AuthOwn)request.getSession().getAttribute(SESSION_WWW_LOGIN_USER);
	}

	public static void clearUser(HttpServletRequest request)
	{
		request.getSession().removeAttribute(SESSION_WWW_LOGIN_USER);
	}
}
