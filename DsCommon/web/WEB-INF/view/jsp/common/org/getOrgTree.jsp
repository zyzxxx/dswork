<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@include file="/commons/include/page.jsp"%>
	<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
$dswork.callback = null;
function refreshNode(re){$dswork.ztree.refreshNode(re);}
$dswork.ztree.click = function(){
	treeNode = $dswork.ztree.getSelectedNode();
	if(treeNode.status > 0 || treeNode.id == 0){attachUrl("getOrg.htm?rootid=${po.id}&pid=" + treeNode.id);}
	else{attachUrl("getOrgById.htm?keyIndex=" + treeNode.id);}
	return false;
};
$dswork.ztree.beforeDblClick = function(treeId, treeNode){
	if(treeNode.id != ${po.id} || treeNode.status == 0){attachUrl("getOrgById.htm?keyIndex=" + treeNode.id);}
	else{attachUrl("getOrg.htm?rootid=${po.id}&pid=" + treeNode.id);}
	return true;
};
$dswork.ztree.showMenu = function(type, x, y){
	var _node = $dswork.ztree.getSelectedNode();
	$("#menu_refresh").show();$("#menu_add").show();$("#menu_del").show();$("#menu_upd").show();$("#menu_sort").show();
	if(${po.id} == _node.id){$("#menu_del").hide();$("#menu_upd").hide();}
	if(_node.status == 0){$("#menu_refresh").hide();$("#menu_add").hide();$("#menu_sort").hide();}
	$("#" + $dswork.ztree.menuName).menu('show', {left:x,top:y});
};
$dswork.ztree.root.name = "组织机构管理";
$dswork.ztree.root.id = ${po.id};
$dswork.ztree.root.status = ${po.status};
$dswork.ztree.url = function(treeNode){return "getOrgJson.htm?pid=" + treeNode.id;};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$("#menu_refresh").click(function(){$z.refreshNode();$z.hideMenu();});
	$("#menu_add").click(function(){
		attachUrl("addOrg1.htm?pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_upd").click(function(){
		attachUrl("updOrg1.htm?keyIndex=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$("#menu_del").click(function(){
		var _node = $z.getSelectedNode();
		if(confirm("以下情况删除失败：下级节点不为空\n是否删除？")){
			$dswork.showRequest();
			$.post("delOrg.htm",{keyIndex:_node.id},function(data){
				$dswork.doAjaxShow(data, function(){
					if($dswork.result.type == 1){attachUrl("");$z.refreshNode(true);}
				});
			});
		}
		$z.hideMenu();
		return false;
	});
	$("#menu_sort").click(function(){
		attachUrl("updOrgSeq1.htm?pid=" + $z.getSelectedNode().id);$z.hideMenu();
	});
	$z.expandRoot();
});

$dswork.ztree.root.iconOpen = "${ctx}/commons/img/orgOpen.png";
$dswork.ztree.root.iconClose = "${ctx}/commons/img/org.png";
$dswork.ztree.dataFilter = function (treeId, parentNode, data){
	if(data){for(var i = 0;i < data.length;i++){
		if(data[i].status == 2){
			data[i].iconOpen = "${ctx}/commons/img/orgOpen.png";
			data[i].iconClose = "${ctx}/commons/img/org.png";
		}
		else if(data[i].status == 1){
			data[i].iconOpen = "${ctx}/commons/img/groupOpen.png";
			data[i].iconClose = "${ctx}/commons/img/group.png";
		}
		else{
			data[i].icon = "${ctx}/commons/img/user.png";
		}
	}}
	return data;
};
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="north" style="overflow:hidden;border:0px;height:25px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">${fn:escapeXml(po.name)}</td>
		<td class="menuTool">
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" title="组织机构管理" style="width:250px;">
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
	