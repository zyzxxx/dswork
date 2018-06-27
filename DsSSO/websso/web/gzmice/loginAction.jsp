<%@page language="java" pageEncoding="UTF-8" import="
dswork.spring.BeanFactory,
dswork.web.MyRequest,
dswork.web.MyAuthCodeServlet,
dswork.websso.service.DsCommonUserService,
dswork.websso.model.DsCommonUser,
dswork.core.util.EncryptUtil
"%><%!
private String isCode(HttpServletRequest request, String authcode)
{
	String randcode = (String) request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode);
	if(randcode == null || randcode.equals(""))
	{
		return "验证码已过期!";
	}
	if(!randcode.toLowerCase().equals(authcode.toLowerCase()))
	{
		return "验证码输入错误,请重新输入!";
	}
	request.getSession().setAttribute(MyAuthCodeServlet.SessionName_Randcode, "");
	return "";
}
%><%
	MyRequest req = new MyRequest(request);
	String account = req.getString("account");
	String password = req.getString("password");
	String authcode = req.getString("authcode");
	String msg = isCode(request, authcode);
	if(msg.length() > 0)
	{
		out.print("0:" + msg);
		return;
	}

	try
	{
		DsCommonUserService service = (DsCommonUserService)BeanFactory.getBean("dsCommonUserService");
		DsCommonUser po = service.getByAccount(account);
		if(po == null)
		{
			out.print("0:用户不存在");
			return;
		}
		password = EncryptUtil.decodeDes(password, "iVIWm5");
		password = EncryptUtil.encryptMd5(password).toLowerCase();
		if(password.equals(po.getPassword()))
		{
			out.print("0:密码错误");
			return;
		}
		request.getSession().setAttribute("COMMON_LOGIN_USER", null);
		out.print(1);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		out.print("0:" + e.getMessage());
	}
%>