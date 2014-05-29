<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style>html,body{width:100%;height:100%;margin:0px;padding:0px;overflow:hidden;}</style>
<script type="text/javascript">
var reValue;
var isDialog = (parent.$jskey && parent.$jskey.dialog && parent.$jskey.dialog.dialogArguments);
var source = isDialog ? parent.$jskey.dialog.dialogArguments.args.data : parent.args.data;//原始对象
var data = source;
function refreshData(){
	reValue = data;
	if(isDialog){parent.$jskey.dialog.returnValue = reValue;}else{parent.reValue = reValue;}
}
refreshData();
function setModel(m){
	data = m;//直接替换
	refreshData();
}
function refreshModel(m){//用于刷新和判断是否已选
	if(data != null){
		if(m.id == data.id){
			setModel(m);//找到就直接替换
			return true;
		}
	}
	return false;
}
function getReturnValue(){closeWindow();}
function closeWindow(){
	if(isDialog){parent.$jskey.dialog.close();}else{parent.closeWindow();}
}
window.onload = function(){if('${url}' == ""){
	document.getElementById("chooseFrame").src = isDialog ? parent.$jskey.dialog.dialogArguments.args.url : parent.args.url;
}}
</script>
</head>
<body>
<iframe id="chooseFrame" name="chooseFrame" src="${url}" style="width:100%;height:100%;border:0px;" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</body>
</html>