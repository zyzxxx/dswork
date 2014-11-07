<%@page language="java" contentType="text/html;charset=UTF-8" import="dswork.web.*,
common.auth.*"%><%
String path = request.getContextPath();
AuthLogin.logout(request);
response.sendRedirect("login.html");
%>