<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 import="dswork.cas.model.IFunc"%><%
String jsoncallback  = String.valueOf(request.getParameter("jsoncallback"));
String user = String.valueOf(request.getParameter("user"));%><%=jsoncallback%>([<%
IFunc[] list = dswork.cas.CasCheckFilter.getFuncByUser(user);

StringBuilder sb = new StringBuilder(300);
if(list != null)
{
	for(IFunc m : list)
	{
		sb.append(",{id:" + m.getId() + ", pid:" + m.getPid() + ", name:\"" + m.getName() + "\", img:\"\", imgOpen:\"\", url:\"" + m.getUri() + "\"}");
	}
	if(list.length > 0)
	{
		%><%=sb.substring(1)%><%
	}
}
%>])