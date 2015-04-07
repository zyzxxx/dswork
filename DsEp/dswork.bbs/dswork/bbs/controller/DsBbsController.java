package dswork.bbs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.bbs.model.DsBbsForum;
import dswork.bbs.model.DsBbsPage;
import dswork.bbs.model.DsBbsSite;
import dswork.bbs.service.DsBbsForumService;
import dswork.bbs.service.DsBbsPageService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;

//@Scope("prototype")
//@Controller
//@RequestMapping("/bbs")
public class DsBbsController extends BaseController
{
	@Autowired
	private DsBbsPageService service;

	//添加
	@RequestMapping("/addDsBbsPage1")
	public String addDsBbsPage1()
	{
		return "/bbs/addDsBbsPage.jsp";
	}
	
	@RequestMapping("/bbs/addDsBbsPage2")
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
	@RequestMapping("/bbs/delDsBbsPage")
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
	@RequestMapping("/bbs/updDsBbsPage1")
	public String updDsBbsPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/bbs/updDsBbsPage.jsp";
	}
	
	@RequestMapping("/bbs/updDsBbsPage2")
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
	@RequestMapping("/bbs/getDsBbsPage")
	public String getDsBbsPage()
	{
		Page<DsBbsPage> pageModel = service.queryPage(getPageRequest());
		put("pageModel", pageModel);
		put("pageNav", new PageNav<DsBbsPage>(request, pageModel));
		return "/bbs/getDsBbsPage.jsp";
	}

	//明细
	@RequestMapping("/bbs/getDsBbsPageById")
	public String getDsBbsPageById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/bbs/getDsBbsPageById.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private DsBbsForumService forumService;

	// 获得栏目列表
	@RequestMapping("/index")
	public String index()
	{
		try
		{
			Long id = req.getLong("siteid", 0);
			put("list", queryForum(id, 0));
			return "/bbs/forum.jsp";
		}
		catch(Exception ex)
		{
		}
		return null;
	}

	// 获得栏目列表
	@RequestMapping("/forum-{id}-{p}")
	public String forum(@PathVariable("id") Long id, @PathVariable("p") int page)
	{
		try
		{
			put("list", queryForum(id, 0));
			return "/bbs/forum/getForum.jsp";
		}
		catch(Exception ex)
		{
		}
		return null;
	}

	/**
	 * 取出当前登录用户的栏目
	 * @param excludeId 需要清空指定id的子栏目
	 * @return List
	 */
	private List<DsBbsForum> queryForum(long siteid, long pid)
	{
		PageRequest rq = getPageRequest();
		rq.getFilters().put("siteid", siteid);
		List<DsBbsForum> clist = forumService.queryList(rq);
		List<DsBbsForum> tlist = new ArrayList<DsBbsForum>();
		for(DsBbsForum m : clist)
		{
			if(m.getPid() == pid && m.getStatus() == 1)
			{
				tlist.add(m);
			}
		}
		return tlist;
	}
}
