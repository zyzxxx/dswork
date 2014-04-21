<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title></title>
<style>body{margin:0px;padding:0px;overflow:hidden;}</style>
<script type="text/javascript">
var data = parent.args.data;
parent.reValue = data;
function refreshData()
{
	parent.reValue = data;
}
function setModel(m, type)//type(true增加,false移除)
{
	for(var i = 0; i < data.length; i++)
	{
		if(m.id == data[i].id)
		{
			if(type)
			{
				data[i] = m;
			}
			else
			{
				data.splice(i, 1);//找到就移除
			}
			refreshData();
			return;
		}
	}
	if(type)
	{
		data[data.length] = m;
		refreshData();
	}
}
function refreshModel(m)//用于刷新和判断是否已选
{
	for(var i = 0; i < data.length; i++)//找到就刷新
	{
		if(m.id == data[i].id)
		{
			data[i] = m;
			refreshData();
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