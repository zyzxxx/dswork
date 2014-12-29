/**
 * 个人用户Dao
 */
package dswork.person.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import dswork.person.model.DsPersonUser;

@Repository
@SuppressWarnings("all")
public class DsPersonUserDao extends BaseDao<DsPersonUser, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsPersonUser.class;
	}

	public DsPersonUser getUserByAccount(String account)
	{
		return (DsPersonUser) this.executeSelect("getUserByAccount", account);
	}
	
	public DsPersonUser getUserByIdcard(String idcard)
	{
		return (DsPersonUser) this.executeSelect("getUserByIdcard", idcard);
	}

	public boolean isExistsAccount(String account)
	{
		DsPersonUser user = getUserByAccount(account);
		if(user != null && user.getAccount().length() > 0 && user.getAccount() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isExistsIdcard(String idcard)
	{
		DsPersonUser user = getUserByIdcard(idcard);
		if(user != null && user.getIdcard().length() > 0 && user.getIdcard() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void updatePassword(long userId, int status, String password)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		map.put("status", status);
		map.put("password", password);
		executeUpdate("updatePassword", map);
	}
}
