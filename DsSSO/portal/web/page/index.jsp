<%@page language="java" pageEncoding="UTF-8"%><%
String path = request.getContextPath();
String obj = dswork.sso.WebFilter.getAccount(session);
if(obj == null)
{
	response.sendRedirect(path + "/logout.jsp");
	return;
}
else
{
	response.sendRedirect(path + "/frame/index.jsp");
}
%><%-- 此页需要放在sso过滤的页面中，用于服务器主动获取账号 --%>