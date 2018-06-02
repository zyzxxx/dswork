<%@page language="java" pageEncoding="UTF-8" import="
java.net.URLEncoder,
dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.websso.model.DsWebssoUser,
dswork.websso.service.DsWebssoUserService
"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%!
private String todo(HttpServletRequest request)
{
	try
	{
		MyRequest req = new MyRequest(request);
		String msg = req.getString("msg", "");
		if(msg.length() > 0)
		{
			return msg;
		}
		DsWebssoUser po = new DsWebssoUser();
		req.getFillObject(po);
		DsWebssoUserService service = (DsWebssoUserService)BeanFactory.getBean("dsWebssoUserService");
		service.save(po);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return e.getMessage();
	}
	return "";
}
%><%
request.setAttribute("msg", todo(request));
%><html>
<body>
<c:if test="${msg!=''}">${msg}</c:if>
<c:if test="${msg==''}"><h3>注册成功！</h3></c:if>
</body>
</html>