/**
 * @描述：用户
 */
package dswork.cas.model;

import java.io.Serializable;

public class IUser implements Serializable
{
	private static final long serialVersionUID = 1L;
	// ID
	private Long id = 0L;
	// 帐号
	private String account = "";
	// 姓名
	private String name = "";
	// 手机
	private String mobile = "";
	// 电话
	private String phone = "";
	// 状态(0,禁止,1,允许)
	private Integer status = 1;
	// 电子邮件
	private String email = "";
	// 身份证号
	private String idcard = "";
	// 工作证号
	private String workcard = "";
	// 创建时间
	private String createtime = "";

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setAccount(String account)
	{
		this.account = String.valueOf(account).trim();
	}

	public String getAccount()
	{
		return this.account;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setStatus(Integer status)
	{
		this.status = (status == null || status.intValue() != 1) ? 0 : 1;
	}

	public Integer getStatus()
	{
		return this.status;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getIdcard()
	{
		return idcard;
	}

	public void setIdcard(String idcard)
	{
		this.idcard = idcard;
	}

	public String getWorkcard()
	{
		return workcard;
	}

	public void setWorkcard(String workcard)
	{
		this.workcard = workcard;
	}

	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}

	public String getCreatetime()
	{
		return this.createtime;
	}
}
