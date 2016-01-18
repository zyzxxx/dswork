/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.web.MyRequest;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;
import testwork.model.Demo;
import testwork.service.demo.DemoService;

@Controller
@RequestMapping("/manage/demo")
public class DemoController
{
	@Autowired
	private DemoService service;

	@RequestMapping("/addDemo1")
	public String addDemo1()
	{
		return "/view/manage/demo/addDemo.jsp";
	}
	
	@RequestMapping("/addDemo2")
	public void addDemo2(PrintWriter out, Demo po)
	{
		try
		{
			service.save(po);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}
	
	@RequestMapping("/delDemo")
	public void delDemo(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		try
		{
			response.sendRedirect(req.getRefererURL());
		}
		catch(Exception ex)
		{
		}
	}
	
	@RequestMapping("/updDemo1")
	public String updDemo1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return "/view/manage/demo/updDemo.jsp";
	}
	
	@RequestMapping("/updDemo2")
	public void updDemo2(PrintWriter out, Demo po)
	{
		try
		{
			service.update(po);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}
	
	@RequestMapping("/getDemo")
	public String getDemo(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		PageRequest pr = new PageRequest();
		pr.setFilters(req.getParameterValueMap(false, false));
		pr.setCurrentPage(req.getInt("page", 1));
		pr.setPageSize(req.getInt("pageSize", 10));
		Page<Demo> pageModel = service.queryPage(pr);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav<Demo>(request, pageModel));
		return "/view/manage/demo/getDemo.jsp";
	}
	
	@RequestMapping("/getDemoById")
	public String getDemoById(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		return "/view/manage/demo/getDemoById.jsp";
	}
}
