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
	o = {};o.id = "${d.roleid}";o.name = "${fn:escapeXml(d.rolename)}";o.sname=o.name;o.systemname = "${fn:escapeXml(d.systemname)}";
	map.put("${d.roleid}", o);
	</c:forEach>
	refreshRole();domsg(false);
	return false;
}
function refreshRole(){
	$("#contactTable>tbody>tr:gt(0)").remove();//除首行外移除所有行
	var arr = map.getValueArray();
	for(var i = 0; i < arr.length; i++){
		var obj = arr[i],_html = "<tr class='list'>";
		_html += "<td style='text-align:left;'>&nbsp;<a title='查看角色明细' onclick='return getRoleById(\"" + obj.id + "\");' href='#'>" + obj.sname + "</a></td>";
		_html += "<td style='text-align:left;'>&nbsp;" + obj.systemname + "</td>"
		_html += "<td><input type='button' class='delete' onclick='$dswork.deleteRow(this, " + obj.id + ")' /></td>";
		_html += "</tr>";
		$("#contactTable").append(_html);
	}
}
function getRoleById(id){
	var arr = map.getValueArray();
	for(var i = 0; i < arr.length; i++){if(arr[i].id == id){
		$jskey.dialog.callback = function(){};
		$jskey.dialog.showDialog({id:"role", title:arr[i].sname + "-功能明细", url:"../rolechoose/getRoleById.htm?roleid=" + id, args:{}, width:"350", height:"450"});
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
	$jskey.dialog.showChoose({id:"choose", title:"选择角色", args:{url:"../rolechoose/getRoleChoose.htm?systemid=${systemid}", data:data}, width:"600", height:"450"});
	return false;
}
$dswork.deleteRow = function (obj,id){map.remove(id);$(obj).parent().parent().remove();domsg(true);};
function domsg(type){$("#showmsg").html(type?"数据已修改，未保存":"");}
$dswork.callback = function(){if($dswork.result.type == 1){location.reload();}};
//$jskey.dialog.callback = function(){var result = $jskey.dialog.returnValue;if(result != null){map = new $jskey.Map();for(var i = 0; i < result.length; i++){map.put(result[i].id + "", result[i]);}refreshRole();domsg(true);}}
function callback(m){map = m;refreshRole();domsg(true);}
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
		$.post("updSetUser2.htm",{userid:"${userid}", roleids:ids},function(data){$dswork.showResponse(data);});
		return false;
	}});
	$("#vinit").click(function(){return init();});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">已选角色 &nbsp;<span id="showmsg" style="color:red;"></span></td>
		<td class="menuTool">
			<a id="vinit" class="refresh" href="#">重置选择</a>
			<a id="vchoose" class="check" href="#">选择角色</a>
			<a id="vsave" class="save" href="#">保存分配</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<table id="contactTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:30%;">角色名称</td>
		<td>所属系统</td>
		<td style="width:8%;">操作</td>
	</tr>
</table>
</body>
</html>
