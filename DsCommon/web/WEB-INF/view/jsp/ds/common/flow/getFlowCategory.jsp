<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<style type="text/css">.menuTool-save {background-position:center left;}</style>
<script type="text/javascript">
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){parent.refreshNode(true);}};
$(function(){
$dswork.page.menu("", "updFlowCategory1.htm", "getFlowCategoryById.htm", "");
$("#listFormMoveAll").click(function(){
	var _c = 0;var v = "0";
	$("input[name='keyIndex']:checked").each(function(){_c++;v=v+","+$(this).val();});
	if(_c > 0){
		v = v.substring(2);
		$("#moveids").val(v);
		var obj = {"title":"移动到选中节点","args":{"data":v}, "url":"updFlowCategoryMove1.htm?rootid=${rootid}"};
		obj.buttons = [{text:"移动",iconCls:"menuTool-save",handler:function(){
			var re = $jskey.dialog.returnValue;
			if(re != null){
				if(re.id == "${pid}"){alert("目标节点与当前节点相同");return false;}
				$("#movepid").val(re.id);
				$("#moveForm").ajaxSubmit({
					beforeSubmit:$dswork.showRequest,
					success:function(data){
						$dswork.doAjaxShow(data, function(){if($dswork.result.type == 1){
							parent.$dswork.ztree.expandAll(false);//收缩掉其余的
							parent.$dswork.ztree.moveUpdate("${pid}", $("#movepid").val());
						}});
					}
				});
				$jskey.dialog.close();
			}
			else{alert("请选择需要移动的目标节点位置");}
		}}];	
		$jskey.dialog.showDialog(obj);
		return true;
	}else{alert("请选择记录！");}
	return false;
});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">流程分类列表</td>
		<td class="menuTool">
			<a class="insert" href="addFlowCategory1.htm?pid=${pid}">添加</a>
			<a class="sort" href="updFlowCategorySeq1.htm?pid=${pid}">排序</a>
			<a class="move" id="listFormMoveAll" href="#">移动所选</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getFlowCategory.htm?rootid=${rootid}&pid=${pid}">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;名称：<input type="text" name="name" value="${fn:escapeXml(param.name)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delFlowCategory.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td>名称</td>
		<td style="width:10%">操作</td>
	</tr>
<c:forEach items="${list}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}"/></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td>${fn:escapeXml(d.name)}</td>
		<td class="menuTool">
			<a class="update" href="updFlowCategory1.htm?keyIndex=${d.id}">修改</a>
		</td>
	</tr>
</c:forEach>
</table>
</form>
<div style="display:none;">
<form id="moveForm" method="post" action="updFlowCategoryMove2.htm">
	<input id="moveids" name="ids" type="hidden" value="" />
	<input id="movepid" name="pid" type="hidden" value="" />
</form>
</div>
<br />
</body>
</html>
