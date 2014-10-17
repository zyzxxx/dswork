package dswork.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.common.model.IFlowTask;
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
			e.printStackTrace();
		}
		return "";
	}
	
	public static void stop(String piid)
	{
		try
		{
			init();
			service.saveStop(Long.parseLong(piid));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean process(long waitid, String[] nextTalias, String paccount, String pname, String resultType, String resultMsg)
	{
		try
		{
			init();
			return service.saveProcess(waitid, nextTalias, paccount, pname, resultType, resultMsg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
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
			e.printStackTrace();
		}
		return null;
	}

	public static boolean takeWaiting(long waitid, String user)
	{
		try
		{
			if(user != null && user.trim().length() > 0)
			{
				service.updateFlowWaitingUser(waitid, user);
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static IFlowWaiting getWaiting(long waitid)
	{
		try
		{
			init();
			return service.getFlowWaiting(waitid);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getTaskList(long flowid)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			init();
			List<IFlowTask> list = service.queryFlowTask(flowid);
			if(list != null)
			{
				for(IFlowTask m : list)
				{
					map.put(m.getTalias(), m.getTname());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
}
