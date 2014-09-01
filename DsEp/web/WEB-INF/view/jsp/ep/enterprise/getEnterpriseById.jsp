<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/getById.jsp" %>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">明细</td>
		<td class="menuTool">
			<a class="back" onclick="window.history.back();return false;" href="#">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">企业名称</td>
		<td class="form_input">${fn:escapeXml(po.name)}</td>
	</tr>
	<tr>
		<td class="form_title">企业编码</td>
		<td class="form_input">${fn:escapeXml(po.qybm)}</td>
	</tr>
	<tr>
		<td class="form_title">状态</td>
		<td class="form_input">${fn:escapeXml(po.status)}</td>
	</tr>
	<tr>
		<td class="form_title">类型</td>
		<td class="form_input">${fn:escapeXml(po.type)}</td>
	</tr>
</table>
</body>
</html>
