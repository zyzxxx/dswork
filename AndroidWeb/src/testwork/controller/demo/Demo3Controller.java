/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.AgileController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import testwork.model.demo.Demo;
import testwork.service.demo.DemoService;

@Scope("prototype")// 必须为多例模式
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/demo3")
public class Demo3Controller extends AgileController
{
	@Autowired
	private DemoService service;
	protected DemoService getService() {return service;};
	
	static int i = 1;
	
	@RequestMapping("/test")
	public void test()
	{
		print(i);
	}
	
	@RequestMapping("/test2")
	public void test2()
	{
		getService();
		print(i);
	}
	
	@RequestMapping("/addDemo1")
	public String addDemo1()
	{
		return "/WEB-INF/view/manage/demo/addDemo.jsp";
	}
	
	@RequestMapping("/addDemo2")
	public void addDemo2(Demo po)
	{
		try
		{
			getService().save(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	
	@RequestMapping("/delDemo")
	public void delDemo() throws IOException
	{
		getService().deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		response.sendRedirect(req.getRefererURL());
	}
	
	@RequestMapping("/updDemo1")
	public String upd1()
	{
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", getService().get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return "/WEB-INF/view/manage/demo/updDemo.jsp";
	}
	@RequestMapping("/updDemo2")
	public void updDemo2(Demo po)
	{
		try
		{
			getService().update(po);
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	
	@RequestMapping("/getDemo")
	public String getDemo()
	{
		Page<Demo> pageModel = getService().queryPage(getPageRequest());
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav(request, pageModel));
		return "/WEB-INF/view/manage/demo/getDemo.jsp";
	}
	
	@RequestMapping("/getDemoById")
	public String getDemoById()
	{
		request.setAttribute("po", getService().get(req.getLong("keyIndex")));
		return "/WEB-INF/view/manage/demo/getDemoById.jsp";
	}
}
