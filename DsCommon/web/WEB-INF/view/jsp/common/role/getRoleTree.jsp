<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/page.jsp"%>
	<%@include file="/commons/include/getEasyui.jsp" %>
	<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
$dswork.callback = null;
function refreshNode(re){$dswork.ztree.refreshNode(re);}
$dswork.ztree.click = function(){
	treeNode = $dswork.ztree.getSelectedNode();
	attachUrl("getRole.htm?systemid=${po.id}&pid=" + treeNode.id);
	return false;
};
$dswork.ztree.beforeDblClick = function(treeId, treeNode){
	if(treeNode.id > 0){attachUrl("getRoleById.htm?keyIndex=" + treeNode.id);}
	else{attachUrl("getRole.htm?systemid=${po.id}&pid=" + treeNode.id);}
	return true;
};
$dswork.ztree.showMenu = function(type, x, y){
	var _node = $dswork.ztree.getSelectedNode();
	$("#menu_refresh").show();$("#menu_add").show();$("#menu_del").show();$("#menu_upd").show();$("#menu_sort").show();
	if(0 == _node.id){$("#menu_del").hide();$("#menu_upd").hide();}
	$("#" + $dswork.ztree.menuName).menu('show', {left:x,top:y});
};
$dswork.ztree.root.name = "角色管理";
$dswork.ztree.root.status = 1;
$dswork.ztree.url = function(treeNode){return "getRoleJson.htm?systemid=${po.id}&pid=" + treeNode.id;};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$("#menu_refresh").click(function(){$z.refreshNode();$z.hideMenu();});
	$("#menu_add").click(function(){
		attachUrl("addRole1.htm?systemid=${po.id}&pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_upd").click(function(){
		attachUrl("updRole1.htm?keyIndex=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_del").click(function(){
		var _node = $z.getSelectedNode();
		if(confirm("以下情况删除失败：下级节点不为空\n是否删除？")){
			$dswork.showRequest();
			$.post("delRole.htm",{keyIndex:_node.id},function(data){
				$dswork.doAjaxShow(data, function(){
					if($dswork.result.type == 1){attachUrl("");$z.refreshNode(true);}
				});
			});
		}
		$z.hideMenu();
		return false;
	});
	$("#menu_sort").click(function(){
		attachUrl("updRoleSeq1.htm?systemid=${po.id}&pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$z.expandRoot();
});
</script>
</head>
<body class="easyui-layout treebody">
<div region="north" style="overflow:hidden;border:0px;height:25px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">${fn:escapeXml(po.name)}</td>
		<td class="menuTool">
			<a class="back" href="../system/getSystem.htm?page=${param.page}">返回</a>
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" title="角色管理" style="overflow:hidden;width:250px;">
	<div class="treediv">
		<ul id="mytree" class="ztree tree" />
	</div>
	<div id="divMenu" class="easyui-menu easyuiMenu">
		<div id="menu_refresh" iconCls="menuTool-refresh">刷新</div>
		<div id="menu_add" iconCls="menuTool-insert">添加</div>
		<div id="menu_del" iconCls="menuTool-delete">删除</div>
		<div id="menu_upd" iconCls="menuTool-update">修改</div>
		<div id="menu_sort" iconCls="menuTool-tool">排序</div>
	</div>
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;" />
</div>
</body>
</html>
	