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
	public static int applicationOB(ApplicationOB entity)
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
	public static int preAcceptOB(PreAcceptOB entity)
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
	public static int aceeptOB(AcceptOB entity)
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
	public static int submitOB(SubmitOB entity)
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
	public static int supplyOB(SupplyOB entity)
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
	public static int supplyAcceptOB(SupplyAcceptOB entity)
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
	public static int blockOB(BlockOB entity)
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
	public static int resumeOB(ResumeOB entity)
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
	public static int completeOB(CompleteOB entity)
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
	public static int receiveRegOB(ReceiveRegOB entity)
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
}
