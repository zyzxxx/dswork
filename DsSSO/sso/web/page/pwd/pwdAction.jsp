<%@page language="java" pageEncoding="UTF-8" import="java.util.*,dswork.web.*,dswork.core.util.*,
dswork.spring.*,dswork.sso.service.*,dswork.sso.model.LoginUser"%><%
try
{
	String account = "";
	boolean isOk = false;
	MyCookie cookie = new MyCookie(request, response);
	String cookieTicket = cookie.getValue(dswork.sso.listener.SessionListener.COOKIETICKET);
	if(cookieTicket != null){// 有cookie存在
		account = TicketService.getAccountByTicket(cookieTicket);
		if(account != null){isOk = true;}
	}
	MyRequest req = new MyRequest(request);
	if(isOk){
		String authcode = req.getString("authcode");
		String randcode = String.valueOf(request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode)).trim();
		if(randcode.equals("null") || randcode.equals("")){
			out.print("0:验证码已过期");
		}
		else if(!randcode.toLowerCase().equals(authcode.toLowerCase())){
			out.print("0:验证码输入错误,请重新输入");
		}
		else{
			AuthFactoryService service = (AuthFactoryService)dswork.spring.BeanFactory.getBean("authFactoryService");
			LoginUser loginUser = service.getLoginUserByAccount(account);
			if(loginUser != null){
				String password = req.getString("password");
				if(password.equals("")){
					out.print("0:密码不能为空");
				}
				else{
					if(EncryptUtil.encryptMd5(req.getString("oldpassword")).equals(loginUser.getPassword().toUpperCase())){
						service.updatePassword(account, password);
						out.print("1:密码修改成功");
					}
					else
					{
						out.print("0:原密码错误");
					}
				}
			}
			else{
				out.print("0:请先登录");
			}
		}
	}
	else{
		out.print("0:非法访问");
	}
}
catch(Exception e)
{
	out.print("0:" + e.getMessage());
}
%>