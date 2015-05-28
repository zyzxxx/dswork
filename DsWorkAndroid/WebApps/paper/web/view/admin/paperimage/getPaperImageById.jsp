<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title></title>
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
</head>
<body>
<img src="<%=path%>/share/data/img.jsp?id=${po.id}" alt="图片"/>
</body>
</html>
