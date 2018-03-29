<%@page language="java" contentType="text/html;charset=UTF-8" %><%
String path = request.getContextPath();
common.authown.AuthOwnUtil.logout(request, response);
common.authown.AuthOwnUtil.login(request, response, "100000000", "admin", "系统管理", "adminadmin");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>功能模块化系统，仅使用临时菜单，详细框架页面不在此包含</title>
<style type="text/css">
*{word-wrap:break-word;}
html,body,div{margin:0;padding:0;}
html,body{*position:static;height:100%;border:0;line-height:1.6;}
html{font-family:sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;}
body,a{font-family:"Microsoft Yahei","Hiragino Sans GB","Helvetica Neue",Helvetica,tahoma,arial,Verdana,sans-serif,"\5B8B\4F53";font-size:12px;color:#182a2d;-webkit-font-smoothing:antialiased;-moz-font-smoothing:antialiased;}
html{overflow:-moz-scrollbars-vertical;}
a{outline:none;text-decoration:none;cursor:pointer;text-decoration:none;}
a:link,a:visited,a:active{color:#182a2d;outline:none;}
a:hover{outline:none;color:#182a2d;text-decoration:none;}
a:focus{outline:none;}
a:hover,a:active{outline:none;}:focus{outline:none;}

label,a,a:link,a:visited,a:active{display:inline-block;font-size:14px;padding:3px 8px;line-height:1.4;}
label{border:solid 1px #bce8f1;color:#31708f;background-color:#d9edf7;}
a,a:link,a:visited,a:active{border:solid 1px #0f9ae0;color:#fff;background-color:#3bb4f2;}
a:hover {background-color:#0f9ae0;}
.level1 {margin:8px 0 0 8px;}
.level2 {margin:2px 0 0 38px;}
.level3 {margin:2px 0 0 68px;}
.level4 {margin:2px 0 0 98px;}
</style>
<script type="text/javascript">
var treedata = [
	{id:10, name:"CMS", img:"", imgOpen:"", url:"", items:[
		 {id:12,name:"栏目管理", img:"", imgOpen:"", url:'/cms/category/getCategory.htm', items:[]}
		,{id:11,name:"模板编辑", img:"", imgOpen:"", url:'/cms/template/getTemplateTree.htm', items:[]}
		,{id:12,name:"附件管理", img:"", imgOpen:"", url:'/cms/file/getFileTree.htm', items:[]}
		
		,{id:13,name:"信息发布", img:"", imgOpen:"", url:'/cms/page/getCategoryTree.htm', items:[]}
		
		,{id:15, name:"采编审核发布", img:"", imgOpen:"", url:"", items:[
	   		 {id:151,name:'信息采编', img:"023.png", imgOpen:"023.png", url:'/cms/edit/getCategoryTree.htm', items:[]}
			,{id:152,name:'信息审核', img:"023.png", imgOpen:"023.png", url:'/cms/audit/getCategoryTree.htm', items:[]}
			,{id:153,name:'信息发布', img:"023.png", imgOpen:"023.png", url:'/cms/publish/getCategoryTree.htm', items:[]}
			,{id:16, name:"权限管理", img:"", imgOpen:"", url:"", items:[
				 {id:161,name:'用户授权', img:"", imgOpen:"", url:'/cms/permission/getUser.htm', items:[]}
			]}
		]}
	]}
];
</script>
</head>
<body>
<div style="overflow:hidden;height:100%;width:100%;">
<div style="overflow:hidden;float:right;height:100%;width:80%;">
	<iframe id="main" name="main" style="height:100%;width:100%;" scrolling="auto" frameborder="0" src="about:blank"></iframe>
</div>
<div style="overflow:auto;float:left;height:100%;width:19.8%;border-right:1px solid #6ea5eb;">
<script type="text/javascript">
for(var i = 0; i < treedata.length; i++){
	var item = treedata[i];
	if(item.url == "" || item.url == "#"){
		document.write("<label class='level1'>" + item.name + "</label><br />");
	}
	else{
		document.write("<a class='level1' target='main' href='<%=path%>" + item.url + "'>" + item.name + "</a><br />");
	}
	for(var j = 0; j < item.items.length; j++){
		var m = item.items[j];
		if(m.url == "" || m.url == "#"){
			document.write("<label class='level2'>" + m.name + "</label><br />");
		}
		else{
			document.write("<a class='level2' target='main' href='<%=path%>" + m.url + "'>" + m.name + "</a><br />");
		}
		
		for(var k = 0; k < m.items.length; k++){
			var mm = m.items[k];
			if(mm.url == "" || mm.url == "#"){
				document.write("<label class='level3'>" + mm.name + "</label><br />");
			}
			else{
				document.write("<a class='level3' target='main' href='<%=path%>" + mm.url + "'>" + mm.name + "</a><br />");
			}
			for(var l = 0; l < mm.items.length; l++){
				var mmm = mm.items[l];
				document.write("<a class='level4' target='main' href='<%=path%>" + mmm.url + "'>" + mmm.name + "</a><br />");
			}
		}
	}
}
</script>
</div>
</div>
</body>
</html>