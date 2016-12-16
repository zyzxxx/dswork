<%@page pageEncoding="UTF-8"%><%
dswork.web.MyRequest req = new dswork.web.MyRequest(request);
response.addHeader("Access-Control-Allow-Origin", "*");
String search = java.net.URLDecoder.decode(req.getString("v"), "UTF-8");
java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
try
{
	dswork.core.page.Page<common.lucene.MyDocument> pageModel = common.lucene.LuceneUtil.search(search, req.getInt("page", 1), req.getInt("pagesize", 10));
	map.put("status", "1");//success
	map.put("msg", "success");
	
	map.put("size", pageModel.getTotalCount());
	map.put("page", pageModel.getCurrentPage());
	map.put("pagesize", pageModel.getPageSize());
	
	map.put("totalpage", pageModel.getTotalPage());
	map.put("rows", pageModel.getResult());
}
catch(Exception ex)
{
	map.put("status", "0");
	map.put("msg", "error");
}
out.print(common.json.GsonUtil.toJson(map));
%>