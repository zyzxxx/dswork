package common.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import dswork.core.util.EncryptUtil;
import dswork.spring.*;
import dswork.web.*;

/**
 * 用户登录类
 * @author skey
 */
public class AuthLogin
{
	public static boolean canSendMsg = true;
	public static final String SessionName_LoginUser = "COMMON_LOGIN_USER";
	private HttpServletRequest request;
	//private HttpServletResponse response;
	private String msg = "";
	
	/**
	 * 构造方法
	 */
	public AuthLogin(PageContext context)
	{
		this.request = (HttpServletRequest)context.getRequest();
		//this.response = (HttpServletResponse)context.getResponse();
	}
	public AuthLogin(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		//this.response = response;
	}

	private boolean isValidCode(String validCode)
	{
		boolean re = true;
		String randcode = (String) this.request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode);
		if(randcode == null || randcode.equals(""))
		{
			this.msg = "验证码已过期!";
			re = false;
		}
		else if(!randcode.toLowerCase().equals(validCode.toLowerCase()))
		{
			this.msg = "验证码输入错误,请重新输入!";
			re = false;
		}
		this.request.getSession().setAttribute(MyAuthCodeServlet.SessionName_Randcode, "");
		return re;
	}

	/**
	 * 登入
	 * @param account 用户帐号
	 * @param password 密码
	 * @param validCode 验证码
	 * @return boolean
	 */
	public boolean login(String account, String password, String validCode)
	{
		if(!this.isValidCode(validCode))
		{
			return false;
		}
		try
		{
			Auth loginUser = ((AuthService)BeanFactory.getBean("authService")).getByAccount(account);
			if(loginUser != null)
			{
				password = EncryptUtil.decodeDes(password, "dswork");
				password = EncryptUtil.encryptMd5(password).toLowerCase();
				if(password.equalsIgnoreCase(loginUser.getPassword().toLowerCase()))
				{
					setLoginUserInfo(loginUser);
					return true;
				}
			}
			this.msg = "用户名或者密码错误，请检查输入！";
			return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	/**
	 * 登出
	 * @param request HttpServletRequest
	 */
	public static void loginOut(HttpServletRequest request)
	{
		request.getSession().setAttribute(SessionName_LoginUser, null);
	}

	/**
	 * 找回密码
	 * @param keyvalue 用户/邮箱
	 * @param validCode 验证码
	 * @return boolean
	 */
	public boolean logpwd(String account, String email, String validCode)
	{
		if(!this.isValidCode(validCode))
		{
			return false;
		}
		try
		{
			List<Auth> authList = ((AuthService)BeanFactory.getBean("authService")).queryListAuth(account, email);
			if(authList.size() == 0)
			{
				this.msg = "找不到相关的用户！";
				return false;
			}
			// 一般一个邮箱很少用超过100次
			if(authList.size() < 100)
			{
				for(Auth m : authList)
				{
					System.out.println(m.getAccount() + " : " + m.getEmail());
					
					
					
					
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	/**
	 * 设置当前的用户登录信息；
	 */
	private void setLoginUserInfo(Auth user)
	{
		this.request.getSession().setAttribute(SessionName_LoginUser, user);
	}

	/**
	 * 得到登录出错的信息
	 * @return String
	 */
	public String getMsg()
	{
		return this.msg;
	}

	/**
	 * 所有方法通过此函数获取当前用户信息
	 */
	public static Auth getLoginUser(HttpServletRequest request, HttpServletResponse response)
	{
		Auth user = (Auth) request.getSession().getAttribute(SessionName_LoginUser);
		if(user != null)
		{
			return user;
		}
		return null;
	}
}
