/**
 * 内容Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsPage;
import dswork.core.db.BaseDao;

@Repository
@SuppressWarnings("all")
public class DsCmsPageDao extends BaseDao<DsCmsPage, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPage.class;
	}

	/**
	 * 更新状态
	 * @param id 主键
	 * @param status 状态
	 */
	public void updateStatus(long id, Integer status)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		executeUpdate("updateStatus", map);
	}

	public int delete(long siteid, long categoryid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("categoryid", categoryid);
		return executeDelete("deleteNeedBeDelete", map);
	}
}
