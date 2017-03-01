<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%--

项目中使用时，dswork.webio.WebioTempUtil需要改为dswork.core.upload.JskeyUpload

--%>
<%@page import="dswork.webio.WebioTempUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>文件上传</title>
</head>
<body>
<%
// 可用MyRequest的getLong("fiFile", -1)替换，此处没有引用框架
long fjFile;
request.setCharacterEncoding("UTF-8");
try
{
	fjFile = Long.parseLong(request.getParameter("fjFile"));
}
catch(Exception ex)
{
	fjFile = -1L;
}
if(fjFile > 0)
{
	String str = request.getParameter("fjFileNames");
	Map<String, String> map = new HashMap<String, String>();
	for(String s : str.split("\\|"))
	{
		try
		{
			String[] s2 = s.split(":");
			if(s2[0].length() > 0 && s2[1].length() > 0)
			{
				map.put(s2[0], s2[1]);
			}
		}
		catch(Exception exx)
		{
		}
	}
	long vid = WebioTempUtil.getSessionKey(request);
	File[] filelist = WebioTempUtil.getFile(vid, fjFile);
	if(filelist != null && filelist.length > 0)
	{
		for(File f : filelist)
		{
			//WebioTempUtil.getToByte(f.getPath());//转换成byte[]
			try
			{
				if(f != null)
				{
					out.println("<br />" + f.getName() + ": " + map.get(f.getName()));
					out.println("<br />您输入的文件已经保存到：" + f.getPath() + "<br />");
				}
				else
				{
					out.println("<br />可能已超时，文件已被删除");
				}
			}
			catch(Exception e)
			{
				out.println("系统出错");
			}
		}
	}
	//WebioTempUtil.delFile(JskeyUpload.getSessionKey(request), fjFile);//已经把文件读取完，调用这句直接删除服务器上的临时目录，节省空间
}
%>
</body>
</html>
