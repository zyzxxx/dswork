<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp"%>
<script type="text/javascript">
function appGO(id){
	document.getElementById("rightFrame").src = "getRoleTree.htm?systemid="+id;
	return false;
}
</script>
</head>
<body class="easyui-layout" fit="true">
<div region="north" style="overflow:hidden;border:0px;height:25px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title"><span style="font-weight:bold;">系统列表</span></td>
		<td class="menuTool">
			<a class="save" onclick="parent.$jskey.dialog.close();" href="#">完成选择</a>
		</td>
	</tr>
</table>
</div>
<div region="center" style="">
	<form id="queryForm" method="post" action="getRoleChoose.htm">
	<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
		<tr>
			<td class="input">
				&nbsp;名称：<input id="name" name="name" value="${fn:escapeXml(param.name)}" style="width:200px;" />
			</td>
			<td class="query" rowspan="2"><input id="_querySubmit_" type="submit" class="button" value="查询" /></td>
		</tr>
		<tr>
			<td class="input">
				&nbsp;标识：<input id="alias" name="alias" value="${fn:escapeXml(param.alias)}" style="width:200px;" />
			</td>
		</tr>
	</table>
	</form>
	<div class="line"></div>
	<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
		<tr class="list_title">
			<td>名称(标识)-单击系统名称进行设置</td>
		</tr>
	<c:forEach items="${pageModel.result}" var="d">
		<tr>
			<td style="text-align:left;" class="menuTool">
				&nbsp;<a class="check" onclick="return appGO('${d.id}');" href="#">${fn:escapeXml(d.name)}(${fn:escapeXml(d.alias)})</a>
			</td>
		</tr>
	</c:forEach>
	</table>
</div>
<div region="east" style="width:250px;">
	<iframe id="rightFrame" name="rightFrame" frameborder="0" scrolling="auto" style="width:100%;height:100%;" src="getRoleTree.htm"></iframe>
</div>
<div region="south" style="overflow:hidden;border:0px;height:25px;">
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</div>
</body>
</html>
