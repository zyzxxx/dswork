/**
 * 样例Model
 */
package testwork.model;
public class Demo
{
	//主键
	private Long id = 0L;
	//标题
	private String title = "";
	//内容
	private String content = "";
	//创建时间
	private String foundtime = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
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