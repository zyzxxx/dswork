package dswork.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@SuppressWarnings("all")
public class MD5FileUtil
{
	// private static final Logger logger = LoggerFactory.getLogger(MD5FileUtil.class);

	public static byte[] getFileMD5String(File file) throws Exception
	{
		FileInputStream in = new FileInputStream(file);
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

	private static String encryptMd5_Byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for(int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else
			{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	

	public static String init(File b) throws Exception
	{
		String md5 = encryptMd5_Byte2hex(getFileMD5String(b)).toUpperCase();
		return md5;
	}
	
	static Map f = new HashMap();

	public static void main(String[] args) throws Exception
	{
		File big = new File("C:/Users/skey/Desktop/a");
		//File big = new File("D:/MySoftware/DataBase");
		for(File i : big.listFiles())
		{
			if(i.isDirectory())
			{
				for(File j : i.listFiles())
				{
					if(j.isDirectory())
						continue;
					String md = init(j);
					System.out.print(md + " " + j.getPath());
					if(f.containsKey(md))
					{
						System.out.print("\n                                 " + f.get(md));
					}
					else
					{
						f.put(md, j.getPath());
					}
					System.out.println();
				}
			}
			else
			{
				String md = init(i);
				System.out.print(md + " " + i.getPath());
				if(f.containsKey(md))
				{
					System.out.print("\n                                 " + f.get(md));
				}
				else
				{
					f.put(md, i.getPath());
				}
				System.out.println();
			}
		}
	}


//	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//	public static char[] encodeHex(byte[] data)
//	{
//		int l = data.length;
//		char[] out = new char[l << 1];
//		// two characters form the hex value.
//		for(int i = 0, j = 0; i < l; i++)
//		{
//			out[j++] = hexDigits[(0xF0 & data[i]) >>> 4];
//			out[j++] = hexDigits[0x0F & data[i]];
//		}
//		return out;
//	}
}
