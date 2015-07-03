package dswork.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CasFilter implements Filter
{
	public final static String KEY_TICKET = "ticket";// url中传来的sessionKey的变量名
	public final static String KEY_SESSIONUSER = "cas.client.user";// sessionUser在session中的key
	private static String loginURL = "";// 登录页面
	private static String validateURL = "";// 验证页面
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
		System.out.println(request.getContextPath() + relativeURI);
		// 判断是否有ticket
		String ticket = request.getParameter(KEY_TICKET);
		if(ticket == null)// 如果不经由ticket的链接过来，则进此处判断
		{
			if(session.getAttribute(KEY_SESSIONUSER) != null)// 已登录
			{
				chain.doFilter(request, response);
				return;
			}
		}
		else// ticket非空,由链接传来ticket
		{
			if(ticket.equals(String.valueOf(session.getAttribute(KEY_TICKET))))// 一样的ticket
			{
				if(session.getAttribute(KEY_SESSIONUSER) != null)// 已登录
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
		// if(request.getContextPath().length() > 0)
		// {
		// relativeURI = relativeURI.replaceFirst(request.getContextPath(), "");
		// }
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
		Object o = session.getAttribute(KEY_SESSIONUSER);
		if(o == null)
		{
			return "";
		}
		return String.valueOf(o).trim();
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
				if(value != null && !value.equals("") && !key.equals(KEY_TICKET) && !key.equals("service"))
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

	private boolean doValidate(HttpSession session, String ticket)
	{
		try
		{
			String account = getOneHtml(validateURL + ticket);// 获取账号名,url[http://127.0.0.1/validate.htm?ticket=]
			if(account.length() > 0)
			{
				session.setAttribute(KEY_SESSIONUSER, account);
				session.setAttribute(KEY_TICKET, ticket);
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		session.removeAttribute(KEY_SESSIONUSER);
		session.removeAttribute(KEY_TICKET);
		return false;
	}

	public static void doLogout(HttpSession session)
	{
		session.setAttribute(KEY_SESSIONUSER, "");
		session.removeAttribute(KEY_SESSIONUSER);
	}

	public void init(FilterConfig config) throws ServletException
	{
		loginURL = String.valueOf(config.getInitParameter("loginURL")).trim();
		loginURL = loginURL + (loginURL.indexOf("?") == -1 ? "?service=" : "&service=");
		validateURL = String.valueOf(config.getInitParameter("validateURL")).trim();
		validateURL = validateURL + (validateURL.indexOf("?") == -1 ? "?ticket=" : "&ticket=");
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

	// 读取一个网页内容
	private static String getOneHtml(String htmlurl) throws IOException
	{
		URL url;
		String temp;
		StringBuffer sb = new StringBuffer();
		try
		{
			url = new URL(htmlurl);
			HttpURLConnection http = null;
			try
			{
				if(url.getProtocol().toLowerCase().equals("https"))
				{
					javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
					javax.net.ssl.TrustManager tm = new TM();
					trustAllCerts[0] = tm;
					javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts, null);
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
					http = (HttpURLConnection) url.openConnection();
					((HttpsURLConnection) http).setHostnameVerifier(CasFilter.HV);// 不进行主机名确认
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(http == null)
			{
				http = (HttpURLConnection) url.openConnection();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));// 读取网页内容
			while((temp = in.readLine()) != null)
			{
				sb.append(temp);
			}
			in.close();
		}
		catch(final MalformedURLException me)
		{
			System.out.println("你输入的URL格式有问题！" + htmlurl);
			me.getMessage();
			throw me;
		}
		catch(final IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		return sb.toString().trim();
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
				return "";
			value = value.trim();
		}
		else
		{
			int k = values.length;
			for(int i = 0; i < values.length; i++)
			{
				if(i == k - 1)
					value += values[i].trim();
				else
					value += values[i].trim() + ",";
			}
		}
		return value;
	}
	
	public final static HostnameVerifier HV = new HostnameVerifier()
	{
		public boolean verify(String urlHostName, SSLSession session)
		{
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			return true;
		}
	};
	private static class TM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager
	{
		public java.security.cert.X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
		{
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException
		{
			return;
		}
	}
}
