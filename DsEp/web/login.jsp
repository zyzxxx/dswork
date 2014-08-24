<%@page language="java" contentType="text/html;charset=UTF-8" %><%
String path = request.getContextPath();
String v = String.valueOf(request.getParameter("v"));
common.auth.Auth auth = new common.auth.Auth();
if(v.equals("1"))
{
	auth.setId(2L);
	auth.setAccount("subadmin");
	auth.setName("协管员");
	auth.setQybm("111111");
	auth.setStatus(0);
	auth.setSex(0);
}
else
{
	auth.setId(1L);
	auth.setAccount("admin");
	auth.setName("管理员");
	auth.setQybm("100000");
	auth.setStatus(1);
	auth.setSex(1);
}
auth.setSsdw("股份有限公司");
auth.setSsbm("技术部");
request.getSession().setAttribute(common.auth.AuthLogin.SessionName_LoginUser, auth);
response.sendRedirect(path + "/manage/frame/index.jsp");
%>