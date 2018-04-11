<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,common.auth.*"%><%
String path = request.getContextPath();
MyRequest req = new MyRequest(request);
String account = req.getString("account");
String password = req.getString("password");
String authcode = req.getString("authcode");
int logintype = req.getInt("logintype", -1);
AuthUtil login = new AuthUtil(pageContext);
String s = "about:blank", m = "";
if(login.login(account, password, logintype, authcode))
{
	common.auth.Auth auth = AuthUtil.getLoginUser(request);
	common.authown.AuthOwnUtil.login(request, response, auth.getId().toString(), auth.getAccount(), auth.getName(), "adminadmin");
}
if(logintype == Auth.ENTERPRISE)
{
	s = "loginEp.html";
}
else if(logintype == Auth.USER)
{
	s = "loginPerson.html";
}
else
{
	s = "login.html";
}
m = login.getMsg();
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