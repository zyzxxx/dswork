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
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsAuditService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/cms/audit")
public class DsCmsAuditController extends BaseController
{
	@Autowired
	private DsCmsAuditService service;

	@RequestMapping("getCategoryTree")
	public String getCategoryAuditTree()
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
					cateList = DsCmsUtil.categoryAccess(_cateList, permission.getAudit());
				}
				service.saveAuditCategoryList(cateList);
				put("siteList", siteList);
				put("cateList", cateList);
			}
			put("siteid", siteid);
			return "/cms/audit/getCategoryTree.jsp";
		}
		catch(Exception e)
		{
			return null;
		}
	}


	// 修改
	@RequestMapping("/auditCategory1")
	public String updCategory1()
	{
		DsCmsAuditCategory po = service.getAuditCategory(req.getLong("id"));
		DsCmsPermission permission = service.getPermission(po.getSiteid(), getAccount());
		if(permission.checkAudit(po.getId()))
		{
			put("po", po);
			return "/cms/audit/auditCategory.jsp";
		}
		return null;
	}

	// 修改
	@RequestMapping("/auditCategory3")
	public String updCategory3()
	{
		DsCmsAuditCategory po = service.getAuditCategory(req.getLong("id"));
		DsCmsPermission permission = service.getPermission(po.getSiteid(), getAccount());
		if(permission.checkAudit(po.getId()))
		{
			put("po", po);
			return "/cms/audit/auditCategoryUrl.jsp";
		}
		return null;
	}

	@RequestMapping("/auditCategory2")
	public void updCategory2(DsCmsAuditCategory po)
	{
		try
		{
			DsCmsCategory m = service.getCategory(po.getId());
			if(m.getScope() != 0 && checkOwn(m.getSiteid()))
			{
				DsCmsAuditCategory _po = service.getAuditCategory(po.getId());
				if(_po.getStatus() == 1)
				{
					_po.setMsg(po.getMsg());
					_po.setStatus(po.getStatus());
					service.updateAuditCategory(_po, m);
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

	// 获得分页
	@RequestMapping("/getPage")
	public String getPage()
	{
		Long categoryid = req.getLong("id");
		DsCmsCategory m = service.getCategory(categoryid);
		DsCmsPermission permission = service.getPermission(m.getSiteid(), getAccount());
		if(permission.checkAudit(m.getId()))
		{
			if(m.getScope() == 0 && checkOwn(m.getSiteid()))// 列表
			{
				PageRequest pr = getPageRequest();
				pr.getFilters().remove("id");
				pr.getFilters().put("siteid", m.getSiteid());
				pr.getFilters().put("categoryid", m.getId());
				pr.getFilters().put("audit", "audit");
				Page<DsCmsAuditPage> pageModel = service.queryPage(pr);
				put("pageModel", pageModel);
				put("pageNav", new PageNav<DsCmsAuditPage>(request, pageModel));
				put("po", m);
				return "/cms/audit/getPage.jsp";
			}
		}
		return null;
	}

	// 审核
	@RequestMapping("/auditPage1")
	public String updPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/audit/auditPage.jsp";
	}

	@RequestMapping("/auditPage2")
	public void updPage2(DsCmsAuditPage po)
	{
		try
		{
			DsCmsAuditPage _po = service.get(po.getId());
			if(_po.getStatus() == 1)
			{
				_po.setStatus(po.getStatus());
				_po.setMsg(po.getMsg());
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
	
	private String getOwn()
	{
		return common.auth.AuthUtil.getLoginUser(request).getOwn();
	}
	
	private String getAccount()
	{
		return common.auth.AuthUtil.getLoginUser(request).getAccount();
	}
}
