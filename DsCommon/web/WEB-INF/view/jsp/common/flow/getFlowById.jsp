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
		<td class="form_title">流程标识</td>
		<td class="form_input">${fn:escapeXml(po.alias)}</td>
	</tr>
	<tr>
		<td class="form_title">流程发布ID</td>
		<td class="form_input">${fn:escapeXml(po.deployid)}</td>
	</tr>
	<tr>
		<td class="form_title">名字</td>
		<td class="form_input">${fn:escapeXml(po.name)}</td>
	</tr>
</table>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>节点标识</td>
		<td>节点名称</td>
		<td>上级任务</td>
		<td>下级任务</td>
		<td>用户ID</td>
		<td>用户参数</td>
	</tr>
<c:forEach items="${po.taskList}" var="d">
	<tr>
		<td>${fn:escapeXml(d.talias)}</td>
		<td>${fn:escapeXml(d.tname)}</td>
		<td>${fn:escapeXml(d.tnodeprev)}</td>
		<td>${fn:escapeXml(d.tnodenext)}</td>
		<td>${fn:escapeXml(d.tusers)}</td>
		<td>${fn:escapeXml(d.tmemo)}</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
