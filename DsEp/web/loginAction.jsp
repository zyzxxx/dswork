<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.AuthLogin"%><%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String account = req.getString("account");
String password = req.getString("password");
String authcode = req.getString("authcode");
int logintype = req.getInt("logintype", 0);
AuthLogin login = new AuthLogin(pageContext);
String s = "about:blank", m = "";
if(logintype > -2 && logintype < 2)
{
	if(login.login(account, password, logintype, authcode))
	{
		response.sendRedirect("manage/frame/index.html");
	}
	if(logintype == 2)
	{
		s = "loginEp.html";
	}
	else if(logintype == 1)
	{
		s = "loginPerson.html";
	}
	else// if(logintype == 0)
	{
		s = "login.html";
	}
	m = login.getMsg();
}
else
{
	m = "非法访问";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title></title>
<script type="text/javascript">alert("<%=m %>");location.href="<%=s%>";</script>
</head>
<body></body>
</html>