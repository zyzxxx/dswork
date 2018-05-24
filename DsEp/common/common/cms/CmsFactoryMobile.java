/**
 * CMSService
 */
package common.cms;

import java.util.HashMap;
import java.util.Map;

import dswork.core.page.Page;

public class CmsFactoryMobile extends CmsFactory
{
	@Deprecated
	public CmsFactoryMobile(long siteid)
	{
		super(siteid);
	}

	public CmsFactoryMobile(CmsFactory cms)
	{
		siteid = cms.siteid;
		site = cms.site;
		dao = cms.getDao();
		categoryList = cms.categoryList;
		categoryMap = cms.categoryMap;
	}

	public Map<String, Object> queryPage(int currentPage, int pageSize, boolean onlyImageTop, boolean onlyPageTop, boolean isDesc, String url, Object categoryid)
	{
		if(currentPage <= 0)
		{
			currentPage = 1;
		}
		if(pageSize <= 0)
		{
			pageSize = 25;
		}
		StringBuilder idArray = new StringBuilder();
		idArray.append(toLong(categoryid));
		Page<Map<String, Object>> page = getDao().queryPage(siteid, currentPage, pageSize, idArray.toString(), isDesc, onlyImageTop, onlyPageTop, null);
		Map<String, Object> map = new HashMap<String, Object>();
		currentPage = page.getCurrentPage();// 更新当前页
		map.put("list", page.getResult());
		Map<String, Object> pagemap = new HashMap<String, Object>();
		pagemap.put("page", currentPage);
		pagemap.put("pagesize", pageSize);
		pagemap.put("first", 1);
		pagemap.put("firsturl", url);
		int tmp = initpage(currentPage - 1, page.getLastPage());
		pagemap.put("prev", tmp);
		pagemap.put("prevurl", (tmp == 1 ? url : (url.replaceAll("\\.html", "_" + tmp + ".html"))));
		tmp = initpage(currentPage + 1, page.getLastPage());
		pagemap.put("next", tmp);
		pagemap.put("nexturl", (tmp == 1 ? url : (url.replaceAll("\\.html", "_" + tmp + ".html"))));
		tmp = page.getLastPage();
		pagemap.put("last", tmp);
		pagemap.put("lasturl", (tmp == 1 ? url : (url.replaceAll("\\.html", "_" + tmp + ".html"))));
		map.put("datauri", url.replaceAll("\\.html", ""));
		map.put("datapage", pagemap);

		url = "/m" + url;
		StringBuilder sb = new StringBuilder();
		int viewpage = 3, temppage = 1;// 左右显示个数
		sb.append("<a");
		if(currentPage == 1)
		{
			sb.append(" class=\"selected\"");
		}
		else
		{
			sb.append(" href=\"").append(site.get("url")).append(url).append("\"");
		}
		sb.append(">1</a>");
		temppage = currentPage - viewpage - 1;
		if(temppage > 1)
		{
			String u = url.replaceAll("\\.html", "_" + temppage + ".html");
			sb.append("<a href=\"").append(site.get("url")).append(u).append("\">...</a>");
		}
		for(int i = currentPage - viewpage; i <= currentPage + viewpage && i < page.getLastPage(); i++)
		{
			if(i > 1)
			{
				String u = (i == 1 ? url : (url.replaceAll("\\.html", "_" + i + ".html")));
				sb.append("<a");
				if(currentPage == i)
				{
					sb.append(" class=\"selected\"");
				}
				else
				{
					sb.append(" href=\"").append(site.get("url")).append(u).append("\"");
				}
				sb.append(">").append(i).append("</a>");
			}
		}
		temppage = currentPage + viewpage + 1;
		if(temppage < page.getLastPage())
		{
			String u = url.replaceAll("\\.html", "_" + temppage + ".html");
			sb.append("<a href=\"").append(site.get("url")).append(u).append("\">...</a>");
		}
		if(1 != page.getLastPage())
		{
			String u = url.replaceAll("\\.html", "_" + page.getLastPage() + ".html");
			sb.append("<a");
			if(currentPage == page.getLastPage())
			{
				sb.append(" class=\"selected\"");
			}
			else
			{
				sb.append(" href=\"").append(site.get("url")).append(u).append("\"");
			}
			sb.append(">").append(page.getLastPage()).append("</a>");
		}
		map.put("datapageview", sb.toString());// 翻页字符串
		return map;
	}
}
