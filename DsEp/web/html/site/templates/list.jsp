<%@page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%common.cms.CmsFactory cms = (common.cms.CmsFactory)request.getAttribute("cms");%>
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
<div class="w990 clear">
  <div class="gk w240 left">
	<%@include file="include/tree.jsp"%>
  </div>
  <div class="w735 right">
	<div class="listpage hei1">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="list">
		<dl class="title"><dt>标题</dt><dd>发布日期</dd></dl>
		<c:forEach items="${datalist}" var="d">
		<dl><dt><a href="${ctx}${d.url}">${d.title}</a></dt><dd>${d.releasetime}</dd></dl>
		</c:forEach>
	  </div>
	  <div class="page">
		${datapageview}
		
		<br /><br /><br />

		<a${datapage.page == 1?' class="selected"':''}<c:if test="${datapage.page == 1}"> onclick="return false;"</c:if> href="${ctx}/${datapage.firsturl}">1</a>
		<c:if test="${datapage.page - 3 > 2 && datapage.page - 3 < datapage.last - 3}"><a href="${ctx}/${datauri}_${datapage.page - 4}.html">...</a></c:if>
		<c:forEach var="i" begin="1" end="${datapage.last}">
			<c:if test="${i > 1 && i < datapage.last && i >= datapage.page - 3 && i <= datapage.page + 3}"><a${datapage.page == i?' class="selected"':''} href="${ctx}/${datauri}_${i}.html">${i}</a></c:if>
		</c:forEach>
		<c:if test="${datapage.page + 3 > 2 && datapage.page + 3 < datapage.last - 3}"><a href="${ctx}/${datauri}_${datapage.page + 4}.html">...</a></c:if>
		<c:if test="${datapage.last != 1}"><a${datapage.page == datapage.last?' class="selected"':''}<c:if test="${datapage.page == datapage.last}"> onclick="return false;"</c:if> href="${ctx}/${datapage.lasturl}">${datapage.last}</a></c:if>
	  </div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
</body>
</html>