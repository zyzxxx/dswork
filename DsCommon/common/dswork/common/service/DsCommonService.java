package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IFlow;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowTask;
import dswork.common.model.IFlowWaiting;
import dswork.core.util.TimeUtil;

@Service
// @SuppressWarnings("all")
public class DsCommonService
{
	@Autowired
	private DsCommonDao dao;

	public IFlowWaiting saveFlowStart(String alias, String ywlsh, String sblsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface)
	{
		String time = TimeUtil.getCurrentTime();
		IFlow flow = dao.getFlow(alias);
		long flowid = 0L;
		if(flow != null)
		{
			flowid = flow.getId();// deployidToFlowid(flow.getDeployid());//flow.getId();
		}
		if(flowid > 0)
		{
			IFlowTask task = dao.getFlowTask(flowid, "start");
			IFlowPi pi = new IFlowPi();
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
			dao.saveFlowPi(pi);
			Long piid = pi.getId();
			IFlowWaiting m = new IFlowWaiting();
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
			dao.saveFlowWaiting(m);
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
		dao.deleteFlowWaitingByPiid(piid);
		dao.updateFlowPi(piid, 0, "");// 结束
	}

	public boolean saveProcess(Long waitid, String[] nextTalias, String account, String name, String resultType, String resultMsg)
	{
		IFlowWaiting m = dao.getFlowWaiting(waitid);
		if(m != null && m.getTcount() <= 1)
		{
			String time = TimeUtil.getCurrentTime();
			IFlowPiData pd = new IFlowPiData();
			pd.setPiid(m.getPiid());
			pd.setTalias(m.getTalias());
			pd.setTname(m.getTname());
			pd.setStatus(0);// 状态(0已处理,1代办,2挂起,3取消挂起)
			pd.setPaccount(account);
			pd.setPname(name);
			pd.setPtime(time);
			pd.setPtype(resultType);
			pd.setMemo(resultMsg);
			dao.saveFlowPiData(pd);
			
			boolean isEnd = false;
			if(nextTalias == null)
			{
				isEnd = true;// 需要结束流程
			}
			else
			{
				dao.deleteFlowWaiting(waitid);// 该待办事项已经处理
				for(int i = 0; i < nextTalias.length; i++)
				{
					String talias = nextTalias[i];
					IFlowWaiting w = dao.getFlowWaitingByPiid(m.getPiid(), talias);
					if(w != null && w.getId().longValue() != 0)
					{
						dao.updateFlowWaiting(w.getId(), time);// 等待数减1
					}
					else
					{
						IFlowWaiting newm = new IFlowWaiting();
						newm.setPiid(m.getPiid());
						newm.setYwlsh(m.getYwlsh());
						newm.setSblsh(m.getSblsh());
						newm.setFlowid(m.getFlowid());
						newm.setFlowname(m.getFlowname());
						newm.setTstart(time);
						newm.setTinterface(m.getTinterface());
						
						IFlowTask t = dao.getFlowTask(m.getFlowid(), talias);
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
						dao.saveFlowWaiting(newm);
					}
					if(talias.equals("end"))
					{
						isEnd = true;
					}
				}
			}
			if(isEnd)
			{
				dao.deleteFlowWaitingByPiid(m.getPiid());// 已经结束，清空所有待办事项
				dao.updateFlowPi(m.getPiid(), 0, "");// 结束
			}
			else
			{
				List<String> list = dao.queryFlowWaitingTalias(m.getPiid());
				if(list == null || list.size() == 0)
				{
					dao.updateFlowPi(m.getPiid(), 0, "");// 结束
				}
				else
				{
					StringBuilder sb = new StringBuilder(100);
					sb.append(list.get(0));
					for(int i = 1; i < list.size(); i++)
					{
						sb.append(",").append(list.get(i));
					}
					dao.updateFlowPi(m.getPiid(), 2, sb.toString());// 处理中
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public List<IFlowWaiting> queryFlowWaiting(String account)
	{
		return dao.queryFlowWaiting("," + account + ",");
	}

	public IFlowWaiting getFlowWaiting(Long waitid)
	{
		return dao.getFlowWaiting(waitid);
	}
	
	public void updateFlowWaitingUser(Long waitid, String tuser)
	{
		dao.updateFlowWaitingUser(waitid, "," + tuser + ",");
	}
	
	public List<IFlowTask> queryFlowTask(Long flowid)
	{
		return dao.queryFlowTask(flowid);
	}
	

//	private long deployidToFlowid(String deployid)
//	{
//		long flowid = 0L;
//		if(deployid != null && deployid.indexOf("-") > 0)
//		{
//			String[] arr = deployid.split("-");
//			if(arr.length > 1)
//			{
//				flowid = Long.parseLong(arr[arr.length - 1]);
//			}
//		}
//		return flowid;
//	}
}
