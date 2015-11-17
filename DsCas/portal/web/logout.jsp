<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.net.URLEncoder"%><%
dswork.sso.WebFilter.logout(session);
String service = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();//: %3A / %2F
response.sendRedirect("http://sso-server:8888/sso/logout?service=" + URLEncoder.encode(service, "UTF-8"));
%>
