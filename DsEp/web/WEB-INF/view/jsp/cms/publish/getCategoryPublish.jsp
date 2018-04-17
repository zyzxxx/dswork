<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
.v{padding-left:3px;}
.v img{line-height:20px;vertical-align:middle;}
.k{padding:0px;margin:0px;}
.k input{border:none;background-color:transparent;text-align:center;}
</style>
</head>
<body>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:20%">栏目ID</td>
		<td style="width:50%">栏目名称</td>
		<td style="width:30%">待发布数量</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td class="k"><input name="keyIndex" type="text" style="width:100%;" readonly="readonly" value="${d.id}" /></td>
		<td class="v" style="text-align:left;">${d.label}${fn:escapeXml(d.name)}&nbsp;<a onclick="return false;" href="#" title="${fn:escapeXml(d.url)}">[${d.scope==0?'列表':d.scope==1?'单页':'外链'}]</a></td>
		<td>${fn:escapeXml(d.count)}</td>
	</tr>
</c:forEach>
</table>
</body>
</html>
