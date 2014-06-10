/**
 * 用户岗位Model
 */
package dswork.common.model;

public class DsCommonUserOrg
{
	// 岗位ID
	private Long orgid = 0L;
	// 用户ID
	private Long userid = 0L;

	public Long getOrgid()
	{
		return orgid;
	}

	public void setOrgid(Long orgid)
	{
		this.orgid = orgid;
	}

	public Long getUserid()
	{
		return userid;
	}

	public void setUserid(Long userid)
	{
		this.userid = userid;
	}
}
