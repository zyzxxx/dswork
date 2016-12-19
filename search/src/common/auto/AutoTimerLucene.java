package common.auto;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import common.lucene.LuceneUtil;

//import dswork.core.util.TimeUtil;
//import dswork.spring.BeanFactory;

public class AutoTimerLucene extends Thread
{
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	private Timer _timer = null;
	private static int count = 0;
	private static synchronized int getCount() {return AutoTimerLucene.count;}
	private static synchronized void setCount(int count) {AutoTimerLucene.count = count;}// count仅用于标记是否启动任务，1为启动，0为不启动
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
	 * 启动线程，该线程仅负责定时器的运行
	 */
	public void run()
	{
		final long index = System.currentTimeMillis();
		System.out.println("********AutomaticExecute线程启动********");
		System.out.println("标识号：" + index);
		if(AutoTimerLucene.getCount() != 0)//上次的任务还没有结束呢，不需要重复执行 了
		{
			System.out.println("********AutomaticExecute线程结束 ，因上次线程任务还在运行，本次不执行********");
			System.out.println("标识号：" + index);
			return;
		}
		AutoTimerLucene.setCount(1);//标记任务启动
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					System.out.println("--AutomaticExecute定时任务启动。--");
					System.out.println("标识号：" + index);
					try
					{
						LuceneUtil.reload();
						AutoTimerLucene.setCount(0);// 退出
						System.out.println("--AutomaticExecute定时任务结束，已无待发信息。--");
						System.out.println("标识号：" + index);
					}
					catch(Exception ex)
					{
						AutoTimerLucene.setCount(0);// 退出
						System.out.println("--AutomaticExecute定时任务异常。--");
						System.out.println("标识号：" + index);
						ex.printStackTrace();
						_timer.cancel();
					}
				}
			};
			_timer = new Timer(true);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			_timer.schedule(_timerTask, cal.getTime(), LuceneUtil.Refreshtime);// 从date开始,每period毫秒执行task.
			
			//_timer.schedule(_timerTask, 0, 60000);// 从服务器启动开始运行,每period毫秒执行
			System.out.println("--发送程序执行间隔60000毫秒。--");
		}
		catch(Exception ex)
		{
			try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
			ex.printStackTrace();
			System.out.println("********AutomaticExecute线程异常结束 ********");
			System.out.println("标识号：" + index);
			AutoTimerLucene.setCount(0);
		}
	}
	/*
	 * 调用toStart(),启动定时清理程序
	 */
	public static final void toStart()
	{
		AutoTimerLucene pj = new AutoTimerLucene();
		pj.start();//启动程序
	}
}
