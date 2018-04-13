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
function showDetail(id){
	$('#mainFrame').attr('src', 'getRecycledCategoryById.htm?siteid=${siteid}&keyIndex=' + id);
}
$dswork.page.join = function(td, menu, id){
	$(menu).append($('<div iconCls="menuTool-graph">还原到栏目</div>').bind("click", function(){
		$jskey.dialog.showDialog({title:'还原到栏目',width:600,height:400,url:'updRecycledCategory1.htm?siteid=${siteid}&keyIndex=' + id});
	}));
};
$(function(){
	$dswork.page.menu("delRecycledCategory.htm?siteid=${siteid}", "", "", "");
	$("#site").bind("click", function(){if($(this).val()!="${siteid}"){
		location.href = "getRecycledCategory.htm?siteid="+$(this).val();
	}});
	$('#dataTable tr.list_body').eq(0).click();
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getRecycledCategory.htm?siteid=${siteid}";
}};
</script>
<style type="text/css">
.v{padding-left:3px;}
.v img{line-height:20px;vertical-align:middle;}
.k{padding:0px;margin:0px;}
.k input{border:none;background-color:transparent;text-align:center;}
</style>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="north" style="overflow:hidden;border:0px;height:30px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" style="width:800px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">回收站栏目列表</td>
		<td class="menuTool">
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="listForm" method="post" action="delRecycledCategory.htm?siteid=${siteid}">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:4%"><input id="chkall" type="checkbox" /></td>
		<td style="width:10%">操作</td>
		<td style="width:12%">栏目ID</td>
		<td>名称</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr class="list_body" onclick='return showDetail("${d.id}");' style="cursor:pointer;">
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td class="k"><input name="keyIndex" type="text" style="width:100%;" readonly="readonly" value="${d.id}" /></td>
		<td class="v" style="text-align:left;">${fn:escapeXml(d.name)}&nbsp;<a onclick="return false;" href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a></td>
	</tr>
</c:forEach>
</table>
</form>
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</c:if>
</html>
