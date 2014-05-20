/**
 * 功能资源Model
 */
package dswork.common.model;
public class DsCommonRes
{
	//主键
	private Long id = 0L;
	//所属功能ID
	private Long funcid = 0L;
	//所属系统ID
	private Long systemid = 0L;
	//资源对应的URL
	private String url = "";
	//排序
	private Integer seq = 0;
	//参数(格式:name1=value2,name2=value2...)
	private String param = "";

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setFuncid(Long funcid)
	{
		this.funcid = funcid;
	}

	public Long getFuncid()
	{
		return this.funcid;
	}

	public void setSystemid(Long systemid)
	{
		this.systemid = systemid;
	}

	public Long getSystemid()
	{
		return this.systemid;
	}

	public void setUrl(String url)
	{
		this.url = String.valueOf(url).trim();
	}

	public String getUrl()
	{
		return this.url;
	}

	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}

	public Integer getSeq()
	{
		return this.seq;
	}

	public void setParam(String param)
	{
		this.param = String.valueOf(param).trim();
	}

	public String getParam()
	{
		return this.param;
	}
}