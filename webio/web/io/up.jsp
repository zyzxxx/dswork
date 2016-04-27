<%--Desc: 上传 | Author: skey--%><%@page language="java" pageEncoding="UTF-8"
import="
java.io.File,
java.net.URLDecoder,
dswork.core.util.FileUtil,
dswork.core.webio.WebioUtil,
dswork.core.webio.EncryptByteUtil,
dswork.core.upload.jspsmart.*"
%><%!
private static final String UPLOAD_MAXSIZE_M = (WebioUtil.MAXSIZE/1024/1024) + "M";
private static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
%><%
String ext = "";
try
{
	//参数均由get方式传递
	ext = request.getParameter("ext");
	ext = WebioUtil.getUploadExt(ext);
	String s_name = request.getParameter("name");//经由get方式传递过来的应用唯一标识
	
	String filename = "";
	String fileext = "";
	boolean isHTML5 = "application/octet-stream".equals(request.getContentType());
	byte[] byteArray = null;
	if(isHTML5)
	{
		String header = request.getHeader("Content-Disposition");
		int iStart = header.indexOf("filename=\"") + 10;
		int iEnd = header.indexOf("\"", iStart);
		filename = header.substring(iStart, iEnd);
		int len = filename.lastIndexOf(".");
		fileext = (len != -1) ? filename.substring(len + 1) : "";
		if(fileext.equals("") || ext.indexOf(fileext) == -1){
			throw new Exception("自定义后缀限制错误");
		}
		int i = request.getContentLength();
		byteArray = new byte[i];
		int j = 0;
		while(j < i)// 获取表单的上传文件
		{
			int k = request.getInputStream().read(byteArray, j, i - j);
			j += k;
		}
	}
	else
	{
		SmartUpload u = new SmartUpload();
		u.setMaxFileSize(WebioUtil.MAXSIZE);//限制每个上传文件的最大长度为10M
		u.setTotalMaxFileSize(WebioUtil.MAXSIZE);//限制总上传数据的长度，每次只上一个，同上
		u.setAllowedFilesList(ext);
		u.initialize(pageContext);
		u.upload();
		dswork.core.upload.jspsmart.File myFile = u.getFiles().getFile(0);// 表单中只有一个文件
		if (!myFile.isMissing())
		{
			fileext = myFile.getFileExt().toLowerCase();//上传文件类型
	 		filename = myFile.getFileName();
	 		byteArray = FileUtil.getToByte(myFile.getInputSteram());
		}
		u.close();
	}
	StringBuilder sb = new StringBuilder();
	System.out.println(sb
			.append("\n").append("---------------")
			.append("\n").append("　当前上传允许的后缀名为：").append(ext)
			.append("\n").append("　当前上传文件为：").append(filename)
		.toString());
	if(s_name == null){
		s_name = "";
	}
	sb.setLength(0);
	char[] arr = s_name.toCharArray();
	for(int i = 0; i < arr.length; i++){
		if(STR.indexOf(arr[i]) != -1){
			sb.append(arr[i]);
		}
	}
	s_name = sb.toString();
	if(s_name.length() == 0){
		s_name = "webio";
	}
	sb.setLength(0);
	String md5 = EncryptByteUtil.getMd5(byteArray);
	String path = sb.append(WebioUtil.PATH)
			.append("/").append(s_name)
			.append("/").append(md5.substring( 0,  4))//1679616=36^4
			.append("/").append(md5.substring( 4,  8))
			.append("/").append(md5.substring( 8, 12))
			.append("/").append(md5.substring(12, 16))
			.append("/").append(md5.substring(16, 20))
			.append("/").append(md5.substring(20, 24))
			.append("/").append(md5.substring(24, 28))
			.append("/").append(md5.substring(28, 32))
			.append("/").toString();
	
	sb.setLength(0);
	FileUtil.createFolder(path);
	String f_n = sb.append(md5).append(".").append(fileext).toString();
	boolean status = FileUtil.writeFile(path + f_n, FileUtil.getToInputStream(byteArray), false);
	
	sb.setLength(0);
	sb = null;
	byteArray = null;
	
	if(!status)
	{
		System.out.println("　文件已存在，跳过保存");
	}
	%>{"err":"","msg":"<%=f_n%>"}<%
	return;
}
catch(Exception ex)
{
	ex.printStackTrace();
	%>{"err":"文件后缀只能为：<%=ext%>(小写扩展名)；文件大小不能超过<%=UPLOAD_MAXSIZE_M %>","msg":""}<%
}
%>