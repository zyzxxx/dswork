<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsFactory, dswork.web.MyRequest, dswork.common.model.*"%>
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
long wid = req.getLong("wid");
if(wid > 0)
{
	IFlowWaiting po = DsFactory.getFlow().getWaiting(wid);
	request.setAttribute("po", po);
	String[] arr = po.getTnext().split("\\|", -1);
%>
	${po.flowname}<br />
	当前任务：
	${po.talias}&nbsp;${po.tname}<br />
	下级任务：
	<%
	for(String s : arr)
	{
		%><select name="taskList"><%
		String[] _v = s.split(",", -1);
		for(String m : _v)
		{
		%><option value="<%=m%>"><%=m%></option><%
		}
		%></select>&nbsp;<%
	}
	%>
	<br />
<%
}
%>
<br />
</body>
</html>