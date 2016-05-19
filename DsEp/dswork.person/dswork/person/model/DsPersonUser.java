/**
 * 个人用户Model
 */
package dswork.person.model;

public class DsPersonUser
{
	// 主键
	private Long id = 0L;
	// 身份证号
	private String idcard = "";
	// 账号
	private String account = "";
	// 密码
	private String password = "";
	// 姓名
	private String name = "";
	// 状态(1启用,0禁用)
	private Integer status = 0;
	// 用户类型
	private Integer usertype = 0;
	// 电子邮件
	private String email = "";
	// 手机
	private String mobile = "";
	// 电话
	private String phone = "";
	// CA证书的KEY
	private String cakey = "";
	// 创建时间
	private String createtime = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getIdcard()
	{
		return idcard;
	}

	public void setIdcard(String idcard)
	{
		this.idcard = idcard;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getUsertype()
	{
		return usertype;
	}

	public void setUsertype(Integer usertype)
	{
		this.usertype = usertype;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getCakey()
	{
		return cakey;
	}

	public void setCakey(String cakey)
	{
		this.cakey = cakey;
	}

	public String getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}
}
