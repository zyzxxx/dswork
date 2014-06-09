package dswork.common.controller;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import dswork.web.MyRequest;
import dswork.common.model.DsCommonOrg;
import dswork.common.service.DsCommonOrgService;
import dswork.core.util.CollectionUtil;

@Controller
@RequestMapping("/common/org")
public class DsCommonOrgController
{
	@Autowired
	private DsCommonOrgService service;
	
	// 添加
	@RequestMapping("/addOrg1")
	public String addOrg1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long pid = req.getLong("pid");
		DsCommonOrg parent = null;
		if(pid > 0)
		{
			parent = service.get(pid);
			if(parent.getStatus() == 0)// 岗位不能添加子节点
			{
				return null;
			}
		}
		else
		{
			parent = new DsCommonOrg();
		}
		request.setAttribute("parent", parent);
		request.setAttribute("pid", pid);
		return "/common/org/addOrg.jsp";
	}
	@RequestMapping("/addOrg2")
	public void addOrg2(HttpServletRequest request, PrintWriter out, DsCommonOrg po)
	{
		try
		{
			if(po.getName().length() == 0)
			{
				out.print("0:名称不能为空");
				return;
			}
			if(0 < po.getPid().longValue())// 存在上级节点时
			{
				DsCommonOrg parent = service.get(po.getPid());
				if(null == parent)
				{
					out.print("0:参数错误，请刷新重试");
					return;
				}
				if(0 == parent.getStatus())// 上级是岗位
				{
					out.print("0:岗位无法添加下级");
					return;
				}
				if(1 == parent.getStatus() && po.getStatus() == 2)// 上级是部门
				{
					out.print("0:部门无法设置下级单位");
					return;
				}
				if(2 == parent.getStatus() && po.getStatus() == 0)// 上级不是部门
				{
					out.print("0:单位无法设置岗位");
					return;
				}
			}
			else
			{
				po.setStatus(2);// 没有上级时必须为单位
			}
			service.save(po);
			out.print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delOrg")
	public void delOrg(HttpServletRequest request, PrintWriter out)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			int v = 0;
			long[] ids = req.getLongArray("keyIndex", 0);
			for(long id : ids)
			{
				if(id <= 0)
				{
					continue;
				}
				int count = service.getCountByPid(id);
				if(0 >= count)
				{
					service.delete(id);
				}
				else
				{
					v++;// out.print("0:下级节点不为空，不能删除");
				}
			}
			out.print("1" + (v > 0 ? ":" + v + "个不能删除，下级节点不为空" : ""));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:下级节点或用户不为空");
		}
	}

	// 修改
	@RequestMapping("/updOrg1")
	public String updOrg1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonOrg po = service.get(id);
		if(null == po)
		{
			return null;// 非法访问，否则肯定存在id
		}
		DsCommonOrg parent = null;
		if(0 < po.getPid())
		{
			parent = service.get(po.getPid());
			if(null == parent)
			{
				return null;// 非法数据，否则肯定存在parent
			}
		}
		else
		{
			parent = new DsCommonOrg();
		}
		request.setAttribute("po", po);
		request.setAttribute("parent", parent);
		return "/common/org/updOrg.jsp";
	}
	@RequestMapping("/updOrg2")
	public void updOrg2(HttpServletRequest request, PrintWriter out, DsCommonOrg po)
	{
		try
		{
			if(0 >= po.getName().length())
			{
				out.print("0:名称不能为空");
				return;
			}
			DsCommonOrg old = service.get(po.getId());
			if(null == old)
			{
				out.print("0:操作失败，请刷新重试");
				return;
			}
			if(po.getStatus() != 0 && old.getStatus() == 0)
			{
				po.setStatus(0);// 岗位类型不能被更改
			}
			if(old.getPid() <= 0)
			{
				po.setStatus(2);// 没有上级则为单位
			}
			else// 存在上级节点时
			{
				DsCommonOrg parent = service.get(po.getPid());
				if(null == parent)
				{
					out.print("0:参数错误，请刷新重试");
					return;
				}
				if(1 == parent.getStatus() && po.getStatus() == 2)// 上级是部门
				{
					out.print("0:部门无法设置下级单位");
					return;
				}
			}
			service.update(po);
			out.print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 排序
	@RequestMapping("/updOrgSeq1")
	public String updOrgSeq1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long pid = req.getLong("pid");
		List<DsCommonOrg> list = service.queryList(pid);
		request.setAttribute("list", list);
		return "/common/org/updOrgSeq.jsp";
	}
	@RequestMapping("/updOrgSeq2")
	public void updOrgSeq2(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		Long[] ids = CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0));
		try
		{
			if(ids.length > 0)
			{
				service.updateSeq(ids);
				out.print(1);
			}
			else
			{
				out.print("0:没有需要排序的节点");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 移动
	@RequestMapping("/updOrgMove1")
	public String updOrgMove1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long rootid = req.getLong("rootid");//作为限制根节点显示
		request.setAttribute("po", (rootid > 0)?service.get(rootid):null);
		request.setAttribute("rootid", rootid);
		return "/common/org/updOrgMove.jsp";
	}
	@RequestMapping("/updOrgMove2")
	public void updOrgMove2(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long pid = req.getLong("pid");
		if(pid <= 0)
		{
			pid = 0;
		}
		else
		{
			DsCommonOrg m = service.get(pid);
			if(m == null)
			{
				out.print("0:不能进行移动");// 移动的节点不存在，或者不在相同的系统
				return;
			}
			if(m.getStatus() == 0)
			{
				out.print("0:不能移动到岗位节点");// 移动的节点不存在，或者不在相同的系统
				return;
			}
		}
		Long[] ids = getLongArray(req.getString("ids"));
		try
		{
			if(ids.length > 0)
			{
				service.updatePid(ids, pid);
				out.print(1);
			}
			else
			{
				out.print("0:没有需要移动的节点");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long rootid = req.getLong("rootid");//作为限制根节点显示
		DsCommonOrg po = null;
		if(rootid > 0)
		{
			po = service.get(rootid);
			if(null == po)
			{
				return null;// 没有此根节点
			}
			if(po.getStatus() == 0)
			{
				return null;// 不能以岗位作为根节点
			}
		}
		else
		{
			po = new DsCommonOrg();
			po.setName("组织机构");
		}
		request.setAttribute("po", po);
		return "/common/org/getOrgTree.jsp";
	}
	// 获得列表
	@RequestMapping("/getOrg")
	public String getOrg(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long rootid = req.getLong("rootid");//作为限制根节点显示
		Long pid = req.getLong("pid");
		List<DsCommonOrg> list = service.queryList(pid);
		request.setAttribute("list", list);
		request.setAttribute("rootid", rootid);
		request.setAttribute("pid", pid);
		return "/common/org/getOrg.jsp";
	}
	// 获得树形管理时的json数据
	@RequestMapping("/getOrgJson")// ByPid
	public void getOrgJson(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long pid = req.getLong("pid");
		out.print(service.queryList(pid));
	}

	// 明细
	@RequestMapping("/getOrgById")
	public String getOrgById(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonOrg po = service.get(id);
		request.setAttribute("po", po);
		return "/common/org/getOrgById.jsp";
	}

	private Long[] getLongArray(String value)
	{
		try
		{
			String[] _v = value.split(",");
			if(_v != null && _v.length > 0)
			{
				Long[] _numArr = new Long[_v.length];
				for(int i = 0; i < _v.length; i++)
				{
					try
					{
						_numArr[i] = Long.parseLong(_v[i].trim());
					}
					catch(NumberFormatException e)
					{
						_numArr[i] = 0L;
					}
				}
				return _numArr;
			}
		}
		catch(Exception e)
		{
		}
		return new Long[0];
	}
}
