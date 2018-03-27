<%@page language="java" pageEncoding="UTF-8"%>
<c:if test="${categoryparent == null}"><%--category.id==61 or category.id==62 or category.id==63 or category.id==85 or --%>
	<c:if test="${category.id==68 or category.id==77 or category.id==81}">
		<c:set var="xpid" value="${category.id}" scope="request" />
	</c:if>
</c:if>
<c:if test="${categoryparent != null}">
	<%--<c:set var="xpid" value="${categoryparent.id}" scope="request" /><c:set var="xname" value="${categoryparent.name}" scope="request" />--%><%--所有栏目(除办事大厅、重点领域、公众互动、其他版块和其子栏目外)--%>
	<c:if test="${category.id >= 68 and category.id <=76}"><c:set var="xpid" value="68" scope="request" /></c:if><%--公众互动及子栏目--%>
	<c:if test="${category.id >= 77 and category.id <=80}"><c:set var="xpid" value="77" scope="request" /></c:if><%--办事大厅及子栏目--%>
	<c:if test="${category.id >= 81 and category.id <=83}"><c:set var="xpid" value="81" scope="request" /></c:if><%--重点领域及子栏目--%>
	<%--<c:if test="${category.id >= 85 and category.id <=94}"><c:set var="xpid" value="85" scope="request" /></c:if>--%><%--其他版块及子栏目--%>
</c:if>
<c:if test="${xpid == null}"><c:set var="xpid" value="63" scope="request" /></c:if>



<c:if test="${xpid == 63}"><c:set var="xname" value="政务公开" scope="request" /></c:if>
<c:if test="${xpid == 68}"><c:set var="xname" value="公众互动" scope="request" /></c:if>
<c:if test="${xpid == 77}"><c:set var="xname" value="办事大厅" scope="request" /></c:if>
<c:if test="${xpid == 81}"><c:set var="xname" value="重点领域" scope="request" /></c:if>
<%--<c:if test="${xpid == 85}"><c:set var="xname" value="其他版块" scope="request" /></c:if>--%>



<div class="vname">&nbsp;&nbsp;${xname}</div>
<div class="vbox">
<ul>
	<c:if test="${xpid == 63}">
		<li><a${61==category.id?' class="selected"':''} href="${ctx}/a/gkzn/index.html">&raquo;&nbsp;公开指南</a></li>
		<li><a${62==category.id?' class="selected"':''} target="_bank" href="${ctx}/a/ysqgk/index.html">&raquo;&nbsp;依申请公开</a></li>
	</c:if>
	<c:if test="${xpid == 81}">
		<li><a target="_bank" href="${ctx}/a/zzjg/index.html">&raquo;&nbsp;领导之窗</a></li>
		<%request.setAttribute("list_xzzf", cms.queryCategory("28"));%><%--行政执法--%>
		<li><a>&raquo;&nbsp;行政执法</a></li>
		<c:forEach items="${list_xzzf}"  var="dd" ><li><a target="_bank" class="${dd.id==category.id?'selected ':''}sm"${dd.scope==2?' target="_blank"':''}<c:if test="${dd.list==null || fn:length(dd.list)==0}"> href="${dd.scope==2?'':ctx}${dd.url}"</c:if>>&raquo;&nbsp;${dd.name}</a></li>
		<c:forEach items="${dd.list}" var="ddd"><li><a target="_bank" class="${ddd.id==category.id?'selected ':''}sm"${ddd.scope==2?' target="_blank"':''}<c:if test="${ddd.list==null || fn:length(ddd.list)==0}"> href="${ddd.scope==2?'':ctx}${ddd.url}"</c:if>>&nbsp;&nbsp;&raquo;&nbsp;${ddd.name}</a></li>
		</c:forEach>
		</c:forEach>
		<li><a target="_bank" href="${ctx}/a/czyjs/index.html">&raquo;&nbsp;财政预决算</a></li>
	</c:if>
	
	<%request.setAttribute("list", cms.queryCategory(request.getAttribute("xpid")));%>
	<c:forEach items="${list}"    var="d"  ><li><a${d.id==category.id?' class="selected"':''}${d.scope==2?' target="_blank"':''}<c:if test="${d.list==null || fn:length(d.list)==0}"> href="${d.scope==2?'':ctx}${d.url}"</c:if>>&raquo;&nbsp;${d.name}</a></li>
	<c:forEach items="${d.list}"  var="dd" ><li><a class="${dd.id==category.id?'selected ':''}sm"${dd.scope==2?' target="_blank"':''}<c:if test="${dd.list==null || fn:length(dd.list)==0}"> href="${dd.scope==2?'':ctx}${dd.url}"</c:if>>&raquo;&nbsp;${dd.name}</a></li>
	<c:forEach items="${dd.list}" var="ddd"><li><a class="${ddd.id==category.id?'selected ':''}sm"${ddd.scope==2?' target="_blank"':''}<c:if test="${ddd.list==null || fn:length(ddd.list)==0}"> href="${ddd.scope==2?'':ctx}${ddd.url}"</c:if>>&nbsp;&nbsp;&raquo;&nbsp;${ddd.name}</a></li>
	</c:forEach>
	</c:forEach>
	</c:forEach>
</ul>
</div>
