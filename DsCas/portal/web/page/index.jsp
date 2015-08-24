<%@page language="java" import="java.util.*,dswork.cas.CasFilter" pageEncoding="UTF-8"%><%
String path = request.getContextPath();
Object obj = session.getAttribute(CasFilter.KEY_SESSIONUSER);
String username = String.valueOf(obj).trim();
if(obj == null || username.length() == 0 || username.equals("null"))
{
	response.sendRedirect(path + "/logout.jsp");
	return;
}
else
{
	response.sendRedirect(path + "/frame/index.jsp");
}
%><%-- 此页需要放在sso过滤的页面中，用于服务器主动获取账号 --%>