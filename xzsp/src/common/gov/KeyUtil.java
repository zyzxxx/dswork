package common.gov;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dswork.core.util.EnvironmentUtil;
import dswork.core.util.FileUtil;
import dswork.core.util.TimeUtil;

public class KeyUtil
{
	private static final String GovXzspPath = EnvironmentUtil.getToString("gov.xzsp.path", "C:/DC_CLIENT");
	private static final String filePath = GovXzspPath + "/DsworkKeyFile.txt";
	private static final int step = (int)EnvironmentUtil.getToLong("gov.xzsp.step", 100L);// 计数器
	private static final int max = 999999;
	private static int count = 0;// 计数器
	private static String currtime = TimeUtil.getCurrentTime("yyyyMMdd");
	private static String readtime = null; 
	private static int readcount = 0;
	private static Timer _timer = null;
	private static final String G12 = EnvironmentUtil.getToString("gov.xzsp.area", "gz");
	private static final String G34 = EnvironmentUtil.getToString("gov.xzsp.org", "000000000");
	private static final String G = G12 + G34;
	private static final int t = (24 * 60 * 60 * 1000);
	static
	{
		TimerTask _timerTask = new TimerTask()
		{
			public void run()
			{
				currtime = TimeUtil.getCurrentTime("yyyyMMdd");
				System.out.println("变更：" + currtime);
			}
		};
		_timer = new Timer(true);
		System.out.println("启动：" + currtime);
		Calendar date = Calendar.getInstance();
		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 0, 0, 1);// 当前
		//System.out.println(TimeUtil.formatDate(date.getTime(), "yyyy-MM-dd HH:mm:ss"));
		_timer.schedule(_timerTask, date.getTime(), t);
		File file = new File(filePath);
		if(!file.isFile())
		{
			FileUtil.delete(filePath);
			FileUtil.writeFile(filePath, currtime + "1", "UTF-8");// 计数器从1开始
		}
	}
	public static synchronized String getLsh(String c)
	{
		String v = "1";
		if(c != null && "0".equals(String.valueOf(c)))
		{
			v = "0";
		}
		count++;
		if(readtime == null || readcount == 0)// 没初始化
		{
			init();// 初始化后需要设置一次
		}
		if(readtime.equals(currtime))
		{
			if(count >= readcount)// 已经用光了
			{
				init();
			}
		}
		else
		{
			count = 1;
			// 写入新的
			readcount = count + step;
			readtime = currtime;
			write();
		}
		String num = "000000" + count;
		return G + currtime.substring(currtime.length() - 6, currtime.length()) + v + num.substring(num.length() - 6, num.length());
	}
	
	public static void read()
	{
		try
		{
			String f = String.valueOf(FileUtil.readFile(filePath, "UTF-8"));
			readtime = f.substring(0, 8);
			readcount = Integer.parseInt(f.substring(8).trim());
			if(readcount > 999999)
			{
				readcount = 999999;
			}
		}
		catch(Exception e)
		{
			FileUtil.delete(filePath);
			FileUtil.writeFile(filePath, currtime + "1", "UTF-8");
		}
	}
	
	public static void write()
	{
		FileUtil.writeFile(filePath, currtime + readcount, "UTF-8", true);
	}
	
	public static void init()
	{
		read();
		if(readcount >= max)// 已经用光所有了
		{
			count = 1;
			readcount = count + step;
		}
		else
		{
			count = readcount;
			readcount = count + step;
			if(readcount >= max)
			{
				readcount = max;
			}
		}
		write();
	}
	
//	public static void main(String[] args)
//	{
//		for(int i = 0; i< 1000010; i++)
//		{
//			System.out.println(KeyUtil.getLsh(null));
//			if(i%1000 == 0)
//			{
//				System.out.println("ok");
//			}
//		}
//	}
}
