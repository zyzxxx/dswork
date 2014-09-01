<%@page language="java" contentType="text/html;charset=UTF-8" %><%
String path = request.getContextPath();
String v = String.valueOf(request.getParameter("v"));
common.auth.Auth auth = new common.auth.Auth();
auth.setId(1L);
auth.setAccount("100000");
auth.setName("管理员");
auth.setQybm("100000");
auth.setStatus(1);
auth.setSsdw("责任有限公司");
auth.setSsbm("技术部");
request.getSession().setAttribute(common.auth.AuthLogin.SessionName_LoginUser, auth);
response.sendRedirect(path + "/index.jsp");
%>