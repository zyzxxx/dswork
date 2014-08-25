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
	$("#listFormSave").click(function(){if(confirm("确定保存排序吗？")){
		$("#listForm").ajaxSubmit($dswork.doAjaxOption);
	}});
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getCategory.htm";
}};
</script>
<style type="text/css">
.v{padding-left:3px;}
.v img{line-height:20px;vertical-align:middle;}
.k{padding:0px;margin:0px;}
.k input{border:none;background-color:transparent;text-align:center;}
</style>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">栏目列表</td>
		<td class="menuTool">
			<a class="insert" href="addCategory1.htm?page=${pageModel.currentPage}">添加</a>
			<a class="save" id="listFormSave" href="#">保存排序</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="listForm" method="post" action="updCategorySeq.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:5%">操作</td>
		<td style="width:6%">排序</td>
		<td style="width:8%">栏目ID</td>
		<td style="width:50%">名称</td>
		<td>目录</td>
		<td>网站模板</td>
		<td>APP模板</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td><input name="seq" type="text" style="width:24px;" value="${d.seq}" /></td>
		<td class="k"><input name="keyIndex" type="text" style="width:60px;" readonly="readonly" value="${d.id}" /></td>
		<td class="v" style="text-align:left;">${d.label}${fn:escapeXml(d.name)}<c:if test="${d.status>0}"><img src="${ctx}/themes/cms/${d.status}.png"<c:if test="${d.status>0}"> title="${fn:escapeXml(d.url)}"</c:if> /></c:if></td>
		<td>${fn:escapeXml(d.folder)}</td>
		<td>${fn:escapeXml(d.viewsite)}</td>
		<td>${fn:escapeXml(d.viewapp)}</td>
	</tr>
</c:forEach>
</table>
</form>
</body>
</html>
