/**
 * 用户角色Model
 */
package dswork.common.model;

public class DsCommonUserRole
{
	// ID
	private Long id = 0L;
	// 用户ID
	private Long userid = 0L;
	// 用户名称
	private String username = "";
	// 用户账号
	private String useraccount = "";
	// 角色ID
	private Long roleid = 0L;
	// 角色名称
	private String rolename = "";
	// 系统ID
	private Long systemid = 0L;
	// 系统名称
	private String systemname = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getUserid()
	{
		return userid;
	}

	public void setUserid(Long userid)
	{
		this.userid = userid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUseraccount()
	{
		return useraccount;
	}

	public void setUseraccount(String useraccount)
	{
		this.useraccount = useraccount;
	}

	public Long getRoleid()
	{
		return roleid;
	}

	public void setRoleid(Long roleid)
	{
		this.roleid = roleid;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}

	public Long getSystemid()
	{
		return systemid;
	}

	public void setSystemid(Long systemid)
	{
		this.systemid = systemid;
	}

	public String getSystemname()
	{
		return systemname;
	}

	public void setSystemname(String systemname)
	{
		this.systemname = systemname;
	}
}
