<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>功能模块化系统，仅使用临时菜单，详细框架页面不在此包含</title>
<style type="text/css">html, body {height:100%;width:100%;padding:0;margin:0;}a{color:#000000;line-height:25px;}</style>
<script type="text/javascript">
var treedata = [
{id:100, name:'扩展功能', img:"", imgOpen:"", url:"", items:[
	 {id:901,name:'组织管理指定', img:"", imgOpen:"", url:'/common/org/getOrgTree.htm?rootid=1', items:[]}
	,{id:902,name:'用户授权管理', img:"", imgOpen:"", url:'/common/userorg/getOrgTree.htm?rootid=1', items:[]}
	,{id:201,name:'用户管理', img:"", imgOpen:"", url:'/common/user/getUser.htm', items:[]}
	,{id:105,name:'样例', img:"", imgOpen:"", url:'/x.jsp', items:[]}
]}
,{id:100000, name:'基础数据', img:"", imgOpen:"", url:"", items:[
	 {id:100001,name:'字典管理', img:"", imgOpen:"", url:'/common/dict/getDict.htm', items:[]}
	,{id:100002,name:'系统管理', img:"", imgOpen:"", url:'/common/system/getSystem.htm', items:[]}
	,{id:100003,name:'组织管理', img:"", imgOpen:"", url:'/common/org/getOrgTree.htm', items:[]}
	,{id:100004,name:'岗位授权管理', img:"", imgOpen:"", url:'/common/orgrole/getOrgTree.htm', items:[]}
]}
];
</script>
</head>
<body>
<div style="height:100%;width:100%;">
<div style="float:left;height:100%;">
<script type="text/javascript">
for(var i = 0; i < treedata.length; i++){
	var item = treedata[i];
	document.write("<br />" + item.name);
	for(var j = 0; j < item.items.length; j++){
		var m = item.items[j];
		document.write("<br />&nbsp;&nbsp;&nbsp;&nbsp;<a target='main' href='<%=request.getContextPath()%>" + m.url + "'>" + m.name + "</a>");
	}
}
</script>
</div>
<div style="float:right;width:800px;height:100%;">
	<iframe id="main" name="main" style="height:100%;width:100%;" scrolling="no" frameborder="0" src="#"></iframe>
</div>
</div>
</body>
</html>