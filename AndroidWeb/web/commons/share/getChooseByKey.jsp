<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title></title>
<style>body{margin:0px;padding:0px;overflow:hidden;}</style>
<script type="text/javascript">
var source = parent.args.data;//原始对象
var data = source;
parent.reValue = data;
function refreshData()
{
	parent.reValue = data;
}
function setModel(m)
{
	data = m;//直接替换
	refreshData();
}
function refreshModel(m)//用于刷新和判断是否已选
{
	if(parent.reValue != null)
	{
		if(m.id == parent.reValue.id)
		{
			setModel(m);//找到就直接替换
			return true;
		}
	}
	return false;
}
function getReturnValue()
{
	parent.closeWindow();
}
window.onload = function()
{
	// 用于兼容旧的写法，旧的写法src不为空
	if('${url}' == "")
	{
		document.getElementById("chooseFrame").src = parent.args.url;
	}
}
</script>
</head>
<body>
<iframe id="chooseFrame" name="chooseFrame" src="${url}" style="width:100%;height:100%;" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</body>
</html>