<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsFactory, dswork.web.MyRequest, dswork.common.model.*"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/upd.jsp"%>
<style type="text/css">
body {line-height:2em;}
</style>
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">流程测试</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="waiting.jsp">返回待办列表</a>
		</td>
	</tr>
</table>
<form id="dataForm" method="post" action="doAction.jsp">
<%
String msg = "";
MyRequest req = new MyRequest(request);
long wid = req.getLong("wid");
try
{
  if(wid > 0){
	IFlowWaiting po = DsFactory.getFlow().getWaiting(wid);
	request.setAttribute("po", po);
	java.util.Map<String, String> map = DsFactory.getFlow().getTaskList(po.getFlowid());
%>
	流程名称：${po.flowname}<br />
	当前任务：${po.talias}&nbsp;${po.tname}<br />
	下级任务：
	<%
	String[] arr = po.getTnext().split("\\|", -1);
	for(String s : arr)
	{
		%><select name="taskList"><%
		for(String m : s.split(",", -1)){%><option value="<%=m%>"><%=map.get(m)%></option><%}
		%></select>&nbsp;<%
	}
	%>
	<br />
	状态：<label><input type="radio"  name="resultType" value="1" checked="checked" />拟同意</label>
		&nbsp;<label><input type="radio"  name="resultType" value="0" />拟拒绝</label>
		&nbsp;<label><input type="radio"  name="resultType" value="-1" />拟作废</label>
	<br />
	意见：<textarea name="resultMsg" style="width:400px;">无</textarea><br />
<input type="hidden" name="wid" value="<%=wid%>" />
</form>
<%
  }else{msg = "处理失败";}
}catch(Exception ex){
	msg = "处理失败";
}%>
<%=msg%>
<br />
</body>
</html>