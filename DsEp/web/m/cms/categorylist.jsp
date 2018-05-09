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
	long[] categoryid = req.getLongArray("categoryid", -1L);

	CmsFactory cms = new CmsFactory(siteid);
	Map<String, Object> s = cms.getSite();
	int currentPage = req.getInt("page", 1);
	int pagesize = req.getInt("pagesize", 25);
	Map<String, Object> mm = cms.queryPage(currentPage, pageSize, true, null, categoryids);
	List<Map<String, Object>> list = (List<Map<String, Object>>)mm.get("rows");
	if(list != null)
	{
		for(Map<String, Object> p : list)
		{
			p.remove("content");
		}
	}
	/*
		{
			status:1,
			msg:"success",
			size:总条数,
			page:当前页,
			pagesize:每页条数,
			totalpage:总页数,
			rows:[当前页数据数组]
		}
	 */
	out.print(GsonUtil.toJson(mm));
	return;
}
catch(Exception e)
{
	map.put("status", "0");
	map.put("msg", "error");
	out.print(GsonUtil.toJson(map));
	return;
}
%>