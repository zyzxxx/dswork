<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style>html,body{width:100%;height:100%;margin:0px;padding:0px;overflow:hidden;}</style>
<script type="text/javascript">
var reValue;// 用于调用页主动获取
var isDialog = (parent.$jskey && parent.$jskey.dialog && parent.$jskey.dialog.dialogArguments);
var data = isDialog ? parent.$jskey.dialog.dialogArguments.args.data : parent.args.data;
function refreshData(){
	reValue = data;
	if(isDialog){parent.$jskey.dialog.returnValue = reValue;}else{parent.reValue = reValue;}
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