/**
 * 日志Model
 */
package dswork.cms.model;

public class DsCmsLog
{
	// 主键
	private Long id = 0L;
	// 站点ID
	private long siteid = 0L;
	// 栏目ID
	private long categoryid = 0L;
	// 内容ID
	private long pageid = 0L;
	// 采编员ID
	private String editid = "";
	// 采编员姓名
	private String editname = "";
	// 采编时间
	private String edittime = "";
	// 审核员ID
	private String auditid = "";
	// 审核意见
	private String auditmsg = "";
	// 审核员姓名
	private String auditname = "";
	// 审核时间
	private String audittime = "";
	// 发布员ID
	private String publishid = "";
	// 发布员姓名
	private String publishname = "";
	// 发布时间
	private String publishtime = "";
	// 状态(-1删除,0新增,1修改,4撤销,8已发)
	private int status = 0;
	// 审核状态(0草稿,1未审核,2不通过,4通过)
	private int auditstatus = 0;
	// 标题(栏目:NAME,内容:TITLE)
	private String title = "";
	// 类型(1单页,2外链)
	private int scope = 0;
	// 链接
	private String url = "";
	// meta关键词
	private String metakeywords = "";
	// meta描述
	private String metadescription = "";
	// 摘要
	private String summary = "";
	// 发布时间
	private String releasetime = "";
	// 信息来源
	private String releasesource = "";
	// 作者
	private String releaseuser = "";
	// 图片
	private String img = "";
	// 内容
	private String content = "";
	// 焦点图(0否,1是)
	private int imgtop = 0;
	// 首页推荐(0否,1是)
	private int pagetop = 0;

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

	public long getCategoryid()
	{
		return categoryid;
	}

	public void setCategoryid(long categoryid)
	{
		this.categoryid = categoryid;
	}

	public long getPageid()
	{
		return pageid;
	}

	public void setPageid(long pageid)
	{
		this.pageid = pageid;
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

	public String getAuditid()
	{
		return auditid;
	}

	public void setAuditid(String auditid)
	{
		this.auditid = auditid;
	}

	public String getAuditmsg()
	{
		return auditmsg;
	}

	public void setAuditmsg(String auditmsg)
	{
		this.auditmsg = auditmsg;
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

	public String getPublishid()
	{
		return publishid;
	}

	public void setPublishid(String publishid)
	{
		this.publishid = publishid;
	}

	public String getPublishname()
	{
		return publishname;
	}

	public void setPublishname(String publishname)
	{
		this.publishname = publishname;
	}

	public String getPublishtime()
	{
		return publishtime;
	}

	public void setPublishtime(String publishtime)
	{
		this.publishtime = publishtime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getAuditstatus()
	{
		return auditstatus;
	}

	public void setAuditstatus(int auditstatus)
	{
		this.auditstatus = auditstatus;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getScope()
	{
		return scope;
	}

	public void setScope(int scope)
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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getImgtop()
	{
		return imgtop;
	}

	public void setImgtop(int imgtop)
	{
		this.imgtop = imgtop;
	}

	public int getPagetop()
	{
		return pagetop;
	}

	public void setPagetop(int pagetop)
	{
		this.pagetop = pagetop;
	}
}
