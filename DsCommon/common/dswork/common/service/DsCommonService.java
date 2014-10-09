package dswork.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowPiDoing;
import dswork.common.model.IFlowTask;
import dswork.core.util.TimeUtil;

@Service
// @SuppressWarnings("all")
public class DsCommonService
{
	@Autowired
	private DsCommonDao dao;

	public String start(String alias, String ywlsh, String account, String name, int piDay, boolean isWorkDay, String taskInterface, String userInterface)
	{
		String time = TimeUtil.getCurrentTime();
		String deployid = dao.getFlowDeployid(alias);
		Long flowid = deployidToFlowid(deployid);
		if(flowid > 0)
		{
			IFlowTask task = dao.getFlowTask(flowid, "start");
			IFlowPi pi = new IFlowPi();
			pi.setYwlsh(ywlsh);
			pi.setAlias(alias);
			pi.setFlowid(flowid);
			pi.setDeployid(deployid);
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
			IFlowPiDoing m = new IFlowPiDoing();
			m.setPiid(piid);
			m.setYwlsh(ywlsh);
			m.setFlowid(flowid);
			m.setTalias(task.getTalias());// "start"
			m.setTname(task.getTname());
			m.setTstart(time);
			m.setTinterface(taskInterface);
			m.setUinterface(userInterface);
			m.setTcount(0);// start没有上级节点，不需要等待
			
			if(task.getTusers().split(",", -1).length > 1)
			{
				m.setThaccount("," + task.getTusers() + ",");// 多人，候选人
				m.setTaccount("");
			}
			else
			{
				m.setThaccount("");// 候选人
				m.setTaccount("," + task.getTusers() + ",");// 单人
			}
			dao.saveFlowPiDoing(m);
			return String.valueOf(piid);
		}
		return "";
	}

	public boolean process(Long doingid, String[] nextTalias, String account, String name, String resultType, String resultMsg)
	{
		IFlowPiDoing m = dao.getFlowPiDoing(doingid);
		if(m != null && m.getTcount() == 0)
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
			dao.deleteFlowPiDoing(doingid);// 该待办事项已经处理
			
			for(int i = 0; i < nextTalias.length; i++)
			{
				String alias = nextTalias[i];
				if(dao.isExistsFlowPiDoing(m.getPiid(), alias))
				{
					dao.updateFlowPiDoing(m.getFlowid(), m.getTalias(), time);// 等待数减1
				}
				else
				{
					IFlowTask t = dao.getFlowTask(m.getFlowid(), alias);
					IFlowPiDoing newm = new IFlowPiDoing();
					newm.setPiid(m.getPiid());
					newm.setYwlsh(m.getYwlsh());
					newm.setFlowid(m.getFlowid());
					newm.setTalias(alias);
					newm.setTname(t.getTname());
					newm.setTstart(time);
					
					String[] s = t.getTusers().split(",", -1);
					if(s.length > 1)
					{
						newm.setThaccount("," + t.getTusers() + ",");// 候选人
					}
					else
					{
						newm.setTaccount("," + t.getTusers() + ",");
					}
					
					newm.setTcount(t.getTcount());
					
					newm.setTinterface(m.getTinterface());
					newm.setUinterface(m.getUinterface());
					dao.saveFlowPiDoing(newm);
				}
			}
			
			List<String> list = dao.queryFlowPiDoingTalias(m.getPiid());
			if(list == null || list.size() == 0)
			{
				dao.updateFlowPiStatus(m.getPiid());
			}
			else
			{
				StringBuilder sb = new StringBuilder(100);
				sb.append(list.get(0));
				for(int i = 1; i < list.size(); i++)
				{
					sb.append(",").append(list.get(i));
				}
				dao.updateFlowPiPialias(m.getPiid(), sb.toString());
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public List<IFlowPiDoing> queryFlowPiDoing(String account)
	{
		return dao.queryFlowPiDoing("," + account + ",");
	}

	public IFlowTask getFlowTask(String deployid, String talias)
	{
		Long flowid = deployidToFlowid(deployid);
		return dao.getFlowTask(flowid, talias);
	}

	private Long deployidToFlowid(String deployid)
	{
		Long flowid = 0L;
		if(deployid != null && deployid.indexOf("-") > 0)
		{
			String[] arr = deployid.split("-");
			if(arr.length > 1)
			{
				flowid = Long.parseLong(arr[arr.length - 1]);
			}
		}
		return flowid;
	}
}
