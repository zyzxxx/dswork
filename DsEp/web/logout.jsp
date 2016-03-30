<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,
common.auth.*"%><%
String path = request.getContextPath();
Auth model = AuthLogin.getLoginUser(request, response);
String url = "login.html";//-1后台用户
AuthLogin.logout(request);
if(model != null)
{
	switch(model.getLogintype().intValue())
	{
		case 1:url = "loginPerson.html";break;//1个人用户
		case 2:url = "loginEp.html";break;//0企业管理员
	}
}
response.sendRedirect(url);
%>