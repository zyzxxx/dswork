<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title>DEMO</title>
	<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
// 扩展菜单写法
$dswork.page.join = function(td, menu, id)
{
	$(menu).append($('<div iconCls="menuTool-user">明细</div>').bind("click", function(){
		location.href = "getDemoById.htm?page=${pageModel.currentPage}&keyIndex=" + id;
	}));
};
$(function()
{
	$dswork.page.menu("delDemo.htm", "updDemo1.htm", "getDemoById.htm", ${pageModel.currentPage});
});
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">DEMO列表</td>
		<td class="menuTool">
			<a class="insert" href="addDemo1.htm">添加</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getDemo.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="title">标题：</td>
		<td class="input"><input name="title" value="${fn:escapeXml(param.title)}" /></td>
	</tr>
	<tr>
		<td class="title">内容：</td>
		<td class="input"><input name="content" value="${fn:escapeXml(param.content)}" /></td>
	</tr>
	<tr>
		<td class="title">创建时间：</td>
		<td class="input"><input name="foundtime" value="${fn:escapeXml(param.foundtime)}" /></td>
	</tr>
	<tr>
		<td class="query" colspan="2"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delDemo.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:3%">操作</td>
		<td>标题</td>
		<td>内容</td>
		<td>创建时间</td>
		<td style="width:15%">操作</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td>${d.title}</td>
		<td>${d.content}</td>
		<td>${d.foundtime}</td>
		<td class="menuTool">
			<a class="update" href="updDemo1.htm?keyIndex=${d.id}">修改</a>
			<a class="delete" href="delDemo.htm?keyIndex=${d.id}">删除</a>
		</td>
	</tr>
</c:forEach>
</table>
<input id="_submit_" type="submit" style="display:none;" />
<input name="page" type="hidden" value="${pageModel.currentPage}" />
</form>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
<br />
</body>
</html>
