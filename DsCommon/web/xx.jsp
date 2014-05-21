<%@ page language="java" pageEncoding="UTF-8" import="dswork.common.DsCommonFactory,dswork.web.MyRequest"
%><%
MyRequest req = new MyRequest(request);
String id = req.getString("id", null);//null则取全部
if(id.equals("0"))// 根节点
{
	id = "";
}
out.print(DsCommonFactory.Json("ztree", id)); %>