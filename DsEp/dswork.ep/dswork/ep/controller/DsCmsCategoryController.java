/**
 * 功能:栏目Controller
 * 开发人员:无名
 * 创建时间:2014-8-24 15:27:31
 */
package dswork.ep.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.PageRequest;
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
	@RequestMapping("/addCategory1")
	public String addDsCmsCategory1()
	{
		return "/cms/category/addCategory.jsp";
	}
	
	@RequestMapping("/addCategory2")
	public void addCategory2(DsCmsCategory po)
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
	@RequestMapping("/delCategory")
	public void delCategory()
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
	@RequestMapping("/updCategory1")
	public String updCategory1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/category/updDsCmsCategory.jsp";
	}
	
	@RequestMapping("/updDsCmsCategory2")
	public void updCategory2(DsCmsCategory po)
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
	@RequestMapping("/getCategory")
	public String getCategory()
	{
		PageRequest rq = getPageRequest();
		rq.getFilters().put("qybm", common.auth.AuthLogin.getLoginUser(request, response).getQybm());
		List<DsCmsCategory> clist = service.queryList(rq);
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory m : clist)
		{
			map.put(m.getId(), m);
			if(m.getPid() == 0)
			{
				list.add(m);// 只把根节点放入list
			}
		}
		for(DsCmsCategory m : clist)
		{
			if(m.getPid() > 0)
			{
				try
				{
					map.get(m.getPid()).add(m);//依次放入其余节点对应的父节点
				}
				catch(Exception ex)
				{
					ex.printStackTrace();// 找不到对应的父栏目
				}
			}
		}
		put("list", list);
		return "/cms/category/getCategory.jsp";
	}

	//明细
	@RequestMapping("/getCategoryById")
	public String getCategoryById()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/cms/category/getCategoryById.jsp";
	}
}
