/**
 * 样例Model
 */
package testwork.model;

public class Mark
{
	// 主键
	private Long id = 0L;
	// 标题
	private Long demoid = 0L;
	// 内容
	private String content = "";
	// 创建时间
	private String foundtime = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getDemoid()
	{
		return demoid;
	}

	public void setDemoid(Long demoid)
	{
		this.demoid = demoid;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFoundtime()
	{
		return foundtime;
	}

	public void setFoundtime(String foundtime)
	{
		this.foundtime = foundtime;
	}
}
