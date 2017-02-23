<%@Page Language="C#"%><%@Import Namespace="Dswork.Webio" %><%@Import Namespace="System.IO" %><%
string ext = "";
try
{
	//以下三个参数均由get方式传递
	ext = Request["ext"];
	ext = WebioTempUtil.GetUploadExt(ext);
	String s_key = Request["sessionkey"];//经由get方式传递过来的唯一标识
	String f_key = Request["filekey"];//经由get方式传递过来的唯一标识
	String uploadone = Request["uploadone"];//经由get方式传递过来的用于判断是否仅上传一个文件（当且仅当uploadone=true）
	long sessionKey = 0;
	long filekey = 0;
	try
	{
		sessionKey = Convert.ToInt64(s_key);
		filekey = Convert.ToInt64(f_key);
	}
	catch(Exception e)
	{
		sessionKey = 0;
		filekey = 0;
	}
	if (s_key == null || f_key == null || sessionKey <= 0 || filekey <= 0)
	{
		%>{"err":"刷新重试","msg":""}<%return;
	}
	try
	{
		HttpFileCollection files = Request.Files;// 表单中只有一个文件
		HttpPostedFile myFile = files[0];
		long file_size = (long)(myFile.ContentLength) * 8L;//获取上传文件大小bit
		String file_name = System.IO.Path.GetFileName(myFile.FileName);//获取文件名
		int len = file_name.LastIndexOf(".");
		String file_ext = (len != -1) ? file_name.Substring(len + 1) : "";//文件类型
		WebioTempUtil.ToStart(sessionKey);
		if (file_size <= WebioTempUtil.UPLOAD_MAXSIZE && ext.IndexOf(file_ext) != -1)
		{
			if (uploadone != null && "true" == uploadone)
			{
				WebioTempUtil.DelFile(sessionKey, filekey);//保存前尝试清除
			}
			// 将上传文件保存到指定目录
			String filePath = WebioTempUtil.GetSavePath(sessionKey, filekey);
			if (!Directory.Exists(filePath))
			{
				Directory.CreateDirectory(filePath);
			}
			string newname = DateTime.Now.Ticks + "." + file_ext;
			myFile.SaveAs(filePath + newname);
			%>{"err":"","msg":"<%=newname %>"}<%
			return;
		}
	}
	catch
	{
		%>{"err":"保存失败","msg":""}<%return;
	}
}
catch
{
}
%>{"err":"文件后缀只能为：<%=WebioTempUtil.GetUploadExt(ext)%>(小写扩展名)；文件大小不能超过<%=(WebioTempUtil.UPLOAD_MAXSIZE/1048576)%>M","msg":""}