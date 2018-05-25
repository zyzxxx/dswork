/**
 * CMSDao
 */
package common.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;

@Repository
@SuppressWarnings("all")
public class DsCmsDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsDao.class;
	}

	public Map<String, Object> getSite(Long siteid)
	{
		return (Map)executeSelect("getSite", siteid);
	}

	public Map<String, Object> getCategory(Long siteid, Long categoryid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("id", categoryid);
		return (Map)executeSelect("getCategory", map);
	}

	public Map<String, Object> get(Long siteid, Long pageid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("id", pageid);
		return (Map)executeSelect("get", map);
	}

	public Page<Map<String, Object>> queryPage(Long siteid, int currentPage, int pageSize, String idArray, Boolean isDesc, boolean onlyImageTop, boolean onlyPageTop, String keyvalue)
	{
		if(currentPage <= 0){currentPage = 1;}
		if(pageSize <= 0){pageSize = 25;}
		Map<String, Object> map = new HashMap<String, Object>();
		PageRequest rq = new PageRequest(currentPage, pageSize, map);
		rq.getFilters().put("siteid", siteid);
		rq.getFilters().put("idArray", idArray);
		rq.getFilters().put("order", isDesc==null||isDesc?" desc ":"");
		rq.getFilters().put("imgtop", onlyImageTop?"1":"");
		rq.getFilters().put("pagetop", onlyPageTop?"1":"");
		rq.getFilters().put("releasetime", TimeUtil.getCurrentTime());
		rq.getFilters().put("status", "8");// 已发布
		rq.getFilters().put("keyvalue", keyvalue);
		return queryPage("query", rq, "queryCount", rq);
	}

	public List<Map<String, Object>> queryCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return (List)executeSelectList("queryCategory", map);
	}
}
