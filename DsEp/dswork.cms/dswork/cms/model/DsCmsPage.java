/**
 * 内容Model
 */
package dswork.cms.model;

public class DsCmsPage
{
	// 主键
	private Long id = 0L;
	// 站点ID
	private long siteid = 0L;
	// 栏目ID
	private long categoryid = 0L;
	// 状态(-1删除,0新增,1修改,8已发)
	private int status = 0;
	// 标题
	private String title = "";
	// 类型(1单页,2外链)
	private int scope = 1;
	// meta关键词
	private String metakeywords = "";
	// meta描述
	private String metadescription = "";
	// 摘要
	private String summary = "";
	// 内容
	private String content = "";
	// 发布时间
	private String releasetime = "";
	// 来源
	private String releasesource = "";
	// 作者
	private String releaseuser = "";
	// 图片
	private String img = "";
	// 焦点图(0否,1是)
	private int imgtop = 0;
	// 首页推荐(0否,1是)
	private int pagetop = 0;
	// 链接
	private String url = "";
	// 数据集
	private String jsondata = "{}";

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

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
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

	public int getScope()
	{
		return scope;
	}

	public void setScope(int scope)
	{
		this.scope = scope;
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getJsondata()
	{
		return jsondata;
	}

	public void setJsondata(String jsondata)
	{
		this.jsondata = jsondata;
	}
}
