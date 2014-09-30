/**
 * 流程待办事项Model
 */
package dswork.common.model;
public class IFlowPiDoing
{
	//主键ID
	private Long id = 0L;
	//实例ID
	private Long piid = 0L;
	//业务流水号
	private String ywlsh = "";
	//流程ID
	private Long flowid = 0L;
	//任务标识
	private String talias = "";
	//任务开始时间
	private String tstart = "";
	//任务经办人
	private String taccount = "";
	//候选经办人
	private String thaccount = "";
	//等待任务数
	private Integer tcount = 0;
	//处理任务接口类
	private String tinterface = "";
	//获取用户接口类
	private String uinterface = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPiid()
	{
		return piid;
	}

	public void setPiid(Long piid)
	{
		this.piid = piid;
	}

	public String getYwlsh()
	{
		return ywlsh;
	}

	public void setYwlsh(String ywlsh)
	{
		this.ywlsh = ywlsh;
	}

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

	public String getTstart()
	{
		return tstart;
	}

	public void setTstart(String tstart)
	{
		this.tstart = tstart;
	}

	public String getTaccount()
	{
		return taccount;
	}

	public void setTaccount(String taccount)
	{
		this.taccount = taccount;
	}

	public String getThaccount()
	{
		return thaccount;
	}

	public void setThaccount(String thaccount)
	{
		this.thaccount = thaccount;
	}

	public Integer getTcount()
	{
		return tcount;
	}

	public void setTcount(Integer tcount)
	{
		this.tcount = tcount;
	}

	public String getTinterface()
	{
		return tinterface;
	}

	public void setTinterface(String tinterface)
	{
		this.tinterface = tinterface;
	}

	public String getUinterface()
	{
		return uinterface;
	}

	public void setUinterface(String uinterface)
	{
		this.uinterface = uinterface;
	}
}