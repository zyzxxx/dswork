package dswork.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.common.model.DsCommonOrg;
import dswork.common.service.DsCommonUserOrgService;

@Scope("prototype")
@Controller
@RequestMapping("/common/userorg")
public class DsCommonUserOrgController extends BaseController
{
	@Autowired
	private DsCommonUserOrgService service;

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree()
	{
		Long rootid = req.getLong("rootid");// 作为限制根节点显示
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
		return "/common/userorg/getOrgTree.jsp";
	}

	@RequestMapping("/getUserOrg")
	public String getUserOrg()
	{
		Long pid = req.getLong("pid");
		put("pid", pid);
		put("orgList", service.queryOrgList(pid));
		put("userList", service.queryUserList(pid));
		return "/common/userorg/getUserOrg.jsp";
	}
}
