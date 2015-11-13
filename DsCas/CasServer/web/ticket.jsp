<%@page contentType="text/html; charset=UTF-8" import="dswork.cas.service.TicketService"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title>Error</title>
<style>
    body {
        width: 38em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif, 微软雅黑;
    }
</style>
</head>
<body>
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String value = null;
String v = "cookie=" + cookies.length;
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];
	v += ("<br />" + cookie.getName() + "=" + cookie.getValue());
	if (cookie.getName().equals("__CookieTicket__"))
	{
		value = cookie.getValue();
		break;
	}
}
%>
<h1>用户账号是:<%=String.valueOf(TicketService.getAccountByTicket(String.valueOf(value))) %>。</h1>
<p><%=v %></p>
<p><a href="<%=request.getContextPath()%>/logout">退出系统</a></p>
<p><em>system administrator.</em></p>
</body>
</html>
