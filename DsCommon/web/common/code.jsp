<%@page import="dswork.http.HttpUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
HttpUtil http = new HttpUtil();
%>
后台管理平台管控密码：<%=http.create("http://127.0.0.1:8888/sso/code.jsp").connect()%>
&nbsp;
<a href="refresh.jsp">刷新密码</a>
<br />

展厅管控密码：<%=http.create("http://127.0.0.1/jxc/code.jsp").connect()%>