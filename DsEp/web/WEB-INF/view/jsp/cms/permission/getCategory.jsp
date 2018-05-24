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
	<tr>
		<td class="title">栏目管理：没有可管理的站点</td>
	</tr>
</table>
</body>
</c:if>
<c:if test="${siteid>=0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$(function(){
	$("#site").bind("click", function(){
		if($(this).val()!="${siteid}"){
			location.href = "getCategory.htm?siteid="+$(this).val();
		}
	});
	$('.cut').each(function(){
		try{
			var e = $(this);
			var t = e.text();
			e.text(t.substring(0, t.length - 1));
		}catch(e){}
	});
});
</script>
<style type="text/css">
td.v{padding-left:3px;text-align:left;}
td.k{padding:0px;margin:0px;}
td.k input{border:none;background-color:transparent;text-align:center;}
</style>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
		</td>
	</tr>
</table>
<div class="line"></div>
<c:if test="${sitePermission==null}">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr><td class="form_input" style="color:red;text-align:center;">该站点没有被授权，默认所有栏目不用审核直接可以提交</td></tr>
</table>
</c:if>
<c:if test="${sitePermission!=null}">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:6%">栏目ID</td>
		<td>栏目名称</td>
		<td>采编人员账号</td>
		<td>审核人员账号</td>
		<td>发布人员账号</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="k"><input name="keyIndex" type="text" style="width:100%;" readonly="readonly" value="${d.id}" /></td>
		<td class="v">${d.label}${fn:escapeXml(d.name)}&nbsp;<a onclick="return false;" href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a></td>
		<td class="cut"><c:forEach items="${sitePermission[d.id].editall}" var="dd">${dd},</c:forEach><c:forEach items="${sitePermission[d.id].editown}" var="dd">${dd}[个人],</c:forEach></td>
		<td class="cut"><c:forEach items="${sitePermission[d.id].audit}" var="dd">${dd},</c:forEach></td>
		<td class="cut"><c:forEach items="${sitePermission[d.id].publish}" var="dd">${dd},</c:forEach></td>
	</tr>
</c:forEach>
</table>
</c:if>
</body>
</c:if>
</html>
