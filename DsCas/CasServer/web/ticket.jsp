<%@page language="java" pageEncoding="utf-8"%>
<%@page import="dswork.cas.service.TicketService"%>
<!DOCTYPE html>
<html>
<head>
<title>用户登录ticket</title>
<meta charset="UTF-8" />
</head>
<body>
用户当前登录的ticket值是:<%=String.valueOf(request.getParameter("ticket")) %>
<br />
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String value = null;
out.println("cookie=" + cookies.length + "<br />");
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];
	if (cookie.getName().equals("__CookieTicket__"))
	{
		value = cookie.getValue();
		break;
	}
}
%>
用户账号是:<%=String.valueOf(TicketService.getAccountByTicket(String.valueOf(value))) %>
<br />
<a href="logout.htm">退出系统</a>
</body>
</html>
