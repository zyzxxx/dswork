package common.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dswork.core.util.EncryptUtil;
import dswork.web.MyCookie;

public class AuthOwnUtil
{
	private static final String COOKIE_WWW_AUTH = "WWW_AUTH";
	private static final String SESSION_WWW_AUTH = "SESSION_WWW_AUTH";

	public static void setUser(HttpServletRequest request, HttpServletResponse response, String id, String account, String name, String own)
	{
		MyCookie cookie = new MyCookie(request, response);
		String value = id + "&" + account + "&" + name + "&" + own;
		request.getSession().setAttribute(SESSION_WWW_AUTH, value);// 放入session
		cookie.addCookie(COOKIE_WWW_AUTH, EncryptUtil.encodeDes(value, "own"));// 放入cookie
	}

	public static AuthOwn getUser(HttpServletRequest request, HttpServletResponse response)
	{
		String s = (String) request.getSession().getAttribute(SESSION_WWW_AUTH);// 先从session里面取
		if(s == null)// 如果session里面取不到的话，再从cookie里面取
		{
			MyCookie cookie = new MyCookie(request, response);
			s = cookie.getValue(COOKIE_WWW_AUTH);
			if(s == null)
			{
				return null;
			}
			s = EncryptUtil.decodeDes(s, "own");
		}
		String[] ss = s.split("&", -1);
		if(ss.length == 4)
		{
			return new AuthOwn(ss[0], ss[1], ss[2], ss[3]);
		}
		return null;
	}

	public static void clearUser(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		cookie.delCookie(COOKIE_WWW_AUTH);// 清除cookie
		request.getSession().removeAttribute(SESSION_WWW_AUTH);// 清除session
	}
}
