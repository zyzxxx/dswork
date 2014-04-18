/**
 * DEMOModel
 */
package dswork.android.model;
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
	
	public Demo()
	{
		super();
	}

	public Demo(Long id, String title, String content, String foundtime) 
	{
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.foundtime = foundtime;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return this.content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFoundtime()
	{
		return this.foundtime;
	}

	public void setFoundtime(String foundtime)
	{
		this.foundtime = foundtime;
	}
}