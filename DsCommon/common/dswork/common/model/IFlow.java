/**
 * 流程Model
 */
package dswork.common.model;

import java.util.ArrayList;
import java.util.List;

public class IFlow
{
	// 流程标识
	private String alias = "";
	// 内部版本0为编辑版本
	private Integer vnum = 0;
	// 流程发布ID，VNUM为0的放最新版本
	private String deployid = "";
	// 名字
	private String name = "";
	private List<IFlowTask> taskList = null;

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public Integer getVnum()
	{
		return vnum;
	}

	public void setVnum(Integer vnum)
	{
		this.vnum = vnum;
	}

	public String getDeployid()
	{
		return deployid;
	}

	public void setDeployid(String deployid)
	{
		this.deployid = deployid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<IFlowTask> getTaskList()
	{
		if(taskList == null)
		{
			return new ArrayList<IFlowTask>();
		}
		return taskList;
	}

	public void setTaskList(List<IFlowTask> taskList)
	{
		this.taskList = taskList;
	}
}
