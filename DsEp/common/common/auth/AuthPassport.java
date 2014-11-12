package common.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AuthPassport
{
	private static Map<Long, String> map = new HashMap<Long, String>();
	private static Timer _timer = null;
	
	static
	{
		TimerTask _timerTask = new TimerTask()
		{
			public void run()
			{
				System.out.println("--清理找回密码任务启动。--");
				try
				{
					if(map.size() > 0)
					{
						long now = java.util.Calendar.getInstance().getTimeInMillis();//取得当前时间
						//清空超时的记录
						for(Map.Entry<Long, String> entry: map.entrySet())
						{
							if(entry.getKey() < now)
							{
								map.put(entry.getKey(), null);
							}
						}
					}
				}
				catch(Exception ex)
				{
					System.out.println("--清理找回密码任务程序异常。--");
				}
			}
		};
		_timer = new Timer(true);
		_timer.schedule(_timerTask, 0, 3600000);// 从服务器启动开始运行,每period毫秒执行
		System.out.println("--清理找回密码任务执行间隔3600000毫秒。--");
	}
	
	public static Long addAccount(String account)
	{
		Long code = java.util.Calendar.getInstance().getTimeInMillis() + 1800000;
		map.put(code, account.toLowerCase());
		return code;
	}
	
	public static String getAccount(String code)
	{
		String account = map.get(code);
		return account == null ? "" : account;
	}
	public static void clearAccount(Long code)
	{
		map.put(code, null);
	}
	

}
