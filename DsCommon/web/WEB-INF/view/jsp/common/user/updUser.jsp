<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getUser.htm?page=${param.page}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getUser.htm?page=${param.page}">返回</a> 
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updUser2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">帐号</td>
		<td class="form_input">${fn:escapeXml(po.account)}</td>
	</tr>
	<tr>
		<td class="form_title">姓名</td>
		<td class="form_input"><input type="text" id="name" name="name" dataType="Chinese" maxlength="25" value="${fn:escapeXml(po.name)}" /> <span class="imp">*</span></td>
	</tr>
	<tr>
		<td class="form_title">手机</td>
		<td class="form_input"><input type="text" id="mobile" name="mobile" require="false" dataType="Mobile" maxlength="50" value="${fn:escapeXml(po.mobile)}" /></td>
	</tr>
	<tr>
		<td class="form_title">电话</td>
		<td class="form_input"><input type="text" id="phone" name="phone" require="false" dataType="Phone" maxlength="50" value="${fn:escapeXml(po.phone)}" /></td>
	</tr>
	<tr>
		<td class="form_title">邮箱</td>
		<td class="form_input"><input type="text" id="email" name="email" style="width:200px;" require="false" dataType="Email" maxlength="250" value="${fn:escapeXml(po.email)}" /></td>
	</tr>
	<tr>
		<td class="form_title">身份证号</td>
		<td class="form_input"><input type="text" id="idcard" name="idcard" style="width:200px;" require="false" dataType="IdCard" maxlength="18" value="${fn:escapeXml(po.idcard)}" /></td>
	</tr>
	<tr>
		<td class="form_title">工作证号</td>
		<td class="form_input"><input type="text" id="workcard" name="workcard" style="width:200px;" require="false" dataType="Require" maxlength="64" value="${fn:escapeXml(po.workcard)}" /></td>
	</tr>
	<tr>
		<td class="form_title">创建时间</td>
		<td class="form_input">${fn:escapeXml(po.createtime)}</td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
