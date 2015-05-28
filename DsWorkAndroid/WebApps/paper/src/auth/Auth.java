package auth;

// 根据实际的需要，可以增减里面的属性
public class Auth
{
	private Long id;
	private String account;
	private String password;
//	private String name;
//	private String email;
//	private Integer logintype;// -1系统用户，0企业用户，1个人用户
//	private Integer loginstatus;// 1正常，0禁用
//	private Integer usertype;// 系统（），企业（1管理员,0普通用户），个人（）
//	private String qybm;// 企业编码，后台用户为空
//	private String ssdw;// 所属单位
//	private String ssbm;// 所属部门

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString() {
		return "Auth [id=" + id + ", account=" + account + ", password="
				+ password + "]";
	}

//	public String getName()
//	{
//		return name;
//	}
//
//	public void setName(String name)
//	{
//		this.name = name;
//	}
//
//	public String getEmail()
//	{
//		return email;
//	}
//
//	public void setEmail(String email)
//	{
//		this.email = email;
//	}
//
//	public Integer getLogintype()
//	{
//		return logintype;
//	}
//
//	public void setLogintype(Integer logintype)
//	{
//		this.logintype = logintype;
//	}
//
//	public Integer getLoginstatus()
//	{
//		return loginstatus;
//	}
//
//	public void setLoginstatus(Integer loginstatus)
//	{
//		this.loginstatus = loginstatus;
//	}
//
//	public Integer getUsertype()
//	{
//		return usertype;
//	}
//
//	public void setUsertype(Integer usertype)
//	{
//		this.usertype = usertype;
//	}
//
//	public String getQybm()
//	{
//		return qybm;
//	}
//
//	public void setQybm(String qybm)
//	{
//		this.qybm = qybm;
//	}
//
//	public String getSsdw()
//	{
//		return ssdw;
//	}
//
//	public void setSsdw(String ssdw)
//	{
//		this.ssdw = ssdw;
//	}
//
//	public String getSsbm()
//	{
//		return ssbm;
//	}
//
//	public void setSsbm(String ssbm)
//	{
//		this.ssbm = ssbm;
//	}

//	public boolean isUser()
//	{
//		return getLogintype().intValue() == 1;
//	}
//
//	public boolean isEnterprise()
//	{
//		return getLogintype().intValue() == 0;
//	}
//
//	public boolean isAdmin()
//	{
//		return getLogintype().intValue() == -1;
//	}
	
}
