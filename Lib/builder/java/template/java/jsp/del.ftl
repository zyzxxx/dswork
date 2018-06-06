<%@page language="java" pageEncoding="UTF-8" import="java.util.*,${frame}.web.MyRequest,${frame}.core.util.*,
${namespace}.MyFactory"%><%
MyRequest req = new MyRequest(request);
try
{
	MyFactory.get${model}Service().deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
	out.print(1);
}
catch (Exception e)
{
	e.printStackTrace();
	out.print("0:" + e.getMessage());
}
%>