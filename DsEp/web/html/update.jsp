<%@page language="java" pageEncoding="UTF-8" import="
java.util.Map,
java.util.HashMap,
java.util.List,
java.util.ArrayList,
java.io.File,
dswork.web.MyRequest,
dswork.spring.BeanFactory,
dswork.cms.model.DsCmsSite,
dswork.cms.dao.DsCmsSiteDao
"%><%!
private String getCmsRoot(HttpServletRequest request)
{
	return request.getSession().getServletContext().getRealPath("/") + "/html/";
}
%><%
try
{
	MyRequest req = new MyRequest(request);
	DsCmsSiteDao sdao = (DsCmsSiteDao)BeanFactory.getBean("dsCmsSiteDao");
	Long id = req.getLong("siteid", -1), siteid = -1L;
	Map<String, Object> map = new HashMap<String, Object>();
	List<DsCmsSite> siteList = (List<DsCmsSite>)sdao.queryList(map);
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
		DsCmsSite site = (DsCmsSite)sdao.get(siteid);
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
<meta charset="UTF-8">
<title>网站模板升级替换</title>
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
<meta charset="UTF-8">
<title>网站模板升级替换</title>
<%@include file="/commons/include/updAjax.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	$("#log").html($dswork.result.msg);
}};
$dswork.showResponse = function(data, status, xhr){
	$dswork.checkResult(data);
	$dswork.callback();
};
function cleanFile(){
	if(confirm("确定清理吗？")){
		$("#dataForm2").ajaxSubmit({success:$dswork.showResponse});
	}
}
$(function(){
	$("#site").on("change", function(){
		location.href = "update.jsp?siteid=" + $("#site").val();
	});
});
</script>
<style>form{display:inline-block;}</style>
</head>
<body>
<div style="width:60%;margin: 0 auto;line-height:38px;">
	切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
	<br />
		<form id="dataForm2" method="post" action="updateCmsDataForURL.jsp">
		<input type="hidden" name="siteid" value="${siteid}">
		</form>
		<input type="button" class="button" onclick="cleanFile();return false;" value="清理模板" />
	<div id="log" style="line-height:25px;">用于升级cms系统，原使用栏目自定义目录改为ID目录</div>
</div>
</body>
</c:if>
</html>
