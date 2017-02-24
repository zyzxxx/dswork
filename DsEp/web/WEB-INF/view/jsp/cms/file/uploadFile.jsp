<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/include/upd.jsp" %>
<script type="text/javascript" src="/web/js/jskey/jskey_upload.js"></script>
<script type="text/javascript">
var o = new $dswork.upload({
	url:"uploadFile2.htm?siteid=${siteid}&path=${path}",
	sessionKey:"${v_session}",fileKey:"${v_file}",ext:"zip,ZIP"
});
$(function(){
	o.init({id:"fjFile", vid:"fjFileNames", show:false, 
		buttonid:"upBtn",
		success:function(p){
			alert('上传成功！');
			parent.refreshNode(false);
		}
	});
});

</script>
<style type="text/css">
#upBtn{margin:30px auto;background-color:#00A2D4;color:#fff;width:300px;padding:20px 0;border:0px;font-size:32px;font-weight:bold;cursor:pointer;}
#upBtn:hover{background-color:#0192D0;}
</style>
</head> 
<body style="background-color:#FAFAFA;">
	<span style="display:block;"><div id="upBtn" onclick="$dswork.doAjaxObject.autoDelayHide('请稍候', 3000);" style="text-align: center;">点击选择上传文件</div></span>
	<input id="fjFile" name="fjFile" type="hidden" value="" dataType="UploadFile" /><input id="fjFileNames" name="fjFileNames" type="hidden" value="" />
	<p style="text-align:left;margin-left:20px;color:red;font-size:16px;">文件上传注意事项</p>
	<hr style="width:100%;"></hr>
	<div style="margin:20px;text-align:left;">
		<p>1. 请需要上传的内容打包成"zip"文件</p>
		<p>2. 所有文件名仅允许大小写字母、数字、下划线、中划线</p>
		<p>3. 文件夹名称不能有小数点</p>
		<p>4. 文件名称必须带上后缀名，且后缀名不能有大写字母</p>
		<p>5. 允许文件类型：${hz}</p>
	</div>
</body>
</html>
