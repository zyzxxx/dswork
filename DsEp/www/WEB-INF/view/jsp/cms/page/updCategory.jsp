<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/updAjax.jsp" %>
<%@include file="/commons/include/editor.jsp" %>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
}};
$(function(){
	try{$("#pid").val("${po.pid}");}catch(e){}
	try{$(".form_title").css("width", "8%");}catch(e){}
	$('#content').xheditor({html5Upload:true,upMultiple:1,upLinkUrl:"uploadFile.htm?categoryid=${po.id}",upImgUrl:"uploadImage.htm?categoryid=${po.id}"});
	function show(){
		var i = new Image();
		i.src = $("#inputImg").val();
		i.onload = function(){$("#imgShow").attr("src",this.src).show()};
		i.onerror = function(){$("#imgShow").hide()};
	}
	function fill(){
		var v = $("#inputImg").val(), m = {}, list = [], count = -1;
		$('<div>'+$('#content').val()+'</div>').find("img").each(function(){var s = $(this).attr("src");if(!m[s]){list.push(s);m[s] = s;}});
		for(var i = 0; i < list.length; i++){if(v == list[i]){count = i;break;}}
		count = (count + 1) % list.length;
		$("#inputImg").val(list[count]);
		show();
	}
	$("#btnFill").on("click", fill);
	$("#btnClean").on("click", function(){$("#inputImg").val("");show();});
	$("#inputImg").on("keyup", show);
	show();
});
function build(categoryid)
{
	$dswork.doAjaxObject.autoDelayHide("发布中", 2000);
	var v = {"siteid":"${po.siteid}"};
	if(categoryid != null)
	{
		v.categoryid = categoryid;
	}
	$.post("build.htm",v,function(data){
		$dswork.doAjaxShow(data, function(){});
	});
}
$(function(){
	$("#btn_category").bind("click", function(){
		if(confirm("是否发布栏目\"${fn:escapeXml(po.name)}\"")){
			$dswork.doAjaxObject.autoDelayHide("发布中", 2000);
			$.post("build.htm",{"siteid":"${po.siteid}", "categoryid":"${po.id}"},function(data){
				$dswork.doAjaxShow(data, function(){});
			});
		}
	});
	$("#btn_site").bind("click", function(){
		if(confirm("是否发布首页")){build(null);}
	});
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="graph" id="btn_category" href="#">发布本栏目</a>
			<a class="look" target="_blank" href="${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${po.siteid}&categoryid=${po.id}">预览本栏目</a>
			<a class="graph" id="btn_site" href="#">发布首页</a>
			<a class="look" target="_blank" href="${ctx}/cmsbuild/buildHTML.chtml?view=true&siteid=${po.siteid}">预览首页</a>
			<a class="save" id="dataFormSave" href="#">保存</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updCategory2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" style="width:400px;" value="${fn:escapeXml(po.summary)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metakeywords)}" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.metadescription)}" /></td>
	</tr>
	<tr>
		<td class="form_title">来源</td>
		<td class="form_input"><input type="text" name="releasesource" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.releasesource)}" /></td>
	</tr>
	<tr>
		<td class="form_title">作者</td>
		<td class="form_input"><input type="text" name="releaseuser" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.releaseuser)}" /></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input">
			<input type="text" name="img" id="inputImg" maxlength="100" style="width:400px;" value="${fn:escapeXml(po.img)}" />
			&nbsp; <input type="button" class="button" id="btnFill" value="识别图片">
			&nbsp; <input type="button" class="button" id="btnClean" value="清空">
			<br />
			<img id="imgShow" style="width:100px">
		</td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" style="width:99%;height:300px;">${po.content}</textarea></td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			发布时间：<input type="text" name="releasetime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value="${fn:escapeXml(po.releasetime)}" />
		</td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
