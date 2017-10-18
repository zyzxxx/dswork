/**
 * 栏目Model
 */
package dswork.cms.model;

public class DsCmsAuditCategory extends DsCmsCategory
{
	// 状态(-1删除,0新增,1修改,8已发)
	// 编辑人员ID
	private String editid = "";
	// 编辑人员姓名
	private String editname = "";
	// 编辑时间
	private String edittime = "";
	// 审核状态(0草稿,1未审核,2不通过,4通过)
	private int auditstatus = 0;
	// 审核人员ID
	private String auditid = "";
	// 审核人员姓名
	private String auditname = "";
	// 审核时间
	private String audittime = "";
	// 审核意见
	private String msg = "";
	public static final int EDIT = 0;
	public static final int AUDIT = 1;
	public static final int NOPASS = 2;
	public static final int PASS = 4;

	public boolean isEdit()
	{
		return auditstatus == EDIT;
	}

	public boolean isAudit()
	{
		return auditstatus == AUDIT;
	}

	public boolean isNopass()
	{
		return auditstatus == NOPASS;
	}

	public boolean isPass()
	{
		return auditstatus == PASS;
	}

	public String getEditid()
	{
		return editid;
	}

	public void setEditid(String editid)
	{
		this.editid = editid;
	}

	public String getEditname()
	{
		return editname;
	}

	public void setEditname(String editname)
	{
		this.editname = editname;
	}

	public String getEdittime()
	{
		return edittime;
	}

	public void setEdittime(String edittime)
	{
		this.edittime = edittime;
	}

	public int getAuditstatus()
	{
		return auditstatus;
	}

	public void setAuditstatus(int auditstatus)
	{
		this.auditstatus = auditstatus;
	}

	public String getAuditid()
	{
		return auditid;
	}

	public void setAuditid(String auditid)
	{
		this.auditid = auditid;
	}

	public String getAuditname()
	{
		return auditname;
	}

	public void setAuditname(String auditname)
	{
		this.auditname = auditname;
	}

	public String getAudittime()
	{
		return audittime;
	}

	public void setAudittime(String audittime)
	{
		this.audittime = audittime;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}
