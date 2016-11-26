<%@page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>


<div class="header">
<embed width="1000" height="335" src="${ctx}/themes/web/banner.swf" type="application/x-shockwave-flash" quality="high" />
</div>
<div class="nav">
    <ul class="left">
        <li><a href="${ctx}/index.html">首页</a></li>
        <li><a href="${ctx}/a/gkzn/index.html">信息公开</a></li>
        <li><a href="${ctx}/a/gzdt2/index.html">工作动态</a></li>
        <li><a href="${ctx}/a/tzgsgg/index.html">通知公告</a></li>
        <li><a href="${ctx}/a/gkyjx/index.html">公众互动</a></li>
        <li><a target="_blank" href="http://wsbs.gz.gov.cn/gz/index.jsp">办事大厅</a></li>
        <li><a target="_blank" href="http://weibo.com/gzly">官方微博</a></li>
        <li class="last"><a target="_blank" href="http://www.visitgz.com/">资讯网</a></li>
    </ul>
</div>


<script type="text/javascript" src="${ctx}/js/jskey_focus.js"></script>
<style type="text/css">
#showid1 img{width:100%;height:100%;margin:0 auto;}
</style>
<div style="width:990px;height:0px;margin:0 auto;position:relative;background:none;z-index:999;">
	<div style="position:absolute;top:40px;left:5px;">
		<a href="http://www.gzly.gov.cn/index.html"><img style="border:none;height:120px;" alt="" src="${ctx}/themes/images/logo.png"/></a>
	</div>
	<div class="tlink" style="position:absolute;top:5px;right:5px;height:20px;">
		<a href="http://www.visitgz.com">广州旅游资讯网</a> /
		<a href="http://weibo.com/gzly">官方微博</a>
	</div>
</div>
<div id="showid1" style="width:100%;height:300px;margin:0 auto;overflow:hidden;background-color:#fff;clear:both;">
	<div class="jskey_focus">
		<ul>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
			<li><a href="#" target="_blank"><img src="${ctx}/f/res/index/3.jpg"/></a></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$jskey.focus({target:"showid1",arrow:false,button:true,mode:"fit"});//,mode:"fit"
</script>

<script type="text/javascript" src="${ctx}/js/jskey_jquery_navtool.js"></script>
<%request.setAttribute("mheader", cms.queryCategory(""));%>
<style>
.nav-my .nav-up a{color:#fff;padding:0 26px;}
.nav-my .nav-up .nav-up-hover a{color:yellow;}
.nav-my .nav-down dl{margin:10px 12px 8px 12px;}
.nav-my .nav-down dt a {color:#ddd;}
.nav-my .nav-down dt a:link {color:#ddd;}
.nav-my .nav-down dt a:hover{color:yellow;}
.nav-my .nav-down dd a {color:#eee;}
.nav-my .nav-down dd a:link {color:#eee;}
.nav-my .nav-down dd a:hover{color:yellow;}
</style>
<div style="width:990px;height:44px;margin:0 auto;">
  <div id="v1" class="nav-tool nav-my">
	<div class="nav-up">
		<ul>
			<li><a href="${ctx}/index.html">首页</a></li>
	        <li data-nav="63"><a href="${ctx}/a/gkzn/index.html">信息公开</a></li>
	        <li data-nav="41"><a href="${ctx}/a/gzdt2/index.html">工作动态</a></li>
	        <li data-nav="64"><a href="${ctx}/a/gzhdbz/index.html">公众互动</a></li>
	        <li data-nav="59"><a href="${ctx}/a/qiwj/index.html">通知公告</a></li>
	        <li data-nav="77"><a href="http://wsbs.gz.gov.cn/gz/index.jsp">办事大厅</a></li>
	        <li data-nav="81"><a href="${ctx}/a/zdly/index.html">重点领域</a></li>
	        <li>
		        <div style="background-color:white;border-radius:5px;margin:10px;">
		        	<input type="text" id="vv" style="margin-left:10px;border:0px;width:140px;height:24px;line-height:24px;" placeholder="请输入关键字" value=""/>
		        	&nbsp;&nbsp;<img onclick="var _x=document.getElementById('vv').value;if(_x!=''){location.href='${ctx}/a/search/index.html?v='+encodeURI(encodeURI(_x));}" style="width:16px;height:18px;margin-right:10px;cursor:pointer;" alt="" src="${ctx}/themes/images/search.png"/>
		        </div>
	        </li>
		</ul>
	</div>
	<div class="nav-down">
		<c:forEach items="${mheader}" var="d">
			<c:if test="${fn:length(d.list)>0}">
			<div class="nav-down-menu" data-nav="${d.id}">
				<c:if test="${d.id == 81}">
					<dl><dt><a target="_blank" href="${ctx}/a/zzjg/index.html">领导之窗</a></dt></dl>
					<dl><dt><a target="_blank" href="${ctx}/a/czyjs/index.html">财政预决算</a></dt></dl>
				</c:if>
				<c:forEach items="${d.list}" var="dd">
					<dl><dt><a target="_blank"<c:if test="${dd.list==null || fn:length(dd.list)==0}"> href="${dd.status==2?'':ctx}${dd.url}"</c:if>>${dd.name}</a></dt>
						<c:forEach items="${dd.list}" var="ddd">
						<dd><a target="_blank" href="${ddd.status==2?'':ctx}${ddd.url}">${ddd.name}</a></dd>
						</c:forEach>
					</dl>
				</c:forEach>
			</div>
			</c:if>
		</c:forEach>
	</div>
  </div>
</div>
<div class="vline">&nbsp;</div>
<script type="text/javascript">
$(function(){
	$("#v1").navtool({height:44,bgColor:"#1C72BE",bgColorHover:"#15539C",bgColorMenu:"#15539C"});// 字体颜色需要使用css进行设置
});
</script>
<%--
--%>