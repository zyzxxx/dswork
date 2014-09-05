<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<%@include file="/commons/include/editor.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getPage.htm?id=${po.categoryid}&page=${page}";
}};
$(function(){
	try{$(".form_title").css("width", "8%");}catch(e){}
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getPage.htm?id=${po.categoryid}&page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updPage2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" style="width:400px;" dataType="Require" value="${fn:escapeXml(po.title)}" /></td>
	</tr>
	<tr>
		<td class="form_title">关键词</td>
		<td class="form_input"><input type="text" name="keywords" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.keywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" style="width:400px;" value="${fn:escapeXml(po.summary)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" class="xheditor" style="width:99%;height:300px;">${po.content}</textarea></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input"><input type="text" name="img" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.img)}" /><label><input type="checkbox" name="imgtop" value="1" ${po.imgtop == 1?' checked="checked"':''}/>焦点图</label></td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			<label><input type="checkbox" name="pagetop" value="1" ${po.pagetop == 1?' checked="checked"':''}/>首页推荐</label>
			&nbsp;&nbsp;发布时间：<input type="text" name="releasetime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value="${fn:escapeXml(po.releasetime)}" />
		</td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
