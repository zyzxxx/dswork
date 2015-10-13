<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>DemoJava</title>
</head>
<body>
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String value = null;
out.println("CookieLength is " + ((cookies != null)?cookies.length:0) + "<br />");
out.println("Cookie:::" + request.getHeader("Cookie") + "<br />");
try{
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];//cookie中的path是不可读的
	String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cookie.getName() + "=" + cookie.getValue() + "<br />";
	out.println(s);
}}catch(Exception ex){ex.printStackTrace();}


out.println("<br />queryString:::" + request.getQueryString() + "<br />");
try{
java.util.Enumeration<String> params = request.getParameterNames();
while(params.hasMoreElements())
{
	String n = String.valueOf(params.nextElement());
	String s = String.valueOf(request.getParameter(n));
	out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + n + "=" + s);
}}catch(Exception ex){ex.printStackTrace();}
%>
</body>
</html>
