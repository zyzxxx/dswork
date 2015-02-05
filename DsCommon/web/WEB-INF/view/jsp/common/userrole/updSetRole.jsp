<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title> 
<%@include file="/commons/include/upd.jsp"%>
<script type="text/javascript">
var map = new $jskey.Map();
function init(){
	var o;
	map = new $jskey.Map();//初始化或还原,对象中的sname是原节点名称,因为tree修改了name属性
	<c:forEach items="${list}" var="d">
	map.put("${d.userid}", {id:"${d.userid}", name:"${fn:escapeXml(d.username)}", account:"${fn:escapeXml(d.useraccount)}"});
	</c:forEach>
	refreshUser();domsg(false);
	return false;
}
function refreshUser(){
	$("#contactTable>tbody>tr:gt(0)").remove();//除首行外移除所有行
	var arr = map.getValueArray();
	for(var i = 0; i < arr.length; i++){
		var obj = arr[i],_html = "<tr class='list'>";
		_html += "<td style='text-align:left;'>&nbsp;<a title='查看用户明细' onclick='return getUserById(\"" + obj.id + "\");' href='#'>" + obj.name + "(" + obj.account + ")</a></td>";
		_html += "<td><input type='button' class='delete' onclick='$dswork.deleteRow(this, " + obj.id + ")' /></td>";
		_html += "</tr>";
		$("#contactTable").append(_html);
	}
}
function getUserById(id){
	var arr = map.getValueArray();
	for(var i = 0; i < arr.length; i++){if(arr[i].id == id){
		$jskey.dialog.callback = function(){};
		$jskey.dialog.showDialog({id:"user", title:"用户明细", url:"./getUserById.htm?userid=" + id, args:{}, width:"400", height:"330"});
		break;
	}}
	return false;
}
function choose(data){
	$jskey.dialog.callback = function(){
		var result = $jskey.dialog.returnValue;
		if(result != null){
			var map = new $jskey.Map(), o;
			for(var i = 0; i < result.length; i++){o=result[i];map.put(o.id + "", o);}
			try{callback(map);}catch(e){alert(e.name + "\n" + e.message);}
		}
	};
	$jskey.dialog.showChoose({id:"choose", title:"勾选用户", args:{url:"./getUserChoose.htm", data:data},width:600,height:400,//,fit:true,draggable:false,
		buttons:[{text:"完成选择",iconCls:"menuTool-submit",handler:function(){$jskey.dialog.close();}}]
	});
	return false;
}
$dswork.deleteRow = function (obj,id){map.remove(id);$(obj).parent().parent().remove();domsg(true);};
function domsg(type){$("#showmsg").html(type?"数据已修改，未保存":"");}
$dswork.callback = function(){if($dswork.result.type == 1){location.reload();}};
function callback(m){map = m;refreshUser();domsg(true);}
$(function(){init();
	$("#vchoose").click(function(){
		var data = map.getValueArray();
		choose(data);
		return false;
	});
	$("#vsave").click(function(){if(confirm("确定保存？")){
		var ids = "",arr = map.getValueArray();
		if(arr.length > 0){
			ids = arr[0].id;
			for(var i = 1; i < arr.length; i++){ids += "," + arr[i].id}
		}
		$dswork.showRequest();
		$.post("updSetRole2.htm",{roleid:"${roleid}", userids:ids},function(data){$dswork.showResponse(data);});
		return false;
	}});
	$("#vinit").click(function(){return init();});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">已选用户&nbsp;<span id="showmsg" style="color:red;"></span></td>
		<td class="menuTool">
			<a id="vinit" class="refresh" href="#">重置选择</a>
			<a id="vchoose" class="check" href="#">选择用户</a>
			<a id="vsave" class="save" href="#">保存分配</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table id="contactTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td>用户(账号)</td>
		<td style="width:8%;">操作</td>
	</tr>
</table>
</body>
</html>
