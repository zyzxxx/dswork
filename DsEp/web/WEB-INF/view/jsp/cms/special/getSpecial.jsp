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
		<td class="title">专题管理：没有可管理的站点</td>
	</tr>
</table>
</body>
</c:if>
<c:if test="${siteid>=0}">
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp" %>
<script type="text/javascript">
$dswork.deleteRow = function(obj){
	var deleteId = $(obj).siblings('input[name="id"]');
	if(deleteId.size() > 0){
		$('#deleteDiv').append($('<input type="hidden" name="deleteId">').val(deleteId.val()));
	}
	$(obj).parent().parent().remove();
};
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getSpecial.htm?siteid=${fn:escapeXml(param.siteid)}";
}};
$(function(){
	$("#site").bind("click", function(){
		if($(this).val()!="${siteid}"){
			location.href = "getCategory.htm?siteid="+$(this).val();
		}
	});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select></td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getCategory.htm?siteid=${fn:escapeXml(param.siteid)}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updSpecial.htm">
<table id="contactTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:23%;">标题</td>
		<td style="width:23%;">模板</td><c:if test="${enablemobile}">
		<td style="width:23%;">移动版模板</td></c:if>
		<td style="width:23%;">链接</td>
		<td class="menuTool"><a class="insert" onclick="$('#contactTable>tbody').append($('#cloneTable').html());" href="#">添加项</a></td>
	</tr><c:forEach items="${list}" var="d">
	<tr class="list">
		<td><input type="text" name="title" value="${d.title}" datatype="Require" /></td>
		<td><select name="viewsite" v="${d.viewsite}"><c:forEach items="${templates}" var="v">
			<option value="${v}">${fn:replace(v,'.jsp','')}</option></c:forEach>
		</select></td><c:if test="${enablemobile}">
		<td><select name="mviewsite" v="${d.mviewsite}"><c:forEach items="${mtemplates}" var="v">
			<option value="${v}">${fn:replace(v,'.jsp','')}</option></c:forEach>
		</select></td></c:if>
		<td><input type="text" name="url" value="${fn:replace(d.url,'/','')}" datatype="Filename" /></td>
		<td><input type="button" class="delete" onclick="$dswork.deleteRow(this)" />
			<input type="hidden" name="id" value="${d.id}" /></td>
	</tr></c:forEach>
</table>
<div id="deleteDiv"></div>
<script type="text/template" id="cloneTable">
	<tr class="list">
		<td><input type="text" name="title" value="" datatype="Require" /></td>
		<td><select name="viewsite"><c:forEach items="${templates}" var="v">
			<option value="${v}">${fn:replace(v,'.jsp','')}</option></c:forEach>
		</select></td><c:if test="${enablemobile}">
		<td><select name="mviewsite"><c:forEach items="${mtemplates}" var="v">
			<option value="${v}">${fn:replace(v,'.jsp','')}</option></c:forEach>
		</select></td></c:if>
		<td><input type="text" name="url" value="" datatype="Filename" /></td>
		<td><input type="button" class="delete" onclick="$dswork.deleteRow(this)" /></td>
	</tr>
</script>
<input type="hidden" name="siteid" value="${fn:escapeXml(siteid)}" />
</form>
</body>
</c:if>
</html>
