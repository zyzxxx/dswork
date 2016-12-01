<%@page pageEncoding="UTF-8"%><%
common.cms.CmsFactory cms = new common.cms.CmsFactory(request);
dswork.web.MyRequest req = new dswork.web.MyRequest(request);
response.addHeader("Access-Control-Allow-Origin", "*");
String search = java.net.URLDecoder.decode(req.getString("v"), "UTF-8");
out.print(common.json.GsonUtil.toJson(cms.queryPage(req.getInt("page", 1), req.getInt("pagesize", 10), true, search)));
%>