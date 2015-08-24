<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%

Cookie cookies[] = request.getCookies();
String value = null;
if(cookies != null)
{
	Cookie cookie = null;
	for(int i = 0; i < cookies.length; i++)
	{
		cookie = cookies[i];
		%><%=cookie.getName()%>=<%=cookie.getValue()%><br /><br /><%
	}
}



%>





<br />

<br />
</body>
</html>