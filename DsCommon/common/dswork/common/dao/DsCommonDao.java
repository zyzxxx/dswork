/**
 * 公共Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.IDict;
import dswork.common.model.IFlow;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;
import dswork.common.model.IOrg;
import dswork.core.db.MyBatisDao;
import dswork.core.util.TimeUtil;

@Repository
@SuppressWarnings("all")
public class DsCommonDao extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCommonDao.class;
	}

	// /////////////////////////////////////////////////////////////////////////
	// 字典
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 获取指定节点的列表数据
	 * @param name 字典分类名
	 * @param alias 上级标识，当alias为null时获取全部节点数据，当alias为""时获取根节点数据
	 * @return List&lt;IDsDict&gt;
	 */
	public List<IDict> queryListDict(String name, String parentAlias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", String.valueOf(name));
		map.put("alias", parentAlias);
		List<IDict> list = executeSelectList("queryDict", map);
		if(parentAlias != null && parentAlias.length() > 0)
		{
			for(IDict po : list)
			{
				po.setPid(parentAlias);
			}
		}
		return list;
	}

	// /////////////////////////////////////////////////////////////////////////
	// 组织机构
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * 根据上级组织机构主键取得列表数据
	 * @param pid 上级组织机构主键
	 * @param status 0-2为指定分类（2单位，1部门，0岗位），超出0-2范围则不过滤
	 * @return List&lt;DsCommonOrg&gt;
	 */
	public List<IOrg> queryListOrg(Long pid, Integer status)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		if(status != null && status.intValue() > -1 && status.intValue() < 3)
		{
			map.put("status", status);
		}
		List<IOrg> list = executeSelectList("queryOrg", map);
		if(pid == 0)
		{
			for(IOrg po : list)
			{
				po.setPid("0");
			}
		}
		return list;
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
