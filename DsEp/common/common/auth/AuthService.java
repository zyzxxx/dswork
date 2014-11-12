/**
 * AuthService
 */
package common.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("authService")
public class AuthService
{
	@Autowired
	private AuthDao dao;

	public Auth getByAccount(String account)
	{
		return dao.getByAccount(account);
	}

	public Auth getEpByAccount(String account)
	{
		return dao.getEpByAccount(account);
	}
	public List<Auth> queryEpList(String account, String email)
	{
		account = (account == null || account.length() == 0) ? null : account;
		email = (email == null || email.length() == 0) ? null : email;
		if(account == null && email == null)
		{
			return new ArrayList<Auth>();
		}
		return dao.queryEpList(account, email);
	}
	public int updateEpPassword(String account, String password)
	{
		return dao.updateEpPassword(account, password);
	}

	public Auth getPersonByAccount(String account)
	{
		return dao.getPersonByAccount(account);
	}
	public List<Auth> queryPersonList(String account, String email)
	{
		account = (account == null || account.length() == 0) ? null : account;
		email = (email == null || email.length() == 0) ? null : email;
		if(account == null && email == null)
		{
			return new ArrayList<Auth>();
		}
		return dao.queryPersonList(account, email);
	}
	public int updatePersonPassword(String account, String password)
	{
		return dao.updatePersonPassword(account, password);
	}
}
