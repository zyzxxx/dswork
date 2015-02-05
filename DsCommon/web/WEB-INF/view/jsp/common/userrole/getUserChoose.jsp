<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
var map = new $jskey.Map();
<c:forEach items="${pageModel.result}" var="d">
map.put("${d.id}", {"id":"${d.id}", "name":"${fn:escapeXml(d.name)}", "account":"${fn:escapeXml(d.account)}"});
</c:forEach>
function isSelected(){
	var arr = map.getValueArray();
	for(var i = 0; i < arr.length; i++){
		dosetting(arr[i].id, parent.refreshModel(arr[i]));
	}
}
function reselect(id, has){
	parent.setModel(map.get(id + ""), has);
	dosetting(id, has);
}
function dosetting(id, has){
	var s = "<input name=\"keyIndex\" type=\"checkbox\" value=\""+id+"\" onclick=\"reselect('"+id+"', ";
	if(has){s += "false);\" checked=\"checked\" />";}else{s += "true);\"/>";}
	document.getElementById("a" + id).innerHTML = s;
}
$(function(){
	try{$("#status").val("${fn:escapeXml(param.status)}");}catch(e){}
	$("#status").bind("change", function(){
		$("#queryForm").submit();
	});
	isSelected();
});
	</script>
</head> 
<body>
<form id="queryForm" method="post" action="getUserChoose.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;姓名：<input id="name" name="name" value="${fn:escapeXml(param.name)}" style="width:75px;" />
			&nbsp;手机：<input id="mobile" name="mobile" value="${fn:escapeXml(param.mobile)}" style="width:75px;" />
			&nbsp;状态：<select id="status" name="status" style="width:55px;"><option value="">全部</option><option value="1">启用</option><option value="0">禁用</option></select>
		</td>
		<td class="query"><input id="_querySubmit_" type="submit" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:8%">请勾选</td>
		<td style="width:20%;">姓名(帐号)</td>
		<td>单位</td>
		<td style="width:15%;">部门</td>
		<td style="width:7%;">状态</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td id="a${d.id}"><input name="keyIndex" type="checkbox" value="${d.id}" onclick="reselect('${d.id}');"/></td>
		<td style="text-align:left;">&nbsp;${fn:escapeXml(d.name)}(${fn:escapeXml(d.account)})</td>
		<td>${fn:escapeXml(d.orgpname)}</td>
		<td>${fn:escapeXml(d.orgname)}</td>
		<td style="color:${1==d.status?"":"red"}">${1==d.status?"启用":"禁用"}</td>
	</tr>
</c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
<br />
</body>
</html>
