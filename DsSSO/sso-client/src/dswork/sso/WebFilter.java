package dswork.sso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dswork.sso.http.HttpUtil;
import dswork.sso.model.IUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebFilter implements Filter
{
	static com.google.gson.Gson gson = AuthGlobal.getGson();
	static Logger log = LoggerFactory.getLogger("dswork.sso");

	private final static String TICKET = "ticket";// url中传来的sessionKey的变量名
	public final static String LOGINER = "sso.web.loginer";// sessionUser在session中的key

	private final static String DS_SSO_TICKET = "DS_SSO_TICKET";
	private final static String DS_SSO_CODE = "DS_SSO_CODE";
	
	private static String loginURL = "";// 登入页面
	private static String logoutURL = "";// 登出页面
	private static boolean sameDomain = false;
	private static Set<String> ignoreURLSet = new HashSet<String>();// 无需验证页面

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String relativeURI = request.getServletPath();// 相对地址
//		String relativeURI = request.getRequestURI();// request.getRequestURI()=request.getContextPath()+request.getServletPath()
//		if(request.getContextPath().length() > 0)
//		{
//			relativeURI = relativeURI.replaceFirst(request.getContextPath(), "");
//		}
		
		IUser ouser = (IUser)session.getAttribute(LOGINER);
		if(log.isInfoEnabled())
		{
			log.info("当前访问地址：" + request.getContextPath() + relativeURI);
			log.info(ouser != null ? "当前登录用户是：" + ouser.getAccount() : "当前没有登录用户");
		}
		
		// 和sso项目在同一域名情况下，可直接忽略ticket模式
		if(sameDomain)
		{
			String ssoTicket = getValueByCookie(request, DS_SSO_TICKET);
			String ssoCode = getValueByCookie(request, DS_SSO_CODE);
			// 使用cookie模式登录，否则还用原方式
			if(ssoTicket != null && ssoCode != null)
			{
				if(ssoTicket.equals(String.valueOf(session.getAttribute(TICKET))))// 一样的ticket
				{
					if(ouser != null)// 已登录
					{
						chain.doFilter(request, response);
						return;
					}
				}
				try
				{
					String msg = dswork.sso.util.EncryptUtil.decodeDes(ssoCode, ssoTicket);
					String[] msgs = msg.split("#", -1);
					if(msgs.length == 2)
					{
						if(doValidateAccount(session, AuthFactory.getUser(msgs[0]), ssoTicket))
						{
							chain.doFilter(request, response);
							return;
						}
					}
				}
				catch(Exception e)
				{
				}
			}
		}
		
		// 判断是否有ticket
		String ticket = request.getParameter(TICKET);
		if(ticket == null)// 如果不经由ticket的链接过来，则进此处判断
		{
			if(log.isInfoEnabled())
			{
				log.info("request中的ticket为空");
			}
			if(ouser != null)// 已登录
			{
				chain.doFilter(request, response);
				return;
			}
		}
		else
		// ticket非空,由链接传来ticket
		{
			if(ticket.equals(String.valueOf(session.getAttribute(TICKET))))// 一样的ticket
			{
				if(session.getAttribute(LOGINER) != null)// 已登录
				{
					chain.doFilter(request, response);
					return;
				}
			}
			else
			{
				if(log.isInfoEnabled())
				{
					log.info("session和request中的ticket不相等");
					if("null".equals(String.valueOf(session.getAttribute(TICKET))))
					{
						log.info("session中的ticket为空");
					}
				}
			}
			if(doValidate(session, ticket))
			{
				chain.doFilter(request, response);
				return;// 验证成功
			}
		}
		// String relativeURI = request.getRequestURI();// 相对地址
		// if(request.getContextPath().length() > 0){relativeURI = relativeURI.replaceFirst(request.getContextPath(), "");}
		if(ignoreURLSet.contains(relativeURI))// 判断是否为无需验证页面
		{
			chain.doFilter(request, response);// 是无需验证页面
			return;
		}
		response.setHeader("P3P", "CP=CAO PSA OUR");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.sendRedirect(getLoginURL(request));
		return;
	}

	public static String getAccount(HttpSession session)
	{
		Map<String, String> m = getLoginer(session);
		String account = String.valueOf(m.get("account"));
		return (account.length() > 0 && !account.equals("null")) ? account : "";
	}

	@SuppressWarnings("all")
	private static String getLoginURL(HttpServletRequest request) throws UnsupportedEncodingException
	{
		String url = "";
		try
		{
			String params = "";
			Enumeration e = request.getParameterNames();
			String key = "";
			String value = "";
			while(e.hasMoreElements())
			{
				key = String.valueOf(e.nextElement());
				value = request.getParameter(key);
				if(value != null && !value.equals("") && !key.equals(TICKET) && !key.equals("service"))
				{
					params += "&" + key + "=" + getValueByParameter(request, key);
				}
			}
			url = request.getRequestURL().toString();
			//params += "&ex=" + System.currentTimeMillis();
			if(params.length() > 0)
			{
				url = url + (url.indexOf("?") == -1 ? "?" : "&") + params.substring(1);
			}
		}
		catch(Exception e)
		{
			url = "";
		}
		return loginURL + URLEncoder.encode(url, "UTF-8");
	}
	
	/**
	 * @note 获取指定用户的基本信息
	 * @param ticket 登录凭证
	 * @return Loginer
	 */
	@SuppressWarnings("all")
	public static Map<String, String> getLoginer(HttpSession session)
	{
		Map<String, String> m = new java.util.HashMap<String, String>();
		try
		{
			IUser user = (IUser) session.getAttribute(LOGINER);
			m.put("id", user.getId() + "");
			m.put("account", user.getAccount());
			m.put("name", user.getName());
			m.put("idcard", user.getIdcard());
			m.put("workcard", user.getWorkcard());
			m.put("orgid", user.getOrgid() + "");
			m.put("orgpid", user.getOrgpid() + "");
		}
		catch(Exception e)
		{
		}
		return m;
	}
	
	public static String getLoginURL()
	{
		return loginURL;
	}
	
	public static String getLogoutURL()
	{
		return logoutURL;
	}

	private boolean doValidate(HttpSession session, String ticket)
	{
		IUser user = null;
		try
		{
			String u = AuthGlobal.getURL() + "/getLoginer?ticket=" + ticket;
			String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
			if(log.isDebugEnabled())
			{
				log.debug("WebFilter:url=" + u + ", json:" + v);
			}
			user = gson.fromJson(v, IUser.class);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return doValidateAccount(session, user, ticket);
	}
	
	private boolean doValidateAccount(HttpSession session, IUser user, String ticket)
	{
		try
		{
			if(user != null && user.getAccount().length() > 0 && !"null".equals(user.getAccount()))
			{
				session.setAttribute(LOGINER, user);
				session.setAttribute(TICKET, ticket);
				if(log.isDebugEnabled())
				{
					log.debug("sameDomain=" + (sameDomain) + ", account=" + user.getAccount() + ", ticket=" + ticket);
				}
				return true;
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		session.removeAttribute(LOGINER);
		session.removeAttribute(TICKET);
		return false;
	}

	public static void logout(HttpSession session)
	{
		session.setAttribute(LOGINER, "");
		session.removeAttribute(LOGINER);
	}

	public void init(FilterConfig config) throws ServletException
	{
		String ssoURL = String.valueOf(config.getInitParameter("ssoURL")).trim();
		String ssoName = String.valueOf(config.getInitParameter("ssoName")).trim();
		String ssoPassword = String.valueOf(config.getInitParameter("ssoPassword")).trim();
		AuthGlobal.init(ssoURL, ssoName, ssoPassword);
		
		loginURL = String.valueOf(config.getInitParameter("loginURL")).trim();
		loginURL = loginURL + (loginURL.indexOf("?") == -1 ? "?service=" : "&service=");
		
		logoutURL = String.valueOf(config.getInitParameter("logoutURL")).trim();
		logoutURL = logoutURL + (logoutURL.indexOf("?") == -1 ? "?service=" : "&service=");
		
		String hasSameDoamin = String.valueOf(config.getInitParameter("sameDomain")).trim();
		if(hasSameDoamin.equals("true"))
		{
			sameDomain = true;// 和sso在同一域名下时，可跳过ticket远程访问，直接读取cookie
		}
		
		
		ignoreURLSet.clear();
		
		String ignoreURL = String.valueOf(config.getInitParameter("ignoreURL")).trim();
		if(ignoreURL.length() > 0)
		{
			String[] values = ignoreURL.trim().split(",");
			for(String value : values)
			{
				if(value.trim().length() > 0)
				{
					ignoreURLSet.add(value.trim());
				}
			}
		}
	}

	public void destroy()
	{
	}

	/**
	 * 根据参数名称取得值
	 * @param parameter
	 * @return
	 */
	private static String getValueByParameter(HttpServletRequest request, String parameter)
	{
		String[] values = request.getParameterValues(parameter);
		String value = "";
		if(values.length == 1)
		{
			value = request.getParameter(parameter);
			if(value == null)
			{
				return "";
			}
			value = value.trim();
		}
		else
		{
			int k = values.length;
			for(int i = 0; i < values.length; i++)
			{
				if(i == k - 1)
				{
					value += values[i].trim();
				}
				else
				{
					value += values[i].trim() + ",";
				}
			}
		}
		return value;
	}
	
	private static String getValueByCookie(HttpServletRequest request, String name)
	{
		Cookie cookies[] = request.getCookies();
		String value = null;
		if(cookies != null)
		{
			Cookie cookie = null;
			for(int i = 0; i < cookies.length; i++)
			{
				cookie = cookies[i];
				if(cookie.getName().equals(name))
				{
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
}
