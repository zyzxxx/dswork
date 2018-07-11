/**
 * DS_WEBSSO_USERModel
 */
package dswork.websso.model;
public class DsWebssoUser
{
	//主键
	private Long id = 0L;
	//身份证号
	private String idcard = "";
	//用户账号
	private String useraccount = "";
	//SSO账号
	private String ssoaccount = "";
	//城市
	private String city = "";
	//国家
	private String country = "";
	//省份
	private String province = "";
	//姓名
	private String name = "";
	//性别(0未知,1男,2女)
	private int sex = 0;
	//电子邮件
	private String email = "";
	//手机
	private String mobile = "";
	//电话
	private String phone = "";
	//头像
	private String avatar = "";
	//qq登陆的openid
	private String openidqq = "";
	//支付宝登陆的openid
	private String openidalipay = "";
	//微信登陆的openid
	private String openidwechat = "";

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

	public String getUseraccount()
	{
		return useraccount;
	}

	public void setUseraccount(String useraccount)
	{
		this.useraccount = useraccount;
	}

	public String getSsoaccount()
	{
		return ssoaccount;
	}

	public void setSsoaccount(String ssoaccount)
	{
		this.ssoaccount = ssoaccount;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
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

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getOpenidqq()
	{
		return openidqq;
	}

	public void setOpenidqq(String openidqq)
	{
		this.openidqq = openidqq;
	}

	public String getOpenidalipay()
	{
		return openidalipay;
	}

	public void setOpenidalipay(String openidalipay)
	{
		this.openidalipay = openidalipay;
	}

	public String getOpenidwechat()
	{
		return openidwechat;
	}

	public void setOpenidwechat(String openidwechat)
	{
		this.openidwechat = openidwechat;
	}
}