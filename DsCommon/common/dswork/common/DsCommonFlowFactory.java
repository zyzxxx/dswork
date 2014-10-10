package dswork.common;

import java.util.List;

import dswork.common.model.IFlowWaiting;
import dswork.common.service.DsCommonService;
import dswork.spring.BeanFactory;

public class DsCommonFlowFactory
{
	private static DsCommonService service = null;

	private static void init()
	{
		if(service == null)
		{
			service = (DsCommonService) BeanFactory.getBean("dsCommonService");
		}
	}

	public static String start(String alias, String ywlsh, String caccount, String cname, int piDay, boolean isWorkDay, String taskInterface)
	{
		try
		{
			init();
			return service.saveStart(alias, ywlsh, caccount, cname, piDay, isWorkDay, taskInterface);
		}
		catch(Exception e)
		{
			return "";
		}
	}

	public static boolean process(Long doingid, String[] nextTalias, String paccount, String pname, String resultType, String resultMsg)
	{
		try
		{
			init();
			return service.saveProcess(doingid, nextTalias, paccount, pname, resultType, resultMsg);
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static List<IFlowWaiting> queryWaiting(String account)
	{
		try
		{
			init();
			return service.queryFlowWaiting(account);
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
