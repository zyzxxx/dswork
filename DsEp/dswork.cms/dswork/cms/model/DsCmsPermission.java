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

	/**
	 * 获取可采编栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的采编权限
	 * @return
	 */
	public String getEditall()
	{
		return editall;
	}

	/**
	 * 设置可采编栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的采编权限
	 * @return
	 */
	public void setEditall(String editall)
	{
		this.editall = editall;
	}

	/**
	 * 获取个人可采编栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的个人采编权限
	 * @return
	 */
	public String getEditown()
	{
		return editown;
	}

	/**
	 * 设置个人可采编栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的个人采编权限
	 * @return
	 */
	public void setEditown(String editown)
	{
		this.editown = editown;
	}

	/**
	 * 获取可审核栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的审核权限
	 * @return
	 */
	public String getAudit()
	{
		return audit;
	}

	/**
	 * 设置可审核栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的审核权限
	 * @return
	 */
	public void setAudit(String audit)
	{
		this.audit = audit;
	}

	/**
	 * 获取可发布栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的发布权限
	 * @return
	 */
	public String getPublish()
	{
		return publish;
	}

	/**
	 * 设置可发布栏目ID字符串<br>
	 * 格式：[,[栏目ID,[栏目ID,...]]]<br>
	 * 一个逗号或空字符串表示没有配置任何栏目的发布权限
	 * @return
	 */
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
