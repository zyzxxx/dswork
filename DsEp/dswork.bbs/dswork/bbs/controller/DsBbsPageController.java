/**
 * 功能:主题Controller
 * 开发人员:白云区
 * 创建时间:2015/4/2 12:54:49
 */
package dswork.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import dswork.bbs.model.DsBbsPage;
import dswork.bbs.service.DsBbsPageService;

@Scope("prototype")
@Controller
@RequestMapping("/bbs/web")
public class DsBbsPageController extends BaseController
{
	@Autowired
	private DsBbsPageService service;

	//添加
	@RequestMapping("/addDsBbsPage1")
	public String addDsBbsPage1()
	{
		return "/bbs/web/addDsBbsPage.jsp";
	}
	
	@RequestMapping("/addDsBbsPage2")
	public void addDsBbsPage2(DsBbsPage po)
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
	@RequestMapping("/delDsBbsPage")
	public void delDsBbsPage()
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
	@RequestMapping("/updDsBbsPage1")
	public String updDsBbsPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/bbs/web/updDsBbsPage.jsp";
	}
	
	@RequestMapping("/updDsBbsPage2")
	public void updDsBbsPage2(DsBbsPage po)
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
	@RequestMapping("/getDsBbsPage")
	public String getDsBbsPage()
	{
		Page<DsBbsPage> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsBbsPage>(request, pageModel));
		return "/bbs/web/getDsBbsPage.jsp";
	}

	//明细
	@RequestMapping("/getDsBbsPageById")
	public String getDsBbsPageById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/bbs/web/getDsBbsPageById.jsp";
	}
}
