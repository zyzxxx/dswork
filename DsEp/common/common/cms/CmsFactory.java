/**
 * CMSService
 */
package common.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import dswork.core.page.Page;
import dswork.ep.dao.DsCmsDao;

//@SuppressWarnings("all")
public class CmsFactory
{
	private static DsCmsDao dao = null;

	private static void init()
	{
		if(dao == null)
		{
			dao = (DsCmsDao)dswork.spring.BeanFactory.getBean("dsCmsDao");
		}
	}
	private static Long toLong(Object v)
	{
		try
		{
			return Long.parseLong(String.valueOf(v));
		}
		catch(Exception e)
		{
			return 0L;
		}
	}
	private Long siteid = 0L;
	Map<String, Object> site = new HashMap<String, Object>();
	public CmsFactory(HttpServletRequest request)
	{
		try
		{
			init();
			String tmp = String.valueOf(request.getParameter("siteid"));
			siteid = toLong(tmp);
			site = dao.getSite(siteid);
		}
		catch(Exception ex)
		{
		}
	}

	public Map<String, Object> getSite()
	{
		return site;
	}

	public Map<String, Object> getCategory(Object categoryid)
	{
		init();
		return dao.getCategory(siteid, toLong(categoryid));
	}

	public Map<String, Object> get(String pageid)
	{
		init();
		return dao.get(siteid, toLong(pageid));
	}

	public List<Map<String, Object>> queryList(int currentPage, int size, boolean onlyImage, boolean onlyPage, boolean isDesc, Object... categoryids)
	{
		init();
		StringBuilder idArray = new StringBuilder();
		idArray.append("0");
		for(int i = 0; i < categoryids.length; i++)
		{
			idArray.append(",").append(toLong(categoryids[i]));
		}
		Page<Map<String, Object>> page = dao.queryPage(siteid, currentPage, size, idArray.toString(), isDesc, onlyImage, onlyPage);
		return page.getResult();
	}

	public Map<String, Object> queryPage(int currentPage, int pageSize, boolean onlyImage, boolean onlyPage, boolean isDesc, String url, Object categoryid)
	{
		init();
		StringBuilder idArray = new StringBuilder();
		idArray.append(toLong(categoryid));
		Page<Map<String, Object>> page = dao.queryPage(siteid, currentPage, pageSize, idArray.toString(), isDesc, onlyImage, onlyPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", page.getResult());
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= page.getLastPage(); i++)
		{
			String u = (i == 1?url:(url.replaceAll("\\.html", "_" + i + ".html")));
			sb.append("<a href=\"").append(u).append("\"").append((i != currentPage ? " class=\"selected\"" : "")).append(">").append(i).append("</a>");
		}
		map.put("page", sb.toString());//翻页字符串
		return map;
	}

	public List<Map<String, Object>> queryCategory(String categoryid)
	{
		init();
		return dao.queryCategory(siteid, toLong(categoryid));
	}
}
