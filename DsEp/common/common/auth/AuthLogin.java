package common.auth;

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
	private String errorMsg = "";
	
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
			this.errorMsg = "验证码已过期!";
			re = false;
		}
		else if(!randcode.toLowerCase().equals(validCode.toLowerCase()))
		{
			this.errorMsg = "验证码输入错误,请重新输入!";
			re = false;
		}
		this.request.getSession().setAttribute(MyAuthCodeServlet.SessionName_Randcode, "");
		return re;
	}

	/**
	 * 登录
	 * @param account 用户帐号
	 * @param password 密码
	 * @param type 用户类型
	 * @param validCode 验证密码
	 * @return
	 */
	public boolean login(String account, String password, String validCode)
	{
		if(!this.isValidCode(validCode))
		{
			return false;
		}
		try
		{
			Auth loginUser = loadAuth(account, password);// 这个方法随便改
			this.errorMsg = "用户名或者密码错误，请检查输入！";
			if(loginUser == null)
			{
				return false;
			}
			setLoginUserInfo(loginUser);
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	public static void loginOut(HttpServletRequest request)
	{
		request.getSession().setAttribute(SessionName_LoginUser, null);
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
	 * @return
	 */
	public String getErrorMssage()
	{
		return this.errorMsg;
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
	
	// 以下为扩展方法
	private static Auth loadAuth(String account, String password)
	{
		Auth user = ((AuthService)BeanFactory.getBean("authService")).getByAccount(account);
		if(user != null)
		{
			password = EncryptUtil.encryptMd5(password).toLowerCase();
			if(password.equals(user.getPassword().toLowerCase()))
			{
				return user;
			}
		}
		return null;
	}
}
