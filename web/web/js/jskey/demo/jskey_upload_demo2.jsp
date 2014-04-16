<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="dswork.core.upload.JskeyUpload"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<%
//可用MyRequest的getLong("fiFile", -1)替换，此处没有引用框架
long fjFile;
if (request.getParameter("fjFile") == null)
{
	fjFile = 0L;
}
else
{
	String str = request.getParameter("fjFile");
	try
	{
		fjFile = Long.parseLong(str);
	}
	catch(Exception ex)
	{
		fjFile = -1L;
	}
}
File f = null;
if(fjFile > 0)
{
	File[] filelist = JskeyUpload.getFile(JskeyUpload.getSessionKey(request), fjFile);
	if(filelist != null && filelist.length > 0)
	{
		JskeyUpload.getToByte(filelist[0].getPath());//转换成byte[]
		f = filelist[0];//文件
	}
	//JskeyUpload.delFile(JskeyUpload.getSessionKey(request), fjFile);//已经把文件读取完，调用这句直接删除服务器上的临时目录，节省空间
}
try
{
	if(f != null)
		out.print("<br />您输入的文件已经保存到：" + f.getPath());
	else
		out.print("<br />可能已超时，文件已被删除");
}
catch(Exception e)
{
	out.print("系统出错");
}
%>
</body>
</html>
