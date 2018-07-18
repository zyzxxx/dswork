package common.cms.model;

public class ViewSpecial
{
	private Long id;
	private String viewsite = "";
	private String mviewsite = "";
	private String url = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
