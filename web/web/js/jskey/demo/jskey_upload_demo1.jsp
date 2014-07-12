<%@page pageEncoding="UTF-8"%>
<%@page import="dswork.core.upload.JskeyUpload"%>
<%
	String path = request.getContextPath();
	long filekey = System.currentTimeMillis();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jskey/jskey_upload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jskey/jskey_core.js"></script>
<script type="text/javascript">
function doAjaxFileUpload()
{
	var _fieldId = arguments[0] || "";//表单ID
	var _filekeyValueId = arguments[1] || "";//表单文件标识,用于记录上传后对应的文件编号,上传失败则此值为0,必须有可读写的value属性,如hidden
	var _filekey = arguments[2] || "";//表单文件编号
	var _fieldDesc = arguments[3] || "";//所上传的文件描述,可为空
	var _fileExt = arguments[4] || "file";//上传后缀名, file和image为默认配置类型，其余为逗号分隔的后缀名"***,***"字符串
	var _hiddenValueId = "fjShow";//用于记录返回信息的表单项ID,可为空
	try
	{
		if(document.getElementById(_filekeyValueId).value != "")
		{
			if(!confirm("您已上传过文件，是否重新上传？"))
			{
				//这里可设置清空file的值
				
				return false;
			}
		}
		var isUploading = $jskey.upload.doUpload({
			url:'<%=request.getContextPath()%>/js/jskey/jskey_upload.jsp?filekey=' + _filekey + '&ext=' + _fileExt,//url不能有filename参数,ext值["file"后台配置的默认类型|"img"后台配置的图片类型|"***,***"后缀名以逗号分隔,但不在默认类型下的会自动过滤]
			fileId:_fieldId,
			fileKey:_filekey,//表单文件标识，目录为大于0的唯一数值(区别于同一session下的其它上传文件)
			fileDesc:_fieldDesc,//所上传的文件描述,可为空
			//dataType:'json',//后台默认为json
			timeout:0,//为0则不计算超时时间
			
			success:function(data)
			{
				//页面返回值data格式为{"err":"","msg":"","name":""},失败(err不为空),成功(msg为保存文件名,name为上传原文件名)
				if(typeof(data.err) != 'undefined')
				{
					if(data.err != '')
					{
						if(_hiddenValueId != "") {document.getElementById( _hiddenValueId).innerHTML = _fieldDesc + ":" + "上传失败" + "\n" + data.err;}
					}
					else
					{
						document.getElementById(_filekeyValueId).value = "";//重新记录新值
						if(_hiddenValueId != "") {document.getElementById( _hiddenValueId).innerHTML = _fieldDesc + ":" + "上传完成，该文件将在最后一次上传操作后<%=JskeyUpload.UPLOAD_TIMEOUT%>秒后超时从服务器上删除" + "<br />原文件：" + data.name + "<br />保存为：" + data.msg;}
						document.getElementById(_filekeyValueId).value = _filekey;//上传成功,记录该文件标识
						//这里可以用data.name取得文件名，可以保存到另一个hidden表单中提交到后台
					}
				}
			},
			error:function(e)
			{
				if(_hiddenValueId != ""){document.getElementById( _hiddenValueId).innerHTML = _fieldDesc + ":" + "上传失败:";}
			}
		});
		if(isUploading && _hiddenValueId != "")
		{
			try
			{
				if(document.getElementById(_hiddenValueId) != null)
				{
					document.getElementById( _hiddenValueId).innerHTML = "上传中...";
				}
			}
			catch(e)
			{
				_hiddenValueId = "";//出错设置为空
			}
		}
	}
	catch(e)
	{
		alert(e.name + "\n" + e.message);
	}
	return false;
}
</script>
</head>
<body>
<center>
<form id="dateForm" action="jskey_upload_demo2.jsp" method="post">
	<div class="line"></div>
	<table border="0" cellspacing="1" cellpadding="0" class="listTable" width="1000">
		<tr>
			<td style="text-align:right;width:30%;">
				上传附件
			</td>
			<td style="text-align:left;width:70%;">
				<input id="fjCheck" onclick="alert('事件复制')" name="fjCheck" type="file" require="false" class="text" style="width:330px;" dataType="UploadFileCheck" /><%--UploadFileCheck--%>
				<input id="fjFile" name="fjFile" type="hidden" value="" dataType="UploadFile" /><%--UploadFile--%>
				<a onclick="return doAjaxFileUpload('fjCheck', 'fjFile', '<%=filekey%>', '上传附件');" href="#">上传附件</a>
				<a onclick="return doAjaxFileUpload('fjCheck', 'fjFile', '<%=filekey%>', '上传图片', 'image');" href="#">上传图片</a>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;" >
				<input type="submit" class="button" id="_submit_" value="提交" style="display:none;" />
				<input type="button" class="button" value="提交" onclick="$jskey.validator.submit('dateForm', '_submit_', 3);" />
				<input type="button" class="button" value="检查" onclick="$jskey.upload.check();" />
			</td>
		</tr>
		<tr>
			<td id="fjShow" colspan="2" style="text-align:left;" >
				&nbsp;
			</td>
		</tr>
	</table>
</form>
</center>
</body>
</html>
<%
	JskeyUpload.refreshSessionKey(request);
%>