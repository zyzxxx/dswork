<%@Page Language="C#"%>
<%@Import Namespace="Dswork.Core.Upload" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>文件上传</title>
<script type="text/javascript" src="../../jquery/jquery.js"></script>
<script type="text/javascript" src="../../dswork/dswork.js"></script>
<script type="text/javascript" src="../jskey_core.js"></script>
<script type="text/javascript" src="../jskey_multiupload.js"></script>
<script type="text/javascript">
var o = new $dswork.upload({sessionKey:<%=JskeyUpload.RefreshSessionKey(Request)%>, fileKey:<%=DateTime.Now.Ticks %>, ext:"file"});
window.onload = function()
{
	o.init({id:"fjFile", vid:"fjFileNames", ext:"image"});//, uploadone:"false"
};
</script>
</head>
<body>
<center>
<form id="dateForm" action="jskey_multiupload_demo2.aspx" method="post">
<%--key--%>
<span><input id="fjFile" name="fjFile" type="hidden" value="" dataType="UploadFile" /></span>
<input id="fjFileNames" name="fjFileNames" type="hidden" value="" /><%--newname:oldname|newname:oldname--%>
<br /><br />
<input type="submit" class="button" id="_submit_" value="提交" />
</form>
</center>
</body>
</html>
