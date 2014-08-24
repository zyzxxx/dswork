<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
$(function(){
	$dswork.page.menu("delDsCmsPage.htm", "updDsCmsPage1.htm", "getDsCmsPageById.htm", "${pageModel.currentPage}");
});
$dswork.doAjax = true;
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getDsCmsPage.htm?page=${pageModel.currentPage}";
}};
</script>
</head> 
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">网页文章列表</td>
		<td class="menuTool">
			<a class="insert" href="addDsCmsPage1.htm?page=${pageModel.currentPage}">添加</a>
			<a class="delete" id="listFormDelAll" href="#">删除所选</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="queryForm" method="post" action="getDsCmsPage.htm">
<table border="0" cellspacing="0" cellpadding="0" class="queryTable">
	<tr>
		<td class="input">
			&nbsp;企业编码：<input type="text" class="text" name="qybm" value="${fn:escapeXml(param.qybm)}" />
			&nbsp;栏目ID：<input type="text" class="text" name="categoryid" value="${fn:escapeXml(param.categoryid)}" />
			&nbsp;标题：<input type="text" class="text" name="title" value="${fn:escapeXml(param.title)}" />
			&nbsp;关键词：<input type="text" class="text" name="keywords" value="${fn:escapeXml(param.keywords)}" />
			&nbsp;摘要：<input type="text" class="text" name="summary" value="${fn:escapeXml(param.summary)}" />
			&nbsp;内容：<input type="text" class="text" name="content" value="${fn:escapeXml(param.content)}" />
			&nbsp;创建时间：<input type="text" class="text" name="createtime" value="${fn:escapeXml(param.createtime)}" />
			&nbsp;图片：<input type="text" class="text" name="img" value="${fn:escapeXml(param.img)}" />
			&nbsp;焦点图(0否，1是)：<input type="text" class="text" name="imgtop" value="${fn:escapeXml(param.imgtop)}" />
			&nbsp;首页推荐(0否，1是)：<input type="text" class="text" name="pagetop" value="${fn:escapeXml(param.pagetop)}" />
			&nbsp;网站模板：<input type="text" class="text" name="viewsite" value="${fn:escapeXml(param.viewsite)}" />
			&nbsp;APP模板：<input type="text" class="text" name="viewapp" value="${fn:escapeXml(param.viewapp)}" />
		</td>
		<td class="query"><input id="_querySubmit_" type="button" class="button" value="查询" /></td>
	</tr>
</table>
</form>
<div class="line"></div>
<form id="listForm" method="post" action="delDsCmsPage.htm">
<table id="dataTable" border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr class="list_title">
		<td style="width:2%"><input id="chkall" type="checkbox" /></td>
		<td style="width:5%">操作</td>
		<td>企业编码</td>
		<td>栏目ID</td>
		<td>标题</td>
		<td>关键词</td>
		<td>摘要</td>
		<td>内容</td>
		<td>创建时间</td>
		<td>图片</td>
		<td>焦点图(0否，1是)</td>
		<td>首页推荐(0否，1是)</td>
		<td>网站模板</td>
		<td>APP模板</td>
	</tr>
<c:forEach items="${pageModel.result}" var="d">
	<tr>
		<td><input name="keyIndex" type="checkbox" value="${d.id}" /></td>
		<td class="menuTool" keyIndex="${d.id}">&nbsp;</td>
		<td>${fn:escapeXml(d.qybm)}</td>
		<td>${fn:escapeXml(d.categoryid)}</td>
		<td>${fn:escapeXml(d.title)}</td>
		<td>${fn:escapeXml(d.keywords)}</td>
		<td>${fn:escapeXml(d.summary)}</td>
		<td>${fn:escapeXml(d.content)}</td>
		<td>${fn:escapeXml(d.createtime)}</td>
		<td>${fn:escapeXml(d.img)}</td>
		<td>${fn:escapeXml(d.imgtop)}</td>
		<td>${fn:escapeXml(d.pagetop)}</td>
		<td>${fn:escapeXml(d.viewsite)}</td>
		<td>${fn:escapeXml(d.viewapp)}</td>
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
