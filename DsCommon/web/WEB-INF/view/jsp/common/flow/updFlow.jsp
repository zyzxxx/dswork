<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getFlow.htm";
}};
$dswork.deleteRow = function (obj){$(obj).parent().parent().remove();};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getFlow.htm?page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updFlow2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">名字</td>
		<td class="form_input"><input type="text" name="name" maxlength="300" dataType="Require" value="${fn:escapeXml(po.name)}" /></td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
<div class="line"></div>
<table id="contactTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:18%">标识</td>
		<td style="width:18%">名称</td>
		<td style="width:35%">任务</td>
		<td style="width:24%">用户</td>
		<td class="menuTool"><a class="add" onclick="$('#end').before($('#cloneTable>tbody>tr:eq(0)').clone());" href="#"></a></td>
	</tr>
	<c:forEach items="${po.taskList}" var="d">
	<tr id="${'end'==d.talias?'end':''}">
		<td><input type="text" name="talias" style="width:90%;" maxlength="300" ${('start'==d.talias or 'end'==d.talias)?' readonly="readonly"':''} dataType="Require" value="${fn:escapeXml(d.talias)}" /></td>
		<td><input type="text" name="tname" style="width:90%;" maxlength="300" dataType="Require" value="${fn:escapeXml(d.tname)}" /></td>
		<td>
			<div>${'start'==d.talias?'':'上级 '}<input type="${'start'==d.talias?'hidden':'text'}" name="tnodeprev" style="width:80%;" maxlength="4000" value="${fn:escapeXml(d.tnodeprev)}" /></div>
			<div>${'end'==d.talias?'':'下级 '}<input type="${'end'==d.talias?'hidden':'text'}" name="tnodenext" style="width:80%;" maxlength="4000" value="${fn:escapeXml(d.tnodenext)}" /></div>
		</td>
		<td><div>用户 <input type="text" name="tusers" style="width:70%;" maxlength="4000" value="${fn:escapeXml(d.tusers)}" /></div>
			<div>参数 <input type="text" name="tmemo" style="width:70%;" maxlength="4000" value="${fn:escapeXml(d.tmemo)}" /></div>
		</td>
		<td><input type="${('start'==d.talias or 'end'==d.talias)?'hidden':'button'}" class="delete" onclick="$dswork.deleteRow(this)" /></td>
	</tr>
</c:forEach>
</table>
</form>
<div style="display:none;">
<table id="cloneTable">
<tr class="list">
		<td><input type="text" name="talias" style="width:90%;" maxlength="300" dataType="Require" value="" /></td>
		<td><input type="text" name="tname" style="width:90%;" maxlength="300" dataType="Require" value="" /></td>
		<td><div>上级 <input type="text" name="tnodeprev" style="width:80%;" maxlength="4000" value="" /></div>
			<div>下级 <input type="text" name="tnodenext" style="width:80%;" maxlength="4000" value="" /></div>
		</td>
		<td><div>用户 <input type="text" name="tusers" style="width:70%;" maxlength="4000" value="" /></div>
			<div>参数 <input type="text" name="tmemo" style="width:70%;" maxlength="4000" value="" /></div>
		</td>
		<td><input type="button" class="delete" onclick="$dswork.deleteRow(this)" /></td>
	</tr>
</table>
</div>
</body>
</html>
