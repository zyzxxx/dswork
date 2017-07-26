package dswork.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsAuditPage;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsEditService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;
import dswork.core.util.TimeUtil;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/cms/edit")
public class DsCmsEditController extends BaseController
{
	@Autowired
	private DsCmsEditService service;

	@RequestMapping("getCategoryTree")
	public String getCategoryEditTree()
	{
		try
		{
			long siteid = req.getLong("siteid", -1);
			DsCmsPermission permission = service.getPermissionByOwnAccount(getOwn(), getAccount());
			List<DsCmsSite> siteList = null;
			List<DsCmsCategory> cateList = null;
			if(permission != null)
			{
				if(siteid == -1)
				{
					cateList = service.queryListCategory(null);
				}
				else
				{
					cateList = service.queryListCategory(siteid);
				}
				Map<Long, Long> map = new HashMap<Long, Long>();
				Iterator<DsCmsCategory> cateListIter = cateList.iterator();
				while(cateListIter.hasNext())
				{
					DsCmsCategory cate = cateListIter.next();
					if(permission.checkEditall(cate.getId()) || permission.checkEditown(cate.getId()))
					{
						map.put(cate.getSiteid(), cate.getSiteid());
					}
					else
					{
						cateListIter.remove();
					}
				}
				siteList = service.queryListSite(getPageRequest());
				Iterator<DsCmsSite> siteListIter = siteList.iterator();
				while(siteListIter.hasNext())
				{
					if(map.get(siteListIter.next().getId()) == null)
					{
						siteListIter.remove();
					}
				}
				if(siteid == -1 && cateList.size() > 0)
				{
					siteid = cateList.get(0).getSiteid();
					cateListIter = cateList.iterator();
					while(cateListIter.hasNext())
					{
						if(cateListIter.next().getSiteid() != siteid)
						{
							cateListIter.remove();
						}
					}
				}
			}
			else
			{
				siteList = new ArrayList<DsCmsSite>();
				cateList = new ArrayList<DsCmsCategory>();
			}
			service.saveAuditCategoryList(cateList);
			put("siteid", siteid);
			put("siteList", siteList);
			put("cateList", cateList);
			return "/cms/edit/getCategoryTree.jsp";
		}
		catch(Exception e)
		{
			return null;
		}
	}










	// 添加页面
	@RequestMapping("/addPage1")
	public String addPage1()
	{
		put("releasetime", TimeUtil.getCurrentTime());
		return "/cms/edit/addPage.jsp";
	}

	@RequestMapping("/addPage2")
	public void addPage2(DsCmsAuditPage po)
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory m = service.getCategory(categoryid);
			DsCmsSite s = service.getSite(m.getSiteid());
			if(m.getStatus() == 0 && checkOwn(s.getOwn()))
			{
				po.setSiteid(m.getSiteid());
				po.setCategoryid(m.getId());
				po.setUseraccount(getAccount());
				if(po.getReleasetime().trim().equals(""))
				{
					po.setReleasetime(TimeUtil.getCurrentTime());
				}
				po.setStatus(0); //草稿
				po.setUrl("/a/" + m.getFolder() + "/" + m.getId() + ".html");
				service.save(po);// url拼接/id.html
				print(1);
			}
			else
			{
				print("0:站点不存在");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 获得页面分页
	@RequestMapping("/getPage")
	public String getPage()
	{
		Long categoryid = req.getLong("id");
		DsCmsCategory m = service.getCategory(categoryid);
		if(m.getStatus() == 0 && checkOwn(m.getSiteid()))// 列表
		{
			PageRequest pr = getPageRequest();
			pr.getFilters().remove("id");
			pr.getFilters().put("siteid", m.getSiteid());
			pr.getFilters().put("categoryid", m.getId());
			DsCmsPermission permission = service.getPermissionByOwnAccount(getOwn(), getAccount());
			if(permission.checkEditown(categoryid))
			{
				pr.getFilters().put("useraccount", getAccount());
			}
			Page<DsCmsAuditPage> pageModel = service.queryPage(pr);
			put("pageModel", pageModel);
			put("pageNav", new PageNav<DsCmsAuditPage>(request, pageModel));
			put("po", m);
			return "/cms/edit/getPage.jsp";
		}
		return null;
	}

	// 删除页面
	@RequestMapping("/delPage")
	public void delPage()
	{
		try
		{
			Long categoryid = req.getLong("id");
			DsCmsCategory po = service.getCategory(categoryid);
			if(po.getStatus() == 0 && checkOwn(po.getSiteid()))
			{
				service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
				print(1);
			}
			else
			{
				print("0:站点不存在");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 采编页面
	@RequestMapping("/updPage1")
	public String updPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		return "/cms/edit/updPage.jsp";
	}

	@RequestMapping("/updPage2")
	public void updPage2(DsCmsAuditPage po)
	{
		try
		{
			DsCmsAuditPage _po = service.get(po.getId());
			po.setUseraccount(getAccount());
			if(_po.getStatus() != 1)
			{
				po.setSiteid(_po.getSiteid());
				po.setUrl(_po.getUrl());
				po.setCategoryid(_po.getCategoryid());
				service.updateAuditPage(po);
			}
			else if(_po.getStatus() == 1)
			{
				_po.setStatus(po.getStatus());
				service.updateAuditPage(_po);
			}
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 采编栏目
	@RequestMapping("/updCategory1")
	public String updCategory1()
	{
		Long id = req.getLong("id");
		DsCmsAuditCategory po = service.getAuditCategory(id);
		if(po.getReleasetime().isEmpty())
		{
			po.setReleasetime(TimeUtil.getCurrentTime());
		}
		put("po", po);
		return "/cms/edit/updCategory.jsp";
	}

	// 采编栏目URL
	@RequestMapping("/updCategory3")
	public String updCategory3()
	{
		Long id = req.getLong("id");
		DsCmsAuditCategory po = service.getAuditCategory(id);
		if(po.getReleasetime().isEmpty())
		{
			po.setReleasetime(TimeUtil.getCurrentTime());
		}
		put("po", po);
		return "/cms/edit/updCategoryUrl.jsp";
	}

	@RequestMapping("/updCategory2")
	public void updCategory2(DsCmsAuditCategory po)
	{
		try
		{
			DsCmsCategory m = service.getCategory(po.getId());
			if(m.getStatus() != 0 && checkOwn(m.getSiteid()))
			{
				DsCmsAuditCategory _po = service.getAuditCategory(po.getId());
				if(_po.getStatus() != 1)
				{
					service.updateAuditCategory(po);
				}
				else if(_po.getStatus() == 1)
				{
					_po.setStatus(po.getStatus());
					service.updateAuditCategory(_po);
				}
				print(1);
			}
			else
			{
				print("0:站点不存在");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}






	private boolean checkOwn(Long siteid)
	{
		try
		{
			return service.getSite(siteid).getOwn().equals(getOwn());
		}
		catch(Exception e)
		{
			return false;
		}
	}

	private boolean checkOwn(String own)
	{
		try
		{
			return own.equals(getOwn());
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	private String getOwn()
	{
		return common.auth.AuthUtil.getLoginUser(request).getOwn();
	}
	
	private String getAccount()
	{
		return common.auth.AuthUtil.getLoginUser(request).getAccount();
	}
}
