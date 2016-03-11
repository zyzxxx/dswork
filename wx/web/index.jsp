<%@page language="java" import="java.util.*, common.wx.*" pageEncoding="UTF-8"%><%
String url = WxFactory.getWxURL("http://112.74.111.188/wx/auth.jsp", "x", false);
%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<%--script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script--%>
</head>
<body>
<a href="<%=url%>">微信登录</a>
</body>
</html>