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
private String getString(Object object){
	return object == null ? "" : String.valueOf(object);
}
private List<Long> splitString(String values){
	List<Long> list = new ArrayList<Long>();
	try{String[] _v = values.split(",", -1);
		if(_v.length > 0){
			for(int i = 0; i < _v.length; i++){
				try{list.add(Long.parseLong(_v[i].trim()));}
				catch(NumberFormatException e){}
			}
		}
	}
	catch(Exception e){}
	return list;
}
%><%
Map<String, Object> map = new HashMap<String, Object>();
try{
	MyRequest req = new MyRequest(request);

	long siteid = req.getLong("siteid", -1);
	String[] categoryids = req.getStringArray("categoryids", true);
	int currentPage = req.getInt("page", 1);
	int pagesize = req.getInt("pagesize", 10);

	CmsFactory cms = new CmsFactory(siteid);
	Map<String, Object> s = cms.getSite();
	
	List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
	map.put("status", "1");
	map.put("msg", "success");
	map.put("rows", rows);
	for(String cid : categoryids){
		List<Long> _ids = splitString(cid);
		if(_ids.size() > 0){// 栏目页
			Map<String, Object> one = new HashMap<String, Object>();
			one.put("categoryids", cid);
			Map<String, Object> c = cms.getCategory(String.valueOf(_ids.get(0)));
			_ids.remove(0);
			if(c == null){
				one.put("status", "0");
				one.put("msg", "栏目不存在");
				rows.add(one);
			}
			else{
				one.put("status", "1");
				one.put("msg", "success");
				one.put("category", c);
				if(_ids.size() > 0){
					List<Map<String, Object>> list = cms.queryList(currentPage, pagesize, false, false, true, _ids);
					for(Map<String, Object> p : list){p.remove("content");}
					one.put("rows", list);
				}
				rows.add(one);
			}
		}
	}
	/*
		{
			status:1,
			msg:"success",
			rows:[
				{
					status:1,
					msg:"success",
					// categoryid:栏目ID,
					categoryname:栏目名称,
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
catch(Exception e){
}
map.put("status", "0");
map.put("msg", "请确认站点是否存在");
out.print(GsonUtil.toJson(map));
return;
%>