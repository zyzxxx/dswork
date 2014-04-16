using System;
using System.Security.Cryptography;
using System.Text;

namespace Dswork.Core.Util
{
	/// <summary>
	/// 加密算法（MD5、Base64）
	/// </summary>
	public class EncryptUtil
	{
		/// <summary>
		/// MD5加密
		/// </summary>
		/// <param name="str">需要加密的字符串</param>
		/// <returns>32位MD5的String，失败返回null</returns>
		public static String EncryptMd5(String str)
		{
			try
			{
				byte[] result = Encoding.Default.GetBytes(str);
				MD5 md5 = new MD5CryptoServiceProvider();
				byte[] output = md5.ComputeHash(result);
				return BitConverter.ToString(output).Replace("-", "");
			}
			catch
			{
			}
			return null;
		}


		/// <summary>
		/// 将字符串转化为base64编码
		/// </summary>
		/// <param name="str">需要加密的字符串</param>
		/// <returns>base64编码的字符串，失败返回null</returns>
		public static String EncodeBase64(String str)
		{
			try
			{
				return Convert.ToBase64String(Encoding.Default.GetBytes(str));
			}
			catch
			{
				return null;
			}
		}

		/// <summary>
		/// 将 BASE64 编码的字符串 进行解码
		/// </summary>
		/// <param name="str">base64编码的字符串</param>
		/// <returns>解码后的字符串，失败返回null</returns>
		public static String DecodeBase64(String str)
		{
			try
			{
				return Encoding.Default.GetString(Convert.FromBase64String(str));
			}
			catch
			{
				return null;
			}
		}
	}
}
