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

	long siteid = req.getLong("siteid", -1);
	long categoryid = req.getLong("categoryid", -1);
	long pageid = req.getLong("pageid", -1);

	CmsFactory cms = new CmsFactory(siteid);
	Map<String, Object> s = cms.getSite();
	map.put("msg", "");
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