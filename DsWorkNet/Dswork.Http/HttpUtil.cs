using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace Dswork.Http
{
	/// <summary>
	/// 扩展Request的功能
	/// </summary>
	public class HttpUtil
	{
		private HttpWebRequest http;
		private Boolean isHttps = false;
		private int connectTimeout = 10000;
		private int readTimeout = 30000;
		private String userAgent = "Mozilla/5.0 (compatible; MSIE 11; Windows NT 6.1; Win64; x64;)";// Gecko/20150123

		/// <summary>
		/// 返回当前是否https请求
		/// </summary>
		public Boolean Https
		{
			get { return isHttps; }
		}

		/// <summary>
		/// 设置超时时间毫秒
		/// </summary>
		/// <param name="connectTimeout">int</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetConnectTimeout(int connectTimeout)
		{
			this.connectTimeout = connectTimeout;
			if (http != null)
			{
				try
				{
					this.http.Timeout = connectTimeout;
				}
				catch
				{
				}
			}
			return this;
		}

		/// <summary>
		/// 设置contentType
		/// </summary>
		/// <param name="contentType">String</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetContentType(String contentType)
		{
			if (http != null)
			{
				this.http.ContentType = contentType;
			}
			return this;
		}

		/// <summary>
		/// 设置读取超时时间毫秒
		/// </summary>
		/// <param name="readTimeout">int</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetReadTimeout(int readTimeout)
		{
			this.readTimeout = readTimeout;
			if (http != null)
			{
				try
				{
					this.http.ReadWriteTimeout = readTimeout;
				}
				catch
				{
				}
			}
			return this;
		}

		/// <summary>
		/// 设置requestMethod
		/// </summary>
		/// <param name="requestMethod">String</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetRequestMethod(String requestMethod)
		{
			if (http != null)
			{
				this.http.Method = requestMethod.ToUpper();
			}
			return this;
		}

		/// <summary>
		/// 设置requestProperty
		/// </summary>
		/// <param name="key">String</param>
		/// <param name="value">String</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetRequestProperty(String key, String value)
		{
			if (http != null)
			{
				try
				{
					this.http.Headers[key] = value;
				}
				catch
				{
				}
			}
			return this;
		}

		/// <summary>
		/// 设置userAgent
		/// </summary>
		/// <param name="userAgent">String</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil SetUserAgent(String userAgent)
		{
			this.userAgent = userAgent;
			if (http != null)
			{
				this.http.Accept = "";
				this.http.UserAgent = userAgent;
			}
			return this;
		}

		/// <summary>
		/// 创建新的http(s)请求，重置除cookie外的所有设置
		/// </summary>
		/// <param name="url">url地址请求</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil Create(String urlpath)
		{
			return Create(urlpath, true);
		}

		/// <summary>
		/// 创建新的http(s)请求，重置除cookie外的所有设置
		/// </summary>
		/// <param name="url">url地址请求</param>
		/// <param name="isHostnameVerifier">是否不确认主机名</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil Create(String url, Boolean isHostnameVerifier)
		{
			this.ClearForm();
			try
			{
				http = WebRequest.Create(url) as HttpWebRequest;
				isHttps = url.StartsWith("https", StringComparison.OrdinalIgnoreCase);
				if (isHostnameVerifier && isHttps)
				{
					HttpCommon.HostnameVerifier();
				}
				this.http.Timeout = connectTimeout;
				this.http.ReadWriteTimeout = readTimeout;
				this.http.UserAgent = userAgent;
				this.http.ContentType = "application/x-www-form-urlencoded";
				this.http.Headers["Accept-Charset"] = "utf-8";
				this.http.Method = "GET";
			}
			catch
			{
			}
			return this;
		}

		public String Connect()
		{
			return Connect("UTF-8");
		}

		public String Connect(String charsetName)
		{
			String result = null;
			try
			{
				DateTime dt = new DateTime();
				this.http.CookieContainer = new CookieContainer();
				if (this.cookies.Count > 0)
				{
					/*
					List<Cookie> list = HttpCommon.GetHttpCookies(this.cookies, Https);
					foreach (Cookie m in list)
					{
						if (m.ExpiryDate == null || (m.ExpiryDate != null && !m.IsExpired(dt)))
						{
							System.Net.Cookie x = new System.Net.Cookie();
							x.Name = m.Name;
							x.Value = m.Value;
							x.Secure = m.Secure;
							x.Path = m.Path;
							x.Domain = m.Domain;
							x.Expires = m.ExpiryDate;
							x.HttpOnly = m.HttpOnly;
							this.http.CookieContainer.Add(x);
						}
					}
					*/
					String _c = HttpCommon.parse(HttpCommon.GetHttpCookies(this.cookies, Https), "; ");
					this.http.CookieContainer.SetCookies(http.RequestUri, _c);
				}
				if (this.form.Count > 0)
				{
					String data = HttpCommon.format(form, charsetName);
					if (this.http.Method.ToUpper() == "GET")
					{
						this.http.Method = "POST";
					}
					Encoding enc = Encoding.GetEncoding(28591);//28591对应iso-8859-1
					using (Stream stream = this.http.GetRequestStream())
					{
						stream.Write(enc.GetBytes(data), 0, data.Length);
					}
				}
				HttpWebResponse res = http.GetResponse() as HttpWebResponse;
				if (res.StatusCode == HttpStatusCode.OK)
				{
					List<Cookie> list = HttpCommon.GetHttpCookies(res.Cookies);
					foreach (Cookie m in list)
					{
						if (m.ExpiryDate == null)
						{
							this.AddCookie(m.Name, m.Value);// 会话cookie
						}
						else
						{
							if (!m.IsExpired(dt))
							{
								this.AddCookie(m.Name, m.Value);
							}
						}
					}
					Stream stream = res.GetResponseStream();
					Encoding ee = charsetName.ToLower().Equals("utf-8") ? new UTF8Encoding(false) : Encoding.GetEncoding(charsetName);
					StreamReader streamReader = new StreamReader(stream, ee);
					result = streamReader.ReadToEnd();
					streamReader.Close();
					stream.Close();
					res.Close();
				}
			}
			catch(Exception ex)
			{
				Console.WriteLine(ex.Message);
			}
			return result;
		}

		// 表单项
		private List<NameValue> form = new List<NameValue>();
		/// <summary>
		/// 清除已清加的表单项
		/// </summary>
		///// <returns>HttpUtil</returns>
		public HttpUtil ClearForm()
		{
			form.Clear();
			return this;
		}

		/// <summary>
		/// 添加表单项
		/// </summary>
		/// <param name="name">name</param>
		/// <param name="value">value</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil AddForm(String name, String value)
		{
			form.Add(new NameValue(name, value));
			return this;
		}

		/// <summary>
		/// 批量添加表单项
		/// </summary>
		/// <param name="name">name</param>
		/// <param name="value">value</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil AddForms(NameValue[] array)
		{
			foreach (NameValue c in array)
			{
				form.Add(c);
			}
			return this;
		}

		// cookie
		private List<Cookie> cookies = new List<Cookie>();

		/// <summary>
		/// 清除已清加的cookie
		/// </summary>
		/// <returns>HttpUtil</returns>
		public HttpUtil ClearCookies()
		{
			cookies.Clear();
			return this;
		}

		/// <summary>
		/// 添加cookie
		/// </summary>
		/// <param name="name">name</param>
		/// <param name="value">value</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil AddCookie(String name, String value)
		{
			cookies.Add(new Cookie(name, value));
			return this;
		}

		/// <summary>
		/// 批量添加cookie
		/// </summary>
		/// <param name="name">name</param>
		/// <param name="value">value</param>
		/// <returns>HttpUtil</returns>
		public HttpUtil AddCookies(Cookie[] array)
		{
			foreach (Cookie c in array)
			{
				cookies.Add(c);
			}
			return this;
		}

		/// <summary>
		/// 复制cookie
		/// </summary>
		/// <param name="onlySessionCookie">true：仅复制会话cookie false：复制非会话cookie null:全部cookie</param>
		/// <returns>List<Cookie></returns>
		public List<Cookie> GetCloneCookies(Boolean onlySessionCookie)
		{
			List<Cookie> lists = HttpCommon.GetHttpCookies(this.cookies, true);
			List<Cookie> list = new List<Cookie>();
			if (onlySessionCookie)
			{
				foreach (Cookie m in lists)
				{
					if (m.ExpiryDate == null)
					{
						list.Add(m.Clone());
					}
				}
			}
			else
			{
				foreach (Cookie m in lists)
				{
					if (m.ExpiryDate != null)
					{
						list.Add(m.Clone());
					}
				}
			}
			return list;
		}
	}
}
