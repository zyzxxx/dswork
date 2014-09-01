/**
 * 企业Model
 */
package dswork.ep.model;

public class DsEpEnterprise
{
	// ID
	private Long id = 0L;
	// 企业名称
	private String name = "";
	// 所属辖区
	private String ssxq = "";
	// 企业编码
	private String qybm = "";
	// 状态
	private Integer status = 0;
	// 类型
	private String type = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSsxq()
	{
		return ssxq;
	}

	public void setSsxq(String ssxq)
	{
		this.ssxq = ssxq;
	}

	public String getQybm()
	{
		return qybm;
	}

	public void setQybm(String qybm)
	{
		this.qybm = qybm;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
