<%@page language="java" pageEncoding="UTF-8" import="common.auth.*"%>
<%
String path = request.getContextPath();
Auth model = AuthLogin.getLoginUser(request, response);
if(model == null)
{
	out.println("<script>top.location.href='" + path + "/login.html';</script>");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>功能模块化系统，仅使用临时菜单，详细框架页面不在此包含</title>
<style type="text/css">html, body {height:100%;width:100%;overflow:hidden;padding:0;margin:0;font-size:16px;}a{color:#000000;line-height:25px;font-size:12px;}</style>
<script type="text/javascript">
<%if(model.isEnterprise()){%>
var treedata = [
	{id:100, name:'企业管理', img:"", imgOpen:"", url:"", items:[
		 {id:202,name:'企业用户管理', img:"", imgOpen:"", url:'/ep/user/getUser.htm', items:[]}
	]}
];
<%}%>
<%if(model.isAdmin()){%>
var treedata = [
	{id:200, name:'企业数据', img:"", imgOpen:"", url:"", items:[
		 {id:201,name:'企业管理', img:"", imgOpen:"", url:'/ep/enterprise/getEnterprise.htm', items:[]}
	]}
	,{id:300, name:'个人数据', img:"", imgOpen:"", url:"", items:[
		{id:301,name:'个人用户管理', img:"", imgOpen:"", url:'/person/user/getUser.htm', items:[]}
	]}
];
<%}%>
<%if(model.isUser()){%>
var treedata = [
	{id:300, name:'个人管理', img:"", imgOpen:"", url:"", items:[
	]}
];
<%}%>
var nn = [
	{id:10, name:'CMS', img:"", imgOpen:"", url:"", items:[
		 {id:12,name:'栏目管理', img:"", imgOpen:"", url:'/cms/category/getCategory.htm', items:[]}
		,{id:13,name:'信息发布', img:"", imgOpen:"", url:'/cms/page/getCategoryTree.htm', items:[]}
	]}
	,{id:20, name:'BBS', img:"", imgOpen:"", url:"", items:[
	     {id:21,name:'站点管理', img:"", imgOpen:"", url:'/bbs/site/getSite.htm', items:[]}
		,{id:22,name:'版块管理', img:"", imgOpen:"", url:'/bbs/forum/getForum.htm', items:[]}
	]}
];
treedata.push(nn[0]);
treedata.push(nn[1]);
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
<br />菜单
<br />&nbsp;&nbsp;&nbsp;&nbsp;<a target='top' href='<%=path%>/logout.jsp'>退出</a>
</div>
<div style="float:right;height:100%;width:80%;min-width:800px;">
	<iframe id="main" name="main" style="height:100%;width:100%;" scrolling="auto" frameborder="0" src="#"></iframe>
</div>
</div>
</body>
</html>