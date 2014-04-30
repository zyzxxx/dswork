<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/updAjax.jsp" %>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getDemo.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updDemo2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">标题：</td>
		<td class="form_input"><input type="text" id="title" name="title" value="${fn:escapeXml(po.title)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容：</td>
		<td class="form_input"><input type="text" id="content" name="content" value="${fn:escapeXml(po.content)}" /></td>
	</tr>
	<tr>
		<td class="form_title">创建时间：</td>
		<td class="form_input"><input type="text" id="foundtime" name="foundtime" value="${fn:escapeXml(po.foundtime)}" /></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
