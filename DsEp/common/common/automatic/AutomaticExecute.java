package common.automatic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dswork.core.util.TimeUtil;
import dswork.spring.BeanFactory;

public class AutomaticExecute extends Thread
{
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	//boolean retry = false;// 用于判断是否有定时发送，但未到时间
	private Timer _timer = null;
	private static int count = 0;
	private static synchronized int getCount() {return AutomaticExecute.count;}
	private static synchronized void setCount(int count) {AutomaticExecute.count = count;}// count仅用于标记是否启动任务，1为启动，0为不启动
	static//临时目录初始化，在服务器启动时执行
	{
		try
		{
			//toStart();//马上执行
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
		if(AutomaticExecute.getCount() != 0)//启动中
		{
			return;
		}
		AutomaticExecute.setCount(1);//标记启动
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
						////DxService dxService = (DxService)BeanFactory.getBean("dxService");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userstatus", "0");
						map.put("status", "0");// 待发送
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
							map.put("userstatus", "0");
							map.put("status", "0");// 待发送
							////list = dxService.queryList(map);
							////if(list == null || list.size() == 0)
							////{
								AutomaticExecute.setCount(0);// 退出
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
						AutomaticExecute.setCount(0);// 退出
						System.out.println("--发送程序异常。--");
						ex.printStackTrace();
						_timer.cancel();
					}
				}
			};
			_timer = new Timer(true);
			// Timer.schedule(TimerTask task, Date date, long period)// 从date开始,每period毫秒执行task.
			_timer.schedule(_timerTask, 0, 60000);// 从服务器启动开始运行,每period毫秒执行
			System.out.println("--发送程序执行间隔60000毫秒。--");
		}
		catch(Exception ex)
		{
			try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
			ex.printStackTrace();
			System.out.println("--发送程序异常结束。--");
			AutomaticExecute.setCount(0);
		}
	}
	/*
	 * 调用toStart(),启动定时清理程序
	 */
	public static final void toStart()
	{
		AutomaticExecute pj = new AutomaticExecute();
		pj.start();//启动程序
	}
}
