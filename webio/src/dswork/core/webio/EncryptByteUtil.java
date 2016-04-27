package dswork.core.webio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@SuppressWarnings("all")
public class EncryptByteUtil
{
	public static byte[] getMd5Byte(InputStream in) throws Exception
	{
		byte[] buffer = new byte[8192];
		int length;
		MessageDigest messagedigest = MessageDigest.getInstance("MD5");
		while((length = in.read(buffer)) != -1)
		{
			messagedigest.update(buffer, 0, length);
		}
		in.close();
		return messagedigest.digest();
	}

	private static String getMd5String(byte[] b)
	{
		StringBuilder hs = new StringBuilder(32);
		String stmp = "";
		for(int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1)
			{
				hs.append("0");
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	public static String getMd5(InputStream in) throws Exception
	{
		String md5 = getMd5String(getMd5Byte(in)).toUpperCase();
		return md5;
	}

	public static String getMd5(byte[] bytes) throws Exception
	{
		InputStream in = new ByteArrayInputStream(bytes);
		String md5 = getMd5String(getMd5Byte(in)).toUpperCase();
		return md5;
	}
}
