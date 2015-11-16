<%@page language="java" pageEncoding="UTF-8" import="
	dswork.sso.SSOFilter,
	dswork.sso.AuthFactory,
	dswork.sso.model.ISystem"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:set var="ctx" value="${pageContext.request.contextPath}" /><%
	ISystem[] arr = AuthFactory.getSystemByUser(SSOFilter.getAccount(session));
%>
<!--<!DOCTYPE html>-->
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/frame/js/jskey/themes/menu/jskey.menu.css" />
<script type="text/javascript" src="${ctx}/frame/js/jskey/jskey.menu.js"></script>
<script type="text/javascript" src="${ctx}/frame/js/jquery.js"></script>
<script type="text/javascript">
if(top.location == this.location){top.location = "${ctx}/index.jsp";}
var sys = [];
sys[0] = {index:0,data:[],id:0,name:"门户",alias:"",domainurl:"",rooturl:"${ctx}",menuurl:"${ctx}/menu.jsp"};
<%if(arr != null){for(int i = 0; i < arr.length; i++){%>
sys[sys.length] = {index:<%=i+1%>,data:[],id:<%=arr[i].getId()%>,name:"<%=arr[i].getName().replaceAll("\"", "\\\\\"")%>",alias:"<%=arr[i].getAlias()%>",domainurl:"<%=arr[i].getDomainurl().replaceAll("\"", "\\\\\"")%>",rooturl:"<%=arr[i].getRooturl().replaceAll("\"", "\\\\\"")%>",menuurl:"<%=arr[i].getMenuurl().replaceAll("\"", "\\\\\"")%>"};
<%}}%>
function menuload(o){
	var url = o.domainurl + o.menuurl;
	url += ((url.indexOf("?") == -1)?"?":"&") + "jsoncallback=?";
	$.getJSON(url, {"user":"<%=SSOFilter.getAccount(session)%>"},
		function(data){
			try{
				o.data = $jskey.menu.format(data);
				$jskey.menu.showNode(o.index, o.data);
			}catch(e){alert(e.message);}
		}
	);
}
</script>
<%--
这个是全部加载在同一个页面
<script type="text/javascript" src="left.js"></script>

这个是每次只加载一个系统
<script type="text/javascript" src="leftone.js"></script>
如果使用leftone.js，则在top.jsp上增加功能菜单
<div onclick="PFM.frames['leftFrame'].showSystem();" title="切换系统"><img id="vswitch" src="#"/><span>切换系统</span></div>
--%>
<script type="text/javascript" src="left.js"></script>
</head>
<body onselectstart="return false;" oncontextmenu="return true;">
</body>
<script type="text/javascript">
init();
</script>
</html>
