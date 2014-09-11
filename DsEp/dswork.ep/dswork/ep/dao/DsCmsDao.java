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

	public Map getSite(Long siteid)
	{
		return (Map)executeSelect("getSite", siteid);
	}

	public Map getCategory(Long categoryid)
	{
		return (Map)executeSelect("getCategory", categoryid);
	}

	public Map get(Long pageid)
	{
		return (Map)executeSelect("get", pageid);
	}

	public Page queryPage(int currentPage, int pageSize, String idArray, boolean isAsc)
	{
		PageRequest rq = new PageRequest(currentPage, pageSize);
		rq.getFilters().put("idArray", idArray);
		rq.getFilters().put("order", isAsc?"":" desc ");
		return queryPage("query", rq, "queryCount", rq);
	}

	public List queryCategory(Long siteid, Long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("pid", pid);
		return (List)executeSelectList("queryCategory", map);
	}
}
