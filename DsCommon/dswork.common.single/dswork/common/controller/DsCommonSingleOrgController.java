package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonSingleOrgService;
import dswork.core.util.CollectionUtil;

@Scope("prototype")
@Controller
@RequestMapping("/ds/common/single/org")
public class DsCommonSingleOrgController extends BaseController
{
	@Autowired
	private DsCommonSingleOrgService service;

	// 添加
	@RequestMapping("/addOrg1")
	public String addOrg1()
	{
		long pid = req.getLong("pid");
		DsCommonOrg parent = null;
		if(pid > 0)
		{
			parent = service.get(pid);
			if(parent == null || parent.getStatus() == 0)// 岗位不能添加子节点
			{
				return null;
			}
		}
		else
		{
			parent = new DsCommonOrg();
		}
		put("parent", parent);
		put("pid", pid);
		return "/ds/common/single/org/addOrg.jsp";
	}

	@RequestMapping("/addOrg2")
	public void addOrg2(DsCommonOrg po)
	{
		try
		{
			if(po.getName().length() == 0)
			{
				print("0:名称不能为空");
				return;
			}
			if(0 < po.getPid().longValue())// 存在上级节点时
			{
				DsCommonOrg parent = service.get(po.getPid());
				if(parent == null)
				{
					print("0:参数错误，请刷新重试");
					return;
				}
				if(parent.getStatus() == 0)// 上级是岗位，这这在单系统中是不可能的
				{
					print("0:参数错误，请刷新重试");
					return;
				}
				if(parent.getStatus() == 1 && po.getStatus() == 2)// 上级是部门
				{
					print("0:部门无法设置下级单位");
					return;
				}
				if(parent.getStatus() == 2 && po.getStatus() == 0)// 上级不是部门
				{
					print("0:单位无法设置岗位");
					return;
				}
			}
			else
			{
				po.setStatus(2);// 没有上级时必须为单位
			}
			service.save(po);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delOrg")
	public void delOrg()
	{
		try
		{
			int v1 = 0, v2 = 0;
			long[] ids = req.getLongArray("keyIndex", 0);
			for(long id : ids)
			{
				if(id > 0)
				{
					int count = service.getCountByPid(id, 1);// 部门
					count += service.getCountByPid(id, 2);// 单位
					if(count == 0)
					{
						count = service.getCountByPid(id, 0);// 岗位
						if(count == 0)
						{
							service.delete(id);
						}
						else
						{
							v2++;
						}
					}
					else
					{
						v1++;
					}
				}
			}
			if(v1 > 0)
			{
				print("1:" + v1 + "个不能删除，下级节点不为空");
			}
			else if(v2 > 0)
			{
				print("1:" + v2 + "个不能删除，部门下有用户");// 一个岗位对应一个用户
			}
			else
			{
				print("1");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:下级节点或用户不为空");
		}
	}

	// 修改
	@RequestMapping("/updOrg1")
	public String updOrg1()
	{
		Long id = req.getLong("keyIndex");
		DsCommonOrg po = service.get(id);
		if(po == null || po.getStatus() == 0)// 为空或者是岗位
		{
			return null;
		}
		DsCommonOrg parent = null;
		if(po.getPid() > 0)
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
		put("po", po);
		put("parent", parent);
		return "/ds/common/single/org/updOrg.jsp";
	}

	@RequestMapping("/updOrg2")
	public void updOrg2(DsCommonOrg po)
	{
		try
		{
			if(0 >= po.getName().length())
			{
				print("0:名称不能为空");
				return;
			}
			DsCommonOrg old = service.get(po.getId());
			if(old == null || old.getStatus() == 0)
			{
				print("0:操作失败，请刷新重试");
				return;
			}
			if(old.getPid() <= 0)
			{
				po.setStatus(2);// 没有上级则为单位
			}
			else// 存在上级节点时
			{
				DsCommonOrg parent = service.get(old.getPid());
				if(parent == null)
				{
					print("0:参数错误，请刷新重试");
					return;
				}
				if(parent.getStatus() == 1 && po.getStatus() == 2)// 上级是部门
				{
					print("0:部门无法设置下级单位");
					return;
				}
			}
			if(old.getStatus() == 2 && po.getStatus() == 1)// 降级
			{
				int count = service.getCountByPid(po.getId(), 2);// 下级不能有单位
				if(0 < count)
				{
					print("0:下级存在单位，不能降级为部门");
					return;
				}
			}
			else if(old.getStatus() == 1 && po.getStatus() == 2)// 升级
			{
				int count = service.getCountByPid(po.getId(), 0);// 下级不能有岗位
				if(0 < count)
				{
					print("0:部门下存在用户或子部门，不能升级为单位");
					return;
				}
			}
			service.update(po);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 排序
	@RequestMapping("/updOrgSeq1")
	public String updOrgSeq1()
	{
		long pid = req.getLong("pid");
		List<DsCommonOrg> list = service.queryList(pid);
		put("list", list);
		return "/ds/common/single/org/updOrgSeq.jsp";
	}

	@RequestMapping("/updOrgSeq2")
	public void updOrgSeq2()
	{
		Long[] ids = CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0));
		try
		{
			if(ids.length > 0)
			{
				service.updateSeq(ids);
				print(1);
			}
			else
			{
				print("0:没有需要排序的节点");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 移动
	@RequestMapping("/updOrgMove1")
	public String updOrgMove1()
	{
		Long rootid = getLoginUser().getOrgpid();// 作为限制根节点显示
		put("po", (rootid > 0) ? service.get(rootid) : null);
		put("rootid", rootid);
		return "/ds/common/single/org/updOrgMove.jsp";
	}

	@RequestMapping("/updOrgMove2")
	public void updOrgMove2()
	{
		long pid = req.getLong("pid");
		if(pid <= 0)
		{
			pid = 0;
		}
		else
		{
			DsCommonOrg m = service.get(pid);
			if(m == null || m.getStatus() == 0)
			{
				print("0:不能进行移动");
				return;
			}
		}
		Long[] ids = getLongArray(req.getString("ids"));
		try
		{
			if(ids.length > 0)
			{
				service.updatePid(ids, pid);
				print(1);
			}
			else
			{
				print("0:没有需要移动的节点");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree()
	{
		long rootid = getLoginUser().getOrgpid();
		DsCommonOrg po = null;
		if(rootid > 0)
		{
			po = service.get(rootid);
			if(po == null || po.getStatus() == 0)
			{
				return null;// 没有此根节点或不能以岗位作为根节点
			}
		}
		else
		{
			po = new DsCommonOrg();
		}
		put("po", po);
		return "/ds/common/single/org/getOrgTree.jsp";
	}

	// 获得列表
	@RequestMapping("/getOrg")
	public String getOrg()
	{
		Long rootid = getLoginUser().getOrgpid();// 作为限制根节点显示
		Long pid = req.getLong("pid");
		List<DsCommonOrg> rawList = service.queryList(pid);
		List<DsCommonOrg> list = new ArrayList<DsCommonOrg>();
		for(DsCommonOrg po : rawList)
		{
			if(po.getStatus() == 1 || po.getStatus() == 2)
			{
				list.add(po);
			}
		}
		put("list", list);
		put("rootid", rootid);
		put("pid", pid);
		return "/ds/common/single/org/getOrg.jsp";
	}

	// 获得树形管理时的json数据
	@RequestMapping("/getOrgJson")
	// ByPid
	public void getOrgJson()
	{
		long pid = req.getLong("pid");
		print(service.queryList(pid));
	}

	// 明细
	@RequestMapping("/getOrgById")
	public String getOrgById()
	{
		Long id = req.getLong("keyIndex");
		DsCommonOrg po = service.get(id);
		put("po", po);
		return "/ds/common/single/org/getOrgById.jsp";
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

	private DsCommonUser getLoginUser()
	{
		String account = dswork.sso.WebFilter.getAccount(session);
		return service.getUserByAccount(account);
	}
}
