<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" pageEncoding="UTF-8" import="
dswork.web.MyRequest,
common.cms.CmsFactory,
common.json.GsonUtil,
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
	CmsFactory cms = new CmsFactory(request);

	long siteid = req.getLong("siteid", -1);
	long categoryid = req.getLong("categoryid", -1);
	long pageid = req.getLong("pageid", -1);

	Map<String, Object> s = cms.getSite();
	map.put("siteid", getString(siteid));
	map.put("sitename", getString(s.get("name")));

	if(pageid > 0)// 内容页
	{
		Map<String, Object> p = cms.get(String.valueOf(pageid));
		if(!p.get("categoryid").equals(categoryid))
		{
			map.put("msg", "参数错误");
			out.print(GsonUtil.toJson(map));
			return;
		}
		Map<String, Object> c = cms.getCategory(String.valueOf(categoryid));
		map.put("categoryid", getString(categoryid));
		map.put("categoryname", getString(c.get("name")));
		map.put("id", getString(p.get("id")));
		map.put("title", getString(p.get("title")));
		map.put("scope", getString(p.get("scope")));
		if(p.get("scope").equals(2))// 外链
		{
			map.put("url", getString(p.get("url")));
			out.print(GsonUtil.toJson(map));
			return;
		}
		if(p.get("scope").equals(1))// 单页
		{
			map.put("summary", getString(p.get("summary")));
			map.put("metakeywords", getString(p.get("metakeywords")));
			map.put("metadescription", getString(p.get("metadescription")));
			map.put("releasetime", getString(p.get("releasetime")));
			map.put("releasesource", getString(p.get("releasesource")));
			map.put("releaseuser", getString(p.get("releaseuser")));
			map.put("img", getString(p.get("img")));
			map.put("content", getString(p.get("content")));
			out.print(GsonUtil.toJson(map));
			return;
		}
	}
	if(categoryid > 0)// 栏目页
	{
		Map<String, Object> c = cms.getCategory(String.valueOf(categoryid));
		map.put("id", getString(categoryid));
		map.put("name", getString(c.get("name")));
		map.put("scope", getString(c.get("scope")));
		if(c.get("scope").equals(2))// 外链页面
		{
			map.put("url", getString(c.get("url")));
			out.print(GsonUtil.toJson(map));
			return;
		}
		if(c.get("scope").equals(1))// 单页页面
		{
			map.put("summary", getString(c.get("summary")));
			map.put("metakeywords", getString(c.get("metakeywords")));
			map.put("metadescription", getString(c.get("metadescription")));
			map.put("releasetime", getString(c.get("releasetime")));
			map.put("releasesource", getString(c.get("releasesource")));
			map.put("releaseuser", getString(c.get("releaseuser")));
			map.put("img", getString(c.get("img")));
			map.put("content", getString(c.get("content")));
			out.print(GsonUtil.toJson(map));
			return;
		}
		if(c.get("scope").equals(1))// 列表
		{
			int currentPage = req.getInt("page", 1);
			int pagesize = req.getInt("pagesize", 25);
			Map<String, Object> mm = cms.queryPage(currentPage, pagesize, false, false, true, String.valueOf(c.get("url")), categoryid);
			Map<String, Object> datapage = (Map<String, Object>) mm.get("datapage");
			datapage.get("page");
			datapage.get("pagesize");
			datapage.get("first");
			datapage.get("prev");
			datapage.get("next");
			datapage.get("last");
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