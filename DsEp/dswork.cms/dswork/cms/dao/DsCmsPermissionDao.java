/**
 * 采编审核权限Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.core.db.MyBatisDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;

@Repository
@SuppressWarnings("all")
public class DsCmsPermissionDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPermission.class;
	}
	
	public int save(DsCmsPermission entity)
	{
		return executeInsert("insert", entity);
	}
	public int delete(long siteid, String account)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("account", account);
		return executeDelete("delete", map);
	}
	public int update(DsCmsPermission entity)
	{
		return executeUpdate("update", entity);
	}
	public DsCmsPermission get(Long siteid, String account)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("account", account);
		return (DsCmsPermission) executeSelect("select", map);
	}

	public List<DsCmsSite> queryListSite()
	{
		return queryList("queryListSite", new HashMap<String, Object>());
	}
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return queryList("queryListCategory", map);
	}
	public List<DsCmsPermission> queryListPermission(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return queryList("queryListPermission", map);
	}
}