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
import dswork.common.model.IFlowPiDoing;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;
import dswork.common.model.IOrg;
import dswork.core.db.MyBatisDao;

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
	// 流程配置
	public String getFlowDeployid(String alias)
	{
		return (String) executeSelect("selectFlowDeployid", alias);
	}
	
	// ////////////////////////
	// 流程实例
	public void saveFlowPi(IFlowPi flowpi)
	{
		executeInsert("insertFlowPi", flowpi);
	}
	public void updateFlowPiStatus(Long id)
	{
		executeUpdate("updateFlowPiStatus", id);
	}
	public void updateFlowPiPialias(Long id, String pialias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pialias", pialias);
		executeUpdate("updateFlowPiPialias", map);
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
	
	// ////////////////////////
	// 流程实例明细
	public void saveFlowPiData(IFlowPiData pidata)
	{
		executeInsert("insertFlowPiData", pidata);
	}
	
	// ////////////////////////
	// 流程实例待办
	public void saveFlowPiDoing(IFlowPiDoing flowpidoing)
	{
		executeInsert("insertFlowPiDoing", flowpidoing);
	}
	public void deleteFlowPiDoing(Long id)
	{
		executeDelete("deleteFlowPiDoing", id);
	}
	public void updateFlowPiDoing(Long piid, String alias, String tstart)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("piid", piid);
		map.put("alias", alias);
		map.put("tstart", tstart);
		executeUpdate("updateFlowPiDoing", map);
	}
	public IFlowPiDoing getFlowPiDoing(Long id)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (IFlowPiDoing) executeSelect("selectFlowPiDoing", map);
	}
	public boolean isExistsFlowPiDoing(Long piid, String alias)
	{
		IFlowPiDoing pidoing = getFlowPiDoingByPiid(piid, alias);
		if(pidoing != null && pidoing.getId().longValue() != 0)
		{
			return true;
		}
		return false;
	}
	public IFlowPiDoing getFlowPiDoingByPiid(Long piid, String alias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("piid", piid);
		map.put("alias", alias);
		return (IFlowPiDoing) executeSelect("getFlowPiDoingByPiid", map);
	}
	public List<String> queryFlowPiDoingTalias(Long piid)
	{
		return executeSelectList("queryFlowPiDoingTalias", piid);
	}
	
	public List<IFlowWaiting> queryFlowWaiting(String account)
	{
		return executeSelectList("queryFlowWaiting", account);
	}
}
