package dswork.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.core.util.EncryptUtil;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import dswork.core.util.TimeUtil;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonUserService;
import dswork.mvc.BaseController;
import dswork.web.MyRequest;

@Scope("prototype")
@Controller
@SuppressWarnings("all")
@RequestMapping("/common/user")
public class DsCommonUserController extends BaseController
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
	public void addUser2(DsCommonUser po)
	{
		try
		{
			if(po.getAccount().length() <= 0)
			{
				print("0:添加失败，账号不能为空");
			}
			else
			{
				if(!service.isExistsByAccount(po.getAccount()))
				{
					po.setCreatetime(TimeUtil.getCurrentTime());
					po.setStatus(0);//默认禁用用户
					po.setPassword(EncryptUtil.encryptMd5(po.getPassword()));
					service.save(po);
					print(1);
				}
				else
				{
					print("0:添加失败，该账号已存在");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/delUser")
	public void delUser()
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
				service.delete(id);
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
	@RequestMapping("/updUser1")
	public String updUser1()
	{
		Long id = req.getLong("keyIndex");
		DsCommonUser po = service.get(id);
		put("po", po);
		put("page", req.getInt("page", 1));
		return "/common/user/updUser.jsp";
	}
	@RequestMapping("/updUser2")
	public void updUser2(DsCommonUser po)
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

	// 修改用户状态
	@RequestMapping("/updUserStatus")
	public void updUserStatus()
	{
		long id = req.getLong("keyIndex");
		int status = req.getInt("status", -1);
		try
		{
			if(status == 0 || status == 1)
			{
				if(id <= 0)
				{
					print("此用户无法更改类型");
				}
				else
				{
					service.updateStatus(id, status);
					print(1);
				}
			}
			else
			{
				print("参数错误");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print(e.getMessage());
		}
	}

	@RequestMapping("/updUserOrg1")
	public String updUserOrg1()
	{
		Long id = req.getLong("keyIndex");
		if(id > 0)
		{
			DsCommonUser po = service.get(id);
			put("po", po);
			put("page", req.getInt("page", 1));
			return "/common/user/updUserOrg.jsp";
		}
		return null;
	}
	@RequestMapping("/updUserOrg2")
	public void updUserOrg2()
	{
		try
		{
			long id = req.getLong("id");
			long orgpid = req.getLong("orgpid");
			long orgid = req.getLong("orgid");
			service.updateOrg(id, orgpid, orgid);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/updUserPassword1")
	public String updUserPassword1()
	{
		Long id = req.getLong("keyIndex");
		if(id > 0)
		{
			DsCommonUser po = service.get(id);
			put("po", po);
			put("page", req.getInt("page", 1));
			return "/common/user/updUserPassword.jsp";
		}
		return null;
	}
	@RequestMapping("/updUserPassword2")
	public void updUserPassword2()
	{
		try
		{
			long id = req.getLong("id");
			String password = req.getString("password");
			service.updatePassword(id, password);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/getUser")
	public String getUser()
	{
		Page<DsCommonUser> pageModel = service.queryPage(getPageRequest());
		PageNav pageNav = new PageNav(request, pageModel);
		put("pageModel", pageModel);
		put("pageNav", pageNav);
		return "/common/user/getUser.jsp";
	}

	// 明细
	@RequestMapping("/getUserById")
	public String getUserById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/common/user/getUserById.jsp";
	}
}
