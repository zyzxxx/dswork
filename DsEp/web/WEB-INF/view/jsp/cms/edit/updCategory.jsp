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
$dswork.callback = function(){if($dswork.result.type==1){
	location.reload();
}}
$(function(){
	$(".form_title").css("width", "8%");
	$('#content').xheditor({html5Upload:true,upMultiple:1,upLinkUrl:"${ctx}/cms/page/uploadFile.htm?categoryid=${po.id}",upImgUrl:"${ctx}/cms/page/uploadImage.htm?categoryid=${po.id}"});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改栏目</td>
		<td class="menuTool">
			<a class="look" target="_blank" href="${ctx}/cms/preview.chtml?siteid=${po.siteid}&categoryid=${po.id}">预览本栏目</a>
		<c:if test="${!po.audit}">
			<a class="submit" id="_submit" href="javascript:void(0);">提交</a>
			<a class="save" id="_save" href="javascript:void(0);">保存</a>
			<script type="text/javascript">
			$(function(){
				$("#_save").click(function(){
					if(confirm("确认保存吗？")){
						$("input[name='auditstatus']").val(0);
						$("#dataForm").ajaxSubmit($dswork.doAjaxOption);
					}
				})
				$("#_submit").click(function(){
					if(confirm("确认提交吗？")){
						$("input[name='auditstatus']").val(1);
						$("#dataForm").ajaxSubmit($dswork.doAjaxOption);
					}
				});
			});
			</script>
		<c:if test="${po.status>0 && (po.draft || po.nopass)}">
			<a class="back" id="_restore" href="javascript:void(0);">还原</a>
			<script type="text/javascript">
			$(function(){
				$("#_restore").click(function(){
					if(confirm("确认还原吗？")){
						$('<form method="post" action="updCategoryRestore.htm">').append('<input name="id" value="${po.id}">').ajaxSubmit($dswork.doAjaxOption);
					}
				})
			});
			</script>
		</c:if>
		</c:if>
		<c:if test="${po.audit}">
			<a class="back" id="_back" href="javascript:void(0);">撤回提交</a>
			<script type="text/javascript">
			$(function(){
				$("#_back").click(function(){
					if(confirm("确认撤回吗？")){
						$("input[name='auditstatus']").val(0);
						$("#dataForm").ajaxSubmit($dswork.doAjaxOption);
					}
				});
				$("input,textarea").attr("readonly", "readonly");
			});
			</script>
		</c:if>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updCategory2.htm">
<c:if test="${po.audit}">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr><td class="form_input" style="color:red;text-align:center;">信息审核中，如需修改需先撤回</td></tr>
</table>
</c:if>
<c:if test="${!po.audit}">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" style="width:400px;" value="${fn:escapeXml(po.summary)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metakeywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metadescription)}" /></td>
	</tr>
	<tr>
		<td class="form_title">来源</td>
		<td class="form_input"><input type="text" name="releasesource" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.releasesource)}" /></td>
	</tr>
	<tr>
		<td class="form_title">作者</td>
		<td class="form_input"><input type="text" name="releaseuser" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.releaseuser)}" /></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input"><input type="text" name="img" maxlength="100" style="width:400px;" value="${fn:escapeXml(po.img)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" style="width:99%;height:300px;">${po.content}</textarea></td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			发布时间：<input type="text" name="releasetime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value="${fn:escapeXml(po.releasetime)}" />
		</td>
	</tr>
</table>
<c:if test="${po.nopass || po.pass}">
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">审核结果</td>
		<td class="form_input" style="color:${po.pass?'green':'red'}">${po.pass?'通过':'不通过'}</td>
	</tr>
<c:if test="${po.nopass}">
	<tr>
		<td class="form_title">审核意见</td>
		<td class="form_input">${fn:escapeXml(po.msg)}</td>
	</tr>
</c:if>
</table>
</c:if>
</c:if>
<input type="hidden" name="id" value="${po.id}" />
<input type="hidden" name="auditstatus" value="${po.auditstatus}" />
</form>
</body>
</html>
