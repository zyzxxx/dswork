<%@page language="java" pageEncoding="UTF-8" import="dswork.web.*,dswork.core.util.*,dswork.sso.model.*,dswork.sso.service.*"%><%
String path = request.getContextPath();
response.setHeader("P3P", "CP=CAO PSA OUR");
response.setHeader("Access-Control-Allow-Origin", "*");
response.setCharacterEncoding("UTF-8");
MyRequest req = new MyRequest(request);
String account = req.getString("account").toLowerCase();
String password = req.getString("password");
String authcode = req.getString("authcode");
String code = req.getString("code");
String serviceURL = req.getString("service", request.getContextPath() + "/ticket.jsp");
try{
	AuthFactoryService service  = (AuthFactoryService)dswork.spring.BeanFactory.getBean("authFactoryService");
	String msg = "用户名或密码错误！";
	String randcode = String.valueOf(request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode)).trim();
	if(randcode.equals("null") || randcode.equals("")){
		msg = "验证码已过期";
	}
	else if(!randcode.toLowerCase().equals(authcode.toLowerCase())){
		msg = "验证码输入错误,请重新输入";
	}
	else{
		request.getSession().setAttribute(dswork.web.MyAuthCodeServlet.SessionName_Randcode, "");// 对了再清除
		LoginUser user = service.getLoginUserByAccount(account);
		if(user != null){
			if(user.getStatus() != 1){
				msg = "用户已禁用，请联系管理员！";
			}
			else if((EncryptUtil.encryptMd5(user.getPassword()+authcode).equals(password))){
				String cookieTicket = dswork.sso.controller.AuthController.putLoginInfo(request, response, user.getAccount(), user.getName());
				try{
					service.saveLogLogin(cookieTicket, dswork.sso.controller.AuthController.getClientIp(request), user.getAccount(), user.getName(), true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				// 成功就跳到切换系统视图
				// 无需登录，生成ticket给应用去登录
				if(serviceURL.startsWith(request.getContextPath() + "/password")){
					response.sendRedirect(serviceURL);
				}
				else{
					response.sendRedirect(serviceURL += ((serviceURL.indexOf("?") != -1) ? "&ticket=" : "?ticket=") + TicketService.getOnceTicket(cookieTicket));
				}
				return;
			}
		}
	}
	// 失败则转回来
	serviceURL = java.net.URLEncoder.encode(serviceURL, "UTF-8");
	request.setAttribute("service", serviceURL);
	request.setAttribute("errorMsg", msg);
	try{
		service.saveLogLogin("", dswork.sso.controller.AuthController.getClientIp(request), account, "", false);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
catch(Exception e){
	e.printStackTrace();
}
%><script type="text/javascript">
alert('${errorMsg}');
location.href='/websso/gzmice/login.jsp?service=${service}';
</script>