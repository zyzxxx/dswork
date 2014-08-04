package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.model.DsCommonFunc;
import dswork.common.model.DsCommonRes;
import dswork.common.model.DsCommonSystem;
import dswork.common.service.DsCommonFuncService;
import dswork.core.util.CollectionUtil;

// 功能
@Scope("prototype")
@Controller
@RequestMapping("/common/func")
public class DsCommonFuncController extends BaseController
{
	@Autowired
	private DsCommonFuncService service;

	// 添加
	@RequestMapping("/addFunc1")
	public String addFunc1()
	{
		Long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		DsCommonFunc parent = null;
		if(pid > 0)
		{
			parent = service.get(pid);
			if(parent.getStatus() == 0)//不是菜单
			{
				return "";//非法访问
			}
		}
		else
		{
			parent = new DsCommonFunc();
		}
		put("parent", parent);
		put("systemid", systemid);
		put("pid", req.getLong("pid"));
		return "/common/func/addFunc.jsp";
	}
	@RequestMapping("/addFunc2")
	public void addFunc2(DsCommonFunc po)
	{
		try
		{
			if (po.getName().length() == 0)
			{
				print("0:名称不能为空");
				return;
			}
			if (0 < po.getPid())
			{
				DsCommonFunc parent = service.get(po.getPid());
				if (null == parent)
				{
					print("0:参数错误，请刷新重试");
					return;
				}
				if(parent.getStatus() == 0)//上级节点不是菜单
				{
					return;//非法访问
				}
			}
			if (po.getAlias().length() != 0)
			{
				//判断是否在该系统下唯一
				if (service.isExistsByAlias(po.getAlias(), po.getSystemid()))
				{
					print("0:操作失败，标识已存在");
					return;
				}
			}
			List<DsCommonRes> list = null;
			//权限资源清单
			String arr_url[] = req.getStringArray("rurl");
			if (0 < arr_url.length)
			{
				list = new ArrayList<DsCommonRes>();
				String arr_param[] = req.getStringArray("rparam");
				if (arr_url.length == arr_param.length)
				{
					for (int i = 0; i < arr_url.length; i++)
					{
						DsCommonRes m = new DsCommonRes();
						m.setUrl(arr_url[i]);
						m.setParam(arr_param[i]);
						if (0 < m.getUrl().length())//为空的不添加，直接忽略
						{
							list.add(m);
						}
					}
				}
				else
				{
					print("0:操作失败，请刷新重试");//url和param的个数不一致
					return;
				}
			}
			po.setResourcesList(list);
			service.save(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delFunc")
	public void delFunc()
	{
		try
		{
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
					v++;//print("0:下级节点不为空，不能删除");
				}
			}
			print("1" + (v > 0?":"+v+"个不能删除，下级节点不为空":""));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	// 删除系统全部功能
	@RequestMapping("/delFuncBySystem")
	public void delFuncBySystem()
	{
		try
		{
			long id = req.getLong("systemid", 0);
			if(0 < id)
			{
				service.deleteBySystem(id);
			}
			print("1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updFunc1")
	public String updFunc1()
	{
		Long id = req.getLong("keyIndex");
		DsCommonFunc  po = service.get(id);
		if (null == po)
		{
			return null;//非法访问，否则肯定存在id
		}
		DsCommonFunc parent = null;
		if (0 < po.getPid())
		{
			parent = service.get(po.getPid());
			if (null == parent)
			{
				return null;//非法数据，否则肯定存在parent
			}
		}
		else
		{
			parent = new DsCommonFunc();
		}
		int count = service.getCountByPid(id);//是否有子节点
		List<DsCommonRes> list = po.getResourcesList();
		put("po", po);
		put("parent", parent);
		put("list", list);
		put("count", count);
		return "/common/func/updFunc.jsp";
	}
	@RequestMapping("/updFunc2")
	public void updFunc2(DsCommonFunc po)
	{
		try
		{
			if (0 >= po.getName().length())
			{
				print("0:名称不能为空");
				return;
			}
			DsCommonFunc _po = service.get(po.getId());
			if (null == _po)
			{
				print("0:操作失败，请刷新重试");
				return;
			}
			if (po.getAlias().length() != 0)
			{
				if (!po.getAlias().equals(_po.getAlias()))//标识被修改
				{
					//判断是否在该系统下唯一
					if (service.isExistsByAlias(po.getAlias(), po.getSystemid()))
					{
						print("0:操作失败，标识已存在");
						return;
					}
				}
			}
			if(0 == po.getStatus())//修改为不是菜单
			{
				int count = service.getCountByPid(po.getId());//是否有子节点
				if(count > 0)
				{
					print("0:操作失败，必须删除子节点才能设置为不是菜单");
					return;
				}
			}
			List<DsCommonRes> list = null;
			if(true)//权限资源清单
			{
				list = new ArrayList<DsCommonRes>();
				String arr_url[] = req.getStringArray("rurl");
				if (0 < arr_url.length)
				{
					String arr_param[] = req.getStringArray("rparam");
					if (arr_url.length == arr_param.length)
					{
						for (int i = 0; i < arr_url.length; i++)
						{
							DsCommonRes m = new DsCommonRes();
							m.setUrl(arr_url[i]);
							m.setParam(arr_param[i]);
							if (0 < m.getUrl().length())//为空的不添加，直接忽略
							{
								list.add(m);
							}
						}
					}
					else
					{
						print("0:操作失败，请刷新重试");//url和param的个数不一致
						return;
					}
				}
				if(0 == po.getStatus() && 0 >= list.size())
				{
					print("0:操作失败，不作为菜单时，权限资源不能为空");
					return;
				}
			}
			po.setResourcesList(list);
			service.update(po);//systemid和seq不修改
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 排序
	@RequestMapping("/updFuncSeq1")
	public String updFuncSeq1()
	{
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		List<DsCommonFunc> list = service.queryList(systemid, pid);
		put("list", list);
		return "/common/func/updFuncSeq.jsp";
	}
	@RequestMapping("/updFuncSeq2")
	public void updFuncSeq2()
	{
		Long[] ids = CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0));
		try
		{
			if (ids.length > 0)
			{
				service.updateSeq(ids);
				print(1);
			}
			else
			{
				print("0:没有需要排序的节点");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	
	// 移动
	@RequestMapping("/updFuncMove1")
	public String updFuncMove1()
	{
		DsCommonSystem po = service.getSystem(req.getLong("systemid"));
		put("po", po);
		return "/common/func/updFuncMove.jsp";
	}
	@RequestMapping("/updFuncMove2")
	public void updFuncMove2()
	{
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		if(pid <= 0)
		{
			pid = 0;
		}
		else
		{
			DsCommonFunc m = service.get(pid);
			if(m == null || m.getSystemid().longValue() != systemid)
			{
				print("0:不能进行移动");// 移动的节点不存在，或者不在相同的系统
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
	@RequestMapping("/getFuncTree")
	public String getFuncTree()
	{
		long systemid = req.getLong("systemid");
		put("po",  service.getSystem(systemid));
		return "/common/func/getFuncTree.jsp";
	}
	// 获得列表
	@RequestMapping("/getFunc")
	public String getFunc()
	{
		long systemid = req.getLong("systemid");
		Long pid = req.getLong("pid");
		List<DsCommonFunc> list = service.queryList(systemid, pid);
		put("list", list);
		put("systemid", systemid);
		put("pid", pid);
		return "/common/func/getFunc.jsp";
	}
	// 获得树形管理时的json数据
	@RequestMapping("/getFuncJson")// BySystemidAndPid
	public void getFuncJson()
	{
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		print(service.queryList(systemid, pid));
	}
	
	// 明细
	@RequestMapping("/getFuncById")
	public String getFuncById()
	{
		Long id = req.getLong("keyIndex");
		DsCommonFunc po = service.get(id);
		List<DsCommonRes> list = po.getResourcesList();
		put("po", po);
		put("list", list);
		return "/common/func/getFuncById.jsp";
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
