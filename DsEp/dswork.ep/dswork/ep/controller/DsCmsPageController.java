package dswork.ep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import dswork.ep.model.DsCmsPage;
import dswork.ep.service.DsCmsPageService;

@Scope("prototype")
@Controller
@RequestMapping("/cms/page")
public class DsCmsPageController extends BaseController
{
	@Autowired
	private DsCmsPageService service;

	//添加
	@RequestMapping("/addPage1")
	public String addPage1()
	{
		return "/cms/page/addPage.jsp";
	}
	
	@RequestMapping("/addPage2")
	public void addPage2(DsCmsPage po)
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

	//删除
	@RequestMapping("/delPage")
	public void delPage()
	{
		try
		{
			service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
			print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	//修改
	@RequestMapping("/updPage1")
	public String updPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/page/updPage.jsp";
	}
	
	@RequestMapping("/updPage2")
	public void updPage2(DsCmsPage po)
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
	@RequestMapping("/getPage")
	public String getPage()
	{
		Page<DsCmsPage> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsCmsPage>(request, pageModel));
		return "/cms/page/getPage.jsp";
	}

	//明细
	@RequestMapping("/getPageById")
	public String getPageById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/cms/page/getPageById.jsp";
	}
}
