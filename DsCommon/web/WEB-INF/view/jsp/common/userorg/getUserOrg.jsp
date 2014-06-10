<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp" %>
<script type="text/javascript">
var uList = [], oList = [];
<c:forEach items="${userList}" var="d">
uList.push({id:"${d.id}",name:"${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})"});
</c:forEach>
<c:forEach items="${orgList}" var="d">
oList.push({id:"${d.id}",name:"${fn:escapeXml(d.name)}"});
</c:forEach>
$(function(){
	var t = $("#userTable"),m;
	for(var i = 0; i < uList.length; i++){m=uList[i];
		t.append('<tr><td>' + m.name + '</td><td class="menuTool"><a class="key" keyIndex="' + m.id + '">受权</a></td></tr>');
	}
	t = $("#orgTable");
	for(var i = 0; i < oList.length; i++){m=oList[i];
		t.append('<tr><td>' + m.name + '</td><td class="menuTool"><a class="key" keyIndex="' + m.id + '">授权</a></td></tr>');
	}
});
</script>
</head>
<body class="easyui-tabs" fit="true">
<div title="用户授权" style="padding:5px;overflow:hidden;">
	<table id="userTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>姓名(帐号)</td>
		<td style="width:15%">操作</td>
	</tr>
</table>
</div>
<div title="岗位授权" style="padding:5px;overflow:hidden;">
	<table id="orgTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>岗位名称</td>
		<td style="width:15%">操作</td>
	</tr>
</table>
</div>
</body>
</html>
