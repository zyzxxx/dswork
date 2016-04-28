<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>文件上传</title>
</head>
<body>
<%
request.setCharacterEncoding("UTF-8");

String str = request.getParameter("fjFileNames");
Map<String, String> map = new HashMap<String, String>();
for(String s : str.split("\\|"))
{
	try
	{
		String[] s2 = s.split(":");
		if(s2[0].length() > 0 && s2[1].length() > 0)
		{
			%><a href="/webio/io/down.jsp?name=MYAPP&f=<%=s2[0]%>"><%=s2[1]%></a><br /><%
			// 使用s2[0]去调用api获取文件流，如果需要存到本地的话
		}
	}
	catch(Exception exx)
	{
	}
}

%>
</body>
</html>
