package dswork.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.core.util.EncryptUtil;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import dswork.core.util.TimeUtil;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonUserService;
import dswork.web.MyRequest;

@Controller
@SuppressWarnings("all")
@RequestMapping("/common/user")
public class DsCommonUserController
{
	@Autowired
	private DsCommonUserService service;

	// 添加
	@RequestMapping("/addUser1")
	public String addUser1()
	{
		return "/common/user/addUser.jsp";
	}
	@RequestMapping("/addUser2")
	public void addUser2(PrintWriter out, DsCommonUser po)
	{
		try
		{
			if(po.getAccount().length() <= 0)
			{
				out.print("0:添加失败，账号不能为空");
			}
			else
			{
				if(!service.isExistByAccount(po.getAccount()))
				{
					po.setCreatetime(TimeUtil.getCurrentTime());
					po.setStatus(0);//默认禁用用户
					po.setPassword(EncryptUtil.encryptMd5(po.getPassword()));
					service.save(po);
					out.print(1);
				}
				else
				{
					out.print("0:添加失败，该账号已存在");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delUser")
	public void delUser(HttpServletRequest request, PrintWriter out)
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
				service.delete(id);
			}
			out.print("1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updUser1")
	public String updUser1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonUser po = service.get(id);
		request.setAttribute("po", po);
		request.setAttribute("page", req.getInt("page", 1));
		return "/common/user/updUser.jsp";
	}
	@RequestMapping("/updUser2")
	public void updUser2(PrintWriter out, DsCommonUser po)
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

	// 修改用户状态
	@RequestMapping("/updUserStatus")
	public void updUserStatus(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long id = req.getLong("keyIndex");
		int status = req.getInt("status", -1);
		try
		{
			if(status == 0 || status == 1)
			{
				if(id <= 0)
				{
					out.print("此用户无法更改类型");
				}
				else
				{
					service.updateStatus(id, status);
					out.print(1);
				}
			}
			else
			{
				out.print("参数错误");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

	@RequestMapping("/updUserOrg1")
	public String updUserOrg1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		if(id > 0)
		{
			DsCommonUser po = service.get(id);
			request.setAttribute("po", po);
			request.setAttribute("page", req.getInt("page", 1));
			return "/common/user/updUserOrg.jsp";
		}
		return null;
	}
	@RequestMapping("/updUserOrg2")
	public void updUserOrg2(HttpServletRequest request, PrintWriter out)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			long id = req.getLong("id");
			long orgpid = req.getLong("orgpid");
			long orgid = req.getLong("orgid");
			service.updateOrg(id, orgpid, orgid);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/updUserPassword1")
	public String updUserPassword1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		if(id > 0)
		{
			DsCommonUser po = service.get(id);
			request.setAttribute("po", po);
			request.setAttribute("page", req.getInt("page", 1));
			return "/common/user/updUserPassword.jsp";
		}
		return null;
	}
	@RequestMapping("/updUserPassword2")
	public void updUserPassword2(HttpServletRequest request, PrintWriter out)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			long id = req.getLong("id");
			String password = req.getString("password");
			service.updatePassword(id, password);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/getUser")
	public String getUser(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Map map = req.getParameterValueMap(false, false);
		Page<DsCommonUser> pageModel = service.queryPage(req.getInt("page", 1), req.getInt("pageSize", 10), map);
		PageNav pageNav = new PageNav(request, pageModel);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", pageNav);
		return "/common/user/getUser.jsp";
	}

	// 明细
	@RequestMapping("/getUserById")
	public String getUserById(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		return "/common/user/getUserById.jsp";
	}
}
