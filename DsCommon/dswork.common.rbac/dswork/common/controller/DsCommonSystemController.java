package dswork.common.controller;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import dswork.web.MyRequest;
import dswork.common.model.DsCommonDict;
import dswork.common.model.DsCommonSystem;
import dswork.common.service.DsCommonSystemService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;

//应用系统
@Controller
@RequestMapping("/common/system")
public class DsCommonSystemController
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
	public void addSystem2(PrintWriter out, DsCommonSystem po)
	{
		try
		{
			if (po.getAlias().length() <= 0)
			{
				out.print("0:添加失败，标识不能为空");
			}
			else
			{
				if (!service.isExistByAlias(po.getAlias()))
				{
					po.setStatus(0);// 默认禁用系统
					service.save(po);
					out.print(1);
				}
				else
				{
					out.print("0:添加失败，标识已存在");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delSystem")
	public void delSystem(HttpServletRequest request, PrintWriter out)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			long systemid = req.getLong("keyIndex", 0);
			int funcCount = service.getCountFuncBySystemid(systemid);
			int roleCount = service.getCountRoleBySystemid(systemid);
			if (0 >= funcCount && 0 >= roleCount)
			{
				service.delete(systemid);
				out.print(1);
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
				out.print("0:" + msg);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updSystem1")
	public String updSystem(HttpServletRequest request, DsCommonDict po)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		return "/common/system/updSystem.jsp";
	}
	@RequestMapping("/updSystem2")
	public void updSystem2(PrintWriter out, DsCommonSystem po)
	{
		try
		{
			service.update(po);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 修改状态
	@RequestMapping("/updSystemStatus")
	public void updSystemStatus(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long id = req.getLong("keyIndex");
		int status = req.getInt("status", -1);
		try
		{
			if (status == 0 || status == 1)
			{
				service.updateStatus(id, status);
				out.print(1);
			}
			else
			{
				out.print("0:参数错误");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/getSystem")
	public String getSystem(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Page<DsCommonSystem> pageModel = service.queryPage(req.getInt("page", 1), req.getInt("pageSize", 10), req.getParameterValueMap(false, false));
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav<DsCommonSystem>(request, pageModel));
		return "/common/system/getSystem.jsp";
	}
	
	// 明细
	@RequestMapping("/getSystemById")
	public String getSystemById(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		return "/common/system/getSystemById.jsp";
	}
}
