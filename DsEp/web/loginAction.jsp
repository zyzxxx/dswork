<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.AuthLogin"%><%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String account = req.getString("account");
String password = req.getString("password");
String authcode = req.getString("authcode");
AuthLogin login = new AuthLogin(pageContext);
if(login.login(account, password, authcode))
{
	response.sendRedirect("manage/frame/index.html");
}
%>
<!DOCTYPE html>
<html>
<head><script type="text/javascript">alert("<%=login.getMsg() %>");location.href="login.html";</script></head>
<body></body>
</html>