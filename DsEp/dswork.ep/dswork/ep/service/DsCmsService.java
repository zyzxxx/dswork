/**
 * CMSService
 */
package dswork.ep.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.ep.dao.DsCmsDao;

@Service
//@SuppressWarnings("all")
public class DsCmsService
{
	@Autowired
	private DsCmsDao dao;

	public Map getSite(Long siteid)
	{
		return dao.getSite(siteid);
	}

	public Map getCategory(Long categoryid)
	{
		return dao.getCategory(categoryid);
	}

	public Map get(Long pageid)
	{
		return dao.get(pageid);
	}

	public List query(int size, boolean isAsc, Long... categoryids)
	{
		return (List)(queryPage(1, size, isAsc, categoryids).get("list"));
	}

	public Map queryPage(int currentPage, int pageSize, boolean isAsc, Long... categoryids)
	{
		Map map = new HashMap();
		
		PageRequest rq = new PageRequest(currentPage, pageSize);
		StringBuilder idArray = new StringBuilder();
		idArray.append("0");
		for(int i = 0; i < categoryids.length; i++)
		{
			idArray.append(",").append(categoryids[i]);
		}
		Page page = dao.queryPage(currentPage, pageSize, idArray.toString(), isAsc);
		map.put("list", page.getResult());
		
		
		map.put("page", "");//翻页字符串
		return map;
	}

	public List queryCategory(Long siteid, Long pid)
	{
		return dao.queryCategory(siteid, pid);
	}
}
