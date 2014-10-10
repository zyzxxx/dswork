<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsFactory"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp"%>
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">流程测试</td>
	</tr>
</table>
<%request.setAttribute("list", DsFactory.getFlow().queryWaiting("admin"));%>
<ul>
<c:forEach items="${list}" var="d" varStatus="status">
	<li>
		${fn:escapeXml(d.flowname)}[${fn:escapeXml(d.talias)}]
		<c:if test="${d.tuser!=''}">
		&nbsp;<a class="update" href="do.jsp?wid=${d.id}">办理</a>
		</c:if>
		<c:if test="${d.tusers!=''}">
		&nbsp;<a class="update" href="take.jsp?wid=${d.id}">取得任务</a>
		</c:if>
	</li>
</c:forEach>
</ul>
<br />
</body>
</html>