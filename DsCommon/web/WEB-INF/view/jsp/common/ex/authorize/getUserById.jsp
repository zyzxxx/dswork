<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/upd.jsp"%>
<script type="text/javascript">
$(function(){
	
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">用户明细</td>
	</tr>
</table>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td style="width:8%">账号</td>
		<td>${user.account}</td>
	</tr>
	<tr>
		<td style="width:8%">姓名</td>
		<td>${user.name}</td>
	</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">岗位列表</td>
	</tr>
</table>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>岗位名称</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td></td>
	</tr>
</c:forEach>
</table>
</body>
</html>
