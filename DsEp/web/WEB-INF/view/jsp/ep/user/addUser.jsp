<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getUser.htm?qybm=${qybm}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getUser.htm?page=${param.page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addUser2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">账号</td>
		<td class="form_input"><input type="text" name="account" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">密码</td>
		<td class="form_input"><input type="password" title="请输入数字和字母的混合密码" id="RepeatToAbc" name="password" dataType="Password" min="6" max="16" msg="密码须由数字和字母组成，长度为6到16个" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">重复</td>
		<td class="form_input"><input type="password" title="请再次输入" name="TestRepeat" dataType="Repeat" to="RepeatToAbc" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">姓名</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" dataType="Require" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">身份证号</td>
		<td class="form_input"><input type="text" name="idcard" maxlength="100" dataType="Idcard" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">电子邮件</td>
		<td class="form_input"><input type="text" name="email" maxlength="100" dataType="Email" require="false" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">手机</td>
		<td class="form_input"><input type="text" name="mobile" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">电话</td>
		<td class="form_input"><input type="text" name="phone" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">传真</td>
		<td class="form_input"><input type="text" name="fax" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">工作证号</td>
		<td class="form_input"><input type="text" name="workcard" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">CA证书的KEY</td>
		<td class="form_input"><input type="text" name="cakey" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属单位</td>
		<td class="form_input"><input type="text" name="ssdw" maxlength="100" style="width:200px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属部门</td>
		<td class="form_input"><input type="text" name="ssbm" maxlength="100" style="width:200px;" value="" /></td>
	</tr>
</table>
<input type="hidden" name="qybm" maxlength="100" value="${qybm}" />
</form>
</body>
</html>
