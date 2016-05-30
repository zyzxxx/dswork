<%@page language="java" import="java.util.*, common.wx.*" pageEncoding="UTF-8"%><%
%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<%--script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script--%>
</head>
<body>
<a href="<%=WxFactory.getLoginURL("http://183.232.32.145/wx/auth.jsp", "m", false)%>">微信端登录</a>


<a href="<%=WxFactory.getLoginURL("http://183.232.32.145/wx/auth.jsp", "2", true)%>">二维码登录</a>
</body>
</html>