<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/addAjax.jsp" %>
<%@include file="/commons/include/editor.jsp" %>
<script type="text/javascript" src="${ctx}/js/smartImgAdd.js"></script>
<script type="text/javascript">
$dswork.callback = function(){if($dswork.result.type == 1){
	location.href = "getPage.htm?id=${fn:escapeXml(param.categoryid)}";
}};
$(function(){
	$(".form_title").css("width", "8%");
	$('#content').xheditor({html5Upload:true,upMultiple:1,upLinkUrl:"uploadFile.htm?categoryid=${fn:escapeXml(param.categoryid)}",upImgUrl:"uploadImage.htm?categoryid=${fn:escapeXml(param.categoryid)}"});
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
	$dswork.readySubmit = function(){if($("#inputImg").val()==""){fill();}};
});
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" class="listLogo">
	<tr>
		<td class="title">添加</td>
		<td class="menuTool">
			<a class="save" onclick="_save();" href="javascript:void(0);">保存</a>
			<a class="submit" onclick="_submit();" href="javascript:void(0);">提交</a>
			<a class="back" href="getPage.htm?id=${fn:escapeXml(param.categoryid)}&page=${fn:escapeXml(param.page)}">返回</a>
			<script type="text/javascript">
			function _save(){if(confirm("确认保存吗？")){
				$('input[name="action"]').val('save');
				$('#dataForm').ajaxSubmit($dswork.doAjaxOption);
			}}
			function _submit(){if(confirm("确认提交吗？")){
				$('input[name="action"]').val('submit');
				$('#dataForm').ajaxSubmit($dswork.doAjaxOption);
			}}
			</script>
		</td>
	</tr>
</table>
<div class="line"></div>
<form id="dataForm" method="post" action="addPage2.htm?categoryid=${fn:escapeXml(param.categoryid)}">
<table border="0" cellspacing="1" cellpadding="0" class="listTable">
	<tr>
		<td class="form_title">是否外链</td>
		<td class="form_input">
			<label><input type="radio" name="scope" value="2" onclick="showUrl(this);" />是</label>
			<label><input type="radio" name="scope" value="1" onclick="showUrl(this);" v="1" />否</label>
			<span id="spanUrl">&nbsp;&nbsp;链接&nbsp;<input type="text" name="url" style="width:400px;" value="" /></span>
			<script type="text/javascript">
			function showUrl(e){
				if($(e).val()=="2"){$("#spanUrl").show();}
				else{$("#spanUrl").hide();}
			}
			$("#spanUrl").hide();
			</script>
		</td>
	</tr>
	<tr>
		<td class="form_title">标题</td>
		<td class="form_input"><input type="text" name="title" maxlength="100" style="width:400px;" dataType="Require" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">摘要</td>
		<td class="form_input"><input type="text" name="summary" maxlength="100" style="width:400px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">meta关键词</td>
		<td class="form_input"><input type="text" name="metakeywords" maxlength="100" style="width:300px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">meta描述</td>
		<td class="form_input"><input type="text" name="metadescription" maxlength="100" style="width:300px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">来源</td>
		<td class="form_input"><input type="text" name="releasesource" maxlength="100" style="width:300px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">作者</td>
		<td class="form_input"><input type="text" name="releaseuser" maxlength="100" style="width:300px;" value="" /></td>
	</tr>
	<tr>
		<td class="form_title">内容</td>
		<td class="form_input"><textarea id="content" name="content" style="width:99%;height:300px;"></textarea></td>
	</tr>
	<tr>
		<td class="form_title">图片</td>
		<td class="form_input">
			<label>&nbsp;<input type="checkbox" name="imgtop" value="1" /> 焦点图</label>
			<input type="text" name="img" id="inputImg" maxlength="100" style="width:400px;" value="" />
			&nbsp; <input type="button" class="button" id="btnFill" value="识别图片">
			&nbsp; <input type="button" class="button" id="btnClean" value="清空">
			<br />
			<img id="imgShow" style="width:100px">
		</td>
	</tr>
	<tr>
		<td class="form_title">发布</td>
		<td class="form_input">
			<label><input type="checkbox" name="pagetop" value="1" /> 首页推荐</label>
			&nbsp;&nbsp;发布时间：<input type="text" name="releasetime" class="WebDate" format="yyyy-MM-dd HH:mm:ss" value="${releasetime}" />
		</td>
	</tr>
</table>
<input type="hidden" name="action" value="" />
</form>
</body>
</html>
