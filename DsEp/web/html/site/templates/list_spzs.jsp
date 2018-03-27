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
<link rel="stylesheet" href="${ctx}/js/tv/skin/skin.css">
<script type="text/javascript" src="${ctx}/js/tv/flowplayer.min.js"></script>
<div class="container w990 clear">
  <div class="w990">
	<div class="listpage">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="list">
		<c:forEach items="${datalist}" var="d">
		<div style="border-top:1px dashed #ddd;padding:10px;height:145px;">
			<dt class="flowplayer" data-swf="${ctx}/js/tv/flowplayer.swf" style="width:240px;height:135px;padding:5px;margin-left:150px;"><video><source type="video/flv" src="${d.releasesource}"></video></dt>
			<dt style="margin-left:15px;"><a target="_blank" href="${ctx}${d.url}">${d.title}</a></dt>
			<dt style="margin-left:15px;">${d.releasetime}</dt>
		</div>
		</c:forEach>
	  </div>
	  <div class="page"><%@include file="include/pageview.jsp"%></div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
</body>
</html>
