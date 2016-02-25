<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	//attachUrl("tree.htm?pid=" + treeNode.id+"&path="+treeNode.path);
	fileDeal(treeNode);
	return false;
};
$dswork.ztree.root.name = "文件";
//$dswork.ztree.root.id = ${po.id};
$dswork.ztree.root.path = "${po.path}";
$dswork.ztree.url = function(treeNode){return "tree.htm?pid=" + treeNode.id+"&path="+treeNode.path};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$z.expandRoot();
	$("#site").bind("click", function(){
		if($(this).val()!="${siteid}"){
			location.href = "getFileTree.htm?siteid="+$(this).val();
		}
	});
});
$dswork.ztree.dataFilter = function (treeId, parentNode, data){
// 	if(data){for(var i = 0;i < data.length;i++){
// 		data[i].iconSkin = "groups";
// 	}}
	return data;
};
function fileDeal(data){
	if(data.isParent == true){
		attachUrl("getFile.htm?path="+data.path);
	}
}
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="north" style="overflow:hidden;border:0px;height:30px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">
			切换站点：
			<select id="site">
				<c:forEach items="${siteList}" var="d">
					<option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" title="资源管理器" style="width:250px;">
 	<div class="treediv">
		<ul id="mytree" class="ztree tree" />
	</div>
<!--	<div id="divMenu" class="easyui-menu easyuiMenu">
		<div id="menu_refresh" iconCls="menuTool-refresh">刷新</div>
		<div id="menu_add" iconCls="menuTool-insert">添加</div>
		<div id="menu_del" iconCls="menuTool-delete">删除</div>
		<div id="menu_upd" iconCls="menuTool-update">修改</div>
		<div id="menu_sort" iconCls="menuTool-sort">排序</div>
		<div id="menu_select" iconCls="menuTool-select">明细</div>
	</div> -->
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</html>
	