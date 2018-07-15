<%@page language="java" pageEncoding="UTF-8"%>
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
	<tr><td class="title">信息发布：没有可管理的站点</td></tr>
</table>
</body>
</c:if>
<c:if test="${siteid>=0}">
<head>
<title></title>
<%@include file="/commons/include/get.jsp"%>
<%@include file="/commons/include/ztree.jsp"%>
<script type="text/javascript">
$dswork.callback=null;
$dswork.ztree.beforeClick=function(treeId,treeNode,clickFlag){
	return treeNode.enable == "true";
};
$dswork.ztree.rightClick=function(event,treeId,treeNode){
	return treeNode.enable == "true";
};
$dswork.ztree.click=function(){
	var node=$dswork.ztree.getSelectedNode();
	if(node.scope == 0){
		attachUrl("getPage.htm?id="+node.id);
		return false;
	}else{
		attachUrl("getCategoryById.htm?id="+node.id);
		return false;
	}
	attachUrl("");
	return false;
};
function build(categoryid,pageid,specialid){
	$dswork.doAjaxObject.autoDelayHide("发布中",2000);
	$.post("build.htm",{"siteid":"${siteid}","categoryid":categoryid,"pageid":pageid,"specialid":specialid},function(data){
		$dswork.doAjaxShow(data,function(){});
	});
}
function unbuild(categoryid,pageid,specialid){
	$dswork.doAjaxObject.autoDelayHide("删除中",2000);
	$.post("unbuild.htm",{"siteid":"${siteid}","categoryid":categoryid,"pageid":pageid,"specialid":specialid},function(data){
		$dswork.doAjaxShow(data,function(){});
	});
}
$(function(){
	var v=[];<c:forEach items="${categoryList}" var="d">
	v.push({"id":"${d.id}","pid":"${d.pid}","name":"${fn:escapeXml(d.name)} [${d.scope==0?'列表':d.scope==1?'单页':'外链'}]","scope":"${d.scope}","enable":"${d.enable}"});</c:forEach>
	$dswork.ztree.nodeArray=v;
	$dswork.ztree.config.async.enable=false;
	var $z=$dswork.ztree;
	$z.load();
	$z.expandAll(true);
	$("#site").bind("click",function(){if($(this).val()!="${siteid}"){location.href="getCategoryTree.htm?siteid="+$(this).val();}});
	$("#special").bind("click",function(){$(this).val()!="0"?$(".view_special").show():$(".view_special").hide();});
	$("#category").bind("click",function(){$(this).val()!="0"?$(".view_category").show():$(".view_category").hide();});
	$("#publish_special").bind("click",function(){var m=$("#special").find("option:selected");if(confirm("是否发布专题\""+m.text()+"\"")){build(-1,-1,m.val());}});
	$("#publish_category").bind("click",function(){var m=$("#category").find("option:selected");if(confirm("是否发布栏目\""+m.text()+"\"")){build(m.val(),-1);}});
	$("#publish_page").bind("click",function(){var m=$("#category").find("option:selected");if(confirm("是否发布栏目\""+m.text()+"\"内容")){build(m.val(),0);}});
	$("#delete_special").bind("click",function(){var m=$("#special").find("option:selected");if(confirm("是否删除专题\""+m.text()+"\"")){unbuild(-1,-1,m.val());}});
	$("#delete_category").bind("click",function(){var m=$("#category").find("option:selected");if(confirm("是否删除栏目\""+m.text()+"\"已发布首页")){unbuild(m.val(),-1);}});
	$("#delete_page").bind("click",function(){var m=$("#category").find("option:selected");if(confirm("是否删除栏目\""+m.text()+"\"已发布内容")){unbuild(m.val(),0);}});
	$("#special").click();
	$("#category").click();
});
</script>
</head>
<body class="easyui-layout treebody" fit="true">
<div region="north" style="overflow:hidden;border:0px;height:30px;">
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">切换站点：<select id="site"><c:forEach items="${siteList}" var="d"><option value="${d.id}"<c:if test="${d.id==siteid}"> selected="selected"</c:if>>${fn:escapeXml(d.name)}</option></c:forEach></select>
			&nbsp;&nbsp;选择要发布的专题：<select id="special"><c:if test="${fn:length(specialList)>1}"><option value="0">全部专题</option></c:if><c:forEach items="${specialList}" var="d"><option value="${d.id}">${fn:escapeXml(d.title)}</option></c:forEach></select>
			<input id="publish_special" type="button" class="button" value="发布" />
			<input type="button" class="button view_special" value="预览" onclick="window.open('${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${siteid}&specialid='+$('#special').val());" /><c:if test="${enablemobile}">
			<input type="button" class="button view_special" value="预览移动版" onclick="window.open('${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${siteid}&mobile=true&specialid='+$('#special').val());" /></c:if>
			<input id="delete_special" type="button" class="button" value="删除" />
			&nbsp;&nbsp;选择要发布的栏目：<select id="category"><option value="0">全部栏目</option><c:forEach items="${categoryList}" var="d"><option value="${d.id}">${d.label}${fn:escapeXml(d.name)}</option></c:forEach></select>
			<input id="publish_category" type="button" class="button" value="发布首页" />
			<input id="publish_page" type="button" class="button" value="发布内容" />
			<input type="button" class="button view_category" value="预览" onclick="window.open('${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${siteid}&categoryid='+$('#category').val())" /><c:if test="${enablemobile}">
			<input type="button" class="button view_category" value="预览移动版" onclick="window.open('${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${siteid}&mobile=true&categoryid='+$('#category').val())" /></c:if>
			<input id="delete_category" type="button" class="button" value="删除首页" />
			<input id="delete_page" type="button" class="button" value="删除内容" />
		</td>
	</tr>
</table>
</div>
<div region="west" split="true" style="width:255px;">
	<div class="treediv">
		<ul id="mytree" class="ztree tree"></ul>
	</div>
</div>
<div region="center" style="overflow:hidden;">
	<iframe id="mainFrame" src="getCategoryPublish.htm?siteid=${siteid}" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
</div>
</body>
</c:if>
</html>
