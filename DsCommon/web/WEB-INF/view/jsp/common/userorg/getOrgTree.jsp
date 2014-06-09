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
	var node = $dswork.ztree.getSelectedNode();
	if(node.status == 1){attachUrl("getOrg.htm?keyIndex=" + node.id);}
	else{attachUrl("about:blank");}
	return false;
};
$dswork.ztree.root.name = "${po.id > 0?fn:escapeXml(po.name):"组织机构"}";
$dswork.ztree.root.id = ${po.id};
$dswork.ztree.root.status = ${po.status};
$dswork.ztree.url = function(treeNode){return "${ctx}/common/share/getJsonOrg.htm?pid=" + treeNode.id;};
$dswork.ztree.dataFilter = function (treeId, parentNode, data){
	var d=[];for(var i =0; i < data.length; i++){
		if(data[i].status == 1){data[i].icon = "${ctx}/commons/img/group.png";}
		if(data[i].status > 0){d.push(data[i]);}
	}return d;
};
$(function(){
	var $z = $dswork.ztree;
	$z.load();
	$z.expandRoot();
});
function choose(data){
	$jskey.dialog.callback = function(){
		var result = $jskey.dialog.returnValue;
		if(result != null){
			var map = new $jskey.Map(), o;
			for(var i = 0; i < result.length; i++){o=result[i];map.put(o.id + "", o);}
			try{window.frames["mainFrame"].callback(map);}catch(e){alert(e.name + "\n" + e.message);}
		}
	};
	$jskey.dialog.showChoose({id:"chooseSystem", title:"选择角色", args:{url:"../rolechoose/getRoleChoose.htm", data:data}, width:"600", height:"450"});
	return false;
}
function showRole(id, name){
	$jskey.dialog.callback = function(){};
	$jskey.dialog.showDialog({id:"role", title:name, url:"../rolechoose/getRoleById.htm?roleid=" + id, args:{}, width:"350", height:"450"});
	return false;
}
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="west" split="true" title="用户授权管理（选择部门）" style="width:250px;">
	<div class="treediv">
		<ul id="mytree" class="ztree tree" />
	</div>
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" name="mainFrame" src="" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</html>
	