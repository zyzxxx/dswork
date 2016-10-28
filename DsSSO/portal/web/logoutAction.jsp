<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.net.URLEncoder"%><%
dswork.sso.WebFilter.logout(session);
String service = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();//: %3A / %2F
//String service = request.getContextPath();//: %3A / %2F
response.sendRedirect(dswork.sso.WebFilter.getLogoutURL() + URLEncoder.encode(service, "UTF-8"));
%>