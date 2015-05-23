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
}};
$(function(){
	try{$("#pid").val("${po.pid}");}catch(e){}
	try{$(".form_title").css("width", "8%");}catch(e){}
	$('#content').xheditor({html5Upload:true,upMultiple:1,upLinkUrl:"uploadFile.htm?categoryid=${po.id}",upImgUrl:"uploadImage.htm?categoryid=${po.id}"});
});
$(function(){
	$("#btn_category").bind("click", function(){
		if(confirm("是否生成栏目\"${fn:escapeXml(po.name)}\"")){
			$.post("build.htm",{"siteid":"${po.siteid}", "categoryid":"${po.id}"},function(data){
				$dswork.doAjaxShow(data, function(){});
			});
		}
	});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="graph" id="btn_category" href="#">生成栏目</a>
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getCategory.htm?siteid=${param.siteid}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metakeywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metadescription)}" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" style="width:99%;height:300px;">${po.content}</textarea></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
