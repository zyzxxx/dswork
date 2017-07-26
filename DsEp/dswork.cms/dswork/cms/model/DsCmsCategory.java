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
	private Long pid = 0L;
	// 站点ID
	private Long siteid = 0L;
	// 栏目名称
	private String name = "";
	// 目录名称
	private String folder = "";
	// 类型(0列表,1单页,2外链)
	private Integer status = 0;
	// 发布状态
	private int publishstatus = 0;
	// 链接
	private String url = "";
	// 栏目模板
	private String viewsite = "";
	// 内容模板
	private String pageviewsite = "";
	// 排序
	private Integer seq = 0;
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
	private int level = 0;
	// 树形标识(管理时扩展显示)
	private String label = "";

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
		return pid;
	}

	public void setPid(Long pid)
	{
		this.pid = (pid == null || pid <= 0) ? 0 : pid;
	}

	public Long getSiteid()
	{
		return siteid;
	}

	public void setSiteid(Long siteid)
	{
		this.siteid = siteid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFolder()
	{
		return folder;
	}

	public void setFolder(String folder)
	{
		this.folder = folder;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public int getPublishstatus()
	{
		return publishstatus;
	}

	public void setPublishstatus(int publishstatus)
	{
		this.publishstatus = publishstatus;
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

	public Integer getSeq()
	{
		return seq;
	}

	public void setSeq(Integer seq)
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

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}
}
