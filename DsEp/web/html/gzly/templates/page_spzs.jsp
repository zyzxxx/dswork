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
<title>${fn:escapeXml(title)}-${category.name}-${site.name}</title>
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
	<div class="listpage hei1 view">
		<div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
		<div class="title">
			${title}
			<div class="subtitle">${releasetime}</div>
		</div>
		<div class="content">
			<dt class="flowplayer" data-swf="${ctx}/js/tv/flowplayer.swf" style="width:480px;height:270px;padding:5px;margin-left:150px;"><video><source type="video/flv" src="${releasesource}"></video></dt>
		</div>
	</div>
  </div>
</div>
<%@include file="include/footer.jsp"%>
</body>
</html>
