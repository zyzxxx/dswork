<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%request.setAttribute("list", cms.queryCategory(""));%>
<div class="vname">&nbsp;&nbsp;信息公开</div>
<div class="vbox">
<ul>
<c:forEach items="${list}"    var="d"  ><li><a${d.id==category.id?' class="selected"':''}${d.status==2?' target="_blank;"':''}<c:if test="${d.list==null || fn:length(d.list)==0}"> href="${d.status==2?'':ctx}${d.url}"</c:if>>&raquo;&nbsp;${d.name}</a></li>
<c:forEach items="${d.list}"  var="dd" ><li><a class="${dd.id==category.id?'selected ':''}sm"${dd.status==2?' target="_blank;"':''}<c:if test="${dd.list==null || fn:length(dd.list)==0}"> href="${dd.status==2?'':ctx}${dd.url}"</c:if>>&raquo;&nbsp;${dd.name}</a></li>
<c:forEach items="${dd.list}" var="ddd"><li><a class="${ddd.id==category.id?'selected ':''}sm"${ddd.status==2?' target="_blank"':''}<c:if test="${ddd.list==null || fn:length(ddd.list)==0}"> href="$${ddd.status==2?'':ctx}${ddd.url}"</c:if>>&nbsp;&nbsp;&raquo;&nbsp;${ddd.name}</a></li>
</c:forEach>
</c:forEach>
</c:forEach>
</ul>
</div>