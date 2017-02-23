<%--Desc: 上传 | Author: skey--%><%@page language="java" pageEncoding="UTF-8"
import="
java.io.File,
java.net.URLDecoder,
dswork.webio.WebioUtil,
dswork.web.*"
%><%!
private static final String UPLOAD_MAXSIZE_M = (WebioUtil.MAXSIZE/1024/1024) + "M";
private static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
%><%
String ext = "";
try
{
	//参数均由get方式传递
	ext = request.getParameter("ext");// 当enctype为application/x-www-form-urlencoded时才会对流处理有影响
	ext = WebioUtil.getUploadExt(ext);
	MyRequest req = new MyRequest(request, WebioUtil.MAXSIZE, 0L, ext, null);
	String s_name = req.getString("name");//经由get方式传递过来的应用唯一标识
	MyFile[] files = req.getFileArray();
	MyFile file = null;
	if(files.length > 0)
	{
		file = files[0];// 只有一个文件
	}
	if(file == null)
	{
		return;
	}
	StringBuilder sb = new StringBuilder();
	System.out.println(sb
			.append("\n").append("---------------")
			.append("\n").append("　当前上传允许的后缀名为：").append(ext)
			.append("\n").append("　当前上传文件为：").append(file.getFileName())
		.toString());
	sb.setLength(0);
	
	// 处理name为纯字母
	char[] arr = s_name.toCharArray();
	for(int i = 0; i < arr.length; i++){
		if(STR.indexOf(arr[i]) != -1){
			sb.append(arr[i]);
		}
	}
	s_name = sb.toString();
	// temp目录主要用于存储临时文件，就算上传了也会被清除
	if(s_name.length() == 0){
		s_name = "webio";
	}
	sb.setLength(0);
	String md5 = WebioUtil.getMd5(file.getFileData());
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
	String f_n = sb.append(md5).append(".").append(file.getFileExt()).toString();
	sb = null;
	if(!file.saveAs(path + f_n, false))
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