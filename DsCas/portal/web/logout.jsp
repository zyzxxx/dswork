<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="dswork.cas.CasFilter,java.net.URLEncoder"%><%
CasFilter.doLogout(session);
String service = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();//: %3A / %2F
response.sendRedirect("http://sso:8888/CasServer/logout?service=" + URLEncoder.encode(service, "UTF-8"));
%>
