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

	/**
	 * 修改密码
	 * @param id 用户对象ID
	 * @param usertype 用户类型
	 * @param password 加密后的密码
	 */
	public void updatePassword(long userId, int usertype, String password)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		map.put("usertype", usertype);
		map.put("password", password);
		executeUpdate("updatePassword", map);
	}

	/**
	 * 修改状态
	 * @param id 用户对象ID
	 * @param status 状态
	 */
	public void updateStatus(long id, int status)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		executeUpdate("updateStatus", map);
	}
}
