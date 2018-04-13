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
	$jskey.dialog.showDialog({title:'栏目明细',fit:true,url:'getRecycledCategoryById.htm?siteid=${siteid}&keyIndex=' + id});
}
$dswork.page.join = function(td, menu, id){
	$(menu).append($('<div iconCls="menuTool-graph">还原到栏目</div>').bind("click", function(){
		$jskey.dialog.showDialog({title:'还原到栏目',width:600,height:400,url:'updRecycledCategory1.htm?siteid=${siteid}&keyIndex=' + id});
	}));
	$(menu).append($('<div iconCls="menuTool-graph">栏目明细</div>').bind("click", function(){
		showDetail(id);
	}));
};
$(function(){
	$dswork.page.menu("delRecycledCategory.htm?siteid=${siteid}", "", "", "");
	$("#site").bind("click", function(){if($(this).val()!="${siteid}"){
		location.href = "getRecycledCategory.htm?siteid="+$(this).val();
	}});
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
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
		</td>
		<td class="menuTool">
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="listForm" method="post" action="delRecycledCategory.htm?siteid=${siteid}">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td style="width:6%">栏目ID</td>
		<td>名称</td>
		<td style="width:17%">栏目模板</td>
		<td style="width:13%">内容模板</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr ondblclick='return showDetail("${d.id}");' style="cursor:pointer;">
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td class="k"><input name="keyIndex" type="text" style="width:100%;" readonly="readonly" value="${d.id}" /></td>
		<td class="v" style="text-align:left;">${fn:escapeXml(d.name)}&nbsp;<a onclick="return false;" href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a></td>
		<td>${fn:escapeXml(d.viewsite)}</td>
		<td>${fn:escapeXml(d.pageviewsite)}</td>
	</tr>
</c:forEach>
</table>
</form>
</body>
</c:if>
</html>
