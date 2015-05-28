package auth.action;

import gwen.devwork.BaseAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import auth.Auth;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/auth")
public class AuthLoginAction extends BaseAction<Auth> 
{
	public static final String SessionName_LoginUser = "COMMON_LOGIN_USER";
	
	private Auth user;
	public Auth getUser() 
	{
		return user;
	}
	public void setUser(Auth user) 
	{
		this.user = user;
	}

	@Action(value = "login", results = {
			@Result(name = "success", location = "/index.jsp"),
			@Result(name = "error", location = "/login.jsp") })
	@Override
	public String execute() throws Exception 
	{
		if (user.getAccount().equals("admin") && user.getPassword().equals("admin")) {
			getSession().setAttribute(SessionName_LoginUser, user);
			return "success";
		} else {
			return "error";
		}
	}
}