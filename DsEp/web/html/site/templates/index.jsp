<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="common.cms.CmsFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%CmsFactory cms = (CmsFactory)request.getAttribute("cms");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0,minimal-ui"/>
<title>${site.name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/web.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/index.css"/>
</head>
<body>
<%@include file="include/header.jsp"%>


<!--浮动二维码-->
<div class="floatPanel">
	<div class="ctrolPanel">
		<div class="titlePanel">
			扫一扫
			<span onclick="disnon()">×</span>
		</div>
		<a href="#"><img src="${ctx}/themes/images/gzlywb.png" /></a>
		<a href="#"><img src="${ctx}/themes/images/gzlywx.png" /></a>
	</div>
</div>
<script type="text/javascript">
function disnon(){
	$(".floatPanel").css("display","none");
}
</script>


<div class="imsg w990 clearfix">
<div class="main left">
  <div class="hotnews">
	<div class="mid clear">
		<dl>
			<dt>新闻头条</dt>
			<dd>
			<div id="rollnews">
			<%request.setAttribute("pagetop", cms.queryList(1, 3, false, true, true, 42));%>
			<c:forEach items="${pagetop}" var="d">
				<div>
					<a target="_blank" href="${ctx}${d.url}"><span>${fn:escapeXml(d.title)}</span></a>
					<a target="_blank" title="详细" href="${ctx}${d.url}"><p>${fn:substring(d.summary, 0, 100)}</p></a>
				</div>
			</c:forEach>
			</div>
			<script type="text/javascript">
			$(function(){
				var x = $("#rollnews");
				var x_h = 84*3;// 这里的6是指你一共多少条新闻，一条高度是84px
				x.html($.trim(x.html()) + $.trim(x.html()) + $.trim(x.html()));
				var i = 1;
				function MarqueeRollNews(){
					var ll = parseInt(x.css("marginTop"));
					ll--;
					x.css("marginTop", ll + "px");
				}
				function MarqueeRollNewsOne(){
					if(x_h + parseInt(x.css("marginTop")) <= 0){x.css("marginTop", "0px");}
					var RollTwo;
					RollTwo=setInterval(function(){
						MarqueeRollNews();
						var t = parseInt(x.css("marginTop"));
						if(t%84 == 0){clearInterval(RollTwo);}
					}, 15);
				}
				var RollOne=setInterval(MarqueeRollNewsOne, 5000);
				x.on("mouseover", function() {clearInterval(RollOne);}); 
				x.on("mouseout", function() {RollOne=setInterval(MarqueeRollNewsOne, 5000);});
			});
			</script>
			</dd>
		</dl>
	</div>

	<div class="mfoucs left clear"><%@include file="include/foucs.jsp"%></div>

	<style type="text/css">
		.news{margin:0;height:27px;width:320px;padding:0;border-bottom:solid 1px #CCC;z-index:2;}
		.news .ytab{float:left;text-align:center;margin:0;width:84px;height:27px;line-height:27px;color:#4b619d;border-right:solid 1px #CCC;border-bottom:none;}
		.news .hover{height:28px;z-index:3;background-color:#fff;}
	</style>
	<script type="text/javascript">
	function setTaba(m, n) {
		$("div.ytab").removeClass("hover");
		$("#ytab"+n).addClass("hover");
		$("div.xtab").hide();
		$("#xtab"+n).show();
	}
	</script>
	<div class="mlist">
		<div class="news">
			<div id="ytab0" onmouseover="setTaba(2,0)" class="ytab hover">最新动态</div>
			<div id="ytab1" onmouseover="setTaba(2,1)" class="ytab">政声传递</div>
		</div>
		<div id="xtab0" class="xtab">
			<%request.setAttribute("gzdt", cms.queryList(1, 12, false, false, true, 42));%>
			<c:forEach items="${gzdt}" var="d" begin="1" end="2">
				<div class="listhot"><a target="_blank" href="${ctx}${d.url}" title="${fn:escapeXml(d.title)}"><span>${fn:escapeXml(d.title)}</span></a></div>
			</c:forEach>
			<ul class="list">
				<c:forEach items="${gzdt}" var="d"  begin="3" end="10">
				<li><a target="_blank" href="${ctx}${d.url}" title="${fn:escapeXml(d.title)}"><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li>
				</c:forEach>
				<li><a target="_blank" href="${ctx}/a/gzdt2/index.html"><span>更多>></span></a></li>
			</ul>
		</div>
		<div id="xtab1" class="xtab" style="display:none;">
			<ul id="zscd" class="list"></ul>
		</div>
		<script type="text/javascript">
		$.ajax({
			url : 'http://app.gd.gov.cn/xxts/pushinfo_json.php',
			dataType : "jsonp",
			jsonp : "pushInfoJsonpCallBack",
			jsonpCallback:"pushInfoJsonpCallBack",
			success : function(data) {
				if(data.length >= 6)
				{
					$.each(data,function(i,json){
						if(i < 6)
						{
							$("#zscd").append("<li><a target='_blank' href='"+json.link+"' title='"+json.title+"'><span>"+json.pubDate+"</span>&raquo;"+json.title+"</a></li>")
						}
					})
				}
				else
				{
					$.each(data,function(i,json){
						$("#zscd").append("<li><a target='_blank' href='"+json.link+"' title='"+json.title+"'><span>"+json.pubDate+"</span>&raquo;"+json.title+"</a></li>")
					});
				}
				$.ajax({
					url : 'http://www.gz.gov.cn/sofpro/gzyyqt/zscd/zscd_json.jsp',
					dataType : "json",
					success : function(data) {
						$.each(data,function(i,json){
							if(i < 3)
							{
								$("#zscd").append("<li><a target='_blank' href='"+json.gzurl+"' title='"+json.gztitle+"'><span>"+json.gzpubdate+"</span>&raquo;"+json.gztitle+"</a></li>")
							}
						})
					$("#zscd").append('<li><a target="_blank" href="http://www.gz.gov.cn/gzgov/zscds/zscd_list2.shtml"><span>更多>></span></a></li>');
					},
					 error:function(){}
				});
			},
			 error:function(){}
		});
		</script>
	</div>

  </div>

  <div class="vline">&nbsp;</div>
  <div class="mlogo"><span>网上办事大厅</span></div>
  <div class="box shixiang">
	<div class="guidmain">
		<div class="service">
			<div class="g_title"><img src="${ctx}/themes/images/tt1.gif"></div>
			<ul>
				<li><img width="33" height="34" src="${ctx}/themes/images/i16.gif"/><a title="公众互动帮助" href="${ctx}/a/gzhdbz/index.html">公众互动帮助</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i3.gif"/><a title="局长信箱" href="${ctx}/a/jzxx/index.html">局长信箱</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i8.gif"/><a title="投诉指南" href="http://wsbs.gz.gov.cn/gz/hotline/gotoIndex.action">投诉指南</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i1.gif"/><a title="旅游单位查询" href="http://www.gzly.gov.cn/a/smlyz/index.html" target="_blank">旅游单位查询</a></li>
			</ul>
		</div>
		<div class="service">
			<div class="g_title"><img src="${ctx}/themes/images/tt2.gif"></div>
			<ul>
				<li><img width="33" height="34" src="${ctx}/themes/images/i13.gif"/><a title="旅行社业务" href="${ctx}/a/lyqy/index.html">旅行社业务</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i14.gif"/><a title="旅游饭店星级评定" href="${ctx}/a/lyfdgl/index.html">旅游饭店星级评定</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i12.gif"/><a title="旅游区（点）质量等级评定" href="${ctx}/a/lyqy/1231.html">旅游区（点）质量等级评定</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i5.gif"/><a title="旅行社变更及注销事项备案办事指南" href="${ctx}/a/lyqy/index.html">旅行社变更及注销事项备案办事指南</a></li>
			</ul>
		</div>
		<div class="service">
			<div class="g_title"><img src="${ctx}/themes/images/tt4.gif"></div>
			<ul>
				<li><img width="33" height="34" src="${ctx}/themes/images/i15.gif"/><a title="办理导游证IC卡" href="${ctx}/a/lycyry/index.html">办理导游证IC卡</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i6.gif"/><a title="导游人员资格考试" href="${ctx}/a/lycyry/index.html">导游人员资格考试</a></li>
				<!----注释
				<li><img width="33" height="34" src="${ctx}/themes/images/i4.gif"/><a title="导游人员培训系统" href="http://www.zgrsw.cn/Elearning_lyj/Student/index.asp">导游人员培训系统</a></li>
				注释---->
				<li><img width="33" height="34" src="${ctx}/themes/images/i7.gif"/><a title="办证指南" href="${ctx}/a/lycyry/index.html">办证指南</a></li>
			</ul>
		</div>
		<div class="service">
			<div class="g_title"><img src="${ctx}/themes/images/tt3.gif"></div>
			<ul>
				<li><img width="33" height="34" src="${ctx}/themes/images/i17.gif"/><a title="工作动态" href="${ctx}/a/gzdt2/index.html">工作动态</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i2.gif"/><a title="相关法规标准" href="${ctx}/a/lyfggz/index.html">相关法规标准</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i16.gif"/><a title="征集调查" href="${ctx}/a/gkyjx/index.html">公开意见征集</a></li>
				<li><img width="33" height="34" src="${ctx}/themes/images/i11.gif"/></li>
			</ul>
		</div>
	</div>
  </div>
  <div class="vline">&nbsp;</div>
  <%@include file="include/index_middle.jsp"%>
</div>

<div class="side right">
  <%@include file="include/index_right.jsp"%>
</div>

</div>
<div class="w990 clearfix">
<%@include file="include/index_links.jsp"%>
</div>
	
<%@include file="include/footer.jsp"%>
</body>
</html>
