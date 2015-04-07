<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
$(function(){
	$dswork.page.menu("delDsBbsPage.htm", "updDsBbsPage1.htm", "getDsBbsPageById.htm", "${pageModel.currentPage}");
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getDsBbsPage.htm?page=${pageModel.currentPage}";
}};
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">主题列表</td>
		<td class="menuTool">
			<a class="insert" href="addDsBbsPage1.htm?page=${pageModel.currentPage}">添加</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getDsBbsPage.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;站点ID：<input type="text" class="text" name="siteid" value="${fn:escapeXml(param.siteid)}" />
			&nbsp;版块ID：<input type="text" class="text" name="forumid" value="${fn:escapeXml(param.forumid)}" />
			&nbsp;发表人ID：<input type="text" class="text" name="userid" value="${fn:escapeXml(param.userid)}" />
			&nbsp;标题：<input type="text" class="text" name="title" value="${fn:escapeXml(param.title)}" />
			&nbsp;链接：<input type="text" class="text" name="url" value="${fn:escapeXml(param.url)}" />
			&nbsp;摘要：<input type="text" class="text" name="summary" value="${fn:escapeXml(param.summary)}" />
			&nbsp;精华(0否，1是)：<input type="text" class="text" name="isessence" value="${fn:escapeXml(param.isessence)}" />
			&nbsp;置顶(0否，1是)：<input type="text" class="text" name="istop" value="${fn:escapeXml(param.istop)}" />
			&nbsp;meta关键词：<input type="text" class="text" name="metakeywords" value="${fn:escapeXml(param.metakeywords)}" />
			&nbsp;meta描述：<input type="text" class="text" name="metadescription" value="${fn:escapeXml(param.metadescription)}" />
			&nbsp;发表人：<input type="text" class="text" name="releaseuser" value="${fn:escapeXml(param.releaseuser)}" />
			&nbsp;发表时间：<input type="text" class="text" name="releasetime" value="${fn:escapeXml(param.releasetime)}" />
			&nbsp;结贴时间：<input type="text" class="text" name="overtime" value="${fn:escapeXml(param.overtime)}" />
			&nbsp;最后回复人：<input type="text" class="text" name="lastuser" value="${fn:escapeXml(param.lastuser)}" />
			&nbsp;最后回复时间：<input type="text" class="text" name="lasttime" value="${fn:escapeXml(param.lasttime)}" />
			&nbsp;点击量：<input type="text" class="text" name="numpv" value="${fn:escapeXml(param.numpv)}" />
			&nbsp;回贴数：<input type="text" class="text" name="numht" value="${fn:escapeXml(param.numht)}" />
			&nbsp;内容：<input type="text" class="text" name="content" value="${fn:escapeXml(param.content)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delDsBbsPage.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td>站点ID</td>
		<td>版块ID</td>
		<td>发表人ID</td>
		<td>标题</td>
		<td>链接</td>
		<td>摘要</td>
		<td>精华(0否，1是)</td>
		<td>置顶(0否，1是)</td>
		<td>meta关键词</td>
		<td>meta描述</td>
		<td>发表人</td>
		<td>发表时间</td>
		<td>结贴时间</td>
		<td>最后回复人</td>
		<td>最后回复时间</td>
		<td>点击量</td>
		<td>回贴数</td>
		<td>内容</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td>${fn:escapeXml(d.siteid)}</td>
		<td>${fn:escapeXml(d.forumid)}</td>
		<td>${fn:escapeXml(d.userid)}</td>
		<td>${fn:escapeXml(d.title)}</td>
		<td>${fn:escapeXml(d.url)}</td>
		<td>${fn:escapeXml(d.summary)}</td>
		<td>${fn:escapeXml(d.isessence)}</td>
		<td>${fn:escapeXml(d.istop)}</td>
		<td>${fn:escapeXml(d.metakeywords)}</td>
		<td>${fn:escapeXml(d.metadescription)}</td>
		<td>${fn:escapeXml(d.releaseuser)}</td>
		<td>${fn:escapeXml(d.releasetime)}</td>
		<td>${fn:escapeXml(d.overtime)}</td>
		<td>${fn:escapeXml(d.lastuser)}</td>
		<td>${fn:escapeXml(d.lasttime)}</td>
		<td>${fn:escapeXml(d.numpv)}</td>
		<td>${fn:escapeXml(d.numht)}</td>
		<td>${fn:escapeXml(d.content)}</td>
	</tr>
</c:forEach>
</table>
<input name="page" type="hidden" value="${pageModel.currentPage}" />
</form>
<table border="0" cellspacing="0" cellpadding="0" class="bottomTable">
	<tr><td>${pageNav.page}</td></tr>
</table>
</body>
</html>
