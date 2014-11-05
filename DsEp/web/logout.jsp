<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,
common.auth.*"%><%
String path = request.getContextPath();
AuthLogin login = new AuthLogin(pageContext);
login.loginOut(request);
response.sendRedirect("login.html");
%>