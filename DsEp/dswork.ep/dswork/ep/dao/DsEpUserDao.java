/**
 * 企业用户Dao
 */
package dswork.ep.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import dswork.ep.model.DsEpUser;

@Repository
@SuppressWarnings("all")
public class DsEpUserDao extends BaseDao<DsEpUser, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsEpUser.class;
	}

	public DsEpUser getUserByAccount(String account)
	{
		return (DsEpUser) this.executeSelect("getUserByAccount", account);
	}

	public boolean isExists(String account)
	{
		DsEpUser user = getUserByAccount(account);
		if(user != null && user.getAccount().length() > 0 && user.getAccount() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void updatePassword(long userId, int usertype, String password)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		map.put("usertype", usertype);
		map.put("password", password);
		executeUpdate("updatePassword", map);
	}
}
