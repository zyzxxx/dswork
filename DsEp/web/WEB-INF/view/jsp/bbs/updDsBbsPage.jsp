<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getDsBbsPage.htm?page=${page}";
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getDsBbsPage.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updDsBbsPage2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">站点ID</td>
		<td class="form_input"><input type="text" name="siteid" maxlength="100" value="${fn:escapeXml(po.siteid)}" /></td>
	</tr>
	<tr>
		<td class="form_title">版块ID</td>
		<td class="form_input"><input type="text" name="forumid" maxlength="100" value="${fn:escapeXml(po.forumid)}" /></td>
	</tr>
	<tr>
		<td class="form_title">发表人ID</td>
		<td class="form_input"><input type="text" name="userid" maxlength="100" value="${fn:escapeXml(po.userid)}" /></td>
	</tr>
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" value="${fn:escapeXml(po.title)}" /></td>
	</tr>
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input"><input type="text" name="url" maxlength="100" value="${fn:escapeXml(po.url)}" /></td>
	</tr>
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" value="${fn:escapeXml(po.summary)}" /></td>
	</tr>
	<tr>
		<td class="form_title">精华(0否，1是)</td>
		<td class="form_input"><input type="text" name="isessence" maxlength="100" value="${fn:escapeXml(po.isessence)}" /></td>
	</tr>
	<tr>
		<td class="form_title">置顶(0否，1是)</td>
		<td class="form_input"><input type="text" name="istop" maxlength="100" value="${fn:escapeXml(po.istop)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" value="${fn:escapeXml(po.metakeywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" value="${fn:escapeXml(po.metadescription)}" /></td>
	</tr>
	<tr>
		<td class="form_title">发表人</td>
		<td class="form_input"><input type="text" name="releaseuser" maxlength="100" value="${fn:escapeXml(po.releaseuser)}" /></td>
	</tr>
	<tr>
		<td class="form_title">发表时间</td>
		<td class="form_input"><input type="text" name="releasetime" maxlength="100" value="${fn:escapeXml(po.releasetime)}" /></td>
	</tr>
	<tr>
		<td class="form_title">结贴时间</td>
		<td class="form_input"><input type="text" name="overtime" maxlength="100" value="${fn:escapeXml(po.overtime)}" /></td>
	</tr>
	<tr>
		<td class="form_title">最后回复人</td>
		<td class="form_input"><input type="text" name="lastuser" maxlength="100" value="${fn:escapeXml(po.lastuser)}" /></td>
	</tr>
	<tr>
		<td class="form_title">最后回复时间</td>
		<td class="form_input"><input type="text" name="lasttime" maxlength="100" value="${fn:escapeXml(po.lasttime)}" /></td>
	</tr>
	<tr>
		<td class="form_title">点击量</td>
		<td class="form_input"><input type="text" name="numpv" maxlength="100" value="${fn:escapeXml(po.numpv)}" /></td>
	</tr>
	<tr>
		<td class="form_title">回贴数</td>
		<td class="form_input"><input type="text" name="numht" maxlength="100" value="${fn:escapeXml(po.numht)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><input type="text" name="content" maxlength="100" value="${fn:escapeXml(po.content)}" /></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
