<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
function updSetUser(id){
	$jskey.dialog.showDialog({url:"./updSetUser1.htm?systemid=${systemid}&id=" + id,title:$("#u" + id).text(),fit:true,draggable:false});
	return false;
}
$dswork.page.join = function(td, menu, id){
	$(menu).append($('<div iconCls="menuTool-key">受权</div>').bind("click", function(){
		updSetUser(id);
	}));
	td.parent().css("cursor", "pointer").bind("dblclick", function(event){updSetUser(id);});
};
$(function(){
	try{$("#status").val("${fn:escapeXml(param.status)}");}catch(e){}
	$dswork.page.menu("", "", "", "");
	$("#status").bind("change", function(){
		$("#queryForm").submit();
	});
});
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">用户受权</td>
		<td class="menuTool">
			<a class="tool" href="getRoleTree.htm?systemid=${systemid}">角色授权</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getUser.htm?systemid=${systemid}">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;姓名：<input id="name" name="name" value="${fn:escapeXml(param.name)}" style="width:75px;" />
			&nbsp;手机：<input id="mobile" name="mobile" value="${fn:escapeXml(param.mobile)}" style="width:75px;" />
			&nbsp;状态：<select id="status" name="status" style="width:55px;"><option value="">全部</option><option value="1">启用</option><option value="0">禁用</option></select>
		</td>
		<td class="query"><input id="_querySubmit_" type="submit" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:5%">操作</td>
		<td style="width:20%;">姓名(帐号)</td>
		<td>单位</td>
		<td style="width:15%;">部门</td>
		<td style="width:23%;">操作</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td class="menuTool" keyIndex="${d.id}" v="${'admin'==d.account?'true':''}">&nbsp;</td>
		<td id="u${d.id}" style="text-align:left;">&nbsp;${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.orgpname)}</td>
		<td>${fn:escapeXml(d.orgname)}</td>
		<td class="menuTool">
			<a class="key" onclick="return updSetUser('${d.id}');" href="#">受权</a>
		</td>
	</tr>
</c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
