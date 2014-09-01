<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script src="../../jquery/jquery.js" type="text/javascript"></script>
<script src="../../easyui/jquery.easyui.js" type="text/javascript"></script>
<script src="../jskey_core.js" type="text/javascript"></script>
<script type="text/javascript">
$dswork.page.join = function(td, menu, id){
	var qybm = td.attr("qybm");
	$(menu).append($('<div iconCls="menuTool-user">用户列表</div>').bind("click", function(){
		$jskey.dialog.showDialog({title:'用户管理', url:'../user/getUser.htm?qybm='+qybm,width:700,height:500});
	}));
};
$(function(){
	$dswork.page.menu("", "updEnterprise1.htm", "", "${pageModel.currentPage}");
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getEnterprise.htm?page=${pageModel.currentPage}";
}};
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">企业列表</td>
		<td class="menuTool">
			<a class="insert" href="addEnterprise1.htm?page=${pageModel.currentPage}">添加</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getEnterprise.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;企业名称：<input type="text" class="text" name="name" value="${fn:escapeXml(param.name)}" />
			&nbsp;状态：<input type="text" class="text" name="status" value="${fn:escapeXml(param.status)}" />
			&nbsp;类型：<input type="text" class="text" name="type" value="${fn:escapeXml(param.type)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td style="width:15%">企业编码</td>
		<td>企业名称</td>
		<td>状态</td>
		<td>类型</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}" qybm="${fn:escapeXml(d.qybm)}">&nbsp;</td>
		<td>${fn:escapeXml(d.qybm)}</td>
		<td>${fn:escapeXml(d.name)}</td>
		<td>${fn:escapeXml(d.status)}</td>
		<td>${fn:escapeXml(d.type)}</td>
	</tr>
</c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
