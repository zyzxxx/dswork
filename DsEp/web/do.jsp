<%@page language="java" contentType="text/html;charset=UTF-8" %><%
String path = request.getContextPath();
String v = String.valueOf(request.getParameter("v"));
common.auth.Auth auth = new common.auth.Auth();
if(v.equals("1"))
{
	auth.setId(1L);
	auth.setAccount("admin");
	auth.setName("系统管理");
	auth.setQybm("100000");
	auth.setStatus(-1);
	auth.setSex(1);
}
else if(v.equals("2"))
{
	auth.setId(1L);
	auth.setAccount("useradmin");
	auth.setName("企业管理");
	auth.setQybm("100000");
	auth.setStatus(0);
	auth.setSex(1);
}
else
{
	auth.setId(2L);
	auth.setAccount("user");
	auth.setName("普通用户");
	auth.setQybm("111111");
	auth.setStatus(1);
	auth.setSex(0);
}
auth.setSsdw("银江股份有限公司");
auth.setSsbm("技术部");
request.getSession().setAttribute(common.auth.AuthLogin.SessionName_LoginUser, auth);
response.sendRedirect(path + "/index.jsp");
%>