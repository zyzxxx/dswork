<%@page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	#showid1 img{width:100%;height:100%;margin:0 auto;}
</style>
<div id="showid1" style="width:990px;height:200px;margin:0 auto;overflow:hidden;background-color:#fff;clear:both;">
	<div class="jskey_focus">
		<ul>
			<li><a href="#" target="_blank"><img src="${ctx}/themes/web/header.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="http://msdn.itellyou.cn/images/itellyou.cn.png"/></a></li>
			<li><a href="#" target="_blank"><img src="http://mat1.gtimg.com/news/2015/zt/xwghz/img/logo.png"/></a></li>
			<li><a href="#" target="_blank"><img src="https://www.baidu.com/img/bdlogo.png"/></a></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$jskey.focus({target:"showid1",arrow:false,button:true});//,mode:"fit"
</script>

<script type="text/javascript" src="${ctx}/js/jskey_jquery_navtool.js"></script>
<%request.setAttribute("mheader", cms.queryCategory(""));%>
<style>
.nav-my .nav-up a{color:#fff;padding:0 35px;}
.nav-my .nav-up .nav-up-hover a{color:yellow;}
.nav-my .nav-down dl{margin:10px 50px 8px 50px;}
.nav-my .nav-down dt a {color:#999;}
.nav-my .nav-down dt a:link {color:#ccc;}
.nav-my .nav-down dt a:hover{color:yellow;}
.nav-my .nav-down dd a {color:#aaa;}
.nav-my .nav-down dd a:link {color:#aaa;}
.nav-my .nav-down dd a:hover{color:yellow;}
</style>
<div style="width:990px;height:44px;margin:0 auto;">
  <div id="v1" class="nav-tool nav-my">
	<div class="nav-up">
		<ul>
			<li><a href="${ctx}/index.html">首页</a></li>
			<c:forEach items="${mheader}" var="d">
			<li data-nav="${d.id}"><a href="${ctx}${d.url}">${d.name}</a></li>
			</c:forEach>
	        <li><a target="_bank" href="http://wsbs.gz.gov.cn/gz/index.jsp">办事大厅</a></li>
	        <li><a target="_bank" href="http://www.weibo.com/">新浪微博</a></li>
	        <li class="last"><a target="_blank" href="https://www.baidu.com/">百度搜索</a></li>
		</ul>
	</div>
	<div class="nav-down">
		<c:forEach items="${mheader}" var="d"><c:if test="${fn:length(d.list)>0}">
			<div class="nav-down-menu" data-nav="${d.id}">
			<c:forEach items="${d.list}" var="dd">
				<dl><dt><a href="${ctx}${dd.url}">${dd.name}</a></dt>
					<c:forEach items="${dd.list}" var="ddd">
					<dd><a href="${ctx}${ddd.list}">${ddd.name}</a></dd></c:forEach></dl>
			</c:forEach></div>
		</c:if></c:forEach>
	</div>
  </div>
</div>
<script type="text/javascript">
$(function(){
	$("#v1").navtool({height:44,bgColor:"#1C72BE",bgColorHover:"#15539C",bgColorMenu:"#15539C"});// 字体颜色需要使用css进行设置
});
</script>





<!--
<div class="header">&nbsp;</div>
<div class="nav">
    <ul class="left">
        <li><a href="${ctx}/index.html">首页</a></li>
		<c:forEach items="${mheader}" var="d">
		<li><a href="${ctx}${d.url}">${d.name}</a></li>
		</c:forEach>
        <li><a target="_bank" href="http://wsbs.gz.gov.cn/gz/index.jsp">办事大厅</a></li>
        <li><a target="_bank" href="http://www.weibo.com/">新浪微博</a></li>
        <li class="last"><a target="_blank" href="https://www.baidu.com/">百度搜索</a></li>
    </ul>
</div>
-->