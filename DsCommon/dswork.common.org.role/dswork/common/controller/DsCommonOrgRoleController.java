package dswork.common.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.web.MyRequest;
import dswork.common.model.DsCommonOrg;
import dswork.common.service.DsCommonOrgRoleService;

@Controller
@RequestMapping("/common/orgrole")
public class DsCommonOrgRoleController
{
	@Autowired
	private DsCommonOrgRoleService service;

	// 树形管理
	@RequestMapping("/getOrgTree")
	public String getOrgTree(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
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
		request.setAttribute("po", po);
		return "/common/orgrole/getOrgTree.jsp";
	}

	// 授权
	@RequestMapping("/updOrgRole1")
	public String updOrgRole1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		DsCommonOrg po = service.get(id);
		if(null == po)
		{
			return null;// 非法访问，否则肯定存在id
		}
		if(0 == po.getStatus())// 岗位才可以授权
		{
			request.setAttribute("po", po);
			request.setAttribute("list", service.queryList(id));
			return "/common/orgrole/updOrgRole.jsp";
		}
		return null;
	}

	@RequestMapping("/updOrgRole2")
	public void updOrgRole2(HttpServletRequest request, PrintWriter out)
	{
		try
		{
			MyRequest req = new MyRequest(request);
			Long id = req.getLong("orgid");
			DsCommonOrg po = service.get(id);
			if(null == po)
			{
				out.print(0);
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
				service.save(id, list);
				out.print(1);
			}
			else
			{
				out.print(0);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}
}
