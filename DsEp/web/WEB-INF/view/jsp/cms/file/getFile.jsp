<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
/* function getfilepath(){
	var filePath=document.getElementById("filepath").value;
	location.href="uploadzip.htm";
} */
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">文件列表</td>
		<td class="menuTool">
			<a class="graph" href="uploadFile1.htm?path=${path}">附件上传</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:40%;">文件名称</td>
		<td>引用地址</td>
	</tr>
<c:forEach items="${list}" var="d">
<c:if test="${d.isParent == false}">
	<tr>
		<td>${fn:escapeXml(d.name)}</td>
		<td>${fn:escapeXml(d.useurl)}</td>
	</tr>
</c:if>
</c:forEach>
</table>
</body>
</html>
