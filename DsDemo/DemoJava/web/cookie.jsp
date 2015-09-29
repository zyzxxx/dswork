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
out.println(request.getHeader("Cookie"));
out.println("cookie=" + ((cookies != null)?cookies.length:0) + "<br />");
try{
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];//cookie中的path是不可读的
	String s = "&nbsp;&nbsp;" + cookie.getName() + "=" + cookie.getValue() + "<br />";
	out.println(s);
}}catch(Exception ex){ex.printStackTrace();}


out.println("<br />params:<br />");
out.println("queryString=" + request.getQueryString() + "<br />");
try{
java.util.Enumeration<String> params = request.getParameterNames();
while(params.hasMoreElements())
{
	String s = String.valueOf(request.getParameter(String.valueOf(params.nextElement())));
	out.println(s);
}}catch(Exception ex){ex.printStackTrace();}
%>
</body>
</html>
