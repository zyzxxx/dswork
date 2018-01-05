<%@page language="java" pageEncoding="UTF-8" import="
java.util.Map,
java.util.HashMap,
java.util.List,
java.util.ArrayList,
java.io.File,
dswork.web.MyRequest,
dswork.spring.BeanFactory,
dswork.cms.model.DsCmsSite,
dswork.cms.service.DsCmsPageService
"%><%!
private String getCmsRoot(HttpServletRequest request)
{
	return request.getSession().getServletContext().getRealPath("/") + "/html/";
}
%><%
try
{
	MyRequest req = new MyRequest(request);
	DsCmsPageService service = (DsCmsPageService)BeanFactory.getBean("dsCmsPageService");
	Long id = req.getLong("siteid", -1), siteid = -1L;
	Map<String, Object> map = new HashMap<String, Object>();
	List<DsCmsSite> siteList = service.queryListSite(map);
	if(siteList != null && siteList.size() > 0)
	{
		request.setAttribute("siteList", siteList);
		if(id >= 0)
		{
			for(DsCmsSite m : siteList)
			{
				if(m.getId() == id)
				{
					siteid = m.getId();
					break;
				}
			}
		}
		if(siteid == -1)
		{
			siteid = siteList.get(0).getId();
		}
	}
	if(siteid >= 0)
	{
		DsCmsSite site = service.getSite(siteid);
		String filePath = getCmsRoot(request) + site.getFolder() + "/html/f/";
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		File fRoot = new File(filePath + "img/");
		if(fRoot.isDirectory())
		{
			for(File f : fRoot.listFiles())
			{
				if(f.isDirectory())
				{
					Map<String, String> m = new HashMap<String, String>();
					m.put("path", f.getPath().replace(f.getParent(), ""));
					m.put("name", f.getName());
					list.add(m);
				}
			}
		}
		request.setAttribute("list", list);
		
		List<Map<String, String>> listFile = new ArrayList<Map<String,String>>();
		fRoot = new File(filePath + "file/");
		if(fRoot.isDirectory())
		{
			for(File f : fRoot.listFiles())
			{
				if(f.isDirectory())
				{
					Map<String, String> m = new HashMap<String, String>();
					m.put("path", f.getPath().replace(f.getParent(), ""));
					m.put("name", f.getName());
					listFile.add(m);
				}
			}
		}
		request.setAttribute("listFile", listFile);
	}
	request.setAttribute("siteid", siteid);
}
catch(Exception ex)
{
}
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<c:if test="${siteid<0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp" %>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">信息发布：没有可管理的站点</td>
	</tr>
</table>
</body>
</c:if>
<c:if test="${siteid>=0}">
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	$("#log").html($dswork.result.msg);
}};
$dswork.showResponse = function(data, status, xhr){
	$dswork.checkResult(data);
	$dswork.callback();
};
function cleanImage(){
	if(confirm("确定清理吗？")){
		$("#dataForm1").ajaxSubmit({success:$dswork.showResponse});
	}
}
function cleanFile(){
	if(confirm("确定清理吗？")){
		$("#dataForm2").ajaxSubmit({success:$dswork.showResponse});
	}
}
</script>
<style>form{display:inline-block;}</style>
</head>
<body>
<div style="width:60%;margin: 0 auto;line-height:38px;">
	切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
	<br />
		<form id="dataForm1" method="post" action="clearImgAction.jsp">
		选择删除的图片目录：<select name="path">
			<option value="">全部</option>
		<c:forEach items="${list}" var="d">
			<option value="${d.path}">${d.name}</option>
		</c:forEach>
		</select>
		<input type="hidden" name="siteid" value="${siteid}">
		</form>
		<input type="button" class="button" onclick="cleanImage();return false;" value="清理图片" />
	<br />
		<form id="dataForm2" method="post" action="clearFileAction.jsp">
		选择删除的附件目录：<select name="path">
			<option value="">全部</option>
		<c:forEach items="${listFile}" var="d">
			<option value="${d.path}">${d.name}</option>
		</c:forEach>
		</select>
		<input type="hidden" name="siteid" value="${siteid}">
		</form>
		<input type="button" class="button" onclick="cleanFile();return false;" value="清理文件" />
	<div id="log" style="line-height:25px;"></div>
</div>
</body>
</c:if>
</html>
