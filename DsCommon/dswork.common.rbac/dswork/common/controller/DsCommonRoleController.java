package dswork.common.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import dswork.web.MyRequest;
import dswork.common.model.DsCommonFunc;
import dswork.common.model.DsCommonRole;
import dswork.common.model.DsCommonRoleFunc;
import dswork.common.model.DsCommonSystem;
import dswork.common.service.DsCommonRoleService;
import dswork.core.util.CollectionUtil;

//角色
@Controller
@RequestMapping("/common/role")
public class DsCommonRoleController
{
	@Autowired
	private DsCommonRoleService service;

	// 添加
	@RequestMapping("/addRole1")
	public String addRole1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		DsCommonRole parent = null;
		if(pid > 0)
		{
			parent = service.get(pid);
		}
		else
		{
			parent = new DsCommonRole();
		}
		request.setAttribute("parent", parent);
		request.setAttribute("systemid", systemid);
		request.setAttribute("pid", req.getLong("pid"));
		return "/common/role/addRole.jsp";
	}
	@RequestMapping("/addRole2")
	public void addRole2(HttpServletRequest request, PrintWriter out, DsCommonRole po)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			if(po.getName().length() == 0)
			{
				out.print("0:名称不能为空");
				return;
			}
			if(0 < po.getPid())
			{
				DsCommonRole parent = service.get(po.getPid());
				if(null == parent)
				{
					out.print("0:参数错误，请刷新重试");
					return;
				}
			}
			int refresh = req.getInt("refresh", 0);
			List<DsCommonRoleFunc> list = null;
			if(refresh == 1)// 需要修改功能权限
			{
				Long[] funcids = getLongArray(req.getString("funcids", "").trim());
				if(funcids.length > 0)
				{
					list = new ArrayList<DsCommonRoleFunc>();
					for(int i = 0; i < funcids.length; i++)
					{
						DsCommonRoleFunc m = new DsCommonRoleFunc();
						m.setFuncid(funcids[i]);
						if(m.getFuncid() > 0)
						{
							list.add(m);
						}
					}
				}
			}
			service.save(po, list);
			out.print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delRole")
	public void delRole(HttpServletRequest request, PrintWriter out)
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
			out.print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updRole1")
	public String updRole1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonRole po = service.get(id);
		if(null == po)
		{
			return null;// 非法访问，否则肯定存在id
		}
		DsCommonRole parent = null;
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
			parent = new DsCommonRole();
		}
		request.setAttribute("po", po);
		request.setAttribute("parent", parent);
		return "/common/role/updRole.jsp";
	}
	@RequestMapping("/updRole2")
	public void updRole2(HttpServletRequest request, PrintWriter out, DsCommonRole po)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			if(0 >= po.getName().length())
			{
				out.print("0:名称不能为空");
				return;
			}
			DsCommonRole _po = service.get(po.getId());
			if(null == _po)
			{
				out.print("0:操作失败，请刷新重试");
				return;
			}
			int refresh = req.getInt("refresh", 0);
			List<DsCommonRoleFunc> list = null;
			if(refresh == 1)// 需要修改功能权限
			{
				Long[] funcids = getLongArray(req.getString("funcids", "").trim());
				if(funcids.length > 0)
				{
					list = new ArrayList<DsCommonRoleFunc>();
					for(int i = 0; i < funcids.length; i++)
					{
						DsCommonRoleFunc m = new DsCommonRoleFunc();
						m.setFuncid(funcids[i]);
						if(m.getFuncid() > 0)
						{
							list.add(m);
						}
					}
				}
			}
			service.update(po, list);
			out.print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 排序
	@RequestMapping("/updRoleSeq1")
	public String updRoleSeq1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		List<DsCommonRole> list = service.queryList(systemid, pid);
		request.setAttribute("list", list);
		return "/common/role/updRoleSeq.jsp";
	}
	@RequestMapping("/updRoleSeq2")
	public void updRoleSeq2(HttpServletRequest request, PrintWriter out)
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
	@RequestMapping("/updRoleMove1")
	public String updRoleMove1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		DsCommonSystem po = service.getSystem(req.getLong("systemid"));
		request.setAttribute("po", po);
		return "/common/role/updRoleMove.jsp";
	}
	@RequestMapping("/updRoleMove2")
	public void updRoleMove2(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		if(pid <= 0)
		{
			pid = 0;
		}
		else
		{
			DsCommonRole m = service.get(pid);
			if(m == null || m.getSystemid().longValue() != systemid)
			{
				out.print("0:不能进行移动");// 移动的节点不存在，或者不在相同的系统
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
	@RequestMapping("/getRoleTree")
	public String getRoleTree(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		request.setAttribute("po", service.getSystem(systemid));
		return "/common/role/getRoleTree.jsp";
	}
	// 获得列表
	@RequestMapping("/getRole")
	public String getRole(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		Long pid = req.getLong("pid");
		List<DsCommonRole> list = service.queryList(systemid, pid);
		request.setAttribute("list", list);
		request.setAttribute("systemid", systemid);
		request.setAttribute("pid", pid);
		return "/common/role/getRole.jsp";
	}
	// 获得树形管理时的json数据
	@RequestMapping("/getRoleJson")// BySystemidAndPid
	public void getRoleJson(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		out.print(service.queryList(systemid, pid));
	}

	// 明细
	@RequestMapping("/getRoleById")
	public String getRoleById(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonRole po = service.get(id);
		request.setAttribute("po", po);
		return "/common/role/getRoleById.jsp";
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

	// 获得功能和被分配到角色的功能
	@RequestMapping("/getRoleFuncJson")// BySystemidAndRoleid
	public void getRoleFuncJson(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		long roleid = req.getLong("roleid");
		List<DsCommonFunc> list = service.queryFuncList(systemid);
		if(null != list && 0 < list.size())
		{
			Map<Long, DsCommonFunc> m = new HashMap<Long, DsCommonFunc>();
			for(DsCommonFunc i : list)
			{
				m.put(i.getId(), i);
			}
			if(0 < roleid)
			{
				List<DsCommonRoleFunc> flist = service.getFuncListByRoleid(roleid);
				for(DsCommonRoleFunc i : flist)
				{
					m.get(i.getFuncid()).setChecked(true);
				}
			}
			out.print(list);
		}
		else
		{
			out.print("[]");
		}
	}
}
