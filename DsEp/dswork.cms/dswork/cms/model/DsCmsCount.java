package dswork.cms.model;

/**
 * 用于统计栏目待审核数量，DsCmsCategory和DsCmsCategoryEdit的xml、dao、service、controller均使用到
 * @author skey
 */
public class DsCmsCount
{
	// 主键
	private long id = 0L;
	// 类型(0内容数量, 1栏目数量)
	private int scope = 0;
	// 审核/待发布计数
	private int count = 0;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getScope()
	{
		return scope;
	}

	public void setScope(int scope)
	{
		this.scope = scope;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
