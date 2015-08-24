<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="dswork.cas.CasFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8" />
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="style/frame.css" />
<script type="text/javascript">
if(top.location == this.location){top.location = "${ctx}/index.jsp";}
function reload(){
	try{
		parent.frames["middleFrame"].$('iframe', parent.frames["middleFrame"].$("#tt").tabs("getSelected"))[0].contentWindow.location.reload();
	}catch(e){alert(e.message);}
}
function doLogo(img){
	var fs = parent.document.getElementById("fk");
	if(document.getElementById("Logo").style.display == "none"){
		img.src = "style/top/fullscreen.png";
		img.title = "放大";
		fs["rows"] = "100,*,24";
		document.getElementById("Logo").style.display = "";
	}
	else{
		img.src = "style/top/customscreen.png";
		img.title = "缩小";
		fs["rows"] = "24,*,0";
		document.getElementById("Logo").style.display = "none";
	}
}
</script>
</head>
<body onselectstart="return false;" oncontextmenu="return false;">
<div class="topframe">
	<div id="Logo" class="logo">
		<div class="left"></div>
		<div class="right"></div>
	</div>
	<div class="show">
		<div class="left">
			<img class="img" src="style/top/man.png"/>
			<div>当前用户：<%=CasFilter.getAccount(session) %></div>
		</div>
		<div class="right">
			<img class="img" src="style/top/home.png"/><a title="首页" onclick="parent.frames['middleFrame'].$('#tt').tabs('select', 0);" href="#">首页</a>
			<img class="img" src="style/top/reload.png"/><a title="刷新主页面" onclick="reload();" href="#">刷新</a>
			<img class="img" src="style/top/logout.png"/><a title="退出系统" target="_top" onclick="" href="${ctx}/logout.jsp">退出</a>
			<img class="img" src="style/top/switch.png" /><a onclick='parent.frames["middleFrame"].frames["leftFrame"].showSystem();return false;' href="#">切换系统</a>
			&nbsp; <img class="img" style="cursor:pointer;" onclick="doLogo(this); return false;" src="style/top/fullscreen.png" title="放大" />
		</div>
	</div>
</div>
<div id="logoDIV" class="title">计算机管理控制程序</div>
</body>
</html>