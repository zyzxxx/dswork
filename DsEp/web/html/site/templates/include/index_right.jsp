<%@page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/js/tv/skin/skin.css">
<!--7.0.4-->
<script type="text/javascript" src="${ctx}/js/tv/flowplayer.min.js"></script>

<%--旅游提示
<%
//旅游提示87
request.setAttribute("lyts", cms.queryList(1, 4, false, false, true, 87));
%>
<div class="tipslogo">&nbsp;</div>
<div class="redbox r_tips">
	<ul class="list">
		<c:forEach items="${lyts}" var="d"><li><a target="_blank" href="${ctx}${d.url}">&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
	</ul>
</div>
<div class="vline">&nbsp;</div>
--%>

<%--
 <div class="flowplayer" data-swf="${ctx}/js/tv/flowplayer.swf" style="width: 240px; height: 180px;">
	<video>
		<source type="video/flv" src="http://www.visitgz.gov.cn/zt/lizhuo/flv/201702151710fqztc.flv">
	</video>
</div>
--%>

<%--宣传视频--%>
<%
//视频展示
request.setAttribute("xcsp_top", cms.queryList(1, 1, false, true, true, 94));
request.setAttribute("xcsp", cms.queryList(1, 3, false, false, true, 94));
%>
<div class="r_logo"><span style="float:right;"><a href="${ctx}/a/spzs/index.html" style="font-size:12px;font-weight:100;color:#fff;margin-right:10px;" target="_blank">更多&gt;&gt;</a></span><span>宣传视频</span></div>
<div class="box" style="text-align:center;">
	<c:forEach items="${xcsp_top}" var="d">
 	<div class="flowplayer" data-swf="${ctx}/js/tv/flowplayer.swf" style="width: 240px; height: 180px;padding-top:10px;">
		<video>
			<source type="video/flv" src="${d.releasesource}">
		</video>
	</div>
	</c:forEach>
	<ul class="list">
	<c:forEach items="${xcsp}" var="d">
		<li style="text-align:left;border-bottom:1px dashed #ddd;border-top:1px dashed #ddd;"><a target="_blank" href="${ctx}${d.url}">&raquo; ${fn:escapeXml(d.title)}</a></li>
	</c:forEach>
	</ul>
</div>
<%----%>

<div class="r_wsbs">
	<div class="vline">&nbsp;</div>
	<a target="_blank" href="${ctx}/a/jzxx/index.html"><img src="${ctx}/themes/images/jzxx.png"></a>
	<a target="_blank" href="${ctx}/a/gkyjx/index.html"><img src="${ctx}/themes/images/gkyjx.png"></a>
 	<a target="_blank" href="http://wsbs.gz.gov.cn/gz/zwgk/zqqd.jsp?orgId=10052"><img src="${ctx}/themes/images/ad_qzqd.jpg"></a>
	<a target="_blank" href="http://wsbs.gz.gov.cn/gz/index.jsp"><img src="${ctx}/themes/images/ad_bsdt.jpg"></a>
	<a target="_blank" href="http://www.visitgz.gov.cn/zt/lyzt70.html"><img src="${ctx}/themes/images/zwgkml.png"></a>
</div>

<%--信息公开--%>
<%----%>
<div class="vline">&nbsp;</div>
<div class="gk">
	<%@include file="tree.jsp"%>
</div>


<%--系统图标--%>
<%--
<div class="vline">&nbsp;</div>
<div class="r_wsbs">
	<a target="_blank" href="http://121.8.226.113/gzsso/login.jsp?v=2&serviceURL=%2Fpt"><img src="${ctx}/themes/images/s_1.jpg"></a>
	<a target="_blank" href="http://121.8.226.113/ndtj/enterprise/login.html"><img src="${ctx}/themes/images/s_2.jpg"></a>
	<a target="_blank" href="http://tj.gzly.gov.cn/"><img src="${ctx}/themes/images/s_4.jpg"></a>
	<a target="_blank" href="http://daoyou-chaxun.cnta.gov.cn/single_info/selectlogin_1.asp"><img src="${ctx}/themes/images/s_5.jpg"></a>
	<a target="_blank" href="http://112.124.51.37/login.jsp"><img src="${ctx}/themes/images/s_7.jpg"></a>
	<a target="_blank" href="http://www.visitgz.gov.cn/channels/54.html"><img src="${ctx}/themes/images/s_8.jpg"></a>
</div>
--%>

<%--
<div class="vline">&nbsp;</div>
<div class="r_logo"><span>出行助手</span></div>
<div class="box">
	<div class="vline">&nbsp;</div>
	<ul class="r_zhushou">
		<li class="L1"><a href="http://www.gdta.gov.cn/gzdt/lycs/" title="旅游常识" target="_blank">旅游常识</a></li>
		<li class="L2"><a href="http://www.gdta.gov.cn/xxgk/fggw/" title="法规公文" target="_blank">旅游法规</a></li>
		<li class="L3"><a href="#">电子地图</a></li>
		<li class="L4"><a target="_blank" href="http://www.weather.com.cn/">天气预报</a></li>
		<li class="L5"><a href="#">旅行社</a></li>
		<li class="L6"><a href="#">酒店宾馆</a></li>
		<li class="L7"><a target="_blank" href="http://www.gdcx.gov.cn/web/gov/share/BigMap.aspx?lat=22.836945920943854&lng=113.455810546875&z=7">交通信息</a></li>
		<li class="L8"><a href="http://www.gdta.gov.cn/cyhm/" title="常用号码" target="_blank">常用号码</a></li>
	</ul>
	<div class="vline">&nbsp;</div>
