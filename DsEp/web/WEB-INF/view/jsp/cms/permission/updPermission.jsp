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
$dswork.callback = function(){
	if($dswork.result.type == 1){
		location.reload();
	}else if($dswork.result.type == 2){
		var ids = $dswork.result.msg.split(",");
		for(var i = 0; i < ids.length - 1; i++){
			$('#mark_' + ids[i]).css({"background-color":"red"});
			$('#aud_' + ids[i]).prop('checked', true);
		}
	}
};
function checkSelf(n){
	n.prop("checked", true);
	var ss = n.attr("id").split("_");
	if(ss[0]=="all" || ss[0]=="own"){
		var _n = $("#" + (ss[0]=="all"?"own_":"all_") + ss[1]);
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
		var form = $('<form action="updPermission1.htm" method="post"></form>')
			.append('<input name="siteid" value="' + $("#site").val() + '">')
			.append('<input name="account" value="${fn:escapeXml(param.account)}">')
			.hide();
		$(document.body).append(form);
		form.submit().remove();
	});
	$("#dataTable input[type='checkbox']").each(function(){
		var self = $(this);
		var ss = self.attr("id").split("_");
		var power = "";
		switch(ss[0]){
		case "all": power = "${permission.editall}"; break;
		case "own": power = "${permission.editown}"; break;
		case "aud": power = "${permission.audit}"; break;
		case "pub": power = "${permission.publish}"; break;
		}
		if(power.indexOf("," + ss[1] + ",") != -1){
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
		editall += $(this).attr("id").split("_")[1] + ",";
	});
	$("input[name='editown']:checked").each(function(){
		editown += $(this).attr("id").split("_")[1] + ",";
	});
	$("input[name='audit']:checked").each(function(){
		audit += $(this).attr("id").split("_")[1] + ",";
	});
	$("input[name='publish']:checked").each(function(){
		publish += $(this).attr("id").split("_")[1] + ",";
	});
	var form = $('<form action="updPermission2.htm" method="post"></form>')
		.append('<input name="editall" value="' + editall + '">')
		.append('<input name="editown" value="' + editown + '">')
		.append('<input name="audit" value="' + audit + '">')
		.append('<input name="publish" value="' + publish + '">')
		.append('<input name="siteid" value="${siteid}">')
		.append('<input name="account" value="${fn:escapeXml(param.account)}">')
		.hide();
	$(document.body).append(form);
	form.ajaxSubmit($dswork.doAjaxOption).remove();
}
</script>
<style type="text/css">
label {padding:2px;height:22px;}
</style>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
			<label style="color:red">备注：采编权【个人】表示只能采编用户本人发布的信息</label>
		</td>
		<td class="menuTool">
			<a class="user" onclick="submit()" href="#">确定授权</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<div style="padding-left:20px;">
	<label style="color:#111"><input class="checkAll" v="editall" type="checkbox" />全选采编权</label>
	<label style="color:red"><input class="checkAll" v="editown" type="checkbox" />全选采编权【个人】</label>
	<label style="color:blue"><input class="checkAll" v="audit" type="checkbox" />全选审核权</label>
	<label style="color:green"><input class="checkAll" v="publish" type="checkbox" />全选发布权</label>
	<script type="text/javascript">
	$('.checkAll').change(function(){
		var o = $(this);
		var v = o.attr('v');
		var c = o.is(":checked");
		if(c){
			if(v == 'editall' || v == 'editown'){
				$('input[v="editall"]').prop("checked", false);
				$('input[v="editown"]').prop("checked", false);
				$('input[name="editall"]').prop("checked", false);
				$('input[name="editown"]').prop("checked", false);
			}
			o.prop("checked", true);
		}
		$('input[name="' + v + '"]').prop("checked", c);
	});
	</script>
</div>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:10%">栏目ID</td>
		<td>栏目名称</td>
		<td style="width:50%">权限</td>
	</tr>
<c:forEach items="${categoryList}" var="d">
	<tr>
		<td>${d.id}</td>
		<td style="text-align:left;">
			&nbsp;${d.label}${fn:escapeXml(d.name)}&nbsp;<a href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a>
		</td>
		<td style="text-align:left;">
			&nbsp;&nbsp;
			${d.label}<label><input id="all_${d.id}" pid="all_${d.pid}" name="editall" type="checkbox" />采编权</label>
			<label style="color:red"><input id="own_${d.id}" pid="own_${d.pid}" name="editown" type="checkbox" />采编权【个人】</label>
			&nbsp;-&nbsp;
			<label style="color:blue" id="mark_${d.id}"><input id="aud_${d.id}" pid="aud_${d.pid}" name="audit" type="checkbox" />审核权</label>
			&nbsp;-&nbsp;
			<label style="color:green"><input id="pub_${d.id}" pid="pub_${d.pid}" name="publish" type="checkbox" />发布权</label>
		</td>
	</tr>
</c:forEach>
</table>
</body>
</c:if>
</html>
