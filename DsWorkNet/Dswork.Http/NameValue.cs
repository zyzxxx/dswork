using System;

namespace Dswork.Http
{
	public class NameValue
	{
		private String name;
		private String value;


		public NameValue(String name, String value)
		{
			this.name = Convert.ToString(name);
			this.value = value;
		}

		public String Name
		{
			get { return name; }
			set { this.name = Convert.ToString(value); }
		}

		public String Value
		{
			get { return value; }
			set { this.value = value; }
		}
	}
}