package dswork.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.model.DsCommonDict;
import dswork.common.model.DsCommonSystem;
import dswork.common.service.DsCommonSystemService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;

//应用系统
@Scope("prototype")
@Controller
@RequestMapping("/common/system")
public class DsCommonSystemController extends BaseController
{
	@Autowired
	private DsCommonSystemService service;

	// 添加
	@RequestMapping("/addSystem1")
	public String addSystem1()
	{
		return "/common/system/addSystem.jsp";
	}
	@RequestMapping("/addSystem2")
	public void addSystem2(DsCommonSystem po)
	{
		try
		{
			if (po.getAlias().length() <= 0)
			{
				print("0:添加失败，标识不能为空");
			}
			else
			{
				if (!service.isExistsByAlias(po.getAlias()))
				{
					po.setStatus(0);// 默认禁用系统
					service.save(po);
					print(1);
				}
				else
				{
					print("0:添加失败，标识已存在");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delSystem")
	public void delSystem()
	{
		try
		{
			long systemid = req.getLong("keyIndex", 0);
			int funcCount = service.getCountFuncBySystemid(systemid);
			int roleCount = service.getCountRoleBySystemid(systemid);
			if (0 >= funcCount && 0 >= roleCount)
			{
				service.delete(systemid);
				print(1);
			}
			else
			{
				String msg = "操作失败，以下内容必须先清除：";
				int c = 0;
				if (0 < funcCount)
				{
					msg += "系统功能";
					c++;
				}
				if (0 < roleCount)
				{
					msg += ((c == 1)?"、系统角色":"系统角色");
				}
				print("0:" + msg);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updSystem1")
	public String updSystem(DsCommonDict po)
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/common/system/updSystem.jsp";
	}
	@RequestMapping("/updSystem2")
	public void updSystem2(DsCommonSystem po)
	{
		try
		{
			service.update(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 修改状态
	@RequestMapping("/updSystemStatus")
	public void updSystemStatus()
	{
		long id = req.getLong("keyIndex");
		int status = req.getInt("status", -1);
		try
		{
			if (status == 0 || status == 1)
			{
				service.updateStatus(id, status);
				print(1);
			}
			else
			{
				print("0:参数错误");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/getSystem")
	public String getSystem()
	{
		Page<DsCommonSystem> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsCommonSystem>(request, pageModel));
		return "/common/system/getSystem.jsp";
	}
	
	// 明细
	@RequestMapping("/getSystemById")
	public String getSystemById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/common/system/getSystemById.jsp";
	}
}
