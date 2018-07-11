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
	location.href = "getSystem.htm?page=${fn:escapeXml(param.page)}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getSystem.htm?page=${fn:escapeXml(param.page)}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addSystem2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">应用标识</td>
		<td class="form_input"><input type="text" id="alias" name="alias" style="width:200px;" dataType="Char" maxlength="64" value="" /> <span class="imp">*</span> <span style="font-weight:bold;">添加后不可修改</span></td>
	</tr>
	<tr>
		<td class="form_title">应用密码</td>
		<td class="form_input"><input type="text" id="password" name="password" style="width:100px;" dataType="Char" maxlength="32" value="1" /></td>
	</tr>
	<tr>
		<td class="form_title">名称</td>
		<td class="form_input"><input type="text" id="name" name="name" style="width:200px;" dataType="RequireTrim" maxlength="100" value="" /> <span class="imp">*</span></td>
	</tr>
	<tr>
		<td class="form_title">网络地址</td>
		<td class="form_input"><input type="text" id="domainurl" name="domainurl" style="width:300px;" require="false" dataType="Url" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">根路径</td>
		<td class="form_input"><input type="text" id="rooturl" name="rooturl" style="width:200px;" maxlength="100" value="" /> <span style="font-weight:bold;">如：/root</span></td>
	</tr>
	<tr>
		<td class="form_title">菜单地址</td>
		<td class="form_input"><input type="text" id="menuurl" name="menuurl" style="width:200px;" maxlength="100" value="" /> <span style="font-weight:bold;">如：/root/menu.jsp</span></td>
	</tr>
	<tr>
		<td class="form_title">备注</td>
		<td class="form_input"><textarea id="memo" name="memo" style="width:99%;height:100px;"></textarea></td>
	</tr>
</table>
</form>
</body>
</html>
