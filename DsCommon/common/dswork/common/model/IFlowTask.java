/**
 * 流程任务Model
 */
package dswork.common.model;

public class IFlowTask
{
	// 流程ID
	private Long flowid = 0L;
	// 节点标识（start开始，end结束）
	private String talias = "";
	// 节点名称
	private String tname = "";
	// 上级任务（合并分支时， 以逗号分隔节点标识， 以|线分隔分支任务）
	private String tnodeprev = "";
	// 用户ID（以逗号分隔节点标识， 以|线分隔分支任务）
	private String tusers = "";
	
	public Long getFlowid()
	{
		return flowid;
	}

	public void setFlowid(Long flowid)
	{
		this.flowid = flowid;
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

	public String getTusers()
	{
		return tusers;
	}

	public void setTusers(String tusers)
	{
		this.tusers = tusers;
	}
}
