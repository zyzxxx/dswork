/**
 * 公共Dao
 */
package dswork.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.common.model.IFlow;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;
import dswork.core.db.MyBatisDao;
import dswork.core.util.TimeUtil;
import dswork.core.util.UniqueId;

@Repository
@SuppressWarnings("all")
public class DsCommonDaoIFlow extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCommonDaoIFlow.class;
	}
	private void updateFlowPi(Long id, int status, String pialias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("pialias", pialias);
		if(status == 0)
		{
			map.put("piend", TimeUtil.getCurrentTime());
		}
		executeUpdate("updateFlowPi", map);
	}
	private IFlowTask getFlowTask(Long flowid, String talias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid", flowid);
		map.put("talias", talias);
		return (IFlowTask) executeSelect("selectFlowTask", map);
	}
	private void saveFlowWaiting(IFlowWaiting m)
	{
		executeInsert("insertFlowWaiting", m);
	}
	private void deleteFlowWaiting(Long id)
	{
		executeDelete("deleteFlowWaiting", id);
	}
	private void deleteFlowWaitingByPiid(Long piid)
	{
		executeDelete("deleteFlowWaitingByPiid", piid);
	}
	private void updateFlowWaiting(Long id, String tstart)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("tstart", tstart);
		executeUpdate("updateFlowWaiting", map);
	}
	private IFlowWaiting getFlowWaitingByPiid(Long piid, String talias)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("piid", piid);
		map.put("talias", talias);
		return (IFlowWaiting) executeSelect("selectFlowWaitingByPiid", map);
	}
	private List<String> queryFlowWaitingTalias(Long piid)
	{
		return executeSelectList("queryFlowWaitingTalias", piid);
	}
	
	
	
	
	
	public List<IFlowTask> queryFlowTask(Long flowid)
	{
		return executeSelectList("queryFlowTask", flowid);
	}
	
	public IFlowPi getFlowPi(String ywlsh)
	{
		List<IFlowPi> list = executeSelectList("queryFlowPi", ywlsh);
		if(list == null || list.size() == 0)
		{
			return null;
		}
		return list.get(0);
	}
	
	public List<IFlowPi> queryFlowPi(String ywlsh)
	{
		return executeSelectList("queryFlowPi", ywlsh);
	}
	
	public List<IFlowPiData> queryFlowPiData(Long piid)
	{
		return executeSelectList("queryFlowPiData", piid);
	}
	
	public void updateFlowWaitingUser(Long id, String tuser)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("tuser", "," + tuser + ",");
		executeUpdate("updateFlowWaitingUser", map);
	}
	public IFlowWaiting getFlowWaiting(Long id)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (IFlowWaiting) executeSelect("selectFlowWaiting", map);
	}
	public List<IFlowWaiting> queryFlowWaiting(String account)
	{
		return executeSelectList("queryFlowWaiting", "," + account + ",");
	}

	public IFlowWaiting saveFlowStart(String alias, String ywlsh, String sblsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface)
	{
		String time = TimeUtil.getCurrentTime();
		IFlow flow = (IFlow) executeSelect("selectFlow", alias);
		long flowid = 0L;
		if(flow != null)
		{
			flowid = flow.getId();// deployidToFlowid(flow.getDeployid());//flow.getId();
		}
		if(flowid > 0)
		{
			IFlowTask task = this.getFlowTask(flowid, "start");
			IFlowPi pi = new IFlowPi();
			pi.setId(UniqueId.genUniqueId());
			pi.setYwlsh(ywlsh);
			pi.setSblsh(sblsh);
			pi.setAlias(alias);
			pi.setFlowid(flowid);
			pi.setDeployid(flow.getDeployid());
			pi.setPiday(piDay);
			pi.setPidaytype(isWorkDay ? 1 : 0);
			pi.setPistart(time);
			pi.setPiend("");
			pi.setStatus(1);// 流程状态(1,申请,2,运行,3挂起,0结束)
			pi.setCaccount(account);
			pi.setCname(name);
			pi.setPialias("start");
			executeInsert("insertFlowPi", pi);
			Long piid = pi.getId();
			IFlowWaiting m = new IFlowWaiting();
			m.setId(UniqueId.genUniqueId());
			m.setPiid(piid);
			m.setYwlsh(ywlsh);
			m.setSblsh(sblsh);
			m.setFlowid(flowid);
			m.setFlowname(flow.getName());
			m.setTalias(task.getTalias());// "start"
			m.setTname(task.getTname());
			m.setTcount(1);// task.getTcount()，start没有上级节点，不需要等待
			m.setTnext(task.getTnext());
			m.setTstart(time);
			m.setTmemo(task.getTmemo());
			m.setTinterface(taskInterface);
			if(task.getTusers().split(",", -1).length > 1)
			{
				m.setTusers("," + task.getTusers() + ",");// 多人，候选人
				m.setTuser("");
			}
			else
			{
				m.setTusers("");// 候选人
				m.setTuser("," + task.getTusers() + ",");// 单人
			}
			this.saveFlowWaiting(m);
			return m;
		}
		return null;
	}

	public String saveStart(String alias, String ywlsh, String sblsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface)
	{
		IFlowWaiting w = saveFlowStart(alias, ywlsh, sblsh, account, name, piDay, isWorkDay, taskInterface);
		if(w != null)
		{
			return String.valueOf(w.getPiid());
		}
		return "";
	}
	
	public void saveStop(Long piid)
	{
		this.deleteFlowWaitingByPiid(piid);
		this.updateFlowPi(piid, 0, "");// 结束
	}

	public boolean saveProcess(Long waitid, String[] nextTalias, String account, String name, String resultType, String resultMsg)
	{
		IFlowWaiting m = this.getFlowWaiting(waitid);
		if(m != null && m.getTcount() <= 1)
		{
			String time = TimeUtil.getCurrentTime();
			IFlowPiData pd = new IFlowPiData();
			pd.setId(UniqueId.genUniqueId());
			pd.setPiid(m.getPiid());
			pd.setTalias(m.getTalias());
			pd.setTname(m.getTname());
			pd.setStatus(0);// 状态(0已处理,1代办,2挂起,3取消挂起)
			pd.setPaccount(account);
			pd.setPname(name);
			pd.setPtime(time);
			pd.setPtype(resultType);
			pd.setMemo(resultMsg);
			executeInsert("insertFlowPiData", pd);
			
			boolean isEnd = false;
			if(nextTalias == null)
			{
				isEnd = true;// 需要结束流程
			}
			else
			{
				this.deleteFlowWaiting(waitid);// 该待办事项已经处理
				for(int i = 0; i < nextTalias.length; i++)
				{
					String talias = nextTalias[i];
					IFlowWaiting w = this.getFlowWaitingByPiid(m.getPiid(), talias);
					if(w != null && w.getId().longValue() != 0)
					{
						this.updateFlowWaiting(w.getId(), time);// 等待数减1
					}
					else
					{
						IFlowWaiting newm = new IFlowWaiting();
						newm.setId(UniqueId.genUniqueId());
						newm.setPiid(m.getPiid());
						newm.setYwlsh(m.getYwlsh());
						newm.setSblsh(m.getSblsh());
						newm.setFlowid(m.getFlowid());
						newm.setFlowname(m.getFlowname());
						newm.setTstart(time);
						newm.setTinterface(m.getTinterface());
						
						IFlowTask t = this.getFlowTask(m.getFlowid(), talias);
						newm.setTalias(t.getTalias());
						newm.setTname(t.getTname());
						newm.setTcount(t.getTcount());
						newm.setTnext(t.getTnext());
						String[] s = t.getTusers().split(",", -1);
						if(s.length > 1)
						{
							newm.setTusers("," + t.getTusers() + ",");// 多人，候选人
							newm.setTuser("");
						}
						else
						{
							newm.setTusers("");// 候选人
							newm.setTuser("," + t.getTusers() + ",");// 单人
						}
						newm.setTmemo(t.getTmemo());
						this.saveFlowWaiting(newm);
					}
					if(talias.equals("end"))
					{
						isEnd = true;
					}
				}
			}
			if(isEnd)
			{
				this.deleteFlowWaitingByPiid(m.getPiid());// 已经结束，清空所有待办事项
				this.updateFlowPi(m.getPiid(), 0, "");// 结束
			}
			else
			{
				List<String> list = this.queryFlowWaitingTalias(m.getPiid());
				if(list == null || list.size() == 0)
				{
					this.updateFlowPi(m.getPiid(), 0, "");// 结束
				}
				else
				{
					StringBuilder sb = new StringBuilder(100);
					sb.append(list.get(0));
					for(int i = 1; i < list.size(); i++)
					{
						sb.append(",").append(list.get(i));
					}
					this.updateFlowPi(m.getPiid(), 2, sb.toString());// 处理中
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}
