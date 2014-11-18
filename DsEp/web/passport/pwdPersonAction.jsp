<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.AuthLogin"%>
<!DOCTYPE html>
<html>
<head><script type="text/javascript">
<%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String v = req.getString("account");
String authcode = req.getString("authcode");
AuthLogin login = new AuthLogin(pageContext);
String account = null;
String email = null; 
if(v.indexOf("@") != -1)
{
	email = v;
}
else
{
	account = v;
}
if(login.logpwd(account, email, 1, authcode))
{
	%>alert("验证码已发送到您的邮箱");location.href="password.html";<%
}
else
{
	%>alert("<%=login.getMsg() %>");location.href="pwdPerson.html";<%
}
%>
</script></head>
<body></body>
</html>