<%@page contentType="text/html; charset=UTF-8" import="dswork.cas.service.TicketService"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>用户登录ticket</title>
</head>
<body>
用户当前登录的ticket值是:<%=String.valueOf(request.getParameter("ticket"))%>
<br />
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String value = null;
out.println("cookie=" + cookies.length + "<br />");
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];
	out.println("&nbsp;&nbsp;" + cookie.getName() + "=" + cookie.getValue() + "=" + cookie.getPath() + "<br />");
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
