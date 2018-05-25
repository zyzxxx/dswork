/**
 * 栏目Model
 */
package dswork.cms.model;

import java.util.ArrayList;
import java.util.List;

public class DsCmsCategory
{
	// 主键
	private Long id = 0L;
	// 父ID
	private Long pid = null;
	// 站点ID
	private long siteid = 0L;
	// 状态(-1删除,0新增,1修改,8已发)
	private int status = 0;
	// 栏目名称
	private String name = "";
	// 类型(0列表,1单页,2外链)
	private int scope = 0;
	// 链接
	private String url = "";
	// 栏目模板
	private String viewsite = "";
	// 内容模板
	private String pageviewsite = "";
	// 移动版栏目模板
	private String mviewsite = "";
	// 移动版内容模板
	private String mpageviewsite = "";
	// 排序
	private int seq = 0;
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
	// 子栏目
	private List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();
	// 级别(管理时扩展显示)
	private boolean enable = false;
	// 树形标识(管理时扩展显示)
	private String label = "";
	// 审核/待发布计数
	private int count = 0;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPid()
	{
		return (pid == null || pid <= 0) ? 0 : pid;
	}

	public void setPid(Long pid)
	{
		this.pid = (pid == null || pid <= 0) ? 0 : pid;
	}

	public long getSiteid()
	{
		return siteid;
	}

	public void setSiteid(long siteid)
	{
		this.siteid = siteid;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public String getViewsite()
	{
		return viewsite;
	}

	public void setViewsite(String viewsite)
	{
		this.viewsite = viewsite;
	}

	public String getPageviewsite()
	{
		return pageviewsite;
	}

	public void setPageviewsite(String pageviewsite)
	{
		this.pageviewsite = pageviewsite;
	}

	public String getMviewsite()
	{
		return mviewsite;
	}

	public void setMviewsite(String mviewsite)
	{
		this.mviewsite = mviewsite;
	}

	public String getMpageviewsite()
	{
		return mpageviewsite;
	}

	public void setMpageviewsite(String mpageviewsite)
	{
		this.mpageviewsite = mpageviewsite;
	}

	public int getSeq()
	{
		return seq;
	}

	public void setSeq(int seq)
	{
		this.seq = seq;
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

	public List<DsCmsCategory> getList()
	{
		return list;
	}

	public void clearList()
	{
		this.list.clear();
	}

	public void add(DsCmsCategory item)
	{
		this.list.add(item);
	}

	public boolean isEnable()
	{
		return enable;
	}

	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
