/**
 * 功能:网页文章Controller
 * 开发人员:无名
 * 创建时间:2014-8-24 15:27:31
 */
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
	@RequestMapping("/addDsCmsPage1")
	public String addDsCmsPage1()
	{
		return "/cms/page/addDsCmsPage.jsp";
	}
	
	@RequestMapping("/addDsCmsPage2")
	public void addDsCmsPage2(DsCmsPage po)
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
	@RequestMapping("/delDsCmsPage")
	public void delDsCmsPage()
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
	@RequestMapping("/updDsCmsPage1")
	public String updDsCmsPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/page/updDsCmsPage.jsp";
	}
	
	@RequestMapping("/updDsCmsPage2")
	public void updDsCmsPage2(DsCmsPage po)
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
	@RequestMapping("/getDsCmsPage")
	public String getDsCmsPage()
	{
		Page<DsCmsPage> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsCmsPage>(request, pageModel));
		return "/cms/page/getDsCmsPage.jsp";
	}

	//明细
	@RequestMapping("/getDsCmsPageById")
	public String getDsCmsPageById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/cms/page/getDsCmsPageById.jsp";
	}
}
