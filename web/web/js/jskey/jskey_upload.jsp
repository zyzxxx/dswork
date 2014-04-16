<%--Desc: 上传 | Author: skey--%><%@page language="java" pageEncoding="UTF-8"
import="java.io.File,java.net.URLDecoder,dswork.core.upload.JskeyUpload,dswork.core.upload.jspsmart.*"
%><%String ext = "";
try
{
	//以下三个参数均由get方式传递
	ext = request.getParameter("ext");
	String filekey = request.getParameter("filekey");//经由get方式传递过来的唯一标识
	String filename = URLDecoder.decode(URLDecoder.decode(request.getParameter("filename"), "UTF-8"), "UTF-8");//经由post传递过来的文件名称
	
	String _t = String.valueOf(JskeyUpload.getSessionKey(request));//当前session的临时目录名，长整形
	SmartUpload u = new SmartUpload();
	u.setMaxFileSize(JskeyUpload.UPLOAD_MAXSIZE);//限制每个上传文件的最大长度为10M
	u.setTotalMaxFileSize(JskeyUpload.UPLOAD_MAXSIZE);//限制总上传数据的长度，每次只上一个，同上
	ext = JskeyUpload.getUploadExt(ext);
	u.setAllowedFilesList(ext);
	u.initialize(pageContext);
	u.upload();
	if(_t == null || filekey == null || _t.equals("null") || _t.trim().length() <= 0 || filekey.length() == 0)
	{
		%>{"err":"访问超时或非法访问","msg":"","name":"","ext":""}<%return;
	}
	long sessionKey = 0;
	long filenum = 0;
	try
	{
		sessionKey = Long.parseLong(_t);
		filenum = Long.parseLong(filekey);
	}
	catch(NumberFormatException e)
	{
		sessionKey = 0;
		filenum = 0;
	}
	if(sessionKey <= 0 || filenum <= 0)
	{
		%>{"err":"刷新重试","msg":"","name":"","ext":""}<%return;
	}
	
	JskeyUpload.toStart(sessionKey);
	JskeyUpload.refreshSessionTime(sessionKey);
	String extType = ""; //文件类型
	StringBuffer _sb = new StringBuffer();
	String filePath = _sb.append(JskeyUpload.UPLOAD_SAVEPATH).append(File.separatorChar).append(sessionKey).append(File.separatorChar).append(filenum).append(File.separatorChar).toString();
	_sb.setLength(0);
	//for (int j = 0; j < 1; j++)// u.getFiles().getCount() 目前只上传单个文件
	//{
		dswork.core.upload.jspsmart.File myFile = u.getFiles().getFile(0);//.getFile(j);
		if (!myFile.isMissing())
		{
	 		//取得上传类型
	 		extType = myFile.getFileExt().toLowerCase();
 			_sb.setLength(0);
	 		System.out.println(_sb.append("当前上传允许的后缀名为：").append(ext).append("\n").append("上传文件为：").append(filename).toString());//名字由客户端脚本传递
	 		try
	 		{
	 			// 将上传文件全部保存到指定目录
	 			JskeyUpload.delFile(sessionKey, filenum);//保存前尝试清除
	 			java.io.File f = new java.io.File(filePath);
	 			f.mkdirs();
	 			_sb.setLength(0);
	 			myFile.saveAs(_sb.append(filePath).append(filenum).append(".").append(extType).toString(), SmartUpload.SAVE_PHYSICAL);%>{"err":"","msg":"<%=filenum + "." + extType%>","name":"<%=filename%>","ext":"<%=extType%>"}<%
	 		}
			catch(Exception e)
 			{
				e.printStackTrace();
				%>{"err":"上传失败！文件后缀只能为：<%=ext%>(小写扩展名)；文件大小不能超过<%=JskeyUpload.UPLOAD_MAXSIZE_M %>","msg":"","name":"","ext":""}<%
				
 			}
		}
	//}
	_sb.setLength(0);
	u.close();
}
catch(Exception ex)
{
	System.out.println(ex.getMessage());
	//ex.printStackTrace();
	%>{"err":"上传失败或被取消！文件后缀只能为：<%=ext%>(小写扩展名)；文件大小不能超过<%=JskeyUpload.UPLOAD_MAXSIZE_M %>","msg":"","name":"","ext":""}<%
}
%>