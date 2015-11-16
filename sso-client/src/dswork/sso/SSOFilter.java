package dswork.sso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dswork.sso.http.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;

public class SSOFilter implements Filter
{
	static com.google.gson.Gson gson = AuthGlobal.getGson();
	static Logger log = LoggerFactory.getLogger("dswork.cas");

	private final static String TICKET = "ticket";// url中传来的sessionKey的变量名
	public final static String LOGINER = "cas.client.loginer";// sessionUser在session中的key
	private static String loginURL = "";// 登录页面
	private static Set<String> ignoreURLSet = new HashSet<String>();// 无需验证页面

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String relativeURI = request.getRequestURI();// 相对地址
		if(request.getContextPath().length() > 0)
		{
			relativeURI = relativeURI.replaceFirst(request.getContextPath(), "");
		}
		if(log.isDebugEnabled())
		{
			log.debug(request.getContextPath() + relativeURI);
		}
		
		// 判断是否有ticket
		String ticket = request.getParameter(TICKET);
		if(ticket == null)// 如果不经由ticket的链接过来，则进此处判断
		{
			if(session.getAttribute(LOGINER) != null)// 已登录
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
			if(params.length() > 0)
			{
				url = url + (url.indexOf("?") == -1 ? "?" : "&") + params;
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
		Map<String, String> m = null;
		try
		{
			m = (Map<String, String>) session.getAttribute(LOGINER);
		}
		catch(Exception e)
		{
		}
		if(m == null)
		{
			m = new HashMap<String, String>();
		}
		return m;
	}

	private boolean doValidate(HttpSession session, String ticket)
	{
		try
		{
			String u = AuthGlobal.getURL() + "/getLoginer?ticket=" + ticket;
			String v = new HttpUtil().create(u, u.startsWith("https:")).connect().trim();
			if(log.isDebugEnabled())
			{
				log.debug("CasFilter:url=" + u + ", json:" + v);
			}
			Map<String, String> m = gson.fromJson(v, new TypeToken<Map<String, String>>(){}.getType());
			//m.get("id");
			//m.get("account");
			//m.get("name");
			//m.get("idcard");
			//m.get("workcard");
			//m.get("orgid");
			//m.get("orgpid");
			String account = String.valueOf(m.get("account"));
			if(account.length() > 0 && !account.equals("null"))
			{
				session.setAttribute(LOGINER, m);
				session.setAttribute(TICKET, ticket);
				if(log.isDebugEnabled())
				{
					log.debug("account=" + account + ", ticket=" + ticket);
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

	public static void doLogout(HttpSession session)
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
}
