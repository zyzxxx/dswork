<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<c:if test="${siteid<0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr><td class="title">栏目管理：没有可管理的站点</td></tr>
</table>
</body>
</c:if>
<c:if test="${siteid>=0}">
<head>
<title></title>
<style type="text/css">

</style>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function checkSelf(n){
	n.prop("checked", true);
	var ss = n.attr("id").split("_");
	if(ss[1]=="all" || ss[1]=="own"){
		var _n = $("#" + ss[0] + "_" + (ss[1]=="all"?"own":"all"));
		if(_n.prop("checked")){
			uncheckSelf(_n);
		}
	}
}
function uncheckSelf(n){
	n.prop("checked", false);
}
$(function(){
	$("#site").change(function(){
		$('<form action="updPermission1.htm" method="post"></form>')
		.append('<input name="siteid" value="' + $("#site").val() + '">')
		.append('<input name="account" value="${fn:escapeXml(param.account)}">')
		.submit();
	});
	$(".empower").each(function(){
		var self = $(this);
		var ss = self.attr("id").split("_");
		var power = "";
		switch(ss[1]){
		case "all": power = "${permission.editall}"; break;
		case "own": power = "${permission.editown}"; break;
		case "aud": power = "${permission.audit}"; break;
		case "pub": power = "${permission.publish}"; break;
		}
		if(power.indexOf("," + ss[0] + ",") != -1){
			checkSelf(self);
		}
	}).click(function(){
		var self = $(this);
		if(self.prop("checked")){
			checkSelf(self);
		}else{
			uncheckSelf(self);
		}
	});
});
function submit(){
	var editall = ",", editown = ",", audit=",", publish = ",";
	$("input[name='editall']:checked").each(function(){
		editall += $(this).attr("id").split("_")[0] + ",";
	});
	$("input[name='editown']:checked").each(function(){
		editown += $(this).attr("id").split("_")[0] + ",";
	});
	$("input[name='audit']:checked").each(function(){
		audit += $(this).attr("id").split("_")[0] + ",";
	});
	$("input[name='publish']:checked").each(function(){
		publish += $(this).attr("id").split("_")[0] + ",";
	});
	$('<form action="updPermission2.htm" method="post"></form>')
	.append('<input name="editall" value="' + editall + '">')
	.append('<input name="editown" value="' + editown + '">')
	.append('<input name="audit" value="' + audit + '">')
	.append('<input name="publish" value="' + publish + '">')
	.append('<input name="siteid" value="${siteid}">')
	.append('<input name="account" value="${fn:escapeXml(param.account)}">')
	.ajaxSubmit($dswork.doAjaxOption);
}
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
			<label style="color:red">备注：采编权【个人】表示只能采编用户本人发布的信息</label>
		</td>
		<td class="menuTool">
			<a class="user" href="javascript:void(0);" onclick="submit()">确定授权</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:8%">栏目ID</td>
		<td>栏目名称</td>
		<td style="width:50%">权限</td>
		<td style="width:5%">备注</td>
	</tr>
<c:forEach items="${categoryList}" var="d">
	<tr>
		<td>${d.id}</td>
		<td style="text-align:left;">
			&nbsp;${d.label}${fn:escapeXml(d.name)}<c:if test="${d.scope>0}">&nbsp;<a href="javascript:void(0);" title="${fn:escapeXml(d.url)}">[${d.scope==1?"单页":"外链"}]</a></c:if>
		</td>
	<c:if test="${fn:length(d.list)==0}">
		<td style="text-align:left;">
			&nbsp;<label style="color:blue"><input id="${d.id}_aud" pid="${d.pid}_aud" name="audit" type="checkbox" />审核权</label>
			&nbsp;-&nbsp;
			<label style="color:green"><input id="${d.id}_pub" pid="${d.pid}_pub" name="publish" type="checkbox" />发布权</label>
			&nbsp;-&nbsp;
			<label><input id="${d.id}_all" pid="${d.pid}_all" name="editall" type="checkbox" />采编权</label>
			<label style="color:red"><input id="${d.id}_own" pid="${d.pid}_own" name="editown" type="checkbox" />采编权【个人】</label>
		</td>
		<td>
			${d.scope<=0?"列表":(d.scope==1?"单页":"外链")}
		</td>
	</c:if>
	<c:if test="${fn:length(d.list)>0}">
		<td></td>
		<td>分类</td>
	</c:if>
	</tr>
</c:forEach>
</table>
</body>
</c:if>
</html>
