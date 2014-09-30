package dswork.common;

import java.util.List;

import dswork.common.model.IFlowPiDoing;
import dswork.common.model.IFlowTask;
import dswork.common.service.DsCommoService;
import dswork.spring.BeanFactory;

public class DsCommonFlowFactory
{
	private static DsCommoService getServcie()
	{
		return (DsCommoService) BeanFactory.getBean("DsCommoService");
	}
	private static DsCommoService service = getServcie();

	public static String start(String alias, String ywlsh, String caccount, String cname, String piDay, boolean isWorkDay, String taskInterface, String userInterface)
	{
		try
		{
			return service.start(alias, ywlsh, caccount, cname, piDay, isWorkDay, taskInterface, userInterface);
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
			return service.process(doingid, nextTalias, paccount, pname, resultType, resultMsg);
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static List<IFlowPiDoing> getDoingList(String account)
	{
		try
		{
			return service.getDoingList("," + account + ",");
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static IFlowTask getTask(String deployid, String talias)
	{
		try
		{
			return service.getTask(deployid, talias);
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
