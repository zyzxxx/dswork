<%@page language="java" pageEncoding="UTF-8"
import="java.util.*, java.io.*"
%><%
request.setCharacterEncoding("UTF-8");
String fname = request.getParameter("f");
//response.setContentType("application/octet-stream");
InputStream is = request.getInputStream();
try
{
	int size = 0, len = 0;
	byte[] tmp = new byte[4096];
	File f = new File("F:/WorkServer/tomcat6.0.33_11111/webapps/ROOT/data/" + fname);
	DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
	while((len = is.read(tmp)) != -1)
	{
		dos.write(tmp, 0, len);
		size += len;
	}
	dos.flush();
	dos.close();
}
catch(IOException e)
{
	e.printStackTrace();
}
%>