package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.model.DsCommonRole;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonUserRoleService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;

@Scope("prototype")
@Controller
@RequestMapping("/common/userrole")
@SuppressWarnings("all")
public class DsCommonUserRoleController extends BaseController
{
	@Autowired
	private DsCommonUserRoleService service;

	@RequestMapping("/getUser")
	public String getUser()
	{
		Page<DsCommonUser> pageModel = service.queryUserPage(getPageRequest());
		PageNav pageNav = new PageNav(request, pageModel);
		put("pageModel", pageModel);
		put("pageNav", pageNav);
		put("systemid", req.getLong("systemid"));
		return "/common/userrole/getUser.jsp";
	}

	@RequestMapping("/updSetUser1")
	public String updSetUser1()
	{
		Long id = req.getLong("id");
		put("systemid", req.getLong("systemid"));
		put("userid", id);
		put("list", service.queryListByUserid(id));
		return "/common/userrole/updSetUser.jsp";
	}
	@RequestMapping("/updSetUser2")
	public void updSetUser2()
	{
		try
		{
			Long userid = req.getLong("userid");
			String ids = req.getString("roleids", "");
			List<Long> list = new ArrayList<Long>();
			if(ids.length() > 0)
			{
				for(String tmp : ids.split(","))
				{
					list.add(new Long(tmp));
				}
			}
			service.saveByUser(userid, list);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/getRoleTree")
	public String getRoleTree()
	{
		long systemid = req.getLong("systemid");
		put("systemid", systemid);
		return "/common/userrole/getRoleTree.jsp";
	}
	@RequestMapping("/getRole")
	public String getRole()
	{
		long systemid = req.getLong("systemid");
		Long pid = req.getLong("pid");
		List<DsCommonRole> list = service.queryRoleList(systemid, pid);
		put("list", list);
		put("systemid", systemid);
		put("pid", pid);
		return "/common/userrole/getRole.jsp";
	}
	
	@RequestMapping("/updSetRole1")
	public String updSetRole1()
	{
		Long id = req.getLong("id");
		put("roleid", id);
		put("list", service.queryListByRoleid(id));
		return "/common/userrole/updSetRole.jsp";
	}
	@RequestMapping("/updSetRole2")
	public void updSetRole2()
	{
		try
		{
			Long roleid = req.getLong("roleid");
			if(roleid > 0)
			{
				String ids = req.getString("userids", "");
				List<Long> list = new ArrayList<Long>();
				if(ids.length() > 0)
				{
					for(String tmp : ids.split(","))
					{
						list.add(new Long(tmp));
					}
				}
				service.saveByRole(roleid, list);
				print(1);
			}
			else
			{
				print(0);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	@RequestMapping("/getUserChoose")
	public String getUserChoose()
	{
		Page<DsCommonUser> pageModel = service.queryUserPage(getPageRequest());
		PageNav pageNav = new PageNav(request, pageModel);
		put("pageModel", pageModel);
		put("pageNav", pageNav);
		return "/common/userrole/getUserChoose.jsp";
	}
	@RequestMapping("/getUserById")
	public String getUserById()
	{
		Long id = req.getLong("userid");
		put("po", service.getUser(id));
		return "/common/userrole/getUserById.jsp";
	}
}
