<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
<h1>纸模明细</h1>
<hr>
外键：${po.pid}<br>
分类名：${po.name}<br>
排序号：${po.sort}<br>
</body>
</html>
