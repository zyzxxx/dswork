<%@page language="java" pageEncoding="UTF-8"%>
<%
//通知公告59
request.setAttribute("tzgg", cms.queryList(1, 10, false, false, true, 59));
//办事指南--旅游企业38
request.setAttribute("lyqy", cms.queryList(1, 10, false, false, true, 38));
//法律法规文件19
request.setAttribute("lyfggz", cms.queryList(1, 10, false, false, true, 19));
//行业管理----旅行社管理25
request.setAttribute("lxsgl", cms.queryList(1, 10, false, false, true, 25));
//财政预算45
request.setAttribute("czys", cms.queryList(1, 5, false, false, true, 45));
//人大建议52
request.setAttribute("rdjy", cms.queryList(1, 5, false, false, true, 52));
%>
<%--
<div class="w360 left">
	<a target="_blank" href="#"><img style="width:360px;height:79px;"="w360" src="${ctx}/f/res/ad/ad_360x79_left.jpg"/></a>
</div>
<div class="w360 right">
	<a target="_blank" href="#"><img style="width:360px;height:79px;" src="${ctx}/f/res/ad/ad_360x79_right.jpg"/></a>
</div>
--%>
<div class="w360 left">
	<a target="_blank" href="http://121.8.226.113/gzsso/login.jsp?v=2&serviceURL=%2Fpt"><img style="width:360px;height:79px;"="w360" src="${ctx}/themes/images/ad_360x79_1.jpg"/></a>
</div>
<div class="w360 right">
	<a target="_blank" href="http://121.8.226.113/ndtj/enterprise/login.html"><img style="width:360px;height:79px;" src="${ctx}/themes/images/ad_360x79_2.jpg"/></a>
</div>
<div class="w360 left">
	<a target="_blank" href="http://www.gzlytj.com/Note/note_list.aspx"><img style="width:360px;height:79px;"="w360" src="${ctx}/themes/images/ad_360x79_3.jpg"/></a>
</div>
<div class="w360 right">
	<a target="_blank" href="http://daoyou-chaxun.cnta.gov.cn/single_info/selectlogin_1.asp"><img style="width:360px;height:79px;" src="${ctx}/themes/images/ad_360x79_4.jpg"/></a>
</div>
<div class="w360 left">
	<a target="_blank" href="http://112.124.51.37/login.jsp"><img style="width:360px;height:79px;"="w360" src="${ctx}/themes/images/ad_360x79_5.jpg"/></a>
</div>
<div class="w360 right">
	<a target="_blank" href="http://www.visitgz.com/channels/54.html"><img style="width:360px;height:79px;" src="${ctx}/themes/images/ad_360x79_6.jpg"/></a>
</div>
<div class="vline">&nbsp;</div>
<div class="w360 left">
	<div class="mlogo"><a href="${ctx}/a/tzgsgg/index.html" target="_blank">更多&gt;&gt;</a><span>通知公告</span></div>
	<div class="box mheight" style="height:280px;">
		<ul class="list">
			<c:forEach items="${tzgg}" var="d"><li><a target="_blank" href="${ctx}${d.url}"><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>
<div class="w360 right">
	<div class="mlogo"><a href="${ctx}/a/bszn/index.html" target="_blank">更多&gt;&gt;</a><span>办事指南</span></div>
	<div class="box mheight" style="height:280px;">
		<ul class="list">
			<c:forEach items="${lyqy}" var="d"><li><a target="_blank" href="${ctx}${d.url}"><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>

<div class="vline">&nbsp;</div>
<div class="w360 left">
	<div class="mlogo"><a href="${ctx}/a/lyfggz/index.html" target="_blank">更多&gt;&gt;</a><span>法规文件</span></div>
	<div class="box mheight" style="height:280px;">
		<ul class="list">
			<c:forEach items="${lyfggz}" var="d"><li><a target="_blank" href="${ctx}${d.url}"><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>
<div class="w360 right">
	<div class="mlogo"><a href="${ctx}/a/qiwj/index.html" target="_blank">更多&gt;&gt;</a><span>行业管理</span></div>
	<div class="box mheight" style="height:280px;">
		<ul class="list">
			<c:forEach items="${lxsgl}" var="d"><li><a target="_blank" href="${ctx}${d.url}" ><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>

<div class="vline">&nbsp;</div>
<div class="w360 left">
	<div class="mlogo"><a href="${ctx}/a/czys/index.html" target="_blank">更多&gt;&gt;</a><span>财政预算</span></div>
	<div class="box mheight">
		<ul class="list">
			<c:forEach items="${czys}" var="d"><li><a target="_blank" href="${ctx}${d.url}"><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>
<div class="w360 right">
	<div class="mlogo"><a href="${ctx}/a/rdjy/index.html" target="_blank">更多&gt;&gt;</a><span>人大建议</span></div>
	<div class="box mheight">
		<ul class="list">
			<c:forEach items="${rdjy}" var="d"><li><a target="_blank" href="${ctx}${d.url}" ><span>${fn:substring(d.releasetime, 0, 10)}</span>&raquo; ${fn:escapeXml(d.title)}</a></li></c:forEach>
		</ul>
	</div>
</div>

<div class="vline">&nbsp;</div>
<div class="w735">
	<a target="_blank" href="http://www.visitgz.com/zt/lyzt49.html">
	<img class="w735" src="http://www.visitgz.gov.cn/zt/lizhuo/images49/images/index_49.jpg"/>
	</a>
</div>
<div class="w735">
 	<a target="_blank" href="http://www.visitgz.com/zt/lyzt47.html">
	<img class="w735" src="http://www.visitgz.gov.cn/zt/lizhuo/images47/index_0103.jpg"/>
	</a>
</div>
<div class="w735">
	<a target="_blank" href="http://www.visitgz.com/zt/lyzt25.html">
	<img class="w735" src="${ctx}/themes/images/index_07.jpg"/>
	</a>
</div>
<div class="w735">
	<a target="_blank" href="http://www.visitgz.com/zt/lyzt12.html">
	<img class="w735" src="${ctx}/themes/images/index_12.jpg"/>
	</a>
</div>
