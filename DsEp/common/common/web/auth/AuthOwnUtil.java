package common.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dswork.core.util.EncryptUtil;
import dswork.web.MyCookie;

public class AuthOwnUtil
{
	private static final String COOKIE_WWW_AUTH = "WWW_AUTH";
	private static final String SESSION_WWW_AUTH = "SESSION_WWW_AUTH";

	public static void setUserCookie(HttpServletRequest request, HttpServletResponse response, String id, String account, String name, String own)
	{
		MyCookie cookie = new MyCookie(request, response);
		String value = id + "&" + account + "&" + name + "&" + own;
		cookie.addCookie(COOKIE_WWW_AUTH, EncryptUtil.encodeDes(value, "own"));
	}

	public static AuthOwn getUserCookie(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		String s = cookie.getValue(COOKIE_WWW_AUTH);
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

	public static void clearUserCookie(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		cookie.delCookie(COOKIE_WWW_AUTH);
	}

	public static void setUser(HttpServletRequest request, AuthOwn authOwn)
	{
		String id = authOwn.getId();
		String account = authOwn.getAccount();
		String name = authOwn.getName();
		String own = authOwn.getOwn();
		String value = id + "&" + account + "&" + name + "&" + own;
		request.getSession().setAttribute(SESSION_WWW_AUTH, value);
	}

	public static AuthOwn getUser(HttpServletRequest request)
	{
		String s = (String) request.getSession().getAttribute(SESSION_WWW_AUTH);
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

	public static void clearUser(HttpServletRequest request)
	{
		request.getSession().removeAttribute(SESSION_WWW_AUTH);
	}
}
