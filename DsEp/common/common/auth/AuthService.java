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
	
	public List<Auth> queryListAuth(String account, String email)
	{
		account = (account == null || account.length() == 0) ? null : account;
		email = (email == null || email.length() == 0) ? null : email;
		if(account == null && email == null)
		{
			return new ArrayList<Auth>();
		}
		return dao.queryListAuth(account, email);
	}
}