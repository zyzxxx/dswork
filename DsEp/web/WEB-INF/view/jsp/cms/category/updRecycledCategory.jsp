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
	parent.location.reload();
}};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">还原到栏目</td>
		<td class="menuTool"><a class="save" id="dataFormSave" href="#">保存</a></td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updRecycledCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable" id="listTable">
	<tr>
		<td class="form_title">选取父栏目</td>
		<td class="form_input" style="text-align:center;">
			<select id="pid" name="pid"><option value="0">≡顶级栏目≡</option>
				<c:forEach items="${list}" var="d"><option value="${d.id}">${d.label}${fn:escapeXml(d.name)}&nbsp;[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
<input type="hidden" name="keyIndex" value="${id}" />
</form>
</body>
