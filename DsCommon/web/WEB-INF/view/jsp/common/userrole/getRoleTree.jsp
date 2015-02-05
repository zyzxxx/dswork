<%@page language="java" pageEncoding="utf-8"%>
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
$dswork.ztree.click = function(){
	var treeNode = $dswork.ztree.getSelectedNode();
	attachUrl("getRole.htm?systemid=${systemid}&pid=" + treeNode.id);
	return false;
};
$dswork.ztree.root.name = "角色授权";
$dswork.ztree.root.status = 1;
$dswork.ztree.url = function(treeNode){return "../rolechoose/getRoleJson.htm?systemid=${systemid}&pid=" + treeNode.id;};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$z.expandRoot();
});
function updSetRole(id, title){
	$jskey.dialog.showDialog({url:"./updSetRole1.htm?systemid=${systemid}&id=" + id,title:"角色(" + title + ")",fit:true,draggable:false});
	return false;
}
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="north" style="width:30px;overflow:hidden;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">角色授权</td>
		<td class="menuTool">
			<a class="tool" href="getUser.htm?systemid=${systemid}">用户受权</a>
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" style="width:250px;">
	<div class="treediv">
		<ul id="mytree" class="ztree tree" />
	</div>
</div>
<div region="center">
	<iframe id="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</html>
	