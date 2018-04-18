<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" pageEncoding="UTF-8" import="
dswork.web.MyRequest,
common.cms.CmsFactory,
common.json.GsonUtil,
java.util.List,
java.util.Map,
java.util.HashMap
"%><%!
private String getString(Object object)
{
	return object == null ? "" : String.valueOf(object);
}
%><%
Map<String, Object> map = new HashMap<String, Object>();
try
{
	MyRequest req = new MyRequest(request);

	long siteid = req.getLong("siteid", -1);
	long categoryid = req.getLong("categoryid", -1);

	CmsFactory cms = new CmsFactory(siteid);
	Map<String, Object> s = cms.getSite();
	map.put("msg", "");
	map.put("siteid", getString(siteid));
	map.put("sitename", getString(s.get("name")));

	if(categoryid > 0)// 栏目页
	{
		Map<String, Object> c = cms.getCategory(String.valueOf(categoryid));
		if(c == null)
		{
			map.put("msg", "栏目不存在");
			out.print(GsonUtil.toJson(map));
			return;
		}
		map.put("id", getString(categoryid));
		map.put("name", getString(c.get("name")));
		map.put("scope", getString(c.get("scope")));
		if(c.get("scope").equals(0))// 列表
		{
			int currentPage = req.getInt("page", 1);
			int pagesize = req.getInt("pagesize", 25);
			Map<String, Object> mm = cms.queryPage(currentPage, pagesize, false, false, true, String.valueOf(c.get("url")), categoryid);
			Map<String, Object> datapage = (Map<String, Object>) mm.get("datapage");
			List<Map<String, Object>> list = (List<Map<String, Object>>)mm.get("list");
			for(Map<String, Object> p : list)
			{
				p.remove("content");
			}
			map.put("list", list);
			map.put("page", datapage.get("page"));
			map.put("pagesize", datapage.get("pagesize"));
			map.put("first", datapage.get("first"));
			map.put("prev", datapage.get("prev"));
			map.put("next", datapage.get("next"));
			map.put("last", datapage.get("last"));
			out.print(GsonUtil.toJson(map));
			return;
		}
	}
	map.put("msg", "参数错误");
	out.print(GsonUtil.toJson(map));
	return;
}
catch(Exception e)
{
	map.put("msg", e.getMessage());
	out.print(GsonUtil.toJson(map));
	return;
}
%>