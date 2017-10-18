/**
 * 采编审核权限Model
 */
package dswork.cms.model;

public class DsCmsPermission
{
	// 主键
	private Long id = 0L;
	// 站点ID
	private long siteid = 0L;
	// 用户账号
	private String account = "";
	// 可采编栏目
	private String editall = "";
	// 可采编栏目（限个人）
	private String editown = "";
	// 可审核栏目
	private String audit = "";
	// 可发布栏目
	private String publish = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public long getSiteid()
	{
		return siteid;
	}

	public void setSiteid(long siteid)
	{
		this.siteid = siteid;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getEditall()
	{
		return editall;
	}

	public void setEditall(String editall)
	{
		this.editall = editall;
	}

	public String getEditown()
	{
		return editown;
	}

	public void setEditown(String editown)
	{
		this.editown = editown;
	}

	public String getAudit()
	{
		return audit;
	}

	public void setAudit(String audit)
	{
		this.audit = audit;
	}

	public String getPublish()
	{
		return publish;
	}

	public void setPublish(String publish)
	{
		this.publish = publish;
	}

	public boolean checkEditall(long categoryid)
	{
		return getEditall().indexOf("," + categoryid + ",") != -1;
	}

	public boolean checkEditown(long categoryid)
	{
		return getEditown().indexOf("," + categoryid + ",") != -1;
	}

	public boolean checkEdit(long categoryid)
	{
		return checkEditall(categoryid) || checkEditown(categoryid);
	}

	public boolean checkAudit(long categoryid)
	{
		return getAudit().indexOf("," + categoryid + ",") != -1;
	}

	public boolean checkPublish(long categoryid)
	{
		return getPublish().indexOf("," + categoryid + ",") != -1;
	}
}
