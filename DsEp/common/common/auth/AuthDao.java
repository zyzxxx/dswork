/**
 * AuthDao
 */
package common.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;

@Repository("authDao")
@SuppressWarnings("all")
public class AuthDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return Auth.class;
	}
	
	public Auth getByAccount(String account)
	{
		return (Auth)executeSelect("getByAccount", account);
	}
	
	public List<Auth> queryListAuth(String account, String email)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("email", email);
		return executeSelectList("queryListAuth", map);
	}
	
	public int updateAuthPassword(String account, String password)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("password", password);
		return executeUpdate("updateAuthPassword", map);
	}
}