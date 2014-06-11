<%@Page Language="C#"%>
<%@Import Namespace="Dswork.Core.Upload" %>
<!DOCTYPE html>
<html>
<head>
<title>文件上传</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../jskey_multiupload.js"></script>
<script type="text/javascript">
function doAjaxFileUpload()
{
	var _buttonID = arguments[0] || "";//span元素ID，用于放置上传按钮
	var _filekey = arguments[1] || "";//标识
	var _keyId = arguments[2] || "";//上传失败则此值为空,成功则为_filekey
	var _keyNamesId = arguments[3] || "";//存储(a:aa|b:bb)后台保存文件名:原始文件名
	var _ext = arguments[4] || "file";//上传后缀名, file和image为默认配置类型，其余为逗号分隔的后缀名"***,***"字符串
	var _showDiv = arguments[5] || "";//用于记录返回信息的表单项ID,可为空
	try
	{
		$jskey.upload.init({
			url: '../jskey_multiupload.aspx?<%--uploadone=true&--%>sessionkey=<%=JskeyUpload.RefreshSessionKey(Request)%>&filekey=' + _filekey + '&ext=' + _ext,
			"button_placeholder_id":_buttonID,
			"fileKey":_filekey,//表单文件标识，上传目录名，大于0的唯一数值(区别于同一session下的其它上传文件)
			file_types:'*.*', //"*.jpg;*.gif"
			file_size_limit: "<%=JskeyUpload.UPLOAD_MAXSIZE/1024%>", //KB
			<%--file_queue_limit:1,--%>
			//param:{},
			debug:true,
			custom_settings:{
				div:_showDiv,
				success:function(data)// {arr:[{id,name,size,state,file,type,msg}],msg:"",err:""}
				{
					if(typeof(data.err) != 'undefined')
					{
						if (data.err != '') {
							alert(data.err);
						}
						var o;
						var _msg = "";
						var _err = "";
						var v = document.getElementById(_keyNamesId).value;
						for (var i = 0; i < data.arr.length; i++) {
							o = data.arr[i];
							if (o.state == "1") {
								v += (v != "" ? "|" : "") + o.file + ":" + o.name;
								_msg = _msg + o.name + " : 已保存为" + o.file + "\n";
							}
							else {
								_err = _err + o.name + " : 原因：" + o.msg + "\n";
							}
						}
						alert("上传成功：\n" + _msg + "\n\n上传失败：\n" + _err);
						//newname:oldname|newname:oldname
						document.getElementById(_keyNamesId).value = v;
						document.getElementById(_keyId).value = (v == "") ? "" : _filekey;//上传成功,记录该文件标识
					}
				}
			}
		});
	}
	catch(e)
	{
		alert(e.name + "\n" + e.message);
	}
	return false;
}
window.onload = function ()
{
	doAjaxFileUpload('spanUploadButton', '<%=DateTime.Now.Ticks %>', 'fjFile', 'fjFileNames', null, 'divUploadProgress');
};
</script>
</head>
<body>
<center>
<span id="spanUploadButton"></span>
<br />
<div id="divUploadProgress" style="text-align:left;"></div>
</center>
<br />

<form id="dateForm" action="jskey_multiupload_demo2.aspx" method="post">
<%--key--%>
<input id="fjFile" name="fjFile" type="hidden" value="" dataType="UploadFile" />
<%--newname:oldname|newname:oldname--%>
<input id="fjFileNames" name="fjFileNames" type="hidden" value="" />
<input type="submit" class="button" id="_submit_" value="提交" />
</form>
</body>
</html>
