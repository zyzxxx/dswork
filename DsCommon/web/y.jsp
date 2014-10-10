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
		<td class="title">测试</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存2</a>
		</td>
	</tr>
</table>
<%--
<%=DsFactory.getFlow().start("tech_duty", "1000", "admin", "管理员", 0, true, "1", "2")%>
--%>

<%--
--%>

<%--
--%>

<%--
--%>
<%request.setAttribute("list", DsFactory.getFlow().queryWaiting("admin"));%>
<ul>
<c:forEach items="${list}" var="d" varStatus="status">
	<li>
		<td>${fn:escapeXml(d.alias)}</td>
		<td>${fn:escapeXml(d.name)}</td>
		&nbsp;<a class="update" href="updFlow1.htm?keyIndex=${d.id}">修改</a>
	</li>
</c:forEach>
</ul>
<br />
</body>
</html>