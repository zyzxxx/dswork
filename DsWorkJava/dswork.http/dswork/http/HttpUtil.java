package dswork.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil
{
	private HttpURLConnection http;
	private boolean isHttps = false;

	public boolean isHttps()
	{
		return isHttps;
	}

	public HttpUtil create(String urlpath)
	{
		return create(urlpath, true);
	}

	/**
	 * 创建新的http(s)请求，重置除cookie外的所有设置
	 * @param url url地址请求
	 * @param isHostnameVerifier 是否不确认主机名
	 * @return HttpUtil
	 */
	public HttpUtil create(String url, boolean isHostnameVerifier)
	{
		this.clearForm();
		URL c;
		try
		{
			c = new URL(url);
			if(isHostnameVerifier && c.getProtocol().toLowerCase().equals("https"))
			{
				isHttps = true;
				HttpsURLConnection.setDefaultSSLSocketFactory(HttpCommon.getSocketFactory());
				this.http = (HttpURLConnection) c.openConnection();
				((HttpsURLConnection) this.http).setHostnameVerifier(HttpCommon.HV);// 不进行主机名确认
			}
			else
			{
				isHttps = false;
				HttpsURLConnection.setDefaultSSLSocketFactory(HttpCommon.getSocketFactoryDefault());
				this.http = (HttpURLConnection) c.openConnection();
			}
			this.http.setDoInput(true);
			this.http.setDoOutput(false);
			this.http.setConnectTimeout(10000);
			this.http.setReadTimeout(10000);
			this.http.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 6.1; Win64; x64;)");// Gecko/20150123
			this.http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			this.http.setRequestProperty("Accept-Charset", "utf-8");
			this.http.setRequestMethod("GET");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 设置超时时间毫秒
	 * @param connectTimeout
	 * @return HttpUtil
	 */
	public HttpUtil setConnectTimeout(int connectTimeout)
	{
		this.http.setConnectTimeout(connectTimeout);
		return this;
	}

	/**
	 * 设置读取超时时间毫秒
	 * @param readTimeout
	 * @return HttpUtil
	 */
	public HttpUtil setReadTimeout(int readTimeout)
	{
		this.http.setReadTimeout(readTimeout);
		return this;
	}
	
	// 表单项
	private List<NameValue> form = new ArrayList<NameValue>();
	/**
	 * 清除已清加的表单项
	 * @return HttpUtil
	 */
	public HttpUtil clearForm()
	{
		form.clear();
		return this;
	}

	/**
	 * 添加表单项
	 * @param name
	 * @param value
	 * @return HttpUtil
	 */
	public HttpUtil addForm(String name, String value)
	{
		form.add(new NameValue(name, value));
		return this;
	}

	/**
	 * 批量添加表单项
	 * @param name
	 * @param value
	 * @return HttpUtil
	 */
	public HttpUtil addForms(NameValue[] array)
	{
		for(NameValue c : array)
		{
			form.add(c);
		}
		return this;
	}
	// 表单项
	private List<Cookie> cookies = new ArrayList<Cookie>();

	/**
	 * 清除已清加的cookie
	 * @return HttpUtil
	 */
	public HttpUtil clearCookies()
	{
		cookies.clear();
		return this;
	}

	/**
	 * 添加cookie
	 * @param name
	 * @param value
	 * @return HttpUtil
	 */
	public HttpUtil addCookie(String name, String value)
	{
		cookies.add(new Cookie(name, value));
		return this;
	}

	/**
	 * 批量添加cookie
	 * @param name
	 * @param value
	 * @return HttpUtil
	 */
	public HttpUtil addCookies(Cookie[] array)
	{
		for(Cookie c : array)
		{
			cookies.add(c);
		}
		return this;
	}
	
	/**
	 * 复制cookie
	 * @param onlySessionCookie true：仅复制会话cookie false：复制非会话cookie null:全部cookie
	 * @return
	 */
	public List<Cookie> getCloneCookies(Boolean onlySessionCookie)
	{
		List<Cookie> lists = HttpCommon.getHttpCookies(this.cookies,  true);
		List<Cookie> list = new ArrayList<Cookie>();
		if(onlySessionCookie == null)
		{
			for(Cookie m : lists)
			{
				list.add(m.clone());
			}
		}
		else if(onlySessionCookie)
		{
			for(Cookie m : lists)
			{
				if(m.getExpiryDate() == null)
				{
					list.add(m.clone());
				}
			}
		}
		else
		{
			for(Cookie m : lists)
			{
				if(m.getExpiryDate() != null)
				{
					list.add(m.clone());
				}
			}
		}
		return list;
	}

	/**
	 * setUseCaches
	 * @param usecaches
	 * @return HttpUtil
	 */
	public HttpUtil setUseCaches(boolean usecaches)
	{
		this.http.setUseCaches(usecaches);
		return this;
	}

	/**
	 * 设置userAgent
	 * @param userAgent
	 * @return HttpUtil
	 */
	public HttpUtil setReadTimeout(String userAgent)
	{
		this.http.setRequestProperty("User-Agent", userAgent);
		return this;
	}

	public String connect()
	{
		String result = null;
		try
		{
			if(this.cookies.size() > 0)
			{
				String _c = HttpCommon.parse(HttpCommon.getHttpCookies(this.cookies,  isHttps()), "; ");
				http.setRequestProperty("Cookie", _c);
			}
			if(this.form.size() > 0)
			{
				String data = HttpCommon.format(form, "UTF-8");
				this.http.setDoOutput(true);
				this.http.setRequestMethod("POST");
				this.http.setRequestProperty("Content-Length", String.valueOf(data.length()));
				DataOutputStream out = new DataOutputStream(this.http.getOutputStream());
				out.writeBytes(data);
				out.flush();
				out.close();
			}
			this.http.connect();
			int _responseCode = http.getResponseCode();// 设置http返回状态200（ok）还是403
			BufferedReader in = null;
			if(_responseCode == 200)
			{
				Date date = new Date();
				List<Cookie> list = HttpCommon.getHttpCookies(http);
				for(Cookie m : list)
				{
					if(m.getExpiryDate() == null)
					{
						this.addCookie(m.getName(), m.getValue());// 会话cookie
					}
					else
					{
						if(!m.isExpired(date))
						{
							this.addCookie(m.getName(), m.getValue());
						}
					}
					System.out.println();
					System.out.println(m);
				}
				in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			}
			else
			{
				in = new BufferedReader(new InputStreamReader(http.getErrorStream()));
			}
			String temp = in.readLine();
			while(temp != null)
			{
				if(result != null)
				{
					result += temp;
				}
				else
				{
					result = temp;
				}
				temp = in.readLine();
			}
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		http.disconnect();
		return result;
	}
}
