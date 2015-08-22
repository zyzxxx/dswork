<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>cookie</title>
</head>
<body>
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String value = null;
out.println("cookie=" + cookies.length + "<br />");
try{
for (int i = 0; i < cookies.length; i++)
{
	cookie = cookies[i];
	String s = "&nbsp;&nbsp;" + cookie.getName() + "=" + cookie.getValue() + "=" + cookie.getPath() + "<br />";
	out.println(s);
	System.out.println(s);
}}catch(Exception ex){ex.printStackTrace();}
try{
java.util.Enumeration<String> params = request.getParameterNames();
while(params.hasMoreElements())
{
	String s = String.valueOf(request.getParameter(String.valueOf(params.nextElement())));
	out.println(s);
	System.out.println(s);
}}catch(Exception ex){ex.printStackTrace();}
%>
</body>
</html>
