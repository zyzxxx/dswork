/**
 * 流程Model
 */
package dswork.common.model;

public class IFlow
{
	// 主键
	private Long id = 0L;
	private String deployid = "";
	private String name = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDeployid()
	{
		return deployid;
	}

	public void setDeployid(String deployid)
	{
		this.deployid = deployid == null ? "" : deployid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
