/**
 * CMSDao
 */
package dswork.ep.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;

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

	public Map<String, Object> getCategory(Long categoryid)
	{
		return (Map)executeSelect("getCategory", categoryid);
	}

	public Map<String, Object> get(Long pageid)
	{
		return (Map)executeSelect("get", pageid);
	}

	public Page<Map<String, Object>> queryPage(int currentPage, int pageSize, String idArray, Boolean isDesc, boolean onlyImage, boolean onlyPage)
	{
		PageRequest rq = new PageRequest(currentPage, pageSize);
		rq.getFilters().put("idArray", idArray);
		rq.getFilters().put("order", isDesc==null||isDesc?" desc ":"");
		rq.getFilters().put("imgtop", onlyImage?"1":"");
		rq.getFilters().put("pagetop", onlyPage?"1":"");
		return queryPage("query", rq, "queryCount", rq);
	}

	public List<Map<String, Object>> queryCategory(Long siteid, Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("pid", pid==null?0:pid);
		return (List)executeSelectList("queryCategory", map);
	}
}
