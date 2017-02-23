<%--Desc: 上传 | Author: skey--%><%@page language="java" pageEncoding="UTF-8"
import="java.io.File,java.net.URLDecoder,dswork.webio.WebioTempUtil,dswork.web.*"
%><%!
private static long thisId = 0;
private static final String UPLOAD_MAXSIZE_M = (WebioTempUtil.UPLOAD_MAXSIZE/1024/1024) + "M";
public synchronized static long genId() throws Exception
{
	long id = 0;
	do
	{
		id = System.currentTimeMillis();
	}
	while (id == thisId);
	thisId = id;
	return id;
}
%><%
String ext = "";
try
{
	//参数均由get方式传递
	ext = request.getParameter("ext");
	ext = WebioTempUtil.getUploadExt(ext);
	MyRequest req = new MyRequest(request, WebioTempUtil.UPLOAD_MAXSIZE, WebioTempUtil.UPLOAD_MAXSIZE, ext, null);
	long sessionKey = req.getLong("sessionkey");//经由get方式传递过来的唯一标识
	long filekey = req.getLong("filekey");//经由get方式传递过来的唯一标识
	String uploadone = req.getString("uploadone");//经由get方式传递过来的用于判断是否仅上传一个文件（当且仅当uploadone=true）
	if(sessionKey <= 0 || filekey <= 0)
	{
		%>{"err":"刷新重试","msg":""}<%return;
	}
	StringBuilder _sb = new StringBuilder();
	MyFile[] myFiles = req.getFileArray();// 表单中只有一个文件
	WebioTempUtil.toStart(sessionKey);
	if (myFiles.length > 0)
	{
		MyFile myFile = myFiles[0];
 		//取得上传类型
		String extType = myFile.getFileExt();//文件类型
 		try
 		{
 			if(uploadone != null && "true".equals(uploadone))
 			{
 				WebioTempUtil.delFile(sessionKey, filekey);//保存前尝试清除
 			}
 			// 将上传文件保存到指定目录
			String filePath = WebioTempUtil.getSavePath(sessionKey, filekey);
 			_sb.setLength(0);
 			System.out.println(_sb.append("当前上传允许的后缀名为：").append(ext).append("\n").append("当前上传文件为：").append(myFile.getFileName()).toString());
 			long fname = genId();
 			_sb.setLength(0);
 			_sb.append(fname).append(".").append(extType);
 			myFile.saveAs(filePath + _sb.toString());
			%>{"err":"","msg":"<%=_sb.toString() %>"}<%
			_sb.setLength(0);
 		}
		catch(Exception e)
		{
			e.printStackTrace();
			%>{"err":"保存失败","msg":""}<%
		}
	}
}
catch(Exception ex)
{
	ex.printStackTrace();
	%>{"err":"文件后缀只能为：<%=ext%>(小写扩展名)；文件大小不能超过<%=UPLOAD_MAXSIZE_M %>","msg":""}<%
}
%>