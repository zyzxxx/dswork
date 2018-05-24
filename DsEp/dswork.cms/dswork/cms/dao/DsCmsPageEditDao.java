/**
 * 内容Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsPageEdit;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsPageEditDao extends BaseDao<DsCmsPageEdit, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsPageEdit.class;
	}

	public int queryCount(Map<String, Object> map)
	{
		return queryCount("queryCount", map);
	}

	public int delete(long siteid, long categoryid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("categoryid", categoryid);
		return executeDelete("deleteNeedBeDelete", map);
	}

	public DsCmsPageEdit getByPushkey(String pushkey)
	{
		return (DsCmsPageEdit) executeSelect("selectByPushkey", pushkey);
	}

	public int updateUrl(Long id, String url)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("url", url);
		return executeUpdate("updateUrl", map);
	}
}
