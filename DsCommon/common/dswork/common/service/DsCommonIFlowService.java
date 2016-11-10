package dswork.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonDaoIFlow;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;

@Service
public class DsCommonIFlowService
{
	@Autowired
	private DsCommonDaoIFlow dao;

	/**
	 * 流程启动
	 * @param alias 启动流程的标识
	 * @param ywlsh 业务流水号
	 * @param caccount 提交人账号
	 * @param cname 提交人姓名
	 * @param piDay 时限天数
	 * @param isWorkDay 时限天数类型(false日历日,true工作日)
	 * 
	 * @param taskInterface 接口类（暂时无用）
	 * @return 流程实例ID
	 */
	public String start(String alias, String ywlsh, String sblsh, String caccount, String cname, int piDay, boolean isWorkDay, String taskInterface)
	{
		try
		{
			return dao.saveStart(alias, ywlsh, sblsh, caccount, cname, piDay, isWorkDay, taskInterface);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 流程启动并返回开始节点待办信息
	 * @param alias 启动流程的标识
	 * @param ywlsh 业务流水号
	 * @param caccount 提交人账号
	 * @param cname 提交人姓名
	 * @param piDay 时限天数
	 * @param isWorkDay 时限天数类型(false日历日,true工作日)
	 * @param taskInterface 接口类（暂时无用）
	 * @return 流程实例的start待办信息或null
	 */
	public IFlowWaiting startFlow(String alias, String ywlsh, String sblsh, String caccount, String cname, int piDay, boolean isWorkDay, String taskInterface)
	{
		return dao.saveFlowStart(alias, ywlsh, sblsh, caccount, cname, piDay, isWorkDay, taskInterface);
	}
	
	public void stop(String piid)
	{
		dao.saveStop(Long.parseLong(piid));
	}

	/**
	 * 流程处理
	 * @param waitid 待办事项ID
	 * @param nextTalias 下级任务列表，如果为null，处理当前任务后，会结束流程
	 * @param paccount 当前处理人账号
	 * @param pname 当前处理人姓名
	 * @param resultType 处理类型
	 * @param resultMsg 处理意见
	 * @return true|false
	 */
	public boolean process(long waitid, String[] nextTalias, String paccount, String pname, String resultType, String resultMsg)
	{
		return dao.saveProcess(waitid, nextTalias, paccount, pname, resultType, resultMsg);
	}

	public List<IFlowWaiting> queryWaiting(String account)
	{
		return dao.queryFlowWaiting(account);
	}

	public boolean takeWaiting(long waitid, String user)
	{
		if(user != null && user.trim().length() > 0)
		{
			dao.updateFlowWaitingUser(waitid, user);
			return true;
		}
		return false;
	}

	public IFlowWaiting getWaiting(long waitid)
	{
		return dao.getFlowWaiting(waitid);
	}

	public Map<String, String> getTaskList(long flowid)
	{
		Map<String, String> map = new HashMap<String, String>();
		List<IFlowTask> list = dao.queryFlowTask(flowid);
		if(list != null)
		{
			for(IFlowTask m : list)
			{
				map.put(m.getTalias(), m.getTname());
			}
		}
		return map;
	}

	public List<IFlowPi> queryFlowPi(String ywlsh)
	{
		return dao.queryFlowPi(ywlsh);
	}

	public IFlowPi getFlowPi(String ywlsh)
	{
		return dao.getFlowPi(ywlsh);
	}

	public List<IFlowPiData> queryFlowPiData(String piid)
	{
		return dao.queryFlowPiData(Long.parseLong(piid));
	}
}
