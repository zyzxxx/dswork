/**
 * 用户Model
 */
package dswork.common.model;

public class DsCommonUser
{
	// ID
	private Long id = 0L;
	// 帐号
	private String account = "";
	// 密码
	private String password = "";
	// 姓名
	private String name = "";
	// 身份证号
	private String idcard = "";
	// 状态(0,禁止,1,允许)
	private Integer status = 1;
	// 电子邮件
	private String email = "";
	// 手机
	private String mobile = "";
	// 电话
	private String phone = "";
	// CA证书的KEY
	private String cakey = "";
	// 工作证号
	private String workcard = "";
	// 创建时间
	private String createtime = "";
	// 所属单位
	private Long orgpid = 0L;
	// 所属部门
	private Long orgid = 0L;
	// 单位名称
	private String orgpname = "";
	// 部门名称
	private String orgname = "";

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
		this.status = (status == null || status.intValue() != 1) ? 0 : 1;
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

	public String getWorkcard()
	{
		return workcard;
	}

	public void setWorkcard(String workcard)
	{
		this.workcard = workcard;
	}

	public String getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}

	public Long getOrgpid()
	{
		return orgpid;
	}

	public void setOrgpid(Long orgpid)
	{
		this.orgpid = orgpid;
	}

	public Long getOrgid()
	{
		return orgid;
	}

	public void setOrgid(Long orgid)
	{
		this.orgid = orgid;
	}

	public String getOrgpname()
	{
		return orgpname;
	}

	public void setOrgpname(String orgpname)
	{
		this.orgpname = orgpname;
	}

	public String getOrgname()
	{
		return orgname;
	}

	public void setOrgname(String orgname)
	{
		this.orgname = orgname;
	}

	@Override
	public String toString()
	{
		try
		{
			return new StringBuilder().append("{id:").append(id).append(",name:\"").append(name.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\\\"")).append("\"}").toString();
		}
		catch(Exception e)
		{
			return "{id:0,name:\"\"}";
		}
	}
}
