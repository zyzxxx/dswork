<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp"%>
<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	if(!confirm("是否继续添加用户？")){location.href = "getUser.htm";}
}};
$(function(){
	$("#orgpname").bind("click", function(e){
		$dswork.showTree({id:"treeid1",width:400,height:200,root:{name:"选择单位"}
			,left:$(this).offset().left, top:$(this).offset().top+20
			,url:function(node){return "${ctx}/common/share/getJsonOrg.htm?pid="+node.id;}
			,check:function(id, node){if(node.id==0){return false;}else{$("#orgpname").val(node.name);$("#orgpid").val(node.id);$("#orgname").val();$("#orgid").val();}}
			,dataFilter:function(id, pnode, data){var d=[];for(var i =0; i < data.length; i++){if(data[i].status == 2){d.push(data[i]);}}return d;}
		});
	});
	$("#orgname").bind("click", function(e){
		var rootid = $("#orgpid").val();
		if(rootid == ""){alert("请先选择单位");return false;}
		$dswork.showTree({id:"treeid2",width:400,height:200,root:{id:rootid, name:"选择部门-"+$("#orgpname").val()}
			,left:$(this).offset().left, top:$(this).offset().top+20
			,url:function(node){return "${ctx}/common/share/getJsonOrg.htm?pid="+node.id;}
			,check:function(id, node){if(node.id==0 || node.status != 1){return false;}else{$("#orgname").val(node.name);$("#orgid").val(node.id);}}
			,dataFilter:function(id, pnode, data){var d=[];for(var i =0; i < data.length; i++){if(data[i].status == 1){d.push(data[i]);}}return d;}
		});
	});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getUser.htm?page=${param.page}">返回</a> 
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addUser2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">帐号</td>
		<td class="form_input"><input type="text" id="account" name="account" dataType="Char" maxlength="50" value="" /> <span class="imp">*</span> <span style="font-weight:bold;">添加后不可修改</span></td>
	</tr>
	<tr>
		<td class="form_title">姓名</td>
		<td class="form_input"><input type="text" id="name" name="name" dataType="Chinese" maxlength="25" value="" /> <span class="imp">*</span></td>
	</tr>
	<tr>
		<td class="form_title">密码</td>
		<td class="form_input"><input type="password" id="password" name="password" style="width:130px;" dataType="Require" maxlength="32" value="000000" /> <span class="imp">*</span> <span style="font-weight:bold;">默认码为：000000</span></td>
	</tr>
	<tr>
		<td class="form_title">确认密码</td>
		<td class="form_input"><input type="password" id="password2" name="password2" style="width:130px;" dataType="Repeat" to="password" msg="两次输入的密码不一致" value="000000" /> <span class="imp">*</span></td>
	</tr>
	<tr>
		<td class="form_title">身份证号</td>
		<td class="form_input"><input type="text" id="idcard" name="idcard" style="width:200px;" require="false" dataType="IdCard" maxlength="18" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属单位</td>
		<td class="form_input"><input type="text" id="orgpname" name="orgpname" value="" /><input type="hidden" id="orgpid" name="orgpid" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属部门</td>
		<td class="form_input"><input type="text" id="orgname" name="orgname" readonly="readonly" value="" /><input type="hidden" id="orgid" name="orgid" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">邮箱</td>
		<td class="form_input"><input type="text" id="email" name="email" readonly="readonly" style="width:200px;" require="false" dataType="Email" maxlength="250" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">手机</td>
		<td class="form_input"><input type="text" id="mobile" name="mobile" require="false" dataType="Mobile" maxlength="50" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">电话</td>
		<td class="form_input"><input type="text" id="phone" name="phone" require="false" dataType="Phone" maxlength="50" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">工作证号</td>
		<td class="form_input"><input type="text" id="workcard" name="workcard" style="width:200px;" require="false" dataType="Require" maxlength="64" value="" /></td>
	</tr>
</table>
</form>
</body>
</html>
