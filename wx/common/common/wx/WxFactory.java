package common.wx;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dswork.core.util.EnvironmentUtil;
import dswork.core.util.EncryptUtil;
import dswork.wx.api.SnsApi;
import dswork.wx.api.TokenApi;
import dswork.wx.model.token.Token;

//import dswork.core.util.TimeUtil;
//import dswork.spring.BeanFactory;
/**
 * 单微信号模式
 * @author skey
 */
public class WxFactory extends Thread
{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static String accessToken = null;
	// 临时目录初始化，在服务器启动时执行
	static
	{
		try
		{
			toStart();// 马上执行
		}
		catch(Exception ex)
		{
			System.out.println("未知错误");
		}
	}

	public static String getAccessToken()
	{
		return accessToken;
	}
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	// boolean retry = false;// 用于判断是否有定时发送，但未到时间
	private Timer _timer = null;
	private static int count = 0;

	private static synchronized int getCount()
	{
		return WxFactory.count;
	}

	// count仅用于标记是否启动任务，1为启动，0为不启动
	private static synchronized void setCount(int count)
	{
		WxFactory.count = count;
	}

	/**
	 * 启动线程
	 */
	public void run()
	{
		if(WxFactory.getCount() != 0)// 启动中
		{
			return;
		}
		WxFactory.setCount(1);// 标记启动
		log.info("--WxFactory线程启动--");
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					log.info("--WxFactory定时任务开始执行--");
					try
					{
						//根据APPID,APPSECRET获取access_token
						Token x = TokenApi.token(APPID, SECRET);
						// 更新当前token
						accessToken = x.getAccess_token();
						log.info("expires_in:" + x.getExpires_in() + ", access_token:" + x.getAccess_token());
						log.info("--WxFactory定时任务正常退出--");
					}
					catch(Exception ex)
					{
						log.error("--WxFactory定时任务异常退出--");
						log.error(ex.getMessage());
					}
					_timer.cancel();
					WxFactory.setCount(0);// 退出
				}
			};
			_timer = new Timer(true);
			// Timer.schedule(TimerTask task, Date date, long period)// 从date开始,每period毫秒执行task.
			_timer.schedule(_timerTask, 0, 5400000);// 从服务器启动开始运行,每period毫秒执行
			log.info("--微信access_token获取程序执行间隔5400秒--");
		}
		catch(Exception ex)
		{
			try
			{
				_timer.cancel();// 尝试停止进程，即使它未初始化或未启动
			}
			catch(Exception timerEx)
			{
			}
			log.error("--WxFactory线程异常结束--");
			log.error(ex.getMessage());
			WxFactory.setCount(0);
		}
	}

	/*
	 * 调用toStart(),启动定时清理程序
	 */
	public static final void toStart()
	{
		WxFactory pj = new WxFactory();
		pj.start();// 启动程序
	}

	public static final String APPID = EnvironmentUtil.getToString("wx.appid", "");
	public static final String SECRET = EnvironmentUtil.getToString("wx.secret", "");
	public static final String TOKEN = EnvironmentUtil.getToString("wx.token", "");

	public static void main(String[] args)
	{
		try
		{
			java.util.Scanner s = new java.util.Scanner(System.in);
			String str = s.next();
			System.out.println(str);
		}
		catch(Exception e)
		{
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getLoginURL(String url, String state, boolean hasQrcode)
	{
		if(hasQrcode)
		{
			return SnsApi.connectQrCode(APPID, url, true, state, null);
		}
		else
		{
			return SnsApi.connectOauth2Code(APPID, url, true, state);
		}
	}
	
	public static String getWxOpenid(String code)
	{
		return SnsApi.oauth2AccessToken(APPID, SECRET, code).getOpenid();
	}
}
