using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Reflection;
using System.Text.RegularExpressions;
using System.Text;
using System.Web;

namespace Dswork.Http
{
	public class Cookie
	{
		private String name;
		private String value;
		private String domain;
		private DateTime expiryDate;
		private String path;
		private Boolean secure = false;//表示cookie通过https连接传送，假如是http，那么就不会被传送
		private Boolean httpOnly = false;//表示是否不允许javascript进行读取

		public Cookie(String name, String value)
		{
			this.name = Convert.ToString(name);
			this.value = value;
		}

		public String Name
		{
			get { return name; }
			set { name = Convert.ToString(value); }
		}

		public String Value
		{
			get { return value; }
			set { this.value = Convert.ToString(value); }
		}

		public String Domain
		{
			get { return domain; }
			set { if (value == null) { domain = null; } else { domain = Convert.ToString(value).ToLower(); } }
		}

		public DateTime ExpiryDate
		{
			get { return expiryDate; }
			set { expiryDate = value; }
		}


		public String Path
		{
			get { return path; }
			set { path = value; }
		}

		public Boolean Secure
		{
			get { return secure; }
			set { secure = value; }
		}

		public Boolean HttpOnly
		{
			get { return httpOnly; }
			set { httpOnly = value; }
		}

		public Boolean IsExpired(DateTime date)
		{
			return (this.expiryDate != null && this.expiryDate.Ticks != 0 && this.expiryDate.Ticks <= date.Ticks);
		}


		public override String ToString()
		{
			StringBuilder buffer = new StringBuilder();
			//		buffer.Append("[version: ");
			//		buffer.Append(Integer.toString(this.cookieVersion));
			//		buffer.Append("]");
			buffer.Append("[name: ");
			buffer.Append(this.name);
			buffer.Append("]");
			buffer.Append("[value: ");
			buffer.Append(this.value);
			buffer.Append("]");
			buffer.Append("[domain: ");
			buffer.Append(this.domain);
			buffer.Append("]");
			buffer.Append("[path: ");
			buffer.Append(this.path);
			buffer.Append("]");
			buffer.Append("[expiry: ");
			buffer.Append(this.expiryDate);
			buffer.Append("]");
			return buffer.ToString();
		}

		public Cookie Clone()
		{
			Cookie c = new Cookie(name, value);
			c.Path = Path;
			c.Secure = Secure;
			c.HttpOnly = HttpOnly;
			c.Domain = Domain;
			c.ExpiryDate = ExpiryDate;
			return c;
		}
	}
}
