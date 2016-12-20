package common.lucene;

public class MyDocument
{
	private String title = "";// 关键字高亮标题
	private String summary = "";// 关键字高亮摘要
	private String url = "";// 相对url地址

	public String getTitle()
	{
		return title;
	}
	public MyDocument setTitle(String title)
	{
		this.title = title;
		return this;
	}
	public String getSummary()
	{
		return summary;
	}
	public MyDocument setSummary(String summary)
	{
		this.summary = summary;
		return this;
	}
	public String getUrl()
	{
		return url;
	}
	public MyDocument setUrl(String url)
	{
		this.url = url;
		return this;
	}
	

//	private String name = "";// 标题
//	private String msg = "";// 内容
//	public String getName()
//	{
//		return name;
//	}
//	public MyDocument setName(String name)
//	{
//		this.name = name;
//		return this;
//	}
//	public String getMsg()
//	{
//		return msg;
//	}
//	public MyDocument setMsg(String msg)
//	{
//		this.msg = msg;
//		return this;
//	}
}
