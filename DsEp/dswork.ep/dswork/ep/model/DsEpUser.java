/**
 * 企业用户Model
 */
package dswork.ep.model;

public class DsEpUser
{
	// 主键
	private Long id = 0L;
	// 企业编码
	private String qybm = "";
	// 账号
	private String account = "";
	// 密码
	private String password = "";
	// 姓名
	private String name = "";
	// 身份证号
	private String idcard = "";
	// 状态
	private Integer status = 0;
	// 电子邮件
	private String email = "";
	// 手机
	private String mobile = "";
	// 电话
	private String phone = "";
	// 工作证号
	private String workcard = "";
	// CA证书的KEY
	private String cakey = "";
	// 创建时间
	private String createtime = "";
	// 所属单位
	private String ssdw = "";
	// 所属部门
	private String ssbm = "";
	// 传真
	private String fax = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getQybm()
	{
		return qybm;
	}

	public void setQybm(String qybm)
	{
		this.qybm = qybm;
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

	public String getIdcard()
	{
		return idcard;
	}

	public void setIdcard(String idcard)
	{
		this.idcard = idcard;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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

	public String getWorkcard()
	{
		return workcard;
	}

	public void setWorkcard(String workcard)
	{
		this.workcard = workcard;
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

	public String getSsdw()
	{
		return ssdw;
	}

	public void setSsdw(String ssdw)
	{
		this.ssdw = ssdw;
	}

	public String getSsbm()
	{
		return ssbm;
	}

	public void setSsbm(String ssbm)
	{
		this.ssbm = ssbm;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}
}
