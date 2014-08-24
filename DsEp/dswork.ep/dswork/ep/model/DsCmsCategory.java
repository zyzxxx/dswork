/**
 * 栏目Model
 */
package dswork.ep.model;
public class DsCmsCategory
{
	//主键
	private Long id = 0L;
	//父ID
	private Long pid = 0L;
	//企业编码
	private String qybm = "";
	//栏目名称
	private String name = "";
	//目录名称
	private String folder = "";
	//状态(0列表，1单页，2外链)
	private Integer status = 0;//├─└─
	//链接
	private String url = "";
	//图片
	private String img = "";
	//网站模板
	private String viewsite = "";
	//APP模板
	private String viewapp = "";
	//排序
	private Integer seq = 0;

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
		this.pid = pid;
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

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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

	public String getViewapp()
	{
		return viewapp;
	}

	public void setViewapp(String viewapp)
	{
		this.viewapp = viewapp;
	}

	public Integer getSeq()
	{
		return seq;
	}

	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}
}