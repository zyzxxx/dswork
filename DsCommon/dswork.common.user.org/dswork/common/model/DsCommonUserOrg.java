/**
 * 用户岗位Model
 */
package dswork.common.model;

public class DsCommonUserOrg
{
	// 岗位ID
	private Long orgid = 0L;
	// 岗位名称
	private String orgname = "";
	// 用户ID
	private Long userid = 0L;
	// 用户名称
	private String username = "";

	public Long getOrgid()
	{
		return orgid;
	}

	public void setOrgid(Long orgid)
	{
		this.orgid = orgid;
	}

	public String getOrgname()
	{
		return orgname;
	}

	public void setOrgname(String orgname)
	{
		this.orgname = orgname;
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
}
