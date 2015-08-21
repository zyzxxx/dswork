using System;

using System.Collections.Generic;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Web;
using System.Text;

namespace Dswork.Http
{
	public class HttpCommon
	{
		private static String NAME_VALUE_SEPARATOR = "=";
		private static String PARAMETER_SEPARATOR = "&";

		public static String format(List<NameValue> parameters, String charset)
		{
			return format(parameters, PARAMETER_SEPARATOR, charset);
		}

		public static String format(List<NameValue> parameters, String parameterSeparator, String charset)
		{
			StringBuilder result = new StringBuilder();
			foreach (NameValue parameter in parameters)
			{
				try
				{
					Encoding enc = charset.ToLower().Equals("utf-8") ? new UTF8Encoding(false) : Encoding.GetEncoding(charset);
					String encodedName = HttpUtility.UrlEncode(parameter.Name, enc);
					String encodedValue = HttpUtility.UrlEncode(parameter.Value, enc);
					if (result.Length > 0)
					{
						result.Append(parameterSeparator);
					}
					result.Append(encodedName);
					if (encodedValue != null)
					{
						result.Append(NAME_VALUE_SEPARATOR);
						result.Append(encodedValue);
					}
				}
				catch (Exception e)
				{
				}
			}
			return result.ToString();
		}

		public static String parse(List<Cookie> parameters, String parameterSeparator)
		{
			StringBuilder result = new StringBuilder();
			foreach (Cookie parameter in parameters)
			{
				if (result.Length > 0)
				{
					result.Append(parameterSeparator);
				}
				result.Append(parameter.Name);
				if (parameter.Value != null)
				{
					result.Append(NAME_VALUE_SEPARATOR);
					result.Append(parameter.Value);
				}
			}
			return result.ToString();
		}

		public static List<Cookie> GetHttpCookies(CookieCollection cookies)
		{
			List<Cookie> list = new List<Cookie>();
			foreach (System.Net.Cookie m in cookies)
			{
				if(!m.Expired)
				{
					Cookie c = new Cookie(m.Name, m.Value);
					c.Path = m.Path;
					c.Domain = m.Domain;
					c.Secure = m.Secure;
					c.ExpiryDate = m.Expires;
					c.HttpOnly = m.HttpOnly;
					list.Add(c);
				}
			}
			return list;
		}

		public static void RefreshCookies(List<Cookie> cookies)
		{
			DateTime date = new DateTime();
			for (int i = cookies.Count - 1; i >= 0; i--)
			{
				if (cookies[i].IsExpired(date))
				{
					cookies.RemoveAt(i);// 移除超时的
				}
			}
		}

		public static List<Cookie> GetHttpCookies(List<Cookie> cookies, Boolean hasSecure)
		{
			HttpCommon.RefreshCookies(cookies);
			List<Cookie> list;
			if (hasSecure)
			{
				list = cookies;
			}
			else
			{
				list = new List<Cookie>();
				foreach (Cookie m in cookies)
				{
					if (!m.Secure)
					{
						list.Add(m.Clone());
					}
				}
			}
			return list;
		}
		public static void HostnameVerifier()
		{
			ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(ValidateServerCertificate);
		}

		private static Boolean ValidateServerCertificate(object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors errors)
		{
			return true;
		}
	}
}