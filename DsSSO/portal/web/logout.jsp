<%@page language="java" pageEncoding="UTF-8" import="
	dswork.sso.WebFilter,
	dswork.sso.AuthFactory,
	dswork.sso.model.ISystem"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:set var="ctx" value="${pageContext.request.contextPath}" /><%
	ISystem[] arr = AuthFactory.getSystemByUser(WebFilter.getAccount(session));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="${ctx}/frame/js/jquery.js"></script>
<script type="text/javascript">
var sys = [];
<%if(arr != null){for(int i = 0; i < arr.length; i++){%>
sys[sys.length] = {index:<%=i%>,data:[],id:<%=arr[i].getId()%>,name:"<%=arr[i].getName().replaceAll("\"", "\\\\\"")%>",alias:"<%=arr[i].getAlias()%>",domainurl:"<%=arr[i].getDomainurl().replaceAll("\"", "\\\\\"")%>",rooturl:"<%=arr[i].getRooturl().replaceAll("\"", "\\\\\"")%>"};
<%}}%>
var index = 0,count = sys.length;
var ok = 0;
function logoutload(o){
	var url = o.domainurl + o.rooturl + "/logout?jsoncallback=?";
	$("body").append("<div id='x" + o.id + "'>" + o.name + "正在退出！</div>");
	$.ajax({
		url:url,
		type:"post",
		dataType:"jsonp",
		cache:false,
		success:function(data){
			ok++;
			$("#x" + o.id).html(o.name + "已退出！");
			if(ok >= count){
				alert("退完应用系统了，现退出门户!");
				location.href="${ctx}/logoutAction.jsp";
			}
		},
		error:function(){alert(o.name + "...出错了！！！");
			ok++;
			if(ok >= count){
				alert("退完应用系统了，现退出门户!");
				location.href="${ctx}/logoutAction.jsp";
			}
		}
	});
}
$(function(){
	for(var i = 0; i < count; i++){
		logoutload(sys[i]);
	}
});
</script>
</head>
<body></body>
</html>

