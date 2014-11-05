<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.AuthLogin"%>
<!DOCTYPE html>
<html>
<head><script type="text/javascript">
<%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String keyvalue = req.getString("keyvalue");
String authcode = req.getString("authcode");
AuthLogin login = new AuthLogin(pageContext);
String account = null;
String email = null; 
if(keyvalue.indexOf("@") != -1)
{
	email = keyvalue;
}
else
{
	account = keyvalue;
}
if(login.logpwd(account, email, authcode))
{
	%>alert("确认邮件已发送到您的邮箱");location.href="login.html";<%
}
else
{
	%>alert("<%=login.getMsg() %>");location.href="logpwd.html";<%
}
%>
</script></head>
<body></body>
</html>