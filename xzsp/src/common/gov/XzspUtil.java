package common.gov;

import MQAPI.AcceptOB;
import MQAPI.ApplicationOB;
import MQAPI.BlockOB;
import MQAPI.CompleteOB;
import MQAPI.MQFun;
import MQAPI.PreAcceptOB;
import MQAPI.ReceiveRegOB;
import MQAPI.ResumeOB;
import MQAPI.SubmitOB;
import MQAPI.SupplyAcceptOB;
import MQAPI.SupplyOB;

public class XzspUtil
{
	/**
	 * 初始化接口对象
	 */
	private static MQFun mqapi;
	static
	{
		try
		{
			mqapi = MQFun.Create(dswork.core.util.EnvironmentUtil.getToString("gov.xzsp.path", "C:/DC_CLIENT"));
			if(mqapi == null)
			{
				System.out.println("DC_CLIENT初始化失败");
			}
		}
		catch(Exception ex)
		{
			System.out.println("DC_CLIENT初始化失败");
			ex.printStackTrace();
		}
	}

	/**
	 * 申办
	 */
	private static int applicationOB(ApplicationOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 预受理
	 */
	private static int preAcceptOB(PreAcceptOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 受理
	 */
	private static int aceeptOB(AcceptOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 审批
	 */
	private static int submitOB(SubmitOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 补交告知
	 */
	private static int supplyOB(SupplyOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 补交受理
	 */
	private static int supplyAcceptOB(SupplyAcceptOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 特别程序申请
	 */
	private static int blockOB(BlockOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 特别程序结果
	 */
	private static int resumeOB(ResumeOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 办结
	 */
	private static int completeOB(CompleteOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 领取登记
	 */
	private static int receiveRegOB(ReceiveRegOB entity)
	{
		try
		{
			return mqapi.Send(entity);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}
	
	public static int sendObject(Object obj)
	{
//		int i = -1;
//		if(obj instanceof ApplicationOB){i = 0;}// ShenBan
//		else if(obj instanceof PreAcceptOB){i = 1;}// YuShouLi
//		else if(obj instanceof AcceptOB){i = 2;}// ShouLi
//		else if(obj instanceof SubmitOB){i = 3;}// ShenPi
//		else if(obj instanceof CompleteOB){i = 4;}// BanJie
//		else if(obj instanceof BlockOB){i = 5;}// TeBieChengXuQiDong
//		else if(obj instanceof ResumeOB){i = 6;}// TeBieChengXuBanJie
//		else if(obj instanceof SupplyOB){i = 7;}// BuJiaoGaoZhi
//		else if(obj instanceof SupplyAcceptOB){i = 8;}// BuJiaoShouLi
//		else if(obj instanceof ReceiveRegOB){i = 9;}// LingQuDengJi
		try
		{
			return mqapi.Send(obj);// 发送对象
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
	}
}
