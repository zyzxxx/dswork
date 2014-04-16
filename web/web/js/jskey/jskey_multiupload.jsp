<%--Desc: 上传 | Author: skey--%><%@page language="java" pageEncoding="UTF-8"
import="java.io.File,java.net.URLDecoder,dswork.core.upload.JskeyUpload,dswork.core.upload.jspsmart.*"
%><%!
private static long thisId = 0;
private static final String UPLOAD_MAXSIZE_M = (JskeyUpload.UPLOAD_MAXSIZE/1024/1024) + "M";
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
	//以下三个参数均由get方式传递
	ext = request.getParameter("ext");
	ext = JskeyUpload.getUploadExt(ext);
	String s_key = request.getParameter("sessionkey");//经由get方式传递过来的唯一标识
	String f_key = request.getParameter("filekey");//经由get方式传递过来的唯一标识
	String uploadone = request.getParameter("uploadone");//经由get方式传递过来的用于判断是否仅上传一个文件（当且仅当uploadone=true）
	
	SmartUpload u = new SmartUpload();
	u.setMaxFileSize(JskeyUpload.UPLOAD_MAXSIZE);//限制每个上传文件的最大长度为10M
	u.setTotalMaxFileSize(JskeyUpload.UPLOAD_MAXSIZE);//限制总上传数据的长度，每次只上一个，同上
	u.setAllowedFilesList(ext);
	u.initialize(pageContext);
	u.upload();
	long sessionKey = 0;
	long filekey = 0;
	try
	{
		sessionKey = Long.parseLong(s_key);
		filekey = Long.parseLong(f_key);
	}
	catch(NumberFormatException e)
	{
		sessionKey = 0;
		filekey = 0;
	}
	if(s_key == null || f_key == null || sessionKey <= 0 || filekey <= 0)
	{
		%>{"err":"刷新重试","msg":""}<%return;
	}
	StringBuilder _sb = new StringBuilder();
	dswork.core.upload.jspsmart.File myFile = u.getFiles().getFile(0);// 表单中只有一个文件
	JskeyUpload.toStart(sessionKey);
	if (!myFile.isMissing())
	{
 		//取得上传类型
		String extType = myFile.getFileExt().toLowerCase();//文件类型
 		try
 		{
 			if(uploadone != null && "true".equals(uploadone))
 			{
 				JskeyUpload.delFile(sessionKey, filekey);//保存前尝试清除
 			}
 			// 将上传文件保存到指定目录
			String filePath = JskeyUpload.getSavePath(sessionKey, filekey);
 			java.io.File f = new java.io.File(filePath);
 			f.mkdirs();
 			_sb.setLength(0);
 			System.out.println(_sb.append("当前上传允许的后缀名为：").append(ext).append("\n").append("当前上传文件为：").append(myFile.getFileName()).toString());
 			long fname = genId();
 			_sb.setLength(0);
 			_sb.append(fname).append(".").append(extType);
	 		myFile.saveAs(filePath + _sb.toString(), SmartUpload.SAVE_PHYSICAL);
			%>{"err":"","msg":"<%=_sb.toString() %>"}<%
			_sb.setLength(0);
 		}
		catch(Exception e)
		{
			e.printStackTrace();
			%>{"err":"保存失败","msg":""}<%
		}
	}
	u.close();
}
catch(Exception ex)
{
	ex.printStackTrace();
	%>{"err":"文件后缀只能为：<%=ext%>(小写扩展名)；文件大小不能超过<%=UPLOAD_MAXSIZE_M %>","msg":""}<%
}
%>