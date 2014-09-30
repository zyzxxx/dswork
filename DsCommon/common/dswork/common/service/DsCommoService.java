package dswork.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IFlowPi;
import dswork.common.model.IFlowPiData;
import dswork.common.model.IFlowPiDoing;
import dswork.common.model.IFlowTask;

@Service
@SuppressWarnings("all")
public class DsCommoService
{
	@Autowired
	private DsCommonDao dao;
	

	public static String start(String alias, String ywlsh, String caccount, String cname, String piDay, boolean isWorkDay, String taskInterface, String userInterface)
	{	
		int pidaytype=0; 
		if(isWorkDay){pidaytype=1;}
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DsCommoService service = new DsCommoService();
		String deployid=service.queryDeployid(alias);
		Long flowid=service.queryFlowid(deployid);
		IFlowTask task=service.queryTask(flowid, "start");
		if(task !=null)
		{
			IFlowPi flowpi=new IFlowPi();
			flowpi.setAlias(alias);
			flowpi.setFlowid(flowid);
			flowpi.setPialias("start");
			flowpi.setDeployid(deployid);
			flowpi.setYwlsh(ywlsh);
			flowpi.setCaccount(caccount);
			flowpi.setCname(cname);
			flowpi.setPiday(Integer.parseInt(piDay));
			flowpi.setPidaytype(pidaytype);
			flowpi.setStatus(1);
			flowpi.setPistart(sj.format(new Date()));
			Long piid=service.saveFlowPi(flowpi);
			if(piid !=null)
			{
				IFlowPiDoing pidoing=new IFlowPiDoing();
				pidoing.setPiid(piid);
				pidoing.setYwlsh(ywlsh);
				pidoing.setFlowid(flowid);
				pidoing.setTinterface(taskInterface);
				pidoing.setUinterface(userInterface);
				pidoing.setTalias("start");
				pidoing.setTstart(sj.format(new Date()));
				pidoing.setTcount(0);	
				String[] s=task.getTusers().split(",",-1);
				if(s.length>1){pidoing.setThaccount(","+task.getTusers()+",");}
				else {pidoing.setTaccount(","+task.getTusers()+",");}
				service.saveFlowPiDoing(pidoing);
			}						
			return String.valueOf(piid);
		}	
		else return "";
	}
	
	public static boolean process(Long doingid, String[] nextTalias, String paccount, String pname, String resultType, String resultMsg)
	{
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DsCommoService service = new DsCommoService();
		IFlowPiDoing pidoing=service.queryByid(doingid);
		if(pidoing !=null)
		{
			IFlowPiData pidata=new IFlowPiData();
			pidata.setPiid(pidoing.getPiid());
			pidata.setTalias(pidoing.getTalias());
			pidata.setPaccount(paccount);
			pidata.setPname(pname);
			pidata.setPtype(resultType);
			pidata.setMemo(resultMsg);
			pidata.setPtime(sj.format(new Date()));
			pidata.setStatus(0);
			IFlowTask task=service.queryTask(pidoing.getFlowid(), pidoing.getTalias());
			pidata.setTname(task.getTname());
			service.saveFlowPiData(pidata, doingid);
			for(int i=0;i<nextTalias.length;i++)
			{
				if(service.isExistsByTalias(pidoing.getPiid(),nextTalias[i]))
				{
					service.updateTcount(pidoing.getTcount()-1,pidoing.getFlowid(), pidoing.getTalias());
				}
				else 
				{
					IFlowTask t=service.queryTask(pidoing.getFlowid(), nextTalias[i]);
					IFlowPiDoing m=new IFlowPiDoing();
					m.setFlowid(pidoing.getFlowid());
					m.setPiid(pidoing.getPiid());
					m.setYwlsh(pidoing.getYwlsh());
					m.setTalias(nextTalias[i]);
					m.setTstart(sj.format(new Date()));
					String[] s=t.getTusers().split(",",-1);
					String[] v=t.getTnodeprev().split("|",-1);
					if(s.length>1){m.setThaccount(","+t.getTusers()+",");}
					else {m.setTaccount(","+t.getTusers()+",");}
					m.setTcount(v.length-1);
					m.setTinterface(pidoing.getTinterface());
					m.setUinterface(pidoing.getUinterface());
					service.saveFlowPiDoing(m);
				}
			}
			List<IFlowPiDoing> list=service.queryBypiid(pidoing.getPiid());
			if(list.isEmpty())
			{service.updateStatus(pidoing.getPiid());}
			else 
			{
				String str="";
				for(IFlowPiDoing a:list)
				{
					str=a.getTalias()+",";
				}
				service.updatePialias(pidoing.getPiid(),str);
			}
		return true;
		}	
		else return false;
	}
	
	public static List<IFlowPiDoing> getDoingList(String account)
	{
		DsCommoService service = new DsCommoService();
		return service.getDoingList(","+account+",");
	}
	
	public static IFlowTask getTask(String deployid,String talias)
	{
		DsCommoService service = new DsCommoService();
		return service.getTask(deployid, talias);
	}
	
//	public String queryDeployid(String alias)
//	{
//		return dao.queryDeployid(alias);
//	}
//	
//	public Long queryFlowid(String deployid)
//	{
//		return dao.queryFlowid(deployid);
//	}
//	
//	public IFlowTask queryTask(Long flowid,String talias)
//	{
//		return dao.queryTask(flowid, talias);
//	}
//	
//	public IFlowTask getTask(String deployid,String talias)
//	{
//		return dao.getTask(deployid, talias);
//	}
//	
//	public Long saveFlowPi(IFlowPi flowpi)
//	{
//		dao.saveFlowPi(flowpi);
//		return flowpi.getId();
//	}
//	
//	public void updateStatus(Long id)
//	{
//		dao.updateStatus(id);
//	}
//	
//	public void updatePialias(Long id,String pialias)
//	{
//		dao.updatePialias(id, pialias);
//	}
//	
//	public IFlowPiDoing queryByid(Long id)
//	{
//		return dao.queryByid(id);
//	}
//	
//	public List<IFlowPiDoing> queryBypiid(Long piid)
//	{
//		return dao.queryBypiid(piid);
//	}
//	
//	public void saveFlowPiDoing(IFlowPiDoing flowpidoing)
//	{
//		dao.saveFlowPiDoing(flowpidoing);
//	}
//	
//	public boolean isExistsByTalias(Long piid,String alias)
//	{
//		return dao.isExistsByTalias(piid,alias);
//	}
//	public void updateTcount(int tcount,Long piid,String alias)
//	{
//		dao.updateTcount(tcount, piid, alias);
//	}
//	
//	public void saveFlowPiData(IFlowPiData pidata,Long id)
//	{
//		dao.saveFlowPiData(pidata);
//		dao.deletePiDoing(id);
//	}
//
//	public List<IFlowPiDoing> getDoingList(String account)
//	{
//		return dao.getDoingList(account);
//	}
}
