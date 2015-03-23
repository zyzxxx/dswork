using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Reflection;
using System.Text;
using System.Xml;
using Dswork.Core.Util;
using Dswork.Core.Page;
using Dswork.Web;

namespace Test
{
	class main
	{
		static void Main(string[] args)
		{
			log4net.Config.XmlConfigurator.ConfigureAndWatch(new System.IO.FileInfo(AppDomain.CurrentDomain.SetupInformation.ApplicationBase + "Config/log4net.config"));
			//log4net.Config.XmlConfigurator.Configure();
			
			Console.WriteLine(EnvironmentUtil.GetToString("jskey.upload.savePath", "sss"));
			Console.WriteLine(0x40);
			Console.WriteLine(0x10);
			//Program.doMain(args);

			string s = "我是明文";
			Console.WriteLine(s = Dswork.Core.Util.EncryptUtil.EncodeDes(s, "dswork"));
			Console.WriteLine(Dswork.Core.Util.EncryptUtil.DecodeDes(s, "dswork"));

			/*
			Hashtable h = new Hashtable();
			h.Add("aaa", "aaa");
			//h.Add("bbb", "20");
			h.Add("aa", "3a0");
			main m = new main();
			MyTest o = new MyTest();
			m.Value(h, o);

			Console.WriteLine(o.aaa);
			Console.WriteLine(o.aa);
			Console.WriteLine(o.bbb);
			Console.WriteLine("");
			Console.WriteLine("");
			Console.WriteLine("");
			*/
			Console.ReadKey();
			Console.Write("");
		}
		public string Bak()
		{
			//Encoder enc = System.Text.Encoding.Unicode.GetEncoder();
			//byte[] unicodeText = new byte[str.Length * 2];
			//enc.GetBytes(str.ToCharArray(), 0, str.Length, unicodeText, 0, true);
			//MD5 md5 = new MD5CryptoServiceProvider();
			//byte[] result = md5.ComputeHash(unicodeText);
			StringBuilder sb = new StringBuilder();
			//for(int i = 0; i < result.Length; i++)
			//{
			//    sb.Append(result[i].ToString("X2"));
			//}
			return sb.ToString();
		}
		public void Value(Hashtable hash, Object classT)
		{
			foreach(PropertyInfo properInfo in classT.GetType().GetProperties())
			{
				try
				{
					properInfo.SetValue(classT, Convert.ChangeType(hash[properInfo.Name], properInfo.PropertyType), null);
				}
				catch(Exception ex)
				{
					Console.WriteLine(ex.Message);
				}
			}
		}
	}
	public class MyTest
	{
		public String aaa {set;get;}
		public int aa {set;get;}
		public long bbb {set;get;}
	}
}
