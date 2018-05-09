<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" pageEncoding="UTF-8" import="
dswork.web.MyRequest,
common.cms.CmsFactory,
common.json.GsonUtil,
java.util.List,
java.util.ArrayList,
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
	int currentPage = req.getInt("page", 1);
	int pagesize = req.getInt("pagesize", 10);

	CmsFactory cms = new CmsFactory(siteid);
	Map<String, Object> s = cms.getSite();
	
	List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
	map.put("status", "1");
	map.put("msg", "success");
	
	map.put("siteid", getString(siteid));// 站点可能不存在
	map.put("sitename", getString(s.get("name")));
	
	map.put("rows", rows);
	for(long cid : categoryid)
	{
		if(cid > 0)// 栏目页
		{
			Map<String, Object> one = new HashMap<String, Object>();
			one.put("categoryid", cid);
			
			Map<String, Object> c = cms.getCategory(String.valueOf(cid));
			if(c == null)
			{
				one.put("status", "0");
				one.put("msg", "栏目不存在");
			}
			else
			{
				one.put("status", "1");
				one.put("msg", "success");
				one.put("category", c);
				if(c.get("scope").equals(0))// 列表
				{
					List<Map<String, Object>> list = cms.queryList(currentPage, pagesize, false, false, true, cid);
					for(Map<String, Object> p : list)
					{
						p.remove("content");
					}
					one.put("rows", list);
				}
			}
			rows.add(one);
		}
	}
	/*
		{
			status:1,
			msg:"success",
			
			siteid:站点ID,
			sitename:站点名称,
			
			rows:[
				{
					status:1,
					msg:"success",
					categoryid:栏目ID,
					category:{栏目对象},
					rows:[当前页数据]
				},
				...
			]
		} 
	 */
	out.print(GsonUtil.toJson(map));
	return;
}
catch(Exception e)
{
}
map.put("status", "0");
map.put("msg", "请确认站点是否存在");
out.print(GsonUtil.toJson(map));
return;
%>