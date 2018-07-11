<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
$dswork.callback = null;
function refreshNode(re){
	if(re == null){$("#menu_select").click();return false;}$dswork.ztree.refreshNode(re);
}
$dswork.ztree.click = function(){
	treeNode = $dswork.ztree.getSelectedNode();
	if(treeNode.id == 0){
		attachUrl("getFlowCategory.htm?rootid=${po.id}&pid=" + treeNode.id);
	}
	else{
		attachUrl("getFlow.htm?categoryid=" + treeNode.id);
	}
	return false;
};
$dswork.ztree.showMenu = function(type, x, y){
	var _node = $dswork.ztree.getSelectedNode();
	$("#menu_del").hide();$("#menu_upd").hide();$("#menu_select").show();
	$("#menu_refresh").show();$("#menu_add").show();$("#menu_sort").show();
	if(0 < _node.id){$("#menu_del").show();$("#menu_upd").show();}
	$("#" + $dswork.ztree.menuName).menu('show', {left:x,top:y});
};
$dswork.ztree.root.name ="流程分类管理";
$dswork.ztree.root.id = ${po.id};
$dswork.ztree.url = function(treeNode){return "getFlowCategoryJson.htm?pid=" + treeNode.id;};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$("#menu_refresh").click(function(){$z.refreshNode();$z.hideMenu();});
	$("#menu_add").click(function(){
		attachUrl("addFlowCategory1.htm?pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_upd").click(function(){
		attachUrl("updFlowCategory1.htm?keyIndex=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_del").click(function(){
		if(confirm("以下情况删除失败：下级节点不为空\n是否删除？")){
			$dswork.showRequest();
			$.post("delFlowCategory.htm",{keyIndex:$z.getSelectedNode().id},function(data){
				$dswork.doAjaxShow(data, function(){
					if($dswork.result.type == 1){attachUrl("");$z.refreshNode(true);}
				});
			});
		}
		$z.hideMenu();
		return false;
	});
	$("#menu_sort").click(function(){
		attachUrl("updFlowCategorySeq1.htm?pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_select").click(function(){
		attachUrl("getFlowCategory.htm?rootid=${po.id}&pid=" + $z.getSelectedNode().id);
	});
	$z.expandRoot();
});
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="west" split="true" title="流程管理" style="width:150px;">
	<div class="treediv">
		<ul id="mytree" class="ztree tree" />
	</div>
	<div id="divMenu" class="easyui-menu easyuiMenu">
		<div id="menu_refresh" iconCls="menuTool-refresh">刷新</div>
		<div id="menu_add" iconCls="menuTool-insert">添加分类</div>
		<div id="menu_del" iconCls="menuTool-delete">删除分类</div>
		<div id="menu_upd" iconCls="menuTool-update">修改分类</div>
		<div id="menu_sort" iconCls="menuTool-sort">排序</div>
		<div id="menu_select" iconCls="menuTool-select">分类管理</div>
	</div>
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</html>
	