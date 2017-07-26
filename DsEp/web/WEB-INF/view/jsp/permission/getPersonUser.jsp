<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function empower(account){
	$jskey.dialog.showDialog({url:"${ctx}/cms/permission/updPermission1.htm?account="+account,title:"分配权限",fit:true,draggable:false});
}
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">个人列表</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getPersonUser.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;账号：<input type="text" name="account" style="width:100px;" value="${fn:escapeXml(param.account)}" />
			&nbsp;姓名：<input type="text" name="name" style="width:100px;" value="${fn:escapeXml(param.name)}" />
			&nbsp;手机：<input type="text" class="text" id="mobile" name="mobile" value="${fn:escapeXml(param.mobile)}" style="width:75px;" />
			&nbsp;状态：<select id="status" name="status" style="width:55px;"><option value="">全部</option><option value="1">启用</option><option value="0">禁用</option></select>
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>姓名(帐号)</td>
		<td style="width:20%">手机</td>
		<td style="width:20%">Email</td>
		<td style="width:20%;">状态</td>
		<td style="width:20%;">授权</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d" varStatus="status">
	<tr>
		<td>${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.mobile)}</td>
		<td>${fn:escapeXml(d.email)}</td>
		<td ${d.status==1?'':'style="color:red"}'}>${1==d.status?"启用":"禁用"}</td>
		<td class="menuTool"><a class="user" href="javascript:void(0);" onclick="empower('${fn:escapeXml(d.account)}')">授予权限</a></td>
	</tr>
</c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
