package dswork.android.util;

import java.security.MessageDigest;
import java.util.Locale;

import android.util.Base64;

/**
 * 加密算法（MD5、Base64）
 */
public class EncryptUtil
{
	/**
	 * MD5加密
	 * @param str 需要加密的String
	 * @return 32位MD5的String，失败返回null
	 */
	public static String encryptMd5(String str)
	{
		if(str != null)
		{
			StringBuilder sb = new StringBuilder();
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] digest = md.digest(str.getBytes());
				String stmp = "";
				for(int n = 0; n < digest.length; n++)
				{
					stmp = (Integer.toHexString(digest[n] & 0XFF));
					sb.append((stmp.length() == 1)?"0":"").append(stmp);
				}
				return sb.toString().toUpperCase(Locale.ENGLISH);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				sb = null;
			}
		}
		return null;
	}

	/**
	 * 将字符串转化为base64编码
	 * @param str 需要加密的String
	 * @return base64编码的String，失败返回null
	 */
	public static String encodeBase64(String str)
	{
		try
		{
			return Base64.encodeToString(str.getBytes(), Base64.CRLF);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * 将 BASE64 编码的字符串 进行解码
	 * @param str base64编码的字符串
	 * @return 解码后的字符串，失败返回null
	 */
	public static String decodeBase64(String str)
	{
		try
		{
			if(str != null)
			{
				return new String(Base64.decode(str, Base64.CRLF));
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}
}
