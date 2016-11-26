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
<script type="text/javascript" src="${ctx}/js/tv/swfobject.js"></script>
<div class="w990 clear">
  <div class="w990">
	<div class="listpage">
	  <div class="logo">&nbsp;&nbsp;当前位置：${category.name}</div>
	  <div class="list">
		<c:forEach items="${datalist}" var="d">
		<div style="border-top:1px dashed #ddd;padding:10px;height:145px;">
			<dt id="xcsp${d.id}" style="width:240px;height:135px;padding:5px;margin-left:150px;"></dt>
			<dt style="margin-left:15px;"><a target="_blank" href="${ctx}${d.url}">${d.title}</a></dt>
			<dt style="margin-left:15px;">${d.releasetime}</dt>
			<script type="text/javascript">
				var so = new SWFObject("${ctx}/js/tv/CuPlayerMiniV20_Black_S.swf","xcsp${d.id}","220","135","9","#000000");
				so.addParam("allowfullscreen","true");
				so.addParam("allowscriptaccess","always");
				so.addParam("wmode","opaque");
				so.addParam("quality","high");
				so.addParam("salign","lt");
				so.addVariable("CuPlayerFile","${d.releasesource}");
				so.addVariable("CuPlayerImage","${d.img}");
				so.addVariable("CuPlayerShowImage","true");
				so.addVariable("CuPlayerWidth","220");
				so.addVariable("CuPlayerHeight","135");
				so.addVariable("CuPlayerAutoPlay","false");
				so.addVariable("CuPlayerAutoRepeat","true");
				so.addVariable("CuPlayerShowControl","true");
				so.addVariable("CuPlayerAutoHideControl","false");
				so.addVariable("CuPlayerAutoHideTime","6");
				so.addVariable("CuPlayerVolume","80");
				so.write("xcsp${d.id}");	
			</script>
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