<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>WebSessionTest</title>
</head>
<body>
<%
try{
String key = String.valueOf(request.getSession().getServletContext().getRealPath("/"));
out.println("<br />WebPath:::" + key + "<br />");
java.util.Map<String, String> map = (java.util.Map<String, String>)session.getAttribute("web");
if(map == null)
{
	map = new java.util.HashMap<String, String>();
	session.setAttribute("web", map);
}
map.put(key, System.currentTimeMillis() + "");

map = (java.util.Map<String, String>)session.getAttribute("web");
java.util.Set<java.util.Map.Entry<String, String>> params = map.entrySet();
for(java.util.Map.Entry ee : params)
{
	String n = String.valueOf(ee.getKey());
	String s = String.valueOf(ee.getValue());
	out.println("<br />&nbsp;&nbsp;&nbsp;&nbsp;" + n + "=" + s + "<br />");
}
}catch(Exception ex){ex.printStackTrace();}
%>
</body>
</html>
