package dswork.ep.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;
import dswork.core.util.TimeUtil;
import dswork.ep.model.DsEpUser;
import dswork.ep.service.DsEpUserService;

@Scope("prototype")
@Controller
@RequestMapping("/ep/user")
public class DsEpUserController extends BaseController
{
	@Autowired
	private DsEpUserService service;

	//添加
	@RequestMapping("/addUser1")
	public String addUser1()
	{
		String qybm = req.getString("qybm");
		put("qybm", qybm);
		return "/ep/user/addUser.jsp";
	}
	
	@RequestMapping("/addUser2")
	public void addUser2(DsEpUser po)
	{
		try
		{
			if(po.getAccount().length() <= 0)
			{
				print("0:添加失败，账号不能为空");
			}
			else
			{
				if(!service.isExists(po.getAccount()))
				{
					po.setStatus(0);
					po.setCreatetime(TimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					service.save(po);
					print(1);
				}
				else
				{
					print("0:添加失败，账号已存在");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//删除
	@RequestMapping("/delUser")
	public void delUser()
	{
		try
		{
			String qybm = req.getString("qybm");
			service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
			print(qybm);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//修改
	@RequestMapping("/updUser1")
	public String updUser1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/ep/user/updUser.jsp";
	}
	
	@RequestMapping("/updUser2")
	public void updUser2(DsEpUser po)
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
	@RequestMapping("/getUser")
	public String getUser()
	{
		String qybm = req.getString("qybm");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qybm", qybm);
		PageRequest rq = getPageRequest();
		rq.setFilters(map);
		Page<DsEpUser> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsEpUser>(request, pageModel));
		put("qybm", qybm);
		return "/ep/user/getUser.jsp";
	}

	//明细
	@RequestMapping("/getUserById")
	public String getUserById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/ep/user/getUserById.jsp";
	}
}
