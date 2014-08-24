/**
 * 功能:栏目Controller
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
import dswork.ep.model.DsCmsCategory;
import dswork.ep.service.DsCmsCategoryService;

@Scope("prototype")
@Controller
@RequestMapping("/cms/category")
public class DsCmsCategoryController extends BaseController
{
	@Autowired
	private DsCmsCategoryService service;

	//添加
	@RequestMapping("/addDsCmsCategory1")
	public String addDsCmsCategory1()
	{
		return "/cms/category/addDsCmsCategory.jsp";
	}
	
	@RequestMapping("/addDsCmsCategory2")
	public void addDsCmsCategory2(DsCmsCategory po)
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
	@RequestMapping("/delDsCmsCategory")
	public void delDsCmsCategory()
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
	@RequestMapping("/updDsCmsCategory1")
	public String updDsCmsCategory1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/category/updDsCmsCategory.jsp";
	}
	
	@RequestMapping("/updDsCmsCategory2")
	public void updDsCmsCategory2(DsCmsCategory po)
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
	@RequestMapping("/getDsCmsCategory")
	public String getDsCmsCategory()
	{
		Page<DsCmsCategory> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsCmsCategory>(request, pageModel));
		return "/cms/category/getDsCmsCategory.jsp";
	}

	//明细
	@RequestMapping("/getDsCmsCategoryById")
	public String getDsCmsCategoryById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/cms/category/getDsCmsCategoryById.jsp";
	}
}
