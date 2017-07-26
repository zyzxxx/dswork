/**
 * 栏目Model
 */
package dswork.cms.model;

public class DsCmsAuditCategory
{
	// 主键
	private Long id = 0L;
	// 站点ID
	private Long siteid = 0L;
	// meta关键词
	private String metakeywords = "";
	// meta描述
	private String metadescription = "";
	// 摘要
	private String summary = "";
	// 创建时间
	private String releasetime = "";
	// 来源
	private String releasesource = "";
	// 作者
	private String releaseuser = "";
	// 图片
	private String img = "";
	// 内容
	private String content = "";
	// 外链URL
	private String url = "";
	// 状态(0草稿,1未审核,2不通过,4通过)
	private int status = 0;
	// 审核意见
	private String msg = "";

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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
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
