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
	parent.$jskey.dialog.close();
}};
$dswork.validCallBack = function(){
	if($("option[pid='" + $("select[name='id']").val() + "']").length > 0){
		alert("不能拷贝到父级栏目");
		return false;
	}
	return true;
};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="copyPage2.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">拷贝到栏目</td>
		<td class="form_input">
			<select name="id">
			<c:forEach items="${list}" var="d">
				<option value="${d.id}" pid="${d.pid}">${d.label}${fn:escapeXml(d.name)}</option>
			</c:forEach>
			</select>
		</td>
	</tr>
</table>
<input type="hidden" name="keyIndex" value="${param.keyIndex}" />
</form>
</body>
</html>
