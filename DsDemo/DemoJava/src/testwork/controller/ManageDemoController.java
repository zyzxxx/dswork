/**
 * 功能:MyBatis样例Controller
 * 开发人员:skey
 * 创建时间:2014-07-01 12:01:01
 */
package testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.web.MyCookie;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;
import testwork.model.Demo;
import testwork.service.ManageDemoService;

@Scope("prototype")
@Controller
@RequestMapping("/manage/demo")// 控制器类名对应url的目录部分(除应用名contextPath)
public class ManageDemoController extends BaseController
{
	@Autowired
	private ManageDemoService service;// 一个service对应一个控制器Controller

	@RequestMapping("/test")
	public void test()
	{
		MyCookie m = new MyCookie(request, response);
		m.addCookie("test", "123456789987654321", 100000);
		session.setAttribute("test", "123456789987654321");
		print("test");
	}

	@RequestMapping("/addDemo1")
	public String addDemo1()
	{
		MyCookie m = new MyCookie(request, response);
		m.addCookie("test", String.valueOf(session.getAttribute("test")), 200000);
		return "/manage/demo/addDemo.jsp";
	}

	@RequestMapping("/addDemo2")
	public void addDemo2(Demo po)
	{
		try
		{
			MyCookie m = new MyCookie(request, response);
			m.addCookie("test", String.valueOf(session.getAttribute("test")), 30000);
			service.save(po);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/delDemo")
	public void delDemo()
	{
		try
		{
			service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
			print("1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/updDemo1")
	public String upd1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/manage/demo/updDemo.jsp";
	}

	@RequestMapping("/updDemo2")
	public void updDemo2(Demo po)
	{
		try
		{
			service.update(po);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/getDemo")
	public String getDemo()
	{
		PageRequest pr = getPageRequest();
		pr.setFilters(req.getParameterValueMap(false, false));
		Page<Demo> pageModel = service.queryPage(pr);
		put("pageModel", pageModel);
		put("pageNav", new PageNav<Demo>(request, pageModel));
		return "/manage/demo/getDemo.jsp";
	}

	@RequestMapping("/getDemoById")
	public String getDemoById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/manage/demo/getDemoById.jsp";
	}
}
