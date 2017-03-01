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
	location.href = "getCategory.htm?siteid=${fn:escapeXml(param.siteid)}";
}};
$(function(){
	try{
		$("#pid").val("${po.pid}");
		$("#viewsite").val("${po.viewsite}");
		$("#pageviewsite").val("${po.pageviewsite}");
	}catch(e){}
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getCategory.htm?siteid=${fn:escapeXml(param.siteid)}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">上级栏目</td>
		<td class="form_input"><select id="pid" name="pid"><option value="0">≡顶级栏目≡</option>
		<c:forEach items="${list}" var="d">
			<option value="${d.id}">${d.label}${fn:escapeXml(d.name)}</option>
		</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="form_title">栏目名称</td>
		<td class="form_input"><input type="text" name="name" maxlength="100" dataType="Require" value="${fn:escapeXml(po.name)}" /></td>
	</tr>
	<c:if test="${po.status!=2}">
	<tr>
		<td class="form_title">目录名称</td>
		<td class="form_input"><input type="text" name="folder" maxlength="100" dataType="Char" value="${fn:escapeXml(po.folder)}" /></td>
	</tr>
	</c:if>
	<tr>
		<td class="form_title">类型</td>
		<td class="form_input">${po.status==2?"外链":po.status==1?"单页":"列表"}</td>
	</tr>
	<c:if test="${po.status==2}">
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input"><input type="text" name="url" maxlength="100" style="width:400px;" dataType="Require" require="false" value="${fn:escapeXml(po.url)}" /></td>
	</tr>
	</c:if>
	<c:if test="${po.status!=2}">
	<tr>
		<td class="form_title">链接</td>
		<td class="form_input">${fn:escapeXml(po.url)}</td>
	</tr>
	</c:if>
	<c:if test="${po.status!=2}">
	<tr>
		<td class="form_title">栏目模板</td>
		<td class="form_input"><select id="viewsite" name="viewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${templates}" var="v"><option value="${v}">${v}</option></c:forEach>
		</select></td>
	</tr>
	<c:if test="${po.status==0}">
	<tr>
		<td class="form_title">内容模板</td>
		<td class="form_input"><select id="pageviewsite" name="pageviewsite" style="width:400px;"><option value=""></option>
			<c:forEach items="${templates}" var="v"><option value="${v}">${v}</option></c:forEach>
		</select></td>
	</tr>
	</c:if>
	</c:if>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
