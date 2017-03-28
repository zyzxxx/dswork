<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.AuthUtil"%>
<!DOCTYPE html>
<html>
<head><script type="text/javascript">
<%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String keyvalue = req.getString("account");
String code = req.getString("code");
String password = req.getString("password");
AuthUtil login = new AuthUtil(pageContext);
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
if(login.logpassword(account, password, code))
{
	if(code.startsWith("ep"))
	{
		%>alert("确认邮件已发送到您的邮箱");location.href="../loginEp.html";<%
	}
	else
	{
		%>alert("确认邮件已发送到您的邮箱");location.href="../loginPerson.html";<%
	}
}
else
{
	if(code.startsWith("ep"))
	{
		%>alert("<%=login.getMsg() %>");location.href="pwdEp.html";<%
	}
	else
	{
		%>alert("<%=login.getMsg() %>");location.href="pwdPerson.html";<%
	}
}
%>
</script></head>
<body></body>
</html>