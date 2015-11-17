<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
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