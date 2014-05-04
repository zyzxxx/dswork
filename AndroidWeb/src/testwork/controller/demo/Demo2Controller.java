/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.db.BaseService;
import testwork.model.demo.Demo;
import testwork.service.demo.DemoService;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/demo2")
public class Demo2Controller extends BaseController<Demo>
{
	@Autowired
	private DemoService service;
	protected BaseService getService() {return service;};
	protected String getViewAdd() {return "/WEB-INF/view/manage/demo2/addDemo.jsp";};
	protected String getViewUpd() {return "/WEB-INF/view/manage/demo2/updDemo.jsp";};
	protected String getViewGet() {return "/WEB-INF/view/manage/demo2/getDemo.jsp";};
	protected String getViewGetById() {return "/WEB-INF/view/manage/demo2/getDemoById.jsp";};
	

	static int i = 1;
	
	@RequestMapping("/test")
	public void test(HttpServletResponse response)
	{
		print(response, i);
	}
	
	@RequestMapping("/test2")
	public void test2(HttpServletResponse response)
	{
		getService();
		print(response, i);
	}

	protected void print(HttpServletResponse response, Object msg)
	{
		try
		{
			PrintWriter out = response.getWriter();
			out.print(msg);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
