<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 import="dswork.sso.model.IFunc"%><%!
/**
 * 获取用户菜单功能，过滤掉不显示在菜单上的功能项
 * @param account
 * @return IFunc[]
 */
public static IFunc[] getFuncByUser(String account)
{
	java.util.List<IFunc> list = new java.util.ArrayList<IFunc>();
	if(account != null && !account.equals(""))
	{
		IFunc[] arr = dswork.sso.AuthFactory.getFunctionByUser(account);
		if(arr != null)
		{
			for(IFunc m : arr)
			{
				if(m.getStatus() == 1)
				{
					list.add(m);
				}
			}
		}
		return list.toArray(new IFunc[list.size()]);
	}
	return null;
}
static {
dswork.sso.AuthGlobal.init("http://127.0.0.1:8888/sso/api", "DsCommon", "1");
}
%><%
String jsoncallback  = String.valueOf(request.getParameter("jsoncallback"));
String user = String.valueOf(request.getParameter("user"));%><%=jsoncallback%>([<%
IFunc[] list = getFuncByUser(user);

StringBuilder sb = new StringBuilder(300);
if(list != null)
{
	for(IFunc m : list)
	{
		sb.append(",{id:" + m.getId() + ", pid:" + m.getPid() + ", name:\"" + m.getName() + "\", img:\"\", imgOpen:\"\", url:\"" + m.getUri() + "\"}");
	}
	if(list.length > 0)
	{
		%><%=sb.substring(1)%><%
	}
}
%>])