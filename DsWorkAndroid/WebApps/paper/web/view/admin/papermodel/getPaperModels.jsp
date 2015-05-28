<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style type="text/css">
.listTable {border:none;background:gray}/*数据表格*/
.listTable tr {padding:0px 2px 0px 2px;}
.listTable td {background-color:#ffffff;color:#2b3e44;word-wrap:break-word;word-break:break-all;}
</style>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/js/gwen/form.js"></script>
<script type="text/javascript">
$gwen.form.callback = function(){
	if($gwen.result.type == 1){
		location.href = "getPaperModels.action?pid=${pid}";
	}
};
$(function(){
	$("#ckb_all").click(function(){
		$("input[name='keyIndex']").prop("checked",$(this).prop("checked"));
	});
	$("a[name='a_del']").click(function(){
		$("#form_del").submit();
	});
});
</script>
</head>
<body>
<a name="a_add" href="addPaperModel1.action?pid=${pid}">新增</a>
<a name="a_del" href="javascript:void(0);">删除</a>
<a name="a_back" href="../papercategory/getPaperCategorys.action">返回</a>

<form id="form_del" action="delPaperModel.action" method="post">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="ckb_all" type="checkbox"/></td>
		<td>主键</td>
		<td>外键</td>
		<td>模型名</td>
		<td>排序号</td>
		<td>操作</td>
	</tr>	
	<s:iterator var="po" value="resultList">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${po.id}"/></td>
		<td>${po.id}</td>
		<td>${po.pid}</td>
		<td>${po.name}</td>
		<td>${po.sort}</td>
		<td>
			<a name="a_upd" href="updPaperModel1.action?id=${po.id}">修改</a>
			<a name="a_getById" href="getPaperModelById.action?id=${po.id}">明细</a>
			<a name="a_getPaperImagesByPid" href="../paperimage/getPaperImages.action?pid=${po.id}&ppid=${po.pid}">图片管理</a>
		</td>
	</tr>
	</s:iterator>
</table>
<input name="page" type="hidden" value="${page.curPage}" />
<input name="pid" type="hidden" value="${pid}" />
</form>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.pageHtml}</td></tr>
</table>
</body>
</html>
