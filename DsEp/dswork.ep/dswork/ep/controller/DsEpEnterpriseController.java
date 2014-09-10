package dswork.ep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.auth.Auth;
import common.auth.AuthLogin;
import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.TimeUtil;
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

	// 添加
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
			user.setCreatetime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			user.setSsdw(ent.getName());
			service.save(ent, user);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updEnterprise1")
	public String updEnterprise1()
	{
		Long id = req.getLong("keyIndex");
		DsEpEnterprise po = service.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qybm", po.getQybm());
		map.put("status", 1); // 1代表管理员
		List<DsEpUser> userlist = service.queryListUser(map);
		if(userlist.size() > 0)
		{
			if(checkUser(userlist.get(0).getId()))
			{
				put("po", po);
				put("admin", userlist.get(0));
				put("page", req.getInt("page", 1));
				return "/ep/enterprise/updEnterprise.jsp";
			}
		}
		return null;
	}

	@RequestMapping("/updEnterprise2")
	public void updEnterprise2(DsEpEnterprise po, DsEpUser user)
	{
		try
		{
			Long id = req.getLong("id2");
			String name = req.getString("name1");
			String password = req.getString("password");
			String account = req.getString("account");
			String mobile = req.getString("mobile");
			String phone = req.getString("phone");
			String email = req.getString("email");
			String idcard = req.getString("idcard");
			String workcard = req.getString("workcard");
			String cakey = req.getString("ssdw");
			String ssbm = req.getString("ssbm");
			Auth auth = AuthLogin.getLoginUser(request, response);
			po.setQybm(auth.getQybm());
			user.setStatus(1);
			user.setQybm(auth.getQybm());
			user.setPassword(password);
			user.setId(id);
			user.setName(name);
			user.setAccount(account);
			user.setMobile(mobile);
			user.setPhone(phone);
			user.setEmail(email);
			user.setIdcard(idcard);
			user.setWorkcard(workcard);
			user.setCakey(cakey);
			user.setSsbm(ssbm);
			service.update(po, user);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/getEnterprise")
	public String getEnterprise()
	{
		Page<DsEpEnterprise> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsEpEnterprise>(request, pageModel));
		return "/ep/enterprise/getEnterprise.jsp";
	}

	// 明细
	@RequestMapping("/getEnterpriseById")
	public String getEnterpriseById()
	{
		Long id = req.getLong("keyIndex");
		DsEpEnterprise po = service.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qybm", po.getQybm());
		map.put("status", 1); // 1代表管理员
		List<DsEpUser> userlist = service.queryListUser(map);
		if(userlist.size() > 0)
		{
			put("po", po);
			put("admin", userlist.get(0));
			return "/ep/enterprise/getEnterpriseById.jsp";
		}
		return null;
	}

	@RequestMapping("/updPassword1")
	public String updPassword1()
	{
		Long id = req.getLong("keyIndex");
		String qybm = service.get(id).getQybm();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("qybm", qybm);
		List<DsEpUser> userlist = service.queryListUser(map);
		if(userlist.size() > 0)
		{
			put("po", userlist.get(0));
			return "/ep/enterprise/updPassword.jsp";
		}
		else
		{
			return null;
		}
	}

	@RequestMapping("/updPassword2")
	public void updPassword2(DsEpUser po)
	{
		try
		{
			if(checkUser(po.getId()))
			{
				service.updatePassword(po.getId(), 1, po.getPassword());
				print(1);
			}
			else
			{
				print("0:信息不符！");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	public boolean checkUser(Long id)
	{
		try
		{
			return service.getUser(id).getQybm().equals(common.auth.AuthLogin.getLoginUser(request, response).getQybm());
		}
		catch(Exception ex)
		{
		}
		return false;
	}
}
