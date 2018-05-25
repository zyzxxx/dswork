<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp"%>
<script type="text/javascript">
$${frame}.callback = function(){if($${frame}.result.type == 1){
	location.href = "get${model}.htm";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="get${model}.htm?page=${'$'}{param.page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="add${model}2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
<#list columnList as c>
	<#if !c.key>
		<#if c.nameLowerCamel != 'memo'>
	<tr>
		<td class="form_title">${c.comment}</td>
		<td class="form_input"><input type="text" name="${c.nameLowerCamel}" maxlength="100" value="" /></td>
	</tr>
		<#else>
	<tr>
		<td class="form_title">${c.comment}</td>
		<td class="form_input"><textarea name="${c.nameLowerCamel}" style="width:400px;height:60px;"></textarea></td>
	</tr>
		</#if>
	</#if>
</#list>
</table>
</form>
</body>
</html>
