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
<script type="text/javascript">
$(function(){
	$("#orgpidname").bind("click", function(e){
		$dswork.showTree({id:"treeid",width:400,height:200,root:{name:"根节点名22"}
			,left:$(this).offset().left, top:$(this).offset().top+20
			,url:function(node){return "/wb/share/getJsonOrg.htm?status=2&pid="+node.id;}
			,check:function(id, node){if(node.id==0){return false;}else{$("#orgpidname").val(node.name);$("#orgpid").val(node.id);}}
			,dataFilter:function(id, pnode, data){var d=[];for(var i =0; i < data.length; i++){if(data[i].status == 2){d.push(data[i]);}}return d;}
		})
	});
	$("#orgidname").bind("click", function(e){
		$dswork.showTree({id:"treeid",width:400,height:200,title:"根节点名11"
			,left:$(this).offset().left, top:$(this).offset().top
			,url:function(node){return "/wb/share/getJsonOrg.htm?pid="+node.id;}
			,check:function(id, node){if(node.id==0 || node.status != 1){return false;}else{$("#orgidname").val(node.name);$("#orgid").val(node.id);}}
			,dataFilter:function(id, pnode, data){for(var i =0; i < data.length; i++){if(data[i].status == 2){data[i].nocheck=true;}}return data;}
		})
	});
});
</script>
</head>
<body>
<%=DsCommonFactory.getCheckbox("ztree", "chk_hello") %>
<br />******************************<br />
<%=DsCommonFactory.getRadio("ztree", "rdo_hello") %>
<br />******************************<br />
<%=DsCommonFactory.getSelect("ztree", "sel_hello") %>
<br />******************************<br />
<select id="news" name="news"><option value="">其他</option><%=DsCommonFactory.getOption("ztree") %></select>
<br />******************************<br />
<%=DsCommonFactory.getDictJson("ztree", "") %>
<br />******************************<br />
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">所属单位</td>
		<td class="form_input"><input type="text" id="orgpidname" name="orgpidname" value="" /><input type="text" id="orgpid" name="orgpid" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">所属部门</td>
		<td class="form_input"><input type="text" id="orgidname" name="orgidname" value="" /><input type="text" id="orgid" name="orgid" value="" /></td>
	</tr>
</table>
</body>
</html>