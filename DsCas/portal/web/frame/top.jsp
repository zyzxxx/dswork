<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="dswork.sso.SSOFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="style/frame.css" />
<script type="text/javascript">
var PFM = parent.frames["middleFrame"];
function $id(id){return document.getElementById(id);}
function reload(){
	try{PFM.$('iframe', PFM.$("#tt").tabs("getSelected"))[0].contentWindow.location.reload();}catch(e){alert(e.message);}
}
function doDraw(){
	var x = parent.document.getElementById("fk");
	if($id("titleDIV").className != "title"){
		x["rows"] = "76,*,24";
		$id("titleDIV").className = "title";
		$id("toolDiv").className = "xtool tool";
		$id("fontscreen").innerHTML = "&#xf0021;";
		$id("vwin").title = "收缩";
	}
	else{
		x["rows"] = "26,*,0";
		$id("titleDIV").className = "minititle";
		$id("toolDiv").className = "xtool minitool";
		$id("fontscreen").innerHTML = "&#xf0022;";
		$id("vwin").title = "恢复";
	}
}
</script>
</head>
<body onselectstart="return false;" oncontextmenu="return false;">
<div class="topframe">
	<div class="left"></div>
	<div class="right"></div>
	<div id="toolDiv" class="xtool minitool">
		<div><i>&#xf0001;</i><b class="show"><%=SSOFilter.getAccount(session) %></b></div>
		<div onclick="PFM.$('#tt').tabs('select', 0);"       title="切换首页"><i>&#xf0003;</i><b>首页</b></div>
		<div onclick="reload();"                             title="刷新页面"><i>&#xf0004;</i><b>刷新</b></div>
		<%--<div onclick="PFM.frames['leftFrame'].showSystem();" title="切换系统"><i>&#xf0009;</i><b>切换系统</b></div>--%>
		<div onclick="top.location.href='${ctx}/logout.jsp';"    title="退出"><i>&#xf0005;</i><b>退出</b></div>
		<div onclick="doDraw();" title="" id="vwin"><i id="fontscreen" class="mini">&#xf0021;</i></div>
	</div>
</div>
<div id="titleDIV" class="minititle">计算机管理控制程序</div>
</body>
<script type="text/javascript">doDraw();</script>
</html>
