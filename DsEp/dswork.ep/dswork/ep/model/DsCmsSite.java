/**
 * 站点Model
 */
package dswork.ep.model;

public class DsCmsSite
{
	// 主键
	private Long id = 0L;
	// 企业编码
	private String qybm = "";
	// 栏目名称
	private String name = "";
	// 目录名称
	private String folder = "";
	// 链接
	private String url = "";
	// 图片
	private String img = "";
	// 网站模板
	private String viewsite = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getQybm()
	{
		return qybm;
	}

	public void setQybm(String qybm)
	{
		this.qybm = qybm;
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
}
