<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
function updSetRole(id, title){
	parent.updSetRole(id, $("#u" + id).text());
	return false;
}
$dswork.page.join = function(td, menu, id){
	$(menu).append($('<div iconCls="menuTool-key">授权</div>').bind("click", function(){
		updSetRole(id);
	}));
	td.parent().css("cursor", "pointer").bind("dblclick", function(event){updSetRole(id);});
};
$(function(){
$dswork.page.menu("", "", "", "");
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">角色列表</td>
		<td class="menuTool">
		</td>
	</tr>
</table>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:5%">操作</td>
		<td style="width:30%">名称</td>
		<td>备注</td>
		<td style="width:8%">操作</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td id="u${d.id}">${fn:escapeXml(d.name)}</td>
		<td>${fn:escapeXml(d.memo)}</td>
		<td class="menuTool">
			<a class="key" onclick="return updSetRole('${d.id}');" href="#">授权</a>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
