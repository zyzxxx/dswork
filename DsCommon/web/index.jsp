<%@page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>功能模块化系统，仅使用临时菜单，详细框架页面不在此包含</title>
<style type="text/css">html, body {height:100%;width:100%;overflow:hidden;padding:0;margin:0;font-size:16px;}a{color:#000000;line-height:25px;font-size:12px;}</style>
<script type="text/javascript">
var treedata = [
{id:100, name:'扩展功能', img:"", imgOpen:"", url:"", items:[
	 {id:901,name:'组织管理指定', img:"", imgOpen:"", url:'/common/org/getOrgTree.htm?rootid=1', items:[]}
	,{id:902,name:'用户授权管理', img:"", imgOpen:"", url:'/common/userorg/getOrgTree.htm?rootid=1', items:[]}
	,{id:201,name:'用户管理', img:"", imgOpen:"", url:'/common/user/getUser.htm', items:[]}
]}
,{id:100000, name:'门户管理', img:"", imgOpen:"", url:"", items:[
	 {id:100002,name:'系统管理', img:"", imgOpen:"", url:'/common/system/getSystem.htm', items:[]}
	,{id:100003,name:'组织管理', img:"", imgOpen:"", url:'/common/org/getOrgTree.htm', items:[]}
	,{id:100004,name:'岗位授权管理', img:"", imgOpen:"", url:'/common/orgrole/getOrgTree.htm', items:[]}
]}
,{id:200000, name:'业务管理', img:"", imgOpen:"", url:"", items:[
	 {id:200001,name:'字典管理', img:"", imgOpen:"", url:'/common/dict/getDict.htm', items:[]}
	,{id:200005,name:'流程管理', img:"", imgOpen:"", url:'/common/flow/getFlowCategoryTree.htm', items:[]}
]}
];
</script>
</head>
<body>
<div style="height:100%;width:100%;">
<div style="float:left;height:100%;padding-left:3px;">
<script type="text/javascript">
for(var i = 0; i < treedata.length; i++){
	var item = treedata[i];
	document.write("<br />" + item.name);
	for(var j = 0; j < item.items.length; j++){
		var m = item.items[j];
		document.write("<br />&nbsp;&nbsp;&nbsp;&nbsp;<a target='main' href='<%=path%>" + m.url + "'>" + m.name + "</a>");
	}
}
</script>

<br />
<br />
<br />
<br />测试菜单
<br />&nbsp;&nbsp;&nbsp;&nbsp;<a target='_blank' href='<%=path%>/flow/waiting.jsp'>流程测试</a>
<br />&nbsp;&nbsp;&nbsp;&nbsp;<a target='main' href='<%=path%>/x.jsp'>字典样例</a>
</div>
<div style="float:right;height:100%;width:80%;min-width:800px;">
	<iframe id="main" name="main" style="height:100%;width:100%;" scrolling="auto" frameborder="0" src="about:blank"></iframe>
</div>
</div>
</body>
</html>