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
	location.href = "getCategory.htm";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getCategory.htm">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">父ID</td>
		<td class="form_input"><input type="text" name="pid" maxlength="100" value="${fn:escapeXml(po.pid)}" /></td>
	</tr>
	<tr>
		<td class="form_title">企业编码</td>
		<td class="form_input"><input type="text" name="qybm" maxlength="100" value="${fn:escapeXml(po.qybm)}" /></td>
	</tr>
	<tr>
		<td class="form_title">栏目名称</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" value="${fn:escapeXml(po.name)}" /></td>
	</tr>
	<tr>
		<td class="form_title">目录名称</td>
		<td class="form_input"><input type="text" name="folder" maxlength="100" value="${fn:escapeXml(po.folder)}" /></td>
	</tr>
	<tr>
		<td class="form_title">状态(0列表，1单页，2外链)</td>
		<td class="form_input"><input type="text" name="status" maxlength="100" value="${fn:escapeXml(po.status)}" /></td>
	</tr>
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input"><input type="text" name="url" maxlength="100" value="${fn:escapeXml(po.url)}" /></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input"><input type="text" name="img" maxlength="100" value="${fn:escapeXml(po.img)}" /></td>
	</tr>
	<tr>
		<td class="form_title">网站模板</td>
		<td class="form_input"><input type="text" name="viewsite" maxlength="100" value="${fn:escapeXml(po.viewsite)}" /></td>
	</tr>
	<tr>
		<td class="form_title">APP模板</td>
		<td class="form_input"><input type="text" name="viewapp" maxlength="100" value="${fn:escapeXml(po.viewapp)}" /></td>
	</tr>
	<tr>
		<td class="form_title">排序</td>
		<td class="form_input"><input type="text" name="seq" maxlength="100" value="${fn:escapeXml(po.seq)}" /></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
