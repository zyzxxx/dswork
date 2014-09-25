/**
 * 流程任务Model
 */
package dswork.common.model;

public class DsCommonFlowTask
{
	// 主键
	private Long id = 0L;
	// 流程ID
	private Long flowid = 0L;
	// 流程发布ID，当前版本此值为空
	private String deployid = "";
	// 节点标识（start开始，end结束）
	private String talias = "";
	// 节点名称
	private String tname = "";
	// 上级任务（合并分支时， 以逗号分隔节点标识， 以|线分隔分支任务）
	private String tnodeprev = "";
	// 下级任务（以逗号分隔节点标识， 以|线分隔分支任务）
	private String tnodenext = "";
	// 用户ID（以逗号分隔节点标识， 以|线分隔分支任务）
	private String tusers = "";
	// 用户参数
	private String tmemo = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getFlowid()
	{
		return flowid;
	}

	public void setFlowid(Long flowid)
	{
		this.flowid = flowid;
	}

	public String getDeployid()
	{
		return deployid;
	}

	public void setDeployid(String deployid)
	{
		this.deployid = deployid;
	}

	public String getTalias()
	{
		return talias;
	}

	public void setTalias(String talias)
	{
		this.talias = talias;
	}

	public String getTname()
	{
		return tname;
	}

	public void setTname(String tname)
	{
		this.tname = tname;
	}

	public String getTnodeprev()
	{
		return tnodeprev;
	}

	public void setTnodeprev(String tnodeprev)
	{
		this.tnodeprev = tnodeprev;
	}

	public String getTnodenext()
	{
		return tnodenext;
	}

	public void setTnodenext(String tnodenext)
	{
		this.tnodenext = tnodenext;
	}

	public String getTusers()
	{
		return tusers;
	}

	public void setTusers(String tusers)
	{
		this.tusers = tusers;
	}

	public String getTmemo()
	{
		return tmemo;
	}

	public void setTmemo(String tmemo)
	{
		this.tmemo = tmemo;
	}
}
