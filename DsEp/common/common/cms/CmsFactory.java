/**
 * CMSService
 */
package common.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;




import dswork.cms.dao.DsCmsDao;
import dswork.core.page.Page;

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
			sb.append("<a");
			if(i != currentPage)
			{
				sb.append(" href=\"").append(site.get("url")).append(u).append("\"");
			}
			sb.append((i == currentPage ? " class=\"selected\"" : "")).append(">").append(i).append("</a>");
		}
		map.put("page", sb.toString());//翻页字符串
		return map;
	}

	/**
	 * 查询指定栏目下的子栏目(包括所有递归子栏目)
	 * @param categoryid 父栏目，查询根栏目为空
	 * @return List&lt;Map&lt;String, Object&gt;&gt;
	 */
	public List<Map<String, Object>> queryCategory(String categoryid)
	{
		init();
		String pid = String.valueOf(toLong(categoryid));
		List<Map<String, Object>> list = dao.queryCategory(siteid);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> m : list)
		{
			String p = String.valueOf(m.get("pid"));
			System.out.println("pid====" + p);
			if(p.equals("null"))
			{
				m.put("pid", "0");
				p = "0";
			}
			m.put("list", new ArrayList<Map<String, Object>>());
			map.put(String.valueOf(m.get("id")), m);
			if(p.equals(pid))
			{
				rs.add(m);
			}
		}
		for(Map<String, Object> m : list)
		{
			String p = String.valueOf(m.get("pid"));
			if(!p.equals("0") && map.get(p) != null)
			{
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> l = (ArrayList<Map<String, Object>>)(((Map<String, Object>)(map.get(p))).get("list"));
				l.add(m);
			}
		}
		return rs;
	}
}
