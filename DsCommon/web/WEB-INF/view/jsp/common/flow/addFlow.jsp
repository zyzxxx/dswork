<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getFlow.htm?categoryid=${param.categoryid}";
}};
$dswork.deleteRow = function (obj){$(obj).parent().parent().remove();};
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getFlow.htm?page=${param.page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addFlow2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<input type="hidden" name="categoryid" value="${param.categoryid}" />
	<input type="hidden" name="status" value="0" />
	<input type="hidden" name="deployid" value="" />
	<tr>
		<td class="form_title">流程标识</td>
		<td class="form_input"><input type="text" name="alias" maxlength="300" dataType="Require" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">名字</td>
		<td class="form_input"><input type="text" name="name" maxlength="300" dataType="Require" value="" /></td>
	</tr>
</table>
<div class="line"></div>
<table id="contactTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:18%">标识</td>
		<td style="width:18%">名称</td>
		<td style="width:35%">任务</td>
		<td style="width:24%">用户</td>
		<td class="menuTool"><a class="add" onclick="$('#end').before($('#cloneTable>tbody>tr:eq(0)').clone());" href="#">添加项</a></td>
	</tr>
	<tr class="list" id="start">
		<td><input type="hidden" style="width:90%;" name="talias" value="start" />start</td>
		<td><input type="text" style="width:90%;" name="tname" maxlength="300" dataType="Require" value="" /></td>
		<td>
			<input type="hidden" style="width:80%;" maxlength="4000" name="tnodeprev" value="" />
			<div>下级 <input type="text" style="width:80%;" maxlength="4000" dataType="Require" name="tnodenext" value="" /></div>
		</td>
		<td>
			<div>用户 <input type="text" style="width:70%;" name="tusers" maxlength="4000" dataType="Require" value="" /></div>
			<div>参数 <input type="text" style="width:70%;" name="tmemo" maxlength="4000" dataType="Require" value="" /></div>
		</td>
		<td></td>
	</tr>
	<tr class="list" id="end">
		<td><input type="hidden" style="width:90%;" name="talias" value="end" />end</td>
		<td><input type="text" style="width:90%;" name="tname" maxlength="300" dataType="Require" value="" /></td>
		<td>
			<div>上级 <input type="text" style="width:80%;" maxlength="4000" dataType="Require" name="tnodeprev" value="" /></div>
			<input type="hidden" style="width:80%;" maxlength="4000" name="tnodenext" value="" />
		</td>
		<td>		
			<div>用户 <input type="text" style="width:70%;" name="tusers" maxlength="4000" dataType="Require" value="" /></div>
			<div>参数 <input type="text" style="width:70%;" name="tmemo" maxlength="4000" dataType="Require" value="" /></div>
		</td>
		<td></td>
	</tr>
</table>
</form>
<div style="display:none;">
<table id="cloneTable">
	<tr class="list">
		<td><input type="text" style="width:90%;" name="talias" maxlength="300" dataType="Require" value="" /></td>
		<td><input type="text" style="width:90%;" name="tname" maxlength="300" dataType="Require" value="" /></td>
		<td>
			<div>上级 <input type="text" style="width:80%;" maxlength="4000" dataType="Require" name="tnodeprev" value="" /></div>
			<div>下级 <input type="text" style="width:80%;" maxlength="4000" dataType="Require" name="tnodenext" value="" /></div>
		</td>
		<td>			
			<div>用户 <input type="text" style="width:70%;" name="tusers" maxlength="4000" dataType="Require" value="" /></div>
			<div>参数 <input type="text" style="width:70%;" name="tmemo" maxlength="4000" dataType="Require" value="" /></div>
		</td>
		<td><input type="button" class="delete" onclick="$dswork.deleteRow(this)" /></td>
	</tr>
</table>
</div>
</body>
</html>
