/**
 * AuthDao
 */
package common.auth;

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
}