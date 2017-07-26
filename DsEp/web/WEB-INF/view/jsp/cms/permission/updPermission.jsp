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
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function checkSelf(n){
	n.prop("checked", true);
	var ss = n.attr("id").split("_");
	if(ss[1]=="all" || ss[1]=="own"){
		var _n = $("#" + ss[0] + "_" + (ss[1]=="all"?"own":"all"));
		if(_n.prop("checked")){
			uncheckSelf(_n);
			uncheckParent(_n);
			uncheckChild(_n);
		}
	}
}
function uncheckSelf(n){
	n.prop("checked", false);
}
function checkParent(self){
	var parent = $("#" + self.attr("pid"));
	if(parent[0]){
		parent.prop("checked", true);;
		checkParent(parent);
	}
}
function uncheckParent(self){
	var parent = $("#" + self.attr("pid"));
	if(parent[0] && parent.prop("checked")){
		var brother = $("input[pid='" + self.attr("pid") + "']");
		var flag = false;
		brother.each(function(){
			flag = flag || $(this).prop("checked");
		});
		if(!flag){
			uncheckSelf(parent);
			uncheckParent(parent);
		}
	}
}
function checkChild(self){
	var child = $("input[pid='" + self.attr("id") + "']")
	if(child[0]){
		child.each(function(){
			checkSelf($(this));
			checkChild($(this));
		});
	}
}
function uncheckChild(self){
	var child = $("input[pid='" + self.attr("id") + "']")
	if(child[0]){
		child.each(function(){
			uncheckSelf($(this));
			uncheckChild($(this));
		});
	}
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
			checkParent(self);
			checkChild(self);
		}else{
			uncheckSelf(self);
			uncheckParent(self);
			uncheckChild(self);
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
		</td>
		<td class="menuTool">
			<a class="user" href="javascript:void(0);" onclick="submit()">确定授权</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>栏目ID</td>
		<td>栏目名称</td>
		<td>采编权限</td>
		<td>审核权限</td>
		<td>发布权限</td>
	</tr>
<c:forEach items="${categoryList}" var="d">
	<tr>
		<td>${d.id}</td>
		<td style="text-align:left;">
			${d.label}${fn:escapeXml(d.name)}<c:if test="${d.status>0}">&nbsp;<a href="javascript:void(0);" title="${fn:escapeXml(d.url)}">[${d.status==1?"单页":"外链"}]</a></c:if>
		</td>
		<td style="text-align:left;">
			${d.label}<label><input class="empower" id="${d.id}_all" pid="${d.pid}_all" name="editall" type="checkbox" />所有采编权限</label>
			<label><input class="empower" id="${d.id}_own" pid="${d.pid}_own" name="editown" type="checkbox" />部分采编权限</label>
		</td>
		<td style="text-align:left;">
			${d.label}<label><input class="empower" id="${d.id}_aud" pid="${d.pid}_aud" name="audit" type="checkbox" />审核权限</label>
		</td>
		<td style="text-align:left;">
			${d.label}<label><input class="empower" id="${d.id}_pub" pid="${d.pid}_pub" name="publish" type="checkbox" />发布权限</label>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</c:if>
</html>
