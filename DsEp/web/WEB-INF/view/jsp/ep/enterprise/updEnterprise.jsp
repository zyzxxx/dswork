<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getEnterprise.htm?page=${page}";
}};
$(function(){
	try{$("#status").val("${status}");}catch(e){}
	try{$("#type").val("${type}");}catch(e){}
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getEnterprise.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updEnterprise2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">企业名称</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" value="${fn:escapeXml(po.name)}" /></td>
	</tr>
	<tr>
		<td class="form_title">企业编码</td>
		<td class="form_input"><input type="text" name="qybm" maxlength="100" value="${fn:escapeXml(po.qybm)}" /></td>
	</tr>
	<tr>
		<td class="form_title">状态</td>
		<td class="form_input"><select id="status" name="status" style="width:135px;">
			<option value="1">正在运营</option>
			<option value="2">暂停运营</option>
			<option value="3">已注销</option>
		</select></td>
	</tr>
	<tr>
		<td class="form_title">类型</td>
		<td class="form_input"><select id="type" name="type" style="width:135px;">
			<option value="1">国营企业</option>
			<option value="2">有限责任公司</option>
		</select></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
