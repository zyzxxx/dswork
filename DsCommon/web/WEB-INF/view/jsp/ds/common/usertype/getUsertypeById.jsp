<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/getById.jsp"%>
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
		<td class="form_title">名称</td>
		<td class="form_input">${fn:escapeXml(po.name)}</td>
	</tr>
	<tr>
		<td class="form_title">标识</td>
		<td class="form_input">${fn:escapeXml(po.alias)}</td>
	</tr>
	<tr>
		<td class="form_title">状态(1)</td>
		<td class="form_input">${fn:escapeXml(po.status)}</td>
	</tr>
	<tr>
		<td class="form_title">排序</td>
		<td class="form_input">${fn:escapeXml(po.seq)}</td>
	</tr>
	<tr>
		<td class="form_title">资源集合</td>
		<td class="form_input">${fn:escapeXml(po.resources)}</td>
	</tr>
	<tr>
		<td class="form_title">扩展信息</td>
		<td class="form_input"><textarea class="readonlytext" style="width:400px;height:60px;">${fn:escapeXml(po.memo)}</textarea></td>
	</tr>
</table>
</body>
</html>
