/**
 * 记录用户登录状态
 */
package dswork.cas.service;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TicketService
{
	private static Map<String, String> map = new HashMap<String, String>();

	/**
	 * 保存用户登录状态，并返回ticket
	 * @param IUser
	 * @return
	 */
	public static synchronized String saveSession(String account)
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
}
