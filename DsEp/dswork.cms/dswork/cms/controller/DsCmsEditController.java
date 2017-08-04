package dswork.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsAuditPage;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsEditService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
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
			Long id = req.getLong("siteid", -1), siteid = -1L;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("own", getOwn());
			map.put("account", getAccount());
			List<DsCmsSite> siteList = service.queryListSite(map);
			if(siteList != null && siteList.size() > 0)
			{
				put("siteList", siteList);
				if(id >= 0)
				{
					for(DsCmsSite m : siteList)
					{
						if(m.getId().longValue() == id)
						{
							siteid = m.getId();
							break;
						}
					}
				}
				if(siteid == -1)
				{
					siteid = siteList.get(0).getId();
				}
			}
			if(siteid >= 0)
			{
				DsCmsPermission permission = service.getPermission(siteid, getAccount());
				List<DsCmsCategory> cateList = new ArrayList<DsCmsCategory>();
				if(permission != null)
				{
					List<DsCmsCategory> _cateList = service.queryListCategory(siteid);
					cateList = DsCmsUtil.categoryAccess(_cateList, permission.getEditall() + permission.getEditown());
					put("edit", permission.getEditall().length() > 2 || permission.getEditown().length() > 2);
					put("audit", permission.getAudit().length() > 2);
					put("publish", permission.getPublish().length() > 2);
				}
				service.saveAuditCategoryList(cateList);
				put("siteList", siteList);
				put("cateList", cateList);
			}
			put("siteid", siteid);
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
			if(m.getScope() == 0 && checkOwn(s.getOwn()))
			{
				po.setSiteid(m.getSiteid());
				po.setCategoryid(m.getId());
				po.setEditid(getAccount());
				po.setEditname(getName());
				po.setEdittime(TimeUtil.getCurrentTime());
				if(po.getReleasetime().trim().equals(""))
				{
					po.setReleasetime(TimeUtil.getCurrentTime());
				}
				po.setStatus(0); //新增
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
		DsCmsPermission permission = service.getPermission(m.getSiteid(), getAccount());
		if(permission.checkEditall(m.getId()) || permission.checkEditown(m.getId()))
		{
			if(m.getScope() == 0 && checkOwn(m.getSiteid()))// 列表
			{
				PageRequest pr = getPageRequest();
				pr.getFilters().remove("id");
				pr.getFilters().put("siteid", m.getSiteid());
				pr.getFilters().put("categoryid", m.getId());
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
			if(po.getScope() == 0 && checkOwn(po.getSiteid()))
			{
				long[] idArray = req.getLongArray("keyIndex", 0);
				List<DsCmsAuditPage> updList = new ArrayList<DsCmsAuditPage>();
				List<DsCmsAuditPage> delList = new ArrayList<DsCmsAuditPage>();
				for(long id : idArray)
				{
					DsCmsAuditPage page = service.get(id);
					if(page != null)
					{
						if(page.getStatus() == 0)// 新增的数据，直接删除
						{
							delList.add(page);
						}
						else// 非新增的数据，需审核后才能删除
						{
							page.setStatus(-1);
							page.setAuditstatus(DsCmsAuditPage.AUDIT);
							updList.add(page);
						}
					}
				}
				service.updateAndDeleteList(updList, delList);
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
			if(_po.isDraft() || _po.isNopass() || _po.isPass())
			{
				_po.setTitle(po.getTitle());
				_po.setMetakeywords(po.getMetakeywords());
				_po.setMetadescription(po.getMetadescription());
				_po.setSummary(po.getSummary());
				_po.setReleasetime(po.getReleasetime());
				_po.setReleasesource(po.getReleasesource());
				_po.setReleaseuser(po.getReleaseuser());
				_po.setImg(po.getImg());
				_po.setImgtop(po.getImgtop());
				_po.setPagetop(po.getPagetop());
				_po.setContent(po.getContent());
				_po.setAuditstatus(po.getAuditstatus());

				_po.setEditid(getAccount());
				_po.setEditname(getName());
				_po.setEdittime(TimeUtil.getCurrentTime());
				service.update(_po);
			}
			else if(_po.isAudit())// 撤回操作
			{
				_po.setAuditstatus(po.getAuditstatus());
				if(_po.getStatus() == -1)// 撤回删除
				{
					_po.setStatus(1);
				}
				service.update(_po);
			}
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}
	//还原内容
	@RequestMapping("/updPageRestore")
	public void updPageRestore(DsCmsAuditPage po)
	{
		try
		{
			DsCmsAuditPage _po = service.get(po.getId());
			if(_po.getStatus() > 0 && (_po.isDraft() || _po.isNopass()))
			{
				DsCmsPage page = service.getPage(po.getId());
				_po.setTitle(page.getTitle());
				_po.setMetakeywords(page.getMetakeywords());
				_po.setMetadescription(page.getMetadescription());
				_po.setSummary(page.getSummary());
				_po.setContent(page.getContent());
				_po.setReleasetime(page.getReleasetime());
				_po.setReleasesource(page.getReleasesource());
				_po.setReleaseuser(page.getReleaseuser());
				_po.setImg(page.getImg());
				_po.setImgtop(page.getImgtop());
				_po.setPagetop(page.getPagetop());
//				_po.setStatus(page.getStatus());
				_po.setAuditstatus(DsCmsAuditPage.PASS);
				service.update(_po);
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
		DsCmsPermission permission = service.getPermission(po.getSiteid(), getAccount());
		if(permission.checkEditall(po.getId()) || permission.checkEditown(po.getId()))
		{
			if(po.getReleasetime().isEmpty())
			{
				po.setReleasetime(TimeUtil.getCurrentTime());
			}
			put("po", po);
			return "/cms/edit/updCategory.jsp";
		}
		return null;
	}

	// 采编栏目URL
	@RequestMapping("/updCategory3")
	public String updCategory3()
	{
		Long id = req.getLong("id");
		DsCmsAuditCategory po = service.getAuditCategory(id);
		DsCmsPermission permission = service.getPermission(po.getSiteid(), getAccount());
		if(permission.checkEditall(po.getId()) || permission.checkEditown(po.getId()))
		{
			if(po.getReleasetime().isEmpty())
			{
				po.setReleasetime(TimeUtil.getCurrentTime());
			}
			put("po", po);
			return "/cms/edit/updCategoryUrl.jsp";
		}
		return null;
	}

	@RequestMapping("/updCategory2")
	public void updCategory2(DsCmsAuditCategory po)
	{
		try
		{
			DsCmsCategory m = service.getCategory(po.getId());
			if(m.getScope() != 0 && checkOwn(m.getSiteid()))
			{
				DsCmsAuditCategory _po = service.getAuditCategory(po.getId());
				if(_po.isDraft() || _po.isNopass() || _po.isPass())
				{
					_po.setMetakeywords(po.getMetakeywords());
					_po.setMetadescription(po.getMetadescription());
					_po.setSummary(po.getSummary());
					_po.setReleasetime(po.getReleasetime());
					_po.setReleasesource(po.getReleasesource());
					_po.setReleaseuser(po.getReleaseuser());
					_po.setImg(po.getImg());
					_po.setContent(po.getContent());
					_po.setUrl(po.getUrl());
					_po.setAuditstatus(po.getAuditstatus());

					_po.setEditid(getAccount());
					_po.setEditname(getName());
					_po.setEdittime(TimeUtil.getCurrentTime());
					service.updateAuditCategory(_po);
				}
				else if(_po.isAudit())
				{
					_po.setAuditstatus(po.getAuditstatus());
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
	//还原栏目
	@RequestMapping("/updCategoryRestore")
	public void updCategoryRestore(DsCmsAuditCategory po)
	{
		try
		{
			DsCmsAuditCategory _po = service.getAuditCategory(po.getId());
			if(_po.getStatus() > 0 && (_po.isDraft() || _po.isNopass()))
			{
				DsCmsCategory c = service.getCategory(po.getId());
				_po.setMetakeywords(c.getMetakeywords());
				_po.setMetadescription(c.getMetadescription());
				_po.setSummary(c.getSummary());
				_po.setContent(c.getContent());
				_po.setReleasetime(c.getReleasetime());
				_po.setReleasesource(c.getReleasesource());
				_po.setReleaseuser(c.getReleaseuser());
				_po.setImg(c.getImg());
				_po.setUrl(c.getUrl());
//				_po.setStatus(c.getStatus());
				_po.setAuditstatus(DsCmsAuditCategory.PASS);
				service.updateAuditCategory(_po);
			}
			print(1);
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
	
	private String getName()
	{
		return common.auth.AuthUtil.getLoginUser(request).getName();
	}
}
