/**
 * 内容Model
 */
package dswork.cms.model;

public class DsCmsAuditPage
{
	// 主键
	private Long id = 0L;
	// 站点ID
	private Long siteid = 0L;
	// 栏目ID
	private Long categoryid = 0L;
	// 状态(-1删除,0新增,1修改,8已发)
	private Integer status = 0;
	// 标题
	private String title = "";
	// meta关键词
	private String metakeywords = "";
	// meta描述
	private String metadescription = "";
	// 摘要
	private String summary = "";
	// 内容
	private String content = "";
	// 创建时间
	private String releasetime = "";
	// 来源
	private String releasesource = "";
	// 作者
	private String releaseuser = "";
	// 图片
	private String img = "";
	// 焦点图(0否,1是)
	private Integer imgtop = 0;
	// 首页推荐(0否,1是)
	private Integer pagetop = 0;
	// 类型(1单页,2外链)
	private Integer scope = 1;
	// 链接
	private String url = "";

	// 编辑人员ID
	private String editid = "";
	// 编辑人员姓名
	private String editname = "";
	// 编辑时间
	private String edittime = "";
	// 审核状态(0草稿,1未审核,2不通过,4通过)
	private Integer auditstatus = 0;
	// 审核人员ID
	private String auditid = "";
	// 审核人员姓名
	private String auditname = "";
	// 审核时间
	private String audittime = "";
	// 审核意见
	private String msg = "";

	public static final int DRAFT = 0;
	public static final int AUDIT = 1;
	public static final int NOPASS = 2;
	public static final int PASS = 4;

	public boolean isDraft()
	{
		return auditstatus == DRAFT;
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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getSiteid()
	{
		return siteid;
	}

	public void setSiteid(Long siteid)
	{
		this.siteid = siteid;
	}

	public Long getCategoryid()
	{
		return categoryid;
	}

	public void setCategoryid(Long categoryid)
	{
		this.categoryid = categoryid;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getMetakeywords()
	{
		return metakeywords;
	}

	public void setMetakeywords(String metakeywords)
	{
		this.metakeywords = metakeywords;
	}

	public String getMetadescription()
	{
		return metadescription;
	}

	public void setMetadescription(String metadescription)
	{
		this.metadescription = metadescription;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getReleasetime()
	{
		return releasetime;
	}

	public void setReleasetime(String releasetime)
	{
		this.releasetime = releasetime;
	}

	public String getReleasesource()
	{
		return releasesource;
	}

	public void setReleasesource(String releasesource)
	{
		this.releasesource = releasesource;
	}

	public String getReleaseuser()
	{
		return releaseuser;
	}

	public void setReleaseuser(String releaseuser)
	{
		this.releaseuser = releaseuser;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public Integer getImgtop()
	{
		return imgtop;
	}

	public void setImgtop(Integer imgtop)
	{
		this.imgtop = imgtop;
	}

	public Integer getPagetop()
	{
		return pagetop;
	}

	public void setPagetop(Integer pagetop)
	{
		this.pagetop = pagetop;
	}

	public Integer getScope()
	{
		return scope;
	}

	public void setScope(Integer scope)
	{
		this.scope = scope;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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

	public Integer getAuditstatus()
	{
		return auditstatus;
	}

	public void setAuditstatus(Integer auditstatus)
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
