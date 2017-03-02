<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
dswork.sso.WebFilter.logout(session);
String service = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();//: %3A / %2F
//String service = request.getContextPath();//: %3A / %2F
System.out.println("\n********\nservice=" + service);
response.sendRedirect(dswork.sso.WebFilter.getLogoutURL(service));
%>