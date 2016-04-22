/**
 * 公共Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.IFlow;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;
import dswork.core.db.MyBatisDao;
import dswork.core.util.TimeUtil;

@Repository
@SuppressWarnings("all")
public class DsCommonDaoIFlow extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCommonDaoIFlow.class;
	}
	// /////////////////////////////////////////////////////////////////////////
	// 流程
	// /////////////////////////////////////////////////////////////////////////
	
	// ////////////////////////
	// 发布的流程配置
	public IFlow getFlow(String alias)
	{
		return (IFlow) executeSelect("selectFlow", alias);
	}
	
	// ////////////////////////
	// 流程实例
	public void saveFlowPi(IFlowPi flowpi)
	{
		executeInsert("insertFlowPi", flowpi);
	}
	public void updateFlowPi(Long id, int status, String pialias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("pialias", pialias);
		if(status == 0)
		{
			map.put("piend", TimeUtil.getCurrentTime());
		}
		executeUpdate("updateFlowPi", map);
	}
	
	// ////////////////////////
	// 流程任务配置
	public IFlowTask getFlowTask(Long flowid, String talias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid", flowid);
		map.put("talias", talias);
		return (IFlowTask) executeSelect("selectFlowTask", map);
	}
	public List<IFlowTask> queryFlowTask(Long flowid)
	{
		return executeSelectList("queryFlowTask", flowid);
	}
	
	// ////////////////////////
	// 流程实例明细
	public void saveFlowPiData(IFlowPiData pidata)
	{
		executeInsert("insertFlowPiData", pidata);
	}
	public List<IFlowPiData> queryFlowPiData(Long piid)
	{
		return executeSelectList("queryFlowPiData", piid);
	}
	
	// ////////////////////////
	// 流程实例待办
	public void saveFlowWaiting(IFlowWaiting m)
	{
		executeInsert("insertFlowWaiting", m);
	}
	public void deleteFlowWaiting(Long id)
	{
		executeDelete("deleteFlowWaiting", id);
	}
	public void deleteFlowWaitingByPiid(Long piid)
	{
		executeDelete("deleteFlowWaitingByPiid", piid);
	}
	public void updateFlowWaiting(Long id, String tstart)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("tstart", tstart);
		executeUpdate("updateFlowWaiting", map);
	}
	public void updateFlowWaitingUser(Long id, String tuser)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("tuser", tuser);
		executeUpdate("updateFlowWaitingUser", map);
	}
	public IFlowWaiting getFlowWaiting(Long id)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (IFlowWaiting) executeSelect("selectFlowWaiting", map);
	}
	public IFlowWaiting getFlowWaitingByPiid(Long piid, String talias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("piid", piid);
		map.put("talias", talias);
		return (IFlowWaiting) executeSelect("selectFlowWaitingByPiid", map);
	}
	public List<String> queryFlowWaitingTalias(Long piid)
	{
		return executeSelectList("queryFlowWaitingTalias", piid);
	}
	public List<IFlowWaiting> queryFlowWaiting(String account)
	{
		return executeSelectList("queryFlowWaiting", account);
	}
}
