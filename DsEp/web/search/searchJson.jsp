<%@page pageEncoding="UTF-8"%><%
dswork.web.MyRequest req = new dswork.web.MyRequest(request);
common.cms.CmsFactory cms = new common.cms.CmsFactory(req.getLong("siteid", -1));
response.addHeader("Access-Control-Allow-Origin", "*");
String search = java.net.URLDecoder.decode(req.getString("v"), "UTF-8");
out.print(common.json.GsonUtil.toJson(cms.queryPage(req.getInt("page", 1), req.getInt("pagesize", 10), true, search)));
%>