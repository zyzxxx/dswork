<%@page language="java" pageEncoding="UTF-8" import="common.auth.*"%>
<%
String path = request.getContextPath();
Auth model = AuthLogin.getLoginUser(request, response);
if(model == null)
{
	out.println("<script>top.location.href='" + path + "/login.jsp';</script>");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>功能模块化系统，仅使用临时菜单，详细框架页面不在此包含</title>
<style type="text/css">html, body {height:100%;width:100%;padding:0;margin:0;font-size:16px;}a{color:#000000;line-height:25px;font-size:12px;}</style>
<script type="text/javascript">
var treedata = [
{id:100, name:'CMS管理', img:"", imgOpen:"", url:"", items:[
	 {id:101,name:'栏目管理', img:"", imgOpen:"", url:'/cms/category/getCategory.htm', items:[]}
	,{id:102,name:'信息发布', img:"", imgOpen:"", url:'/cms/page/getCategoryTree.htm', items:[]}
]}
,{id:200, name:'基础数据', img:"", imgOpen:"", url:"", items:[
	 {id:201,name:'企业管理', img:"", imgOpen:"", url:'/ep/enterprise/getEnterprise.htm', items:[]}
	,{id:202,name:'用户管理', img:"", imgOpen:"", url:'/ep/user/getUser.htm', items:[]}
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