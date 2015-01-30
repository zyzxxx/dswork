package common.automatic;

//import java.util.ArrayList;
//import java.util.Calendar;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
//import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import common.gov.DsXzsp;
import common.gov.DsXzspDao;
import common.gov.XzspUtil;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;

//import dswork.core.util.TimeUtil;
//import dswork.spring.BeanFactory;

public class AutomaticExecuteXzsp extends Thread
{
	private static final int time = (int)dswork.core.util.EnvironmentUtil.getToLong("gov.xzsp.time", 300000L);// 每300秒尝试调用接口
	// ################################################################################################
	// 定时任务相关
	// ################################################################################################
	//boolean retry = false;// 用于判断是否有定时发送，但未到时间
	private Timer _timer = null;
	private static int count = 0;
	private static synchronized int getCount() {return AutomaticExecuteXzsp.count;}
	private static synchronized void setCount(int count) {AutomaticExecuteXzsp.count = count;}// count仅用于标记是否启动任务，1为启动，0为不启动
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
		System.out.println("--发送程序启动。--");
		try
		{
			TimerTask _timerTask = new TimerTask()
			{
				public void run()
				{
					if(AutomaticExecuteXzsp.getCount() != 0)//启动中，即上次的任务还没执行完
					{
						System.out.println("--上次发送线程未结束，本次不执行。--");
						return;
					}
					AutomaticExecuteXzsp.setCount(1);//标记启动
					System.out.println("--发送线程启动。--");
					try
					{
						init();
						//long now = Calendar.getInstance().getTimeInMillis();//取得当前时间
						////DxService dxService = (DxService)BeanFactory.getBean("dxService");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("fszt", "0");// 待发送
						map.put("fscs_end", "10");// 发送次数少于n次的
						List<DsXzsp> list = dao.queryList(new PageRequest(map));
						for(DsXzsp m : list)
						{
							m.setFscs(m.getFscs() + 1);
							m.setFssj("");
							m.setMemo("未知错误。");
							try
							{
								Object o = outputObject(m.getSpobject());
								if(o != null)
								{
									int result = XzspUtil.sendObject(o);
									m.setFszt((result == 1) ? 1 : 0);
									switch(result)
									{
										case 1:
											m.setMemo("信息发送成功");
											m.setFssj(TimeUtil.getCurrentTime());
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
								//XzspUtil.
								//long send = TimeUtil.convertString(m.getFssj(), "yyyy-MM-dd HH:mm:ss").getTime();
								//if(send > now)// 未到时间发送
								//{
								//	retry = true;// 有未到时间暂时不发送的信息
								//}
								//else
								//{
									////dxService.updateStatus(m.getId(), 1);// 发送成功，更新状态
								//}
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
			//try {_timer.cancel();}catch(Exception timerEx) {}//尝试停止进程，即使它未初始化或未启动
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
		pj.start();//启动程序
	}

	public static Object outputObject(String v)
	{
		try
		{
			Object o = null;
			ByteArrayInputStream bais = new ByteArrayInputStream(v.getBytes("ISO-8859-1"));
			java.io.ObjectInputStream ois = new ObjectInputStream(bais);
			o = ois.readObject();
			ois.close();
			bais.close();
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
