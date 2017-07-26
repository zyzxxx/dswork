<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type==1){
	location.reload();
}}
$(function(){
	$(".form_title").css("width", "8%");
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">审核</td>
	<c:if test="${po.status==1}">
		<td class="menuTool">
			<a class="submit" id="_pass" href="javascript:void(0);">通过</a>
			<a class="close" id="_nopass" href="javascript:void(0);">不通过</a>
			<script type="text/javascript">
			$("#_pass").click(function(){
				if(confirm("确认通过？")){
					$("input[name='status']").val(4);
					$("#dataForm").ajaxSubmit($dswork.doAjaxOption);
				}
			});
			$("#_nopass").click(function(){
				if(confirm("确认不通过？")){
					$("input[name='status']").val(2);
					$("#dataForm").ajaxSubmit($dswork.doAjaxOption);
				}
			})
			</script>
		</td>
	</c:if>
	</tr>
</table>
<div class="line"></div>
<c:if test="${po.status!=1}">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr><td class="form_input" style="color:red;text-align:center;">没有新提交的页面</td></tr>
</table>
</c:if>
<c:if test="${po.status==1}">
<form id="dataForm" method="post" action="auditCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input">${fn:escapeXml(po.summary)}</td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input">${fn:escapeXml(po.metakeywords)}</td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input">${fn:escapeXml(po.metadescription)}</td>
	</tr>
	<tr>
		<td class="form_title">来源</td>
		<td class="form_input">${fn:escapeXml(po.releasesource)}</td>
	</tr>
	<tr>
		<td class="form_title">作者</td>
		<td class="form_input">${fn:escapeXml(po.releaseuser)}</td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input">${fn:escapeXml(po.img)}</td>
	</tr>
	<tr>
		<td class="form_title">预览</td>
		<td class="form_input menuTool">
			<a class="look" target="_blank" href="${ctx}/cms/preview.chtml?siteid=${po.siteid}&categoryid=${po.id}">查看栏目内容</a>
		</td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			发布时间：${fn:escapeXml(po.releasetime)}
		</td>
	</tr>
</table>
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">审核意见</td>
		<td class="form_input"><textarea name="msg" style="width:99%;heigth:100px;"></textarea></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
<input type="hidden" name="status" value="${po.status}" />
</form>
</c:if>
</body>
</html>
