<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/page.jsp" %>
	<script type="text/javascript">
	</script>
</head>
<body class="easyui-tabs" fit="true">
<div title="用户授权" style="padding:5px;overflow:hidden;">
	<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:40%;">姓名(帐号)</td>
		<td>手机</td>
		<td style="width:10%">操作</td>
	</tr>
<c:forEach items="${userList}" var="d">
	<tr>
		<td>${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.mobile)}</td>
		<td class="menuTool">
			<a class="update" href="updOrg1.htm?keyIndex=${d.id}">受权</a>
		</td>
	</tr>
</c:forEach>
</table>
</div>
<div title="岗位授权" style="padding:5px;overflow:hidden;">
	<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>岗位名称</td>
		<td style="width:10%">操作</td>
	</tr>
<c:forEach items="${orgList}" var="d">
	<tr>
		<td>${fn:escapeXml(d.name)}</td>
		<td class="menuTool">
			<a class="update" href="updOrg1.htm?keyIndex=${d.id}">授权</a>
		</td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>
