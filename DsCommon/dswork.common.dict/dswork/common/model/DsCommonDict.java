/**
 * 字典分类Model
 */
package dswork.common.model;

public class DsCommonDict
{
	// 主键
	private Long id = 0L;
	// 引用名
	private String name = "";
	// 名称
	private String label = "";
	// 状态(1:树形，0:列表)
	private Integer status = 0;
	// 排序
	private Integer seq = 0;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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
