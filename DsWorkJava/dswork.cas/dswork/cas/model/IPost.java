/**
 * @描述：岗位
 */
package dswork.cas.model;

import java.io.Serializable;

public class IPost implements Serializable
{
	private static final long serialVersionUID = 1L;
	// 主键
	private Long id = 0L;
	// 所属组织机构ID
	private Long orgid = 0L;
	// 名称
	private String name = "";
	// 备注
	private String memo = "";
	// 成立时间
	private String foundtime = "";

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setOrgid(Long orgid)
	{
		this.orgid = orgid;
	}

	public Long getOrgid()
	{
		return this.orgid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getMemo()
	{
		return this.memo;
	}

	public void setFoundtime(String foundtime)
	{
		this.foundtime = foundtime;
	}

	public String getFoundtime()
	{
		return this.foundtime;
	}
}
