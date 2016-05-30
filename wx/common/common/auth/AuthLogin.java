package common.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


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

	/**
	 * 登入
	 * @param account 用户帐号
	 * @param password 密码
	 * @param logintype 用户类型
	 * @param validCode 验证码
	 * @return boolean
	 */
	public boolean login(String account, String password, int logintype, String validCode)
	{
		try
		{
			Auth loginUser = null;
			if(logintype == -1)
			{
			}
			else if(logintype == 0)
			{
			}
			else if(logintype == 1)
			{
			}
			if(loginUser != null)
			{
				if(loginUser.getLoginstatus() == 1)// 正常
				{
					if(password.equalsIgnoreCase(loginUser.getPassword().toLowerCase()))
					{
						setLoginUserInfo(loginUser);
						return true;
					}
				}
				else
				{
					this.msg = "用户已禁用！";
				}
			}
			else
			{
				this.msg = "用户名或者密码错误，请检查输入！";
			}
		}
		catch(Exception ex)
		{
		}
		return false;
	}

	/**
	 * 登出
	 * @param request HttpServletRequest
	 */
	public static void logout(HttpServletRequest request)
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
