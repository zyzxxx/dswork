/**
 * AuthDao
 */
package common.auth;

import java.util.ArrayList;
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

	////////////////////////////
	//管理相关
	////////////////////////////
	public Auth getByAccount(String account)
	{
		return (Auth)executeSelect("getByAccount", account);
	}

	////////////////////////////
	//企业相关
	////////////////////////////
	public Auth getEpByAccount(String account)
	{
		return (Auth)executeSelect("getEpByAccount", account);
	}
	public List<Auth> queryEpList(String account, String email)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account!=null?account:null);
		map.put("email", account==null?String.valueOf(email):null);
		return executeSelectList("queryEpList", map);
	}
	public int updateEpPassword(String account, String password)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("password", password);
		return executeUpdate("updateEpPassword", map);
	}

	////////////////////////////
	//个人相关
	////////////////////////////
	public Auth getPersonByAccount(String account, String idcard)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account!=null?account:null);
		map.put("idcard", account==null?String.valueOf(idcard):null);
		return (Auth)executeSelect("getPersonByAccount", account);
	}
	public List<Auth> queryPersonList(String account, String idcard, String email)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account!=null?account:null);
		map.put("idcard", idcard!=null?idcard:null);
		map.put("email", account==null && idcard == null?String.valueOf(email):null);
		return executeSelectList("queryPersonList", map);
	}
	public int updatePersonPassword(String account, String password)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", account);
		map.put("password", password);
		return executeUpdate("updatePersonPassword", map);
	}
}