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
<title>${category.name} - ${site.name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/web.css"/>
<meta name="keywords" content="${category.metakeywords}"/>
<meta name="description" content="${category.metadescription}"/>
</head>
<body>
<%@include file="include/header.jsp"%>
<div class="container w990 clear">
  <div class="gk w240 left">
	<%@include file="include/tree.jsp"%>
  </div>
  <div class="w735 right">
	<div class="listpage">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <%request.setAttribute("clist", cms.queryCategory(String.valueOf(request.getAttribute("categoryid"))));%>
	  <c:forEach items="${clist}" var="c">
		<c:set var="ccid" value="${c.id}" scope="request" />
		<c:set var="cname" value="${c.name}" scope="request" />
		<c:set var="curl" value="${c.url}" scope="request" />
		<c:set var="cstatus" value="${c.status}" scope="request" />
		<%request.setAttribute("vlist", cms.queryList(1, 8, false, false, true, request.getAttribute("ccid")));%>
		<div class="list">
			<dl class="logo"><dt>${cname}</dt><dd><a class="more"${cstatus==2?' target="_blank"':''} href="<c:if test="${cstatus != 2}">${ctx}</c:if>${curl}">&raquo; 更多</a></dd></dl>
			<dl class="title"><dt>标题</dt><dd>发布日期</dd></dl>
			<c:forEach items="${vlist}" var="d">
			<dl><dt><a target="_blank" href="${ctx}${d.url}">${d.title}</a></dt><dd>${d.releasetime}</dd></dl>
			</c:forEach>
		</div>
		<div class="vline">&nbsp;</div>
	  </c:forEach>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
</body>
</html>