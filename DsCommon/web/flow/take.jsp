<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsFactory, dswork.web.MyRequest"%>
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
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存2</a>
		</td>
	</tr>
</table>
<%
MyRequest req = new MyRequest(request);
long waitid = req.getLong("wid");
if(waitid > 0)
{
	DsFactory.getFlow().takeWaiting(waitid, "admin");
	response.sendRedirect("wait.jsp");
}
%>
<br />
</body>
</html>