package common.wx;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.reflect.TypeToken;

import common.JSONUtil;
import dswork.core.util.EnvironmentUtil;
import dswork.http.HttpUtil;

//import dswork.core.util.TimeUtil;
//import dswork.spring.BeanFactory;

public class WxExecute extends Thread
{
	private static String accessToken = null;
	private long _time = 0;
	public static String getAccessToken()
	{
		return accessToken;
	}
	
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	//boolean retry = false;// 用于判断是否有定时发送，但未到时间
	private Timer _timer = null;
	private static int count = 0;
	private static synchronized int getCount() {return WxExecute.count;}
	private static synchronized void setCount(int count) {WxExecute.count = count;}// count仅用于标记是否启动任务，1为启动，0为不启动
	static//临时目录初始化，在服务器启动时执行
	{
		try
		{
			toStart();//马上执行
		}
		catch(Exception ex)
		{
			System.out.println("未知错误");
		}
	}
	/**
	 * 启动线程
	 */
	public void run()
	{
		if(WxExecute.getCount() != 0)//启动中
		{
			return;
		}
		WxExecute.setCount(1);//标记启动
		System.out.println("--发送程序启动。--");
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					System.out.println("--发送线程启动。--");
					try
					{
						//long now = Calendar.getInstance().getTimeInMillis();//取得当前时间
						HttpUtil http = new HttpUtil();
						//grant_type 获取access_token填写client_credential
						String json = http.create(getAccessTokenURL, false).connect();
						Map<String, String> map = JSONUtil.toBean(json, new TypeToken<Map<String, String>>(){}.getType());
						accessToken = String.valueOf(map.get("access_token")).trim();
						
						System.out.println("expires_in:" + map.get("expires_in") + ", access_token:" + map.get("access_token"));
						
						
						
						
						
						
						
						////DxService dxService = (DxService)BeanFactory.getBean("dxService");
						//Map<String, Object> map = new HashMap<String, Object>();
						//map.put("userstatus", "0");
						//map.put("status", "0");// 待发送
						////List<Dx> list = dxService.queryList(map);
						////for(Dx m : list)
						////{
							//long send = TimeUtil.convertString(m.getFssj(), "yyyy-MM-dd HH:mm:ss").getTime();
							//if(send > now)// 未到时间发送
							//{
							//	retry = true;// 有未到时间暂时不发送的信息
							//}
							//else
							//{
								////dxService.updateStatus(m.getId(), 1);// 发送成功，更新状态
							//}
						////}
						//if(!retry)// 没有需要发送的
						//{
							// 执行完再判断一次
							map.clear();
							//map.put("userstatus", "0");
							//map.put("status", "0");// 待发送
							////list = dxService.queryList(map);
							////if(list == null || list.size() == 0)
							////{
								WxExecute.setCount(0);// 退出
								System.out.println("--已无待发信息，发送线程和程序自动结束。--");
								_timer.cancel();// 全部发完了结束
							////}
							//else{}//又出现了新的待发信息，等下次启动再执行
						//}
						//else
						//{
						//	System.out.println("--还有待发信息，等待发送线程定时执行。--");
						//}
					}
					catch(Exception ex)
					{
						WxExecute.setCount(0);// 退出
						System.out.println("--发送程序异常。--");
						ex.printStackTrace();
						_timer.cancel();
					}
				}
			};
			_timer = new Timer(true);
			// Timer.schedule(TimerTask task, Date date, long period)// 从date开始,每period毫秒执行task.
			_timer.schedule(_timerTask, 0, 5400000);// 从服务器启动开始运行,每period毫秒执行
			System.out.println("--微信access_token获取程序执行间隔5400秒。--");
		}
		catch(Exception ex)
		{
			try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
			ex.printStackTrace();
			System.out.println("--发送程序异常结束。--");
			WxExecute.setCount(0);
		}
	}
	/*
	 * 调用toStart(),启动定时清理程序
	 */
	public static final void toStart()
	{
		WxExecute pj = new WxExecute();
		pj.start();//启动程序
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			getAccessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + "wxd5ad49acc5250549" + "&secret=" + "6b179b6c1b09dcff70fc49af09a5f751";
			java.util.Scanner s = new java.util.Scanner(System.in);
			String str = s.next();
			System.out.println(str);
		}
		catch(Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static boolean checkSign(String signature, String timestamp, String nonce)
	{
		String[] arr = {nonce, timestamp, token};
		Arrays.sort(arr);
		String k = "";
		for(int i = 0; i < arr.length; i++){
			k += arr[i];
		}
		String c = Sha1Util.getSha1(k);
		return signature.equals(c);
	}


	/**
	 * 获取微信用户授权码code
	 * @param uri 授权后跳转地址
	 * @param state 可以带上a-zA-Z0-9最多128字节
	 * @param getinfo 是否需要用户基本信息,false时只能获取openid
	 * @return String
	 */
	public static String getWxURL(String uri, String state, boolean getinfo)
	{
		try
		{
			String c = getinfo ? "snsapi_userinfo" : "snsapi_base";
			return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + encodeURL(uri) + "&response_type=code&scope=" + c + "&state=" + state + "#wechat_redirect";
		}
		catch(Exception e)
		{
			return "";
		}
	}

	/**
	 * 直接根据code获取基本信息
	 * @param code
	 * @return String
	 */
	public static Map<String, String> getWxToken(String code)
	{
		try
		{
			/*{
			   "access_token":"ACCESS_TOKEN",
			   "expires_in":7200,
			   "refresh_token":"REFRESH_TOKEN",
			   "openid":"OPENID",
			   "scope":"SCOPE"
			}*/
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
			HttpUtil http = new HttpUtil();
			String json = http.create(url).connect();
			Map<String, String> map = JSONUtil.toBean(json, new TypeToken<Map<String, String>>(){}.getType());
			return map;
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	/**
	 * 直接根据code获取openid
	 * @param code
	 * @return String
	 */
	public static String getWxOpenid(String code)
	{
		return getWxToken(code).get("openid");
	}

	// state可以带上a-zA-Z0-9最多128字节
	public static String getWxUserInfo(String code)
	{
		try
		{
			/*{
			   "access_token":"ACCESS_TOKEN",
			   "expires_in":7200,
			   "refresh_token":"REFRESH_TOKEN",
			   "openid":"OPENID",
			   "scope":"SCOPE"
			}*/
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
			HttpUtil http = new HttpUtil();
			String json = http.create(url).connect();
			Map<String, String> map = JSONUtil.toBean(json, new TypeToken<Map<String, String>>(){}.getType());
			return map.get("openid");
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	public static final String appid = EnvironmentUtil.getToString("wx.appid", "");
	public static final String secret = EnvironmentUtil.getToString("wx.secret", "");
	public static final String token = EnvironmentUtil.getToString("wx.token", "");
	private static String getAccessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
	private static String encodeURL(String uri)
	{
		try
		{
			return URLEncoder.encode(uri, "ISO-8859-1");
		}
		catch(Exception e)
		{
			return "";
		}
	}
}
