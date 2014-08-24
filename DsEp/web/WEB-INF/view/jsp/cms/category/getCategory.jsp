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
	$dswork.page.menu("delCategory.htm", "updCategory1.htm", "getCategoryById.htm", "");
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getCategory.htm";
}};
</script>
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
<form id="queryForm" method="post" action="getCategory.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;父ID：<input type="text" class="text" name="pid" value="${fn:escapeXml(param.pid)}" />
			&nbsp;企业编码：<input type="text" class="text" name="qybm" value="${fn:escapeXml(param.qybm)}" />
			&nbsp;栏目名称：<input type="text" class="text" name="name" value="${fn:escapeXml(param.name)}" />
			&nbsp;目录名称：<input type="text" class="text" name="folder" value="${fn:escapeXml(param.folder)}" />
			&nbsp;状态(0列表，1单页，2外链)：<input type="text" class="text" name="status" value="${fn:escapeXml(param.status)}" />
			&nbsp;链接：<input type="text" class="text" name="url" value="${fn:escapeXml(param.url)}" />
			&nbsp;图片：<input type="text" class="text" name="img" value="${fn:escapeXml(param.img)}" />
			&nbsp;网站模板：<input type="text" class="text" name="viewsite" value="${fn:escapeXml(param.viewsite)}" />
			&nbsp;APP模板：<input type="text" class="text" name="viewapp" value="${fn:escapeXml(param.viewapp)}" />
			&nbsp;排序：<input type="text" class="text" name="seq" value="${fn:escapeXml(param.seq)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delCategory.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td>父ID</td>
		<td>企业编码</td>
		<td>栏目名称</td>
		<td>目录名称</td>
		<td>状态(0列表，1单页，2外链)</td>
		<td>链接</td>
		<td>图片</td>
		<td>网站模板</td>
		<td>APP模板</td>
		<td>排序</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td>${fn:escapeXml(d.pid)}</td>
		<td>${fn:escapeXml(d.qybm)}</td>
		<td>${fn:escapeXml(d.name)}</td>
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
