/**
 * DEMOModel
 */
package dswork.android.model;

import dswork.android.db.BaseModel;

public class Demo extends BaseModel
{
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
		this.id = id;
		this.title = title;
		this.content = content;
		this.foundtime = foundtime;
	}
	public Demo(String title, String content, String foundtime) 
	{
		this.title = title;
		this.content = content;
		this.foundtime = foundtime;
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