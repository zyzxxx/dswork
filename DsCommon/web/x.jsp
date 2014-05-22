<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="dswork.common.DsCommonFactory"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/page.jsp"%>
<%@include file="/commons/include/getEasyui.jsp" %>
<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
	$dswork.ztree.root.name = "例子";
	$dswork.ztree.root.nocheck = true;
	$dswork.ztree.url = function(treeNode){return "xx.jsp?id=" + treeNode.id;};
	$dswork.ztree.config.check.chkStyle = "radio";
	$dswork.ztree.config.check.enable = true;
	$(function(){
		$jskey.radio.reselect("rdo_hello", "tree2");
		var $z = $dswork.ztree;
		$z.load();
		$z.expandRoot();
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
<ul id="mytree" class="ztree" />
<br />******************************<br />
<br />******************************<br />
<br />******************************<br />
<br />******************************<br />
</body>
</html>