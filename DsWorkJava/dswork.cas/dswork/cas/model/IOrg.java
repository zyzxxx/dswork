/**
 * @描述：组织机构 
 */
package dswork.cas.model;

import java.io.Serializable;

public class IOrg implements Serializable
{
	private static final long serialVersionUID = 1L;
	// 部门ID
	private Long id = 0L;
	// 上级ID(本表,所属机关或单位)
	private Long pid = 0L;
	// 分组信息
	private Long gid = 0L;
	// 名称
	private String name = "";
	// 职责范围
	private String dutyscope = "";
	// 备注
	private String memo = "";
	// 是否机构(1机关/单位,0机构/部门)
	private Integer status = 0;
	// 所属区域ID(status=1时必填)
	private String districtcode = "";
	// 成立时间
	private String foundtime = "";
	// 排序
	private Long seq = 0L;

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setPid(Long pid)
	{
		this.pid = (pid == null || pid <= 0) ? 0 : pid;
	}

	public Long getPid()
	{
		return this.pid;
	}

	public Long getGid()
	{
		return gid;
	}

	public void setGid(Long gid)
	{
		this.gid = gid;
	}

	public void setName(String name)
	{
		this.name = String.valueOf(name).trim();
	}

	public String getName()
	{
		return this.name;
	}

	public void setDutyscope(String dutyscope)
	{
		this.dutyscope = dutyscope;
	}

	public String getDutyscope()
	{
		return this.dutyscope;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getMemo()
	{
		return this.memo;
	}

	public void setDistrictcode(String districtcode)
	{
		this.districtcode = districtcode;
	}

	public String getDistrictcode()
	{
		return this.districtcode;
	}

	public void setFoundtime(String foundtime)
	{
		this.foundtime = foundtime;
	}

	public String getFoundtime()
	{
		return this.foundtime;
	}

	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = (status == null || status.intValue() != 1) ? 0 : 1;
	}
}
