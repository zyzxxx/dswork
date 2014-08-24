<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp" %>
<script type="text/javascript">
$(function(){
	$dswork.page.menu("delCategory.htm", "updCategory1.htm", "", "");
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getCategory.htm";
}};
</script>
<style type="text/css">.v{padding-left:3px;}</style>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">栏目列表</td>
		<td class="menuTool">
			<a class="insert" href="addCategory1.htm?page=${pageModel.currentPage}">添加</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="listForm" method="post" action="delCategory.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:5%">操作</td>
		<td style="width:8%">排序</td>
		<td>名称</td>
		<td>目录</td>
		<td>状态(0列表，1单页，2外链)</td>
		<td>链接</td>
		<td>图片</td>
		<td>网站模板</td>
		<td>APP模板</td>
		<td>排序</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td><input name="keyIndex" type="text" style="width:36px;" value="${d.seq}" /></td>
		<td class="v" style="text-align:left;">${d.label}${fn:escapeXml(d.name)}</td>
		<td>${fn:escapeXml(d.folder)}</td>
		<td>${fn:escapeXml(d.status)}</td>
		<td>${fn:escapeXml(d.url)}</td>
		<td>${fn:escapeXml(d.img)}</td>
		<td>${fn:escapeXml(d.viewsite)}</td>
		<td>${fn:escapeXml(d.viewapp)}</td>
		<td>${fn:escapeXml(d.seq)}</td>
	</tr>
</c:forEach>
</table>
</form>
</body>
</html>
