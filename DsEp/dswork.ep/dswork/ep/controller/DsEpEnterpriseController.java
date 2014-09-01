package dswork.ep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.ep.model.DsEpEnterprise;
import dswork.ep.model.DsEpUser;
import dswork.ep.service.DsEpEnterpriseService;

@Scope("prototype")
@Controller
@RequestMapping("/ep/enterprise")
public class DsEpEnterpriseController extends BaseController
{
	@Autowired
	private DsEpEnterpriseService service;

	//添加
	@RequestMapping("/addEnterprise1")
	public String addEnterprise1()
	{
		return "/ep/enterprise/addEnterprise.jsp";
	}
	
	@RequestMapping("/addEnterprise2")
	public void addEnterprise2(DsEpEnterprise ent, DsEpUser user)
	{
		try
		{
			String username = req.getString("username");
			user.setName(username);
			user.setQybm(ent.getQybm());
			user.setStatus(1);// 企业管理员
			service.save(ent, user);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//修改
	@RequestMapping("/updEnterprise1")
	public String updEnterprise1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/ep/enterprise/updEnterprise.jsp";
	}
	
	@RequestMapping("/updEnterprise2")
	public void updEnterprise2(DsEpEnterprise po)
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

	//获得分页
	@RequestMapping("/getEnterprise")
	public String getEnterprise()
	{
		Page<DsEpEnterprise> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsEpEnterprise>(request, pageModel));
		return "/ep/enterprise/getEnterprise.jsp";
	}

	//明细
	@RequestMapping("/getEnterpriseById")
	public String getEnterpriseById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/ep/enterprise/getEnterpriseById.jsp";
	}
}
