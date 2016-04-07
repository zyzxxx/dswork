/**
 * 记录用户登录状态
 */
package dswork.sso.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TicketService
{
	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

	/**
	 * 保存用户登录状态，并返回ticket
	 * @param IUser
	 * @return
	 */
	public static String saveSession(String account)
	{
		String ticket = String.valueOf(UUID.randomUUID()) + System.currentTimeMillis();//java.util.UUID.fromString(account + System.currentTimeMillis()).toString();
		map.put(ticket, account);// 这里可以考虑放到数据库或者memcache中
		return ticket;
	}
	public static void removeSession(String ticket)
	{
		map.remove(ticket);
	}

	/**
	 * 根据ticket返回登录用户名，没有则返回null
	 * @param ticket
	 * @return
	 */
	public static String getAccountByTicket(String ticket)
	{
		try
		{
			return map.get(ticket);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static ConcurrentHashMap<String, String> onceMap = new ConcurrentHashMap<String, String>();

	public static String getOnceTicket(String ticket)
	{
		String onceTicket = UUID.randomUUID().toString() + System.currentTimeMillis();
		onceMap.put(onceTicket, ticket);
		return onceTicket;
	}

	/**
	 * 根据onceTicket返回登录用户名，没有则返回null
	 * @param onceTicket
	 * @return String
	 */
	public static String getAccountByOnceTicket(String onceTicket)
	{
		String account = null;
		try
		{
			String ticket = onceMap.get(onceTicket);
			onceMap.remove(onceTicket);// 使用后必须移除
			if(ticket != null)
			{
				account = TicketService.getAccountByTicket(ticket);
			}
		}
		catch (Exception e)
		{
		}
		return account;
	}
}
