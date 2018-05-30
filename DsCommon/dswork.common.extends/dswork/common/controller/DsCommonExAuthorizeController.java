package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.common.model.DsCommonOrg;
import dswork.common.model.DsCommonUser;
import dswork.common.service.DsCommonExAuthorizeService;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/common/ex/authorize")
public class DsCommonExAuthorizeController extends BaseController
{
	@Autowired
	private DsCommonExAuthorizeService service;

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree()
	{
		Long rootid = getLoginUser().getOrgpid();// 作为限制根节点显示
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
		put("po", po);
		return "/common/ex/authorize/getOrgTree.jsp";
	}

	@RequestMapping("/getUserOrg")
	public String getUserOrg()
	{
		Long pid = req.getLong("pid");
		put("pid", pid);
		put("orgList", service.queryOrgList(pid));
		put("userList", service.queryUserList(pid));
		return "/common/ex/authorize/getUserOrg.jsp";
	}

	@RequestMapping("/updSetUser1")
	public String updSetUser1()
	{
		Long id = req.getLong("id");
		put("list", service.queryListByUserid(id));
		return "/common/ex/authorize/updSetUser.jsp";
	}
	@RequestMapping("/updSetUser2")
	public void updSetUser2()
	{
		try
		{
			Long userid = req.getLong("userid");
			String ids = req.getString("orgids", "");
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

	@RequestMapping("/updSetOrg1")
	public String updSetOrg1()
	{
		Long id = req.getLong("id");
		put("list", service.queryListByOrgid(id));
		return "/common/ex/authorize/updSetOrg.jsp";
	}
	@RequestMapping("/updSetOrg2")
	public void updSetOrg2()
	{
		try
		{
			Long orgid = req.getLong("orgid");
			if(orgid > 0)
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
				service.saveByOrg(orgid, list);
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
	
	
	@RequestMapping("/getUserById")
	public String getUserById()
	{
		Long id = req.getLong("id");
		put("user", service.getUserById(id));
		put("list", service.queryOrgListByUserid(id));
		return "/common/ex/authorize/getUserById.jsp";
	}
}
