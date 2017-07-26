/**
 * 采编审核权限Model
 */
package dswork.cms.model;

public class DsCmsPermission
{
	// 数据ID
	private long id = 0L;
	// 所有者
	private String own = "";
	// 用户账号
	private String account = "";
	// 可审核栏目
	private String audit = "";
	// 可采编栏目（包括栏目内所有页面）
	private String editall = "";
	// 可采编栏目（包括栏目内自己创建的页面）
	private String editown = "";
	// 可发布栏目
	private String publish = "";

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getOwn()
	{
		return own;
	}

	public void setOwn(String own)
	{
		this.own = own;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getAudit()
	{
		return audit;
	}

	public void setAudit(String audit)
	{
		this.audit = audit;
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

	public boolean checkAudit(long categoryid)
	{
		return getAudit().indexOf("," + categoryid + ",") != -1;
	}
	
	public boolean checkPublish(long categoryid)
	{
		return getPublish().indexOf("," + categoryid + ",") != -1;
	}
}
