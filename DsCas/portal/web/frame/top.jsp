<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="dswork.cas.CasFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
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
		$id("titleDIV").className = "title";
		$id("toolDiv").className = "xtool tool";
		setimg("big_");
		x["rows"] = "76,*,24";
	}
	else{
		$id("titleDIV").className = "minititle";
		$id("toolDiv").className = "xtool minitool";
		setimg("mini_");
		x["rows"] = "26,*,0";
	}
}
function setimg(v){
	var p = "style/top/white/" + v;
	$id("vuser").src = p + "user.png";
	$id("vhome").src = p + "home.png";
	$id("vreload").src = p + "reload.png";
	$id("vswitch").src = p + "switch.png";
	$id("vlogout").src = p + "logout.png";
	$id("vwin").title=(v=="big_"?"最大化":"恢复");
	try{$id("vscreen").src = p + "screen.png";}catch(e){}
}
</script>
</head>
<body onselectstart="return false;" oncontextmenu="return false;">
<div class="topframe">
	<div class="left"></div>
	<div class="right"></div>
	<div id="toolDiv" class="xtool minitool">
		<div><img id="vuser" src="#"/><span class="show"><%=CasFilter.getAccount(session) %></span></div>
		<div onclick="PFM.$('#tt').tabs('select', 0);"       title="切换首页"><img id="vhome"   src="#"/><span>首页</span></div>
		<div onclick="reload();"                             title="刷新页面"><img id="vreload" src="#"/><span>刷新</span></div>
		<div onclick="PFM.frames['leftFrame'].showSystem();" title="切换系统"><img id="vswitch" src="#"/><span>切换系统</span></div>
		<div onclick="top.location.href='${ctx}/logout.jsp';"    title="退出"><img id="vlogout" src="#"/><span>退出</span></div>
		<div onclick="doDraw();" title="" id="vwin"><img id="vscreen" src="#" class="mini"/></div>
	</div>
</div>
<div id="titleDIV" class="minititle">计算机管理控制程序</div>
</body>
<script type="text/javascript">doDraw();</script>
</html>
