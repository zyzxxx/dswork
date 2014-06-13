<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsCommonFactory"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp"%>
<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript" src="/web/js/dswork/form.js"></script>
<script type="text/javascript">
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
	
	
	function loaddata(value, selectid){
		$.post("${ctx}/common/share/getJsonDict.htm",{name:"SSXQ", value:value},function(data){
			var s = $("#" + selectid);
			var a = eval(data);
			for(var i=0; i<a.length; i++){
				var o = $("<option></option>");
				o.text(a[i].name);
				o.attr("value", a[i].id);
				s.append(o);
			}
			s.click();
		});
	}
	$("#v1").bind("click", function(e){
		document.getElementById("v2").options.length = 0;
		document.getElementById("v3").options.length = 0;
		loaddata($(this).val(), "v2");
	});
	$("#v2").bind("click", function(e){
		document.getElementById("v3").options.length = 0;
		loaddata($(this).val(), "v3");
	});
	loaddata("", "v1");
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">测试</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
		</td>
	</tr>
</table>
<br />******************************<br />
<form id="dataForm" method="post" action="#">
<span><select id="v1"></select></span>
&nbsp;<span><select id="v2"></select></span>
&nbsp;<span><select id="v3"></select></span> 
<br />******************************<br />
<span><%=DsCommonFactory.getCheckbox("SSXQ", "chk_hello") %></span>
<br />******************************<br />
<span><%=DsCommonFactory.getRadio("SSXQ", "rdo_hello") %></span>
<br />******************************<br />
<span><%=DsCommonFactory.getSelect("SSXQ", "sel_hello") %></span>
<br />******************************<br />
<span><select id="news" name="news"><option value="">其他</option><%=DsCommonFactory.getOption("SSXQ") %></select></span>
<br />******************************<br />
<%=DsCommonFactory.getDictJson("SSXQ", "") %>
<br />******************************<br />
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">所属单位</td>
		<td class="form_input"><input type="text" id="orgpname" name="orgpname" readonly="readonly" value="" /><input type="text" id="orgpid" name="orgpid" readonly="readonly" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属部门</td>
		<td class="form_input"><input type="text" id="orgname" name="orgname" readonly="readonly" value="" /><input type="text" id="orgid" name="orgid" readonly="readonly" value="" /></td>
	</tr>
</table>
</form>
<br />
</body>
</html>