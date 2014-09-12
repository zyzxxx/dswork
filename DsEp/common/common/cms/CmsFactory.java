/**
 * CMSService
 */
package common.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.core.page.Page;
import dswork.ep.dao.DsCmsDao;

//@SuppressWarnings("all")
public class CmsFactory
{
	private static DsCmsDao dao;
	static
	{
		dao = (DsCmsDao)dswork.spring.BeanFactory.getBean("dsCmsDao");
	}
	private Long toLong(String v)
	{
		try
		{
			return Long.parseLong(v);
		}
		catch(Exception e)
		{
			return 0L;
		}
	}

	public Map<String, Object> getSite(String siteid)
	{
		return dao.getSite(toLong(siteid));
	}

	public Map<String, Object> getCategory(String categoryid)
	{
		return dao.getCategory(toLong(categoryid));
	}

	public Map<String, Object> get(String pageid)
	{
		return dao.get(toLong(pageid));
	}

	public Map<String, Object> queryPage(int currentPage, int pageSize, boolean onlyImage, boolean onlyPage, boolean isDesc, Long... categoryids)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder idArray = new StringBuilder();
		idArray.append("0");
		for(int i = 0; i < categoryids.length; i++)
		{
			idArray.append(",").append(categoryids[i]);
		}
		Page<Map<String, Object>> page = dao.queryPage(currentPage, pageSize, idArray.toString(), isDesc, onlyImage, onlyPage);
		map.put("list", page.getResult());
		
		
		map.put("page", "");//翻页字符串
		return map;
	}

	public List<Map<String, Object>> queryCategory(String siteid, String categoryid)
	{
		return dao.queryCategory(toLong(siteid), toLong(categoryid));
	}
}
