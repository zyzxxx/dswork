package dswork.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.util.EncryptUtil;
import dswork.sso.dao.AuthFactoryDao;
import dswork.sso.model.IFunc;
import dswork.sso.model.IOrg;
import dswork.sso.model.ISystem;
import dswork.sso.model.IUser;
import dswork.sso.model.LoginUser;

@Service
// @SuppressWarnings("unchecked")
public class AuthFactoryService
{
	private AuthFactoryDao dao;
//	private CasCacheService cache;

	@Autowired
	public void setAuthFactoryDao(AuthFactoryDao dao)
	{
		this.dao = dao;
	}

//	@Autowired
//	public void setCasCacheService(CasCacheService cache)
//	{
//		this.cache = cache;
//	}
	public void saveLogLogin(String ticket, String ip, String account, String name, boolean isSuccess)
	{
		dao.saveLogLogin(ticket, ip, account, name, isSuccess);
	}
	public void saveLogLogout(String ticket, boolean isTimeout, boolean isUpdatePassword)
	{
		dao.saveLogLogout(ticket, isTimeout, isUpdatePassword);
	}
	public void updatePassword(String account, String password) throws Exception
	{
		dao.updatePassword(account, EncryptUtil.encryptMd5(password));
	}
	
	public LoginUser getLoginUserByAccount(String account)
	{
		return dao.getLoginUserByAccount(account);
	}
	
	public LoginUser getLoginUserByCAKEY(String cakey)
	{
		return dao.getLoginUserByCAKEY(cakey);
	}

	public IOrg getOrgByOrgId(String orgid)
	{
		if (orgid == null || "".equals(orgid))
		{
			return null;
		}
		return dao.getOrgByOrgid(orgid);
	}

	public IOrg[] queryOrgByOrgPid(String orgpid)
	{
		try
		{
			List<IOrg> list = dao.queryOrgByOrgPid(Long.parseLong(orgpid));
			if (list != null)
			{
				return list.toArray(new IOrg[list.size()]);
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public IUser getUserByAccount(String account)
	{
		if (account == null || "".equals(account))
		{
			return null;
		}
		IUser user = null;
		try
		{
//			if (cache != null && cache.containsKey("account." + account))
//			{
//				user = (IUser) cache.get("account." + account);
//			}
//			else
//			{
				user = (IUser) dao.getUserByAccount(account);
//				if (cache != null && user != null)
//				{
//					cache.put("account." + account, user, 600);// 10分钟
//				}
//			}
		}
		catch (Exception e)
		{
			user = (IUser) dao.getUserByAccount(account);
		}
		return user;
	}

	public IUser[] queryUserByPostid(String postid)
	{
		List<IUser> list = dao.queryUserByPostid(postid);
		if (list != null)
		{
			return list.toArray(new IUser[list.size()]);
		}
		return null;
	}

	public IOrg[] getPostByUserId(Long userid)
	{
		List<IOrg> list = dao.queryPostByUserId(userid);
		if (list != null)
		{
			return list.toArray(new IOrg[list.size()]);
		}
		return null;
	}

	public ISystem getSystem(String systemAlias)
	{
		return dao.getSystem(systemAlias);
	}

	public ISystem[] getSystemByUser(String account)
	{
		List<ISystem> list = dao.querySystemByAccount(account);
		if (list != null)
		{
			return list.toArray(new ISystem[list.size()]);
		}
		return null;
	}

	public IFunc[] getFuncBySystemAlias(String systemAlias)
	{
		List<IFunc> list = dao.queryFuncBySystemAlias(systemAlias);
		if (list != null)
		{
			return list.toArray(new IFunc[list.size()]);
		}
		return null;
	}

	public IFunc[] getFuncBySystemAliasAndAccount(String systemAlias, String account)
	{
//		String key = systemAlias + "." + account;
//		if (cache != null && cache.containsKey(key))
//		{
//			return (IFunc[]) cache.get(key);
//		}
//		else
//		{
			List<IFunc> list = dao.getFuncBySystemAliasAndAccount(systemAlias, account);
			IFunc[] arr = list.toArray(new IFunc[list.size()]);
//			if (cache != null)
//			{
//				try
//				{
//					cache.put(key, arr, 600);
//				}
//				catch (Exception e)
//				{
//					System.out.println("myerror:");
//					e.printStackTrace();
//				}
//			}
			return arr;
//		}
	}

	public IFunc[] getFuncBySystemAliasAndPostid(String systemAlias, String postid)
	{
		List<IFunc> list = dao.getFuncBySystemAliasAndPostid(systemAlias, postid);
		return list.toArray(new IFunc[list.size()]);
	}
}
