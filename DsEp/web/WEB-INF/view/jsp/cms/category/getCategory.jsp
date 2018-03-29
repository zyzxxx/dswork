<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<c:if test="${siteid<0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">栏目管理：没有可管理的站点</td>
	</tr>
</table>
</body>
</c:if>

<c:if test="${siteid>=0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$(function(){
	$dswork.page.menu("delCategory.htm?siteid=${siteid}", "updCategory1.htm?siteid=${siteid}", "", "");
	$("#listFormSave").click(function(){if(confirm("确定保存排序吗？")){
		$("#listForm").ajaxSubmit($dswork.doAjaxOption);
	}});
	$("#site").bind("click", function(){
		if($(this).val()!="${siteid}"){
			location.href = "getCategory.htm?siteid="+$(this).val();
		}
	});
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getCategory.htm?siteid=${siteid}";
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
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
		</td>
		<td class="menuTool">
			<a class="insert" href="addCategory1.htm?siteid=${siteid}">添加</a>
			<a class="save" id="listFormSave" href="#">保存排序</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="listForm" method="post" action="updCategorySeq.htm?siteid=${siteid}">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:5%">操作</td>
		<td style="width:5%">排序</td>
		<td style="width:6%">栏目ID</td>
		<td>名称</td>
		<td style="width:17%">栏目模板</td>
		<td style="width:13%">内容模板</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td><input name="seq" type="text" style="width:24px;" value="${d.seq}" /></td>
		<td class="k"><input name="keyIndex" type="text" style="width:100%;" readonly="readonly" value="${d.id}" /></td>
		<td class="v" style="text-align:left;">${d.label}${fn:escapeXml(d.name)}&nbsp;<a onclick="return false;" href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a></td>
		<td>${fn:escapeXml(d.viewsite)}</td>
		<td>${fn:escapeXml(d.pageviewsite)}</td>
	</tr>
</c:forEach>
</table>
</form>
</body>
</c:if>
</html>
