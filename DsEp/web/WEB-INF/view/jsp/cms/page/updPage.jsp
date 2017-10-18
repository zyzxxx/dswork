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
	location.href = "getPage.htm?id=${po.categoryid}&page=${page}";
}};
$(function(){
	try{$(".form_title").css("width", "8%");}catch(e){}
	$('#content').xheditor({html5Upload:true,upMultiple:1,upLinkUrl:"uploadFile.htm?categoryid=${po.categoryid}",upImgUrl:"uploadImage.htm?categoryid=${po.categoryid}",internalScript:true});
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
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">修改</td>
		<td class="menuTool">
			<a class="save" id="dataFormSave" href="#">保存</a>
			<a class="back" href="getPage.htm?id=${po.categoryid}&page=${page}">返回</a>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="updPage2.htm">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">是否外链</td>
		<td class="form_input">
			<label><input type="radio" name="scope" value="2" onclick="showUrl(this);" />是</label>
			<label><input type="radio" name="scope" value="1" onclick="showUrl(this);" v="${po.scope}" />否</label>
			<span id="spanUrl">&nbsp;&nbsp;链接&nbsp;<input id="xurl" type="text" name="url" style="width:400px;" value="" /></span>
			<script type="text/javascript">
			function showUrl(e){
				if($(e).val()=="2"){$("#spanUrl").show();$("#xurl").val("${fn:escapeXml(po.url)}");}
				else{$("#spanUrl").hide();}
			}
			<c:if test="${po.scope==2}">$("#spanUrl").show();$("#xurl").val("${fn:escapeXml(po.url)}");</c:if>
			<c:if test="${po.scope!=2}">$("#spanUrl").hide();</c:if>
			</script>
		</td>
	</tr>
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" style="width:400px;" dataType="Require" value="${fn:escapeXml(po.title)}" /></td>
	</tr>
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
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" style="width:99%;height:300px;">${po.content}</textarea></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input">
			<label>&nbsp;<input type="checkbox" name="imgtop" value="1" ${po.imgtop == 1?' checked="checked"':''}/>焦点图</label>
			<input type="text" name="img" id="inputImg" maxlength="100" style="width:300px;" value="${fn:escapeXml(po.img)}" />
			&nbsp; <input type="button" class="button" id="btnFill" value="识别图片">
			&nbsp; <input type="button" class="button" id="btnClean" value="清空">
			<br>
			<img id="imgShow" style="width:100px">
		</td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			<label><input type="checkbox" name="pagetop" value="1" ${po.pagetop == 1?' checked="checked"':''}/>首页推荐</label>
			&nbsp;&nbsp;发布时间：<input type="text" name="releasetime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value="${fn:escapeXml(po.releasetime)}" />
		</td>
	</tr>
</table>
<input type="hidden" name="id" value="${po.id}" />
</form>
</body>
</html>
