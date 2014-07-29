/**
 * AuthService
 */
package common.auth;

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
}