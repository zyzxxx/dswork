<%@page language="java" pageEncoding="UTF-8" import="dswork.common.DsFactoryService, dswork.web.MyRequest"%><%
MyRequest req = new MyRequest(request);
long waitid = req.getLong("wid");
if(waitid > 0)
{
	DsFactoryService.getFlow().takeWaiting(waitid, "admin");
	response.sendRedirect("waiting.jsp");
}
%>