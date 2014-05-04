/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.hbmdemo;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dswork.mvc.BaseController;
import dswork.core.db.BaseService;
import testwork.model.hbmdemo.HbmDemo;
import testwork.service.hbmdemo.HbmDemoService;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/hbmdemo")
public class HbmDemoController extends BaseController<HbmDemo>
{
	@Autowired
	private HbmDemoService service;
	protected BaseService getService() {return service;};
	protected String getViewAdd() {return "/WEB-INF/view/manage/demo/addDemo.jsp";};
	protected String getViewUpd() {return "/WEB-INF/view/manage/demo/updDemo.jsp";};
	protected String getViewGet() {return "/WEB-INF/view/manage/demo/getDemo.jsp";};
	protected String getViewGetById() {return "/WEB-INF/view/manage/demo/getDemoById.jsp";};
	
	@RequestMapping("/addDemo1")
	public String add1(HttpServletRequest request, HttpServletResponse response)
	{
		return super.add1(request, response);
	}
	@RequestMapping("/addDemo2")
	public void add2(HttpServletRequest request, HttpServletResponse response, HbmDemo po) throws IOException
	{
		super.add2(request, response, po);
	}
	@RequestMapping("/delDemo")
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		super.del(request, response);
	}
	@RequestMapping("/updDemo1")
	public String upd1(HttpServletRequest request, HttpServletResponse response)
	{
		return super.upd1(request, response);
	}
	@RequestMapping("/updDemo2")
	public void upd2(HttpServletRequest request, HttpServletResponse response, HbmDemo po) throws IOException
	{
		super.upd2(request, response, po);
	}
	@RequestMapping("/getDemo")
	public String get(HttpServletRequest request, HttpServletResponse response)
	{
		return super.get(request, response);
	}
	@RequestMapping("/getDemoById")
	public String getById(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0")Long keyIndex)
	{
		return super.getById(request, response, keyIndex);
	}
}
