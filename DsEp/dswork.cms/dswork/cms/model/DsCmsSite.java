/**
 * 站点Model
 */
package dswork.cms.model;

public class DsCmsSite
{
	// 主键
	private Long id = 0L;
	// 拥有者
	private String own = "";
	// 站点名称
	private String name = "";
	// 目录名称
	private String folder = "";
	// 链接
	private String url = "";
	// 图片
	private String img = "";
	// 网站模板
	private String viewsite = "";
	// 移动版网站模板
	private String mviewsite = "";
	// 开始日志(0否,1是)
	private int enablelog = 0;
	// 开启移动版(0否,1是)
	private int enablemobile = 0;
	// meta关键词
	private String metakeywords = "";
	// meta描述
	private String metadescription = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public String getViewsite()
	{
		return viewsite;
	}

	public void setViewsite(String viewsite)
	{
		this.viewsite = viewsite;
	}

	public String getMviewsite()
	{
		return mviewsite;
	}

	public void setMviewsite(String mviewsite)
	{
		this.mviewsite = mviewsite;
	}

	public int getEnablelog()
	{
		return enablelog;
	}

	public void setEnablelog(int enablelog)
	{
		this.enablelog = enablelog;
	}
	
	public void setWriteLog(boolean enablelog)
	{
		this.enablelog = enablelog ? 1 : 0;
	}

	public boolean isWriteLog()
	{
		return this.enablelog == 1;
	}

	public int getEnablemobile()
	{
		return enablemobile;
	}

	public void setEnablemobile(int enablemobile)
	{
		this.enablemobile = enablemobile;
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
}
