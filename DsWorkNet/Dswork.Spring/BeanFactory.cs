using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Spring.Context.Support;

namespace Dswork.Spring
{
	/// <summary>
	/// 从ApplicationContext取得spring管理的类
	/// </summary>
	public class BeanFactory
	{
		public static Object GetObject(String name)
		{
			return ContextRegistry.GetContext().GetObject(name);
		}

		public static T GetObject<T>(String name)
		{
			return ContextRegistry.GetContext().GetObject<T>(name);
		}
    }
}