</div>
--%>


<%--在线调查--%>
<div id="zxdc"></div>
<script type="text/javascript">
$.getJSON('/zw/zwclient/survey/surveyJsonAction.jsp', function(data){
	var result = data.result;
	var zxdc="";
	for(var i = 0; i < result.length; i++){
		var d = result[i];
		var t;
		if(i == 0){
			t = "<div class=\"vline\">&nbsp;</div><a style=\"text-decoration: none;\" onclick=\"showLogo("+i+");return false;\" href=\"#\"><div id=\"logo"+(i+1)+"\" class=\"r_logo\"><span>在线调查"+(i+1)+"</span></div></a><div style=\"display:;\" id=\"box"+(i+1)+"\" class=\"box\"><form style=\"padding-left:10px;\" method=\"post\" action=\"zw/zwclient/survey/surveyAction.jsp?keyIndex="+d.id+"\">";
		}else{
			t = "<div class=\"vline\">&nbsp;</div><a style=\"text-decoration: none;\" onclick=\"showLogo("+i+");return false;\" href=\"#\"><div id=\"logo"+(i+1)+"\" class=\"r_logo\"><span>在线调查"+(i+1)+"</span></div></a><div style=\"display: none;\" id=\"box"+(i+1)+"\" class=\"box\"><form style=\"padding-left:10px;\" method=\"post\" action=\"zw/zwclient/survey/surveyAction.jsp?keyIndex="+d.id+"\">";
		}
		
		t = t + "<ul style=\"list-style:none;\"><h4>"+d.title+"</h4>";
		if(d.option1 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice1\" name=\"choice\" value=\"1\"/>&nbsp;&nbsp;"+d.option1+"</li>";
		}
		if(d.option2 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice2\" name=\"choice\" value=\"2\"/>&nbsp;&nbsp;"+d.option2+"</li>";
		}
		if(d.option3 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice3\" name=\"choice\" value=\"3\"/>&nbsp;&nbsp;"+d.option3+"</li>";
		}
		if(d.option4 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice4\" name=\"choice\" value=\"4\"/>&nbsp;&nbsp;"+d.option4+"</li>";
		}
		if(d.option5 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice5\" name=\"choice\" value=\"5\"/>&nbsp;&nbsp;"+d.option5+"</li>";
		}
		if(d.option6 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice6\" name=\"choice\" value=\"6\"/>&nbsp;&nbsp;"+d.option6+"</li>";
		}
		if(d.option7 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice7\" name=\"choice\" value=\"7\"/>&nbsp;&nbsp;"+d.option7+"</li>";
		}
		if(d.option8 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice8\" name=\"choice\" value=\"8\"/>&nbsp;&nbsp;"+d.option8+"</li>";
		}
		if(d.option9 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice9\" name=\"choice\" value=\"9\"/>&nbsp;&nbsp;"+d.option9+"</li>";
		}
		if(d.option10 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice10\" name=\"choice\" value=\"10\"/>&nbsp;&nbsp;"+d.option10+"</li>";
		}
		if(d.option11 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice11\" name=\"choice\" value=\"11\"/>&nbsp;&nbsp;"+d.option11+"</li>";
		}
		if(d.option12 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice12\" name=\"choice\" value=\"12\"/>&nbsp;&nbsp;"+d.option12+"</li>";
		}
		if(d.option13 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice13\" name=\"choice\" value=\"13\"/>&nbsp;&nbsp;"+d.option13+"</li>";
		}
		if(d.option14 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice14\" name=\"choice\" value=\"14\"/>&nbsp;&nbsp;"+d.option14+"</li>";
		}
		if(d.option15 != ""){
			t = t + "<li><input type=\"radio\" id=\"choice15\" name=\"choice\" value=\"15\"/>&nbsp;&nbsp;"+d.option15+"</li>";
		}
		t = t+"</ul><br/><input type=\"submit\" style=\"width: 60px;height:25px;background-color:#0192D0;border:0px;border-radius:2px;color:#fff;\" value=\"投票\"/>  <a style=\"text-decoration:none;color:#da3b01;\" href=\"${ctx}/a/dcjg/index.html?keyIndex="+d.id+"&a="+(new Date().getTime())+"\"\">查看结果</a></form><div class=\"vline\">&nbsp;</div></div>";
		zxdc = zxdc + t;
	}
	document.getElementById('zxdc').innerHTML = zxdc;
});
function showLogo(d){
	if(d == 0){
		document.getElementById("box1").style.display="";
		document.getElementById("box2").style.display="none";
		document.getElementById("box3").style.display="none";
	}else if(d == 1){
		document.getElementById("box1").style.display="none";
		document.getElementById("box2").style.display="";
		document.getElementById("box3").style.display="none";
	}else if(d == 2){
		document.getElementById("box1").style.display="none";
		document.getElementById("box2").style.display="none";
		document.getElementById("box3").style.display="";
	}
}
</script>
