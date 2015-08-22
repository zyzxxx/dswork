/**
 * 获取用户信息
 */
package dswork.cas.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.core.util.EncryptUtil;
import dswork.web.MyCookie;
import dswork.web.MyRequest;
import dswork.cas.service.TicketService;
import dswork.cas.service.CasFactoryService;
import dswork.cas.listener.SessionListener;
import dswork.cas.model.LoginUser;

@Controller
public class CasController
{
	static Logger log = LoggerFactory.getLogger(CasController.class.getName());
	
	@Autowired
	private CasFactoryService service;
	private static Map<String, String> onceMap = new HashMap<String, String>();

	/**
	 * 用户登录系统的入口
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MyRequest req = new MyRequest(request);
		String serviceURL = req.getString("service", request.getContextPath() + "/ticket.jsp");
		if(log.isDebugEnabled())
		{
			log.debug(serviceURL);
		}
		MyCookie cookie = new MyCookie(request, response);
		String cookieTicket = cookie.getValue(SessionListener.COOKIETICKET);
		if(cookieTicket != null)// 有cookie存在
		{
			String account;
			account = TicketService.getAccountByTicket(cookieTicket);
			if(account != null)
			{
				// 无需登录，生成ticket给应用去登录
				response.sendRedirect(serviceURL += ((serviceURL.indexOf("?") != -1) ? "&ticket=" : "?ticket=") + getOnceTicket(cookieTicket));
				return null;
			}
			removeLoginInfo(request, response);// 把相关信息删除
		}
		request.setAttribute("errorMsg", "");
		request.setAttribute("code", "ZHN3b3Jr");
		request.setAttribute("service", serviceURL);
		return "/login.jsp";
	}

	/**
	 * 用户登录系统的验证方法
	 */
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		String account = req.getString("account").toLowerCase();
		String password = req.getString("password");
		String serviceURL = req.getString("service", request.getContextPath() + "/ticket.jsp");
		if(log.isDebugEnabled())
		{
			log.debug(serviceURL);
		}
		try
		{
			LoginUser user = service.getLoginUserByAccount(account);
			String msg = "用户名或密码错误！";
			password = EncryptUtil.decodeDes(password, "ZHN3b3Jr");
			if(user != null)
			{
				if(user.getStatus() != 1)// Status:1允许，0禁止
				{
					msg = "用户已禁用，请联系管理员！";
				}
				else if(EncryptUtil.encryptMd5(password).equals(user.getPassword()))
				{
					String ticket = putLoginInfo(request, response, user.getAccount());
					try
					{
						service.saveLogLogin(ticket, request.getRemoteAddr(), user.getAccount(), user.getName(), true);
					}
					catch(Exception logex)
					{
					}
					// 成功就跳到切换系统视图
					response.sendRedirect(serviceURL += ((serviceURL.indexOf("?") != -1) ? "&ticket=" : "?ticket=") + getOnceTicket(ticket));
					return null;
				}
			}
			// 失败则转回来
			request.setAttribute("account", account);
			request.setAttribute("password", password);
			request.setAttribute("service", serviceURL);
			request.setAttribute("errorMsg", msg);
			request.setAttribute("code", "ZHN3b3Jr");
			try
			{
				service.saveLogLogin("", request.getRemoteAddr(), account, "", false);
			}
			catch(Exception logex)
			{
			}
			return "/login.jsp";
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 用户登出系统
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			MyCookie cookie = new MyCookie(request, response);
			String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
			service.saveLogLogout(String.valueOf(ticket), false, false);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		removeLoginInfo(request, response);// 试着删除
		MyRequest req = new MyRequest(request);
		String serviceURL = java.net.URLEncoder.encode(req.getString("service", request.getContextPath() + "/ticket.jsp"), "UTF-8");
		response.sendRedirect(request.getContextPath() + "/login.htm?service=" + String.valueOf(serviceURL));
		return;
	}

	// 明细
	@RequestMapping("/validate")
	public void validate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String account = null;
		MyRequest req = new MyRequest(request);
		String onceTicket = req.getString("ticket");
		if(onceTicket.length() > 0)
		{
			String ticket = onceMap.get(onceTicket);
			onceMap.remove(onceTicket);// 使用后必须移除
			if(ticket != null)
			{
				account = TicketService.getAccountByTicket(ticket);
			}
		}
		response.setContentType("text/plain");
		response.getWriter().print(account != null ? account : "");
		response.getWriter().close();
	}

	@RequestMapping("/index")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendRedirect(request.getContextPath() + "/login.htm");
	}

	private String putLoginInfo(HttpServletRequest request, HttpServletResponse response, String account)
	{
		MyCookie cookie = new MyCookie(request, response);
		String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
		TicketService.removeSession(ticket);// 删除
		ticket = TicketService.saveSession(account);
		cookie.addCookie(SessionListener.COOKIETICKET, ticket);// 更新
		return ticket;
	}

	private void removeLoginInfo(HttpServletRequest request, HttpServletResponse response)
	{
		MyCookie cookie = new MyCookie(request, response);
		String ticket = String.valueOf(cookie.getValue(SessionListener.COOKIETICKET));
		TicketService.removeSession(ticket);// 删除
		cookie.delCookie(SessionListener.COOKIETICKET);
	}
	
	private String getOnceTicket(String ticket)
	{
		String onceTicket = UUID.randomUUID().toString() + System.currentTimeMillis();
		onceMap.put(onceTicket, ticket);
		return onceTicket;
	}
}
