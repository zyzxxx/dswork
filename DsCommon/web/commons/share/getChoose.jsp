<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style>html,body,iframe{width:100%;height:100%;margin:0px;padding:0px;overflow:hidden;}</style>
<script type="text/javascript">alert("");
var reValue;// 用于调用页主动获取或兼容旧页面
var $jskey={dialog:{returnValue:{},dialogArguments:{args:parent.$jskey.dialog.dialogArguments.args}}};//为了统一子页面
var data = parent.$jskey.dialog.dialogArguments.args.data;
function refreshData(){
	reValue = data;
	$jskey.dialog.returnValue = data;
	parent.$jskey.dialog.returnValue = data;
}
refreshData();
function setModel(m, type){//type(true增加,false移除)
	for(var i = 0; i < data.length; i++){
		if(m.id == data[i].id){
			if(type){
				data[i] = m;
			}
			else{
				data.splice(i, 1);//找到就移除
			}
			refreshData();
			return;
		}
	}
	if(type){
		data[data.length] = m;
		refreshData();
	}
}
function refreshModel(m){//用于刷新和判断是否已选
	for(var i = 0; i < data.length; i++){//找到就刷新
		if(m.id == data[i].id){
			data[i] = m;
			refreshData();
			return true;
		}
	}
	return false;
}
function getReturnValue(){closeWindow();}
function closeWindow(){parent.$jskey.dialog.close();}
$jskey.dialog.close = function(){closeWindow();};
window.onload = function(){if('${url}' == ""){
	document.getElementById("chooseFrame").src = parent.$jskey.dialog.dialogArguments.args.url;
}}
</script>
</head>
<body><iframe id="chooseFrame" name="chooseFrame" src="${url}" style="width:100%;height:100%;border:0px;" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe></body>
</html>