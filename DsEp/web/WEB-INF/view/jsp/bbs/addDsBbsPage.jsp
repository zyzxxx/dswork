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
	location.href = "getDsBbsPage.htm";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getDsBbsPage.htm?page=${param.page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addDsBbsPage2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">站点ID</td>
		<td class="form_input"><input type="text" name="siteid" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">版块ID</td>
		<td class="form_input"><input type="text" name="forumid" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">发表人ID</td>
		<td class="form_input"><input type="text" name="userid" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input"><input type="text" name="url" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">精华(0否，1是)</td>
		<td class="form_input"><input type="text" name="isessence" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">置顶(0否，1是)</td>
		<td class="form_input"><input type="text" name="istop" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">发表人</td>
		<td class="form_input"><input type="text" name="releaseuser" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">发表时间</td>
		<td class="form_input"><input type="text" name="releasetime" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">结贴时间</td>
		<td class="form_input"><input type="text" name="overtime" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">最后回复人</td>
		<td class="form_input"><input type="text" name="lastuser" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">最后回复时间</td>
		<td class="form_input"><input type="text" name="lasttime" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">点击量</td>
		<td class="form_input"><input type="text" name="numpv" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">回贴数</td>
		<td class="form_input"><input type="text" name="numht" maxlength="100" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><input type="text" name="content" maxlength="100" value="" /></td>
	</tr>
</table>
</form>
</body>
</html>
