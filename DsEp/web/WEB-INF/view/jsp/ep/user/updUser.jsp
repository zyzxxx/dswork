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
	location.href = "getUser.htm?page=${page}&qybm=${po.qybm}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getUser.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updUser2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">账号</td>
		<td class="form_input"><input type="text" name="account" maxlength="100" value="${fn:escapeXml(po.account)}" /></td>
	</tr>
	<tr>
		<td class="form_title">密码</td>
		<td class="form_input"><input type="password" title="请输入数字和字母的混合密码" id="RepeatToAbc" name="password" dataType="Password" min="6" max="16" msg="密码须由数字和字母组成，长度为6到16个" value="${po.password}" /></td>
	</tr>
	<tr>
		<td class="form_title">重复</td>
		<td class="form_input"><input type="password" title="请再次输入" name="TestRepeat" dataType="Repeat" to="RepeatToAbc" value="${po.password}" /></td>
	</tr>
	<tr>
		<td class="form_title">姓名</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" dataType="Require" value="${fn:escapeXml(po.name)}" /></td>
	</tr>
	<tr>
		<td class="form_title">身份证号</td>
		<td class="form_input"><input type="text" name="idcard" maxlength="100" dataType="Idcard" value="${fn:escapeXml(po.idcard)}" /></td>
	</tr>
	<tr>
		<td class="form_title">电子邮件</td>
		<td class="form_input"><input type="text" name="email" maxlength="100" dataType="Email" value="${fn:escapeXml(po.email)}" /></td>
	</tr>
	<tr>
		<td class="form_title">手机</td>
		<td class="form_input"><input type="text" name="mobile" maxlength="100" value="${fn:escapeXml(po.mobile)}" /></td>
	</tr>
	<tr>
		<td class="form_title">电话</td>
		<td class="form_input"><input type="text" name="phone" maxlength="100" value="${fn:escapeXml(po.phone)}" /></td>
	</tr>
	<tr>
		<td class="form_title">传真</td>
		<td class="form_input"><input type="text" name="fax" maxlength="100" value="${fn:escapeXml(po.fax)}" /></td>
	</tr>
	<tr>
		<td class="form_title">工作证号</td>
		<td class="form_input"><input type="text" name="workcard" maxlength="100" value="${fn:escapeXml(po.workcard)}" /></td>
	</tr>
	<tr>
		<td class="form_title">CA证书的KEY</td>
		<td class="form_input"><input type="text" name="cakey" maxlength="100" value="${fn:escapeXml(po.cakey)}" /></td>
	</tr>
	<tr>
		<td class="form_title">所属单位</td>
		<td class="form_input"><input type="text" name="ssdw" maxlength="100" style="width:200px;" value="${fn:escapeXml(po.ssdw)}" /></td>
	</tr>
	<tr>
		<td class="form_title">所属部门</td>
		<td class="form_input"><input type="text" name="ssbm" maxlength="100" style="width:200px;" value="${fn:escapeXml(po.ssbm)}" /></td>
	</tr>
</table>
<input type="hidden" name="status" maxlength="100" value="${po.status}" />
<input type="hidden" name="createtime" value="${po.createtime}" />
<input type="hidden" name="qybm" value="${po.qybm}"/>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
