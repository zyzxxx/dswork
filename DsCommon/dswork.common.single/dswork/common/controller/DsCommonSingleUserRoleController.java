package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonSingleUserRoleService;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/ds/common/single/userrole")
public class DsCommonSingleUserRoleController extends BaseController
{
	@Autowired
	private DsCommonSingleUserRoleService service;

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree()
	{
		Long rootid = getLoginUser().getOrgpid();// 作为限制根节点显示
		DsCommonOrg po = null;
		if(rootid > 0)
		{
			po = service.getOrg(rootid);
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
		put("po", po);
		return "/ds/common/single/userrole/getOrgTree.jsp";
	}

	@RequestMapping("/getUser")
	public String getUser()
	{
		Long pid = req.getLong("pid");
		put("list", service.queryUserList(pid));
		return "/ds/common/single/userrole/getUser.jsp";
	}

	@RequestMapping("/getUserById")
	public String getUserById()
	{
		Long id = req.getLong("id");
		Long systemid = req.getLong("systemid");
		put("po", service.getUser(id));
		put("list", service.queryOrgRoleList(id, systemid));
		return "/ds/common/single/userrole/getUserById.jsp";
	}

	// 授权
	@RequestMapping("/updUserRole1")
	public String updUserRole1()
	{
		Long id = req.getLong("id");
		Long systemid = req.getLong("systemid");
		DsCommonOrg po = service.getOrg(id);
		if(null == po)
		{
			return null;// 非法访问，否则肯定存在id
		}
		if(0 == po.getStatus())// 岗位才可以授权
		{
			put("po", service.getUser(id));
			put("list", service.queryOrgRoleList(id, systemid));
			return "/ds/common/single/userrole/updUserRole.jsp";
		}
		return null;
	}

	@RequestMapping("/updUserRole2")
	public void updUserRole2()
	{
		try
		{
			Long id = req.getLong("orgid");
			DsCommonOrg po = service.getOrg(id);
			if(null == po)
			{
				print(0);
				return;// 非法访问，否则肯定存在id
			}
			if(0 == po.getStatus())// 岗位才可以授权
			{
				String ids = req.getString("roleids", "");
				List<Long> list = new ArrayList<Long>();
				if(ids.length() > 0)
				{
					for(String tmp : ids.split(","))
					{
						list.add(new Long(tmp));
					}
				}
				service.saveOrgRole(id, list);
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

	private DsCommonUser getLoginUser()
	{
		String account = dswork.sso.WebFilter.getAccount(session);
		return service.getUserByAccount(account);
	}
}
