/**
 * 样例信息Model
 */
package testwork.model;

//@javax.persistence.Entity
//@org.hibernate.annotations.Entity(dynamicUpdate = true)
//@Table(name = "DEMO")
@SuppressWarnings("all")
public class Demo// implements java.io.Serializable
{
	//样例编号
	private Long id;
	//样例编号
	private long id2;
	public long getId2()
	{
		return id2;
	}

	public void setId2(long id2)
	{
		this.id2 = id2;
	}

	//标题
	private String title = "";
	//内容
	private String content = "";
	//创建时间
	private String foundtime = "";

	public void setId(Long id)
	{
		this.id = id;
	}

	//@Id
	//@GeneratedValue(generator = "hibernate-uuid")
	//@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	//@Column(length = 32)
	//@Column(name = "ID")
	public Long getId()
	{
		return this.id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getContent()
	{
		return this.content;
	}

	public void setFoundtime(String foundtime)
	{
		this.foundtime = foundtime;
	}

	public String getFoundtime()
	{
		return this.foundtime;
	}
}