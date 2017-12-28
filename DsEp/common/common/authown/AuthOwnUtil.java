package common.authown;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dswork.core.util.EncryptUtil;
import dswork.web.MyCookie;

public class AuthOwnUtil
{
	private static final String WEB_AUTH_COOKIE = "WEB_AUTH";
	private static final String WEB_AUTH_SESSION = "WEB_AUTH_SESSION";

	public static void login(HttpServletRequest request, HttpServletResponse response, String id, String account, String name, String own)
	{
		MyCookie cookie = new MyCookie(request, response);
		String value = id + "&" + account + "&" + name + "&" + own;
		cookie.addCookie(WEB_AUTH_COOKIE, EncryptUtil.encodeDes(value, "own"));
	}
	public static void logout(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		cookie.delCookie(WEB_AUTH_COOKIE);
	}
	
	public static AuthOwn getUser(HttpServletRequest request)
	{
		String s = (String) request.getSession().getAttribute(WEB_AUTH_SESSION);
		if(s == null)
		{
			return null;
		}
		String[] ss = s.split("&", -1);
		if(ss.length == 4)
		{
			return new AuthOwn(ss[0], ss[1], ss[2], ss[3]);
		}
		return null;
	}

	protected static AuthOwn getUserCookie(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		String s = cookie.getValue(WEB_AUTH_COOKIE);
		if(s == null)
		{
			return null;
		}
		s = EncryptUtil.decodeDes(s, "own");
		String[] ss = s.split("&", -1);
		if(ss.length == 4)
		{
			return new AuthOwn(ss[0], ss[1], ss[2], ss[3]);
		}
		return null;
	}
	protected static void setUser(HttpServletRequest request, String id, String account, String name, String own)
	{
		String value = id + "&" + account + "&" + name + "&" + own;
		request.getSession().setAttribute(WEB_AUTH_SESSION, value);
	}
	protected static void clearUser(HttpServletRequest request)
	{
		request.getSession().removeAttribute(WEB_AUTH_SESSION);
	}
}
