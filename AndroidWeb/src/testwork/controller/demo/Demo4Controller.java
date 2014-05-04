/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.AgileController;
import dswork.spring.BeanFactory;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import testwork.model.demo.Demo;
import testwork.service.demo.DemoService;

@Scope("prototype")// 必须为多例模式
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/demo4")
public class Demo4Controller extends AgileController
{
	private static DemoService service;
	public static DemoService getService(){
	    if(service == null){service = (DemoService) BeanFactory.getBean("demoService");}
	    return service;
	}
	
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
			service.save(po);
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
		service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		response.sendRedirect(req.getRefererURL());
	}
	
	@RequestMapping("/updDemo1")
	public String upd1()
	{
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return "/WEB-INF/view/manage/demo/updDemo.jsp";
	}
	@RequestMapping("/updDemo2")
	public void updDemo2(Demo po)
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
	
	@RequestMapping("/getDemo")
	public String getDemo()
	{
		Page<Demo> pageModel = service.queryPage(getPageRequest());
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav(request, pageModel));
		return "/WEB-INF/view/manage/demo/getDemo.jsp";
	}
	
	@RequestMapping("/getDemoById")
	public String getDemoById()
	{
		request.setAttribute("po", service.get(req.getLong("keyIndex")));
		return "/WEB-INF/view/manage/demo/getDemoById.jsp";
	}
}
