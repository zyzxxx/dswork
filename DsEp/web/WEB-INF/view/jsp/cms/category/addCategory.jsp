<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getCategory.htm?siteid=${fn:escapeXml(param.siteid)}";
}};
$(function(){
	$("#scope").bind("click", function(){
		if($("#scope").val() == 2){
			$("#mylink").show();
			$("#template_category").val("").hide();
			$("#template_page").val("").hide();
			$(".viewsite").val("");
			$(".pageviewsite").val("");
			$("#url").attr("require", "true");
		}
		else if($("#scope").val() == 1){
			$("#mylink").val("").hide();
			$("#template_category").show();
			$("#template_page").val("").hide();
			$(".pageviewsite").val("");
			$("#url").attr("require", "false");
		}
		else{
			$("#mylink").val("").hide();
			$("#template_category").show();
			$("#template_page").show();
			$("#url").attr("require", "false");
		}
	}).bind("change", function(){
		$("#scope").click();
	});
	$("#scope").click();
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getCategory.htm?siteid=${fn:escapeXml(param.siteid)}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">上级栏目</td>
		<td class="form_input"><select name="pid"><option value="0">≡顶级栏目≡</option>
		<c:forEach items="${list}" var="d">
			<option value="${d.id}">${d.label}${fn:escapeXml(d.name)}&nbsp;[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</option>
		</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="form_title">栏目名称</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" dataType="Require" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">类型</td>
		<td class="form_input">
			<select id="scope" name="scope" style="width:100px;">
				<option value="0">列表</option>
				<option value="1">单页</option>
				<option value="2">外链</option>
			</select>
		</td>
	</tr>
	<tbody id="mylink">
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input"><input type="text" id="url" name="url" maxlength="100" style="width:400px;" dataType="Require" require="false" value="" /></td>
	</tr>
	</tbody>
	<tbody id="template_category">
	<tr>
		<td class="form_title">栏目模板</td>
		<td class="form_input"><select class="viewsite" name="viewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${templates}" var="v"><c:if test="${not fn:startsWith(v,'page')}"><option value="${v}">${fn:replace(v,'.jsp','')}</option></c:if></c:forEach>
		</select></td>
	</tr>
<c:if test="${enablemobile}">
	<tr>
		<td class="form_title">移动版栏目模板</td>
		<td class="form_input"><select class="viewsite" name="mviewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${mtemplates}" var="v"><c:if test="${not fn:startsWith(v,'page')}"><option value="${v}">${fn:replace(v,'.jsp','')}</option></c:if></c:forEach>
		</select></td>
	</tr>
</c:if>
	</tbody>
	<tbody id="template_page">
	<tr>
		<td class="form_title">内容模板</td>
		<td class="form_input"><select class="pageviewsite" name="pageviewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${templates}" var="v"><c:if test="${fn:startsWith(v,'page')}"><option value="${v}">${fn:replace(v,'.jsp','')}</option></c:if></c:forEach>
		</select></td>
	</tr>
<c:if test="${enablemobile}">
	<tr>
		<td class="form_title">移动版内容模板</td>
		<td class="form_input"><select class="pageviewsite" name="mpageviewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${mtemplates}" var="v"><c:if test="${fn:startsWith(v,'page')}"><option value="${v}">${fn:replace(v,'.jsp','')}</option></c:if></c:forEach>
		</select></td>
	</tr>
</c:if>
	</tbody>
</table>
<input type="hidden" name="siteid" value="${fn:escapeXml(param.siteid)}" />
</form>
</body>
</html>
