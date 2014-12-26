<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getUser.htm?page=${pageModel.currentPage}&qybm=${qybm}";
}};
$dswork.page.join = function(td, menu, id){
	var usertype  = $(td).attr("usertype");
	if(usertype == null || typeof(usertype)=="undefined"){return true;}
	if(usertype != "1"){
		$(menu).append($('<div iconCls="menuTool-user">删除</div>').bind("click", function(event){
			if(confirm("确认删除吗？")){$dswork.page.del(event, "delUser.htm", id, "${pageModel.currentPage}", td);}
		}));
		$(menu).append($('<div iconCls="menuTool-user">修改密码</div>').bind("click", function(event){
			location.href = "updUserPassword1.htm?keyIndex=" + id;
		}));
	}
};
$(function(){
	$dswork.page.menu("", "updUser1.htm", "getUserById.htm", "${pageModel.currentPage}");
});
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">${ssdw}用户列表</td>
		<td class="menuTool">
			<a class="insert" href="addUser1.htm?page=${pageModel.currentPage}">添加</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getUser.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;关键字查询：<input type="text" name="keyvalue" style="width:200px;" title="企业编码、手机或电话" value="${fn:escapeXml(param.keyvalue)}" />
			&nbsp;账号：<input type="text" name="account" style="width:100px;" value="${fn:escapeXml(param.account)}" />
			&nbsp;姓名：<input type="text" name="name" style="width:100px;" value="${fn:escapeXml(param.name)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delUser.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td>姓名(帐号)</td>
		<td style="width:15%">手机</td>
		<td style="width:20%">Email</td>
		<td style="width:10%">用户类型</td>
		<td style="width:20%">创建时间</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><c:if test="${d.usertype != 1}"><input name="keyIndex" type="checkbox" value="${d.id}" /></c:if></td>
		<td class="menuTool" usertype="${d.usertype}" keyIndex="${d.id}">&nbsp;</td>
		<td>${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.mobile)}</td>
		<td>${fn:escapeXml(d.email)}</td>
		<td class="usertype" style="${d.usertype == 1?'color:red;':''}">${d.usertype==1?"管理员":"用户"}</td>
		<td>${fn:escapeXml(d.createtime)}</td>
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
