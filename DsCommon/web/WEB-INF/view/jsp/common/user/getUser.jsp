<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function updStatus(objid, id){
	var obj = $("#" + objid), o = document.getElementById(objid);
	$.post("updUserStatus.htm",{"keyIndex":id,"status":obj.attr("v")==0?1:0},function(data){if(1 == data){
		if(1 == obj.attr("v")){
			obj.text("设为管理员").attr("v", 0);$("#td_" + objid).text("普通用户").css("color", "");
		}
		else{
			obj.text("设为普通用户").attr("v", 1);$("#td_" + objid).text("管理员").css("color", "red");
		}}
	else{alert("失败!"+data);}});
	return false;
}
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">用户账号列表</td>
		<td class="menuTool">
			<a class="add" href="addUser1.htm">添加</a>
			<a class="del" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getSysUser.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;姓名：<input id="name" name="name" value="${fn:escapeXml(param.name)}" style="width:75px;" />
			手机：<input id="mobile" name="mobile" value="${fn:escapeXml(param.mobile)}" style="width:75px;" />
			状态：<select id="status" name="status" style="width:50px;"><option value="">全部</option><option value="1"${1==param.status?' selected="selected"':''}>启用</option><option value=0${0==param.status?' selected="selected"':''}>禁用</option></select>
		</td>
		<td class="query"><input id="_querySubmit_" type="submit" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delUser.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%;"><input id="chkall" type="checkbox" /></td>
		<td style="width:28%;">姓名(帐号)</td>
		<td style="width:15%;">手机</td>
		<td style="width:25%;">邮箱</td>
		<td style="width:8%;">状态</td>
		<td style="width:22%;">操作</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d" varStatus="status">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" ${'admin'==d.account?'style="display:none;"':''}/></td>
		<td style="text-align:left;">&nbsp;${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.mobile)}</td>
		<td>${fn:escapeXml(d.email)}</td>
		<td id="td_a_status${status.index}" style="color:${1==d.status?"":"red"}">${1==d.status?"启用":"禁用"}</td>
		<td class="menuTool">
			<a ${'admin'==d.account?'style="display:none;"':''} id="a_status${status.index}" name="a_status" v="${d.status}" class="check" href="#" onclick="return updStatus('a_status${status.index}', '${d.id}');">${1==d.status?'禁用':'启用'}</a>
			<a ${'admin'==d.account?'style="display:none;"':''} class="upd" href="updUser1.htm?keyIndex=${d.id}">修改</a>
			<a class="get" href="getUserById.htm?keyIndex=${d.id}">明细</a>
		</td>
	</tr>
</c:forEach>
</table>
<input name="page" type="hidden" value="${pageModel.currentPage}" />
</form>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
