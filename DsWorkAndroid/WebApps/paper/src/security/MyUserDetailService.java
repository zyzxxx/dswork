package security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SuppressWarnings("deprecation")
public class MyUserDetailService implements UserDetailsService 
{
	// 登陆验证时，通过username获取用户的所有权限信息，
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException 
	{
		System.out.println("Spring Security登录验证...");
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		GrantedAuthorityImpl auth1 = new GrantedAuthorityImpl("ROLE_ADMIN");
		GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl("ROLE_NO");

		//从数据库获取用户帐号和密码
		String _username = "ole";
		String _userpwd = "111";
		
		if (username.equals(_username))
		{
			auths = new ArrayList<GrantedAuthority>();
			auths.add(auth1);
			auths.add(auth2);
		}

		User user = new User(username, _userpwd, true, true, true, true, auths);
		return user;
	}
}