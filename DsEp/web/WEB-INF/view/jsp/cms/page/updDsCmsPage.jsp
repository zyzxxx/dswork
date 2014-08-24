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
	location.href = "getDsCmsPage.htm?page=${page}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getDsCmsPage.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updDsCmsPage2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">企业编码</td>
		<td class="form_input"><input type="text" name="qybm" maxlength="100" value="${fn:escapeXml(po.qybm)}" /></td>
	</tr>
	<tr>
		<td class="form_title">栏目ID</td>
		<td class="form_input"><input type="text" name="categoryid" maxlength="100" value="${fn:escapeXml(po.categoryid)}" /></td>
	</tr>
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" value="${fn:escapeXml(po.title)}" /></td>
	</tr>
	<tr>
		<td class="form_title">关键词</td>
		<td class="form_input"><input type="text" name="keywords" maxlength="100" value="${fn:escapeXml(po.keywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" value="${fn:escapeXml(po.summary)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><input type="text" name="content" maxlength="100" value="${fn:escapeXml(po.content)}" /></td>
	</tr>
	<tr>
		<td class="form_title">创建时间</td>
		<td class="form_input"><input type="text" name="createtime" maxlength="100" value="${fn:escapeXml(po.createtime)}" /></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input"><input type="text" name="img" maxlength="100" value="${fn:escapeXml(po.img)}" /></td>
	</tr>
	<tr>
		<td class="form_title">焦点图(0否，1是)</td>
		<td class="form_input"><input type="text" name="imgtop" maxlength="100" value="${fn:escapeXml(po.imgtop)}" /></td>
	</tr>
	<tr>
		<td class="form_title">首页推荐(0否，1是)</td>
		<td class="form_input"><input type="text" name="pagetop" maxlength="100" value="${fn:escapeXml(po.pagetop)}" /></td>
	</tr>
	<tr>
		<td class="form_title">网站模板</td>
		<td class="form_input"><input type="text" name="viewsite" maxlength="100" value="${fn:escapeXml(po.viewsite)}" /></td>
	</tr>
	<tr>
		<td class="form_title">APP模板</td>
		<td class="form_input"><input type="text" name="viewapp" maxlength="100" value="${fn:escapeXml(po.viewapp)}" /></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
