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

	private boolean isCode(String authcode)
	{
		boolean re = true;
		String randcode = (String) this.request.getSession().getAttribute(MyAuthCodeServlet.SessionName_Randcode);
		if(randcode == null || randcode.equals(""))
		{
			this.msg = "验证码已过期!";
			re = false;
		}
		else if(!randcode.toLowerCase().equals(authcode.toLowerCase()))
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
		if(!this.isCode(validCode))
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
	public static void logout(HttpServletRequest request)
	{
		request.getSession().setAttribute(SessionName_LoginUser, null);
	}

	/**
	 * 找回密码
	 * @param account 账号
	 * @param email 邮箱
	 * @param validCode 验证码
	 * @return boolean
	 */
	public boolean logpwd(String account, String email, String validCode)
	{
		if(!this.isCode(validCode))
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
			// 邮箱查询如果超出5次
			if(authList.size() < 5)
			{
				for(Auth m : authList)
				{
					System.out.println(m.getAccount() + " : " + m.getEmail());
					String code = String.valueOf(AuthPassport.addAccount(m.getAccount()));
					String msg = "此验证码有限期为30分钟<br />您需要找回密码的账号是：" + m.getAccount() + "<br />" + "你的账号验证码是：" + code;
					common.email.EmailUtil.send(null, null, null, m.getEmail(), "", "", "找回密码", common.email.EmailUtil.createMimeMultipart(msg));
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
	 * 重置密码
	 * @param keyvalue 账号
	 * @param password 密码
	 * @param code 账号验证码
	 * @return boolean
	 */
	public boolean logpassword(String account, String password, String code)
	{
		String check = AuthPassport.getAccount(code);
		if(!check.equals(account.toLowerCase()))
		{
			return false;
		}
		try
		{
			password = EncryptUtil.encryptMd5(password).toLowerCase();
			((AuthService)BeanFactory.getBean("authService")).updateAuthPassword(account, password);
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
