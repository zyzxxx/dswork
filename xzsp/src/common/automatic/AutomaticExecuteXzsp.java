package common.automatic;

import java.util.Timer;
import java.util.TimerTask;

import common.gov.DsXzspDao;

public class AutomaticExecuteXzsp extends Thread
{
	private static final int time = (int) dswork.core.util.EnvironmentUtil.getToLong("gov.xzsp.time", 300000L);// 每300秒尝试调用接口
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	// boolean retry = false;// 用于判断是否有定时发送，但未到时间
	private Timer _timer = null;
	private static int count = 0;

	private static synchronized int getCount()
	{
		return AutomaticExecuteXzsp.count;
	}

	private static synchronized void setCount(int count)
	{
		AutomaticExecuteXzsp.count = count;
	}// count仅用于标记是否启动任务，1为启动，0为不启动

	/**
	 * 启动线程
	 */
	@SuppressWarnings("unchecked")
	public void run()
	{
		System.out.println("--发送程序启动。--");
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					if(AutomaticExecuteXzsp.getCount() != 0)// 启动中，即上次的任务还没执行完
					{
						System.out.println("--上次发送线程未结束，本次不执行。--");
						return;
					}
					AutomaticExecuteXzsp.setCount(1);// 标记启动
					System.out.println("--发送线程启动。--");
					try
					{
						init();
						java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
						map.put("fszt", "0");// 待发送
						map.put("fscs_end", "10");// 发送次数少于n次的
						java.util.List<common.gov.DsXzsp> list = dao.queryList(new dswork.core.page.PageRequest(map));
						for(common.gov.DsXzsp m : list)
						{
							m.setFscs(m.getFscs() + 1);
							m.setFssj("");
							m.setMemo("未知错误。");
							try
							{
								Object o = outputObject(m.getSpobject(), m.getSptype());
								if(o != null)
								{
									int result = common.gov.XzspUtil.sendObject(o);
									m.setFszt((result == 1) ? 1 : 0);
									switch(result)
									{
										case 1:
											m.setMemo("信息发送成功");
											m.setFssj(dswork.core.util.TimeUtil.getCurrentTime());
											break;
										case 2:
											m.setMemo("信息发送失败，网络不通或者配置不对。");
											break;
										case 3:
											m.setMemo("封装的信息不符合规范要求，数据未发送成功，需要重发。");
											m.setFszt(3);
											break;
										case 4:
											m.setMemo("连不到数据交换平台。");
											break;
									}
								}
								else
								{
									m.setFszt(3);
									m.setMemo("封装的信息不符合规范要求，数据未发送成功，需要重发。");
								}
							}
							catch(Exception mex)
							{
								mex.printStackTrace();
								m.setFszt(0);
								m.setMemo(mex.getMessage());
							}
							dao.update(m);
						}
					}
					catch(Exception ex)
					{
						AutomaticExecuteXzsp.setCount(0);// 退出
						System.out.println("--发送程序异常。--");
						ex.printStackTrace();
					}
					AutomaticExecuteXzsp.setCount(0);// 退出
					System.out.println("--发送线程结束。--");
				}
			};
			_timer = new Timer(true);
			// Timer.schedule(TimerTask task, Date date, long period)// 从date开始,每period毫秒执行task.
			_timer.schedule(_timerTask, 0, time);// 从服务器启动开始运行,每period毫秒执行
			System.out.println("--发送程序执行间隔60000毫秒。--");
		}
		catch(Exception ex)
		{
			AutomaticExecuteXzsp.setCount(0);
			// try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
			ex.printStackTrace();
			System.out.println("--发送程序异常结束。--");
		}
	}

	/*
	 * 调用toStart(),启动定时清理程序
	 */
	public static final void toStart()
	{
		AutomaticExecuteXzsp pj = new AutomaticExecuteXzsp();
		pj.start();// 启动程序
	}

	private static com.google.gson.GsonBuilder builder = new com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Object outputObject(String v, int i)
	{
		try
		{
			com.google.gson.Gson gson = builder.create();
			Object o = null;
			if(i == 0){o = gson.fromJson(v, MQAPI.ApplicationOB.class);}// ShenBan
			else if(i == 1){o = gson.fromJson(v, MQAPI.PreAcceptOB.class);}// YuShouLi
			else if(i == 2){o = gson.fromJson(v, MQAPI.AcceptOB.class);}// ShouLi
			else if(i == 3){o = gson.fromJson(v, MQAPI.SubmitOB.class);}// ShenPi
			else if(i == 4){o = gson.fromJson(v, MQAPI.CompleteOB.class);}// BanJie
			else if(i == 5){o = gson.fromJson(v, MQAPI.BlockOB.class);}// TeBieChengXuQiDong
			else if(i == 6){o = gson.fromJson(v, MQAPI.ResumeOB.class);}// TeBieChengXuBanJie
			else if(i == 7){o = gson.fromJson(v, MQAPI.SupplyOB.class);}// BuJiaoGaoZhi
			else if(i == 8){o = gson.fromJson(v, MQAPI.SupplyAcceptOB.class);}// BuJiaoShouLi
			else if(i == 9){o = gson.fromJson(v, MQAPI.ReceiveRegOB.class);}// LingQuDengJi
			return o;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	private static DsXzspDao dao;

	private static void init()
	{
		if(dao == null)
		{
			dao = (DsXzspDao) dswork.spring.BeanFactory.getBean("dsXzspDao");
		}
	}
}
