<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getDict.htm?status=${fn:escapeXml(param.status)}&page=${fn:escapeXml(parem.page)}";
}};
$(function(){
	if("${fn:escapeXml(param.status)}" == "0"){document.getElementById("status0").checked = true;}
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getDict.htm?status=${fn:escapeXml(param.status)}&page=${fn:escapeXml(param.page)}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addDict2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">引用名</td>
		<td class="form_input"><input type="text" name="name" dataType="Char" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">名称</td>
		<td class="form_input"><input type="text" name="label" dataType="Require" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">分类</td>
		<td class="form_input">
			<input type="radio" id="status1" name="status" value="1" /><label for="status1">树形集合</label>
			<input type="radio" id="status0" name="status" value="0" checked="checked" /><label for="status0">列表集合</label>
			<span style="font-weight:bold;">保存后不可修改</span>
		</td>
	</tr>
</table>
</form>
</body>
</html>
