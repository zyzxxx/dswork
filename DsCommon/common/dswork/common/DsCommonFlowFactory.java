package dswork.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowPiDoing;
import dswork.common.model.IFlowTask;
import dswork.common.service.DsCommoService;
import dswork.spring.BeanFactory;

public class DsCommonFlowFactory
{	
	private static DsCommoService getServcie(){return (DsCommoService) BeanFactory.getBean("dsCommoService");}
	private static DsCommoService service = getServcie();
	public static String start(String alias, String ywlsh, String caccount, String cname, String piDay, boolean isWorkDay, String taskInterface, String userInterface)
	{	
	}
	
	public static boolean process(Long doingid, String[] nextTalias, String paccount, String pname, String resultType, String resultMsg)
	{
	}
	
	public static List<IFlowPiDoing> getDoingList(String account)
	{
		DsCommoService service = new DsCommoService();
		return service.getDoingList(","+account+",");
	}
	
	public static IFlowTask getTask(String deployid,String talias)
	{
		DsCommoService service = new DsCommoService();
		return service.getTask(deployid, talias);
	}
}
