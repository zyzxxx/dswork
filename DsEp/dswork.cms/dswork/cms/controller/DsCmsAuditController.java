package dswork.cms.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsPageEdit;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsAuditService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;

@Scope("prototype")
@Controller
@RequestMapping("/cms/audit")
public class DsCmsAuditController extends DsCmsBaseController
{
	@Autowired
	private DsCmsAuditService service;

	@RequestMapping("getCategoryTree")
	public String getCategoryTree()
	{
		try
		{
			Long id = req.getLong("siteid", -1), siteid = -1L;
			List<DsCmsSite> siteList = service.queryListSite(getOwn());
			if(siteList != null && siteList.size() > 0)
			{
				put("siteList", siteList);
				if(id >= 0)
				{
					for(DsCmsSite m : siteList)
					{
						if(m.getId() == id)
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
				List<DsCmsCategory> categoryList = service.queryListCategory(siteid);
				categoryList = categoryAccess(categoryList, this);
				put("siteList", siteList);
				put("categoryList", categoryList);
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
		try
		{
			long id = req.getLong("id");
			DsCmsCategoryEdit po = service.getCategoryEdit(id);
			if(po == null)
			{
				po = service.saveCategoryEdit(id);
			}
			if(checkAudit(po.getSiteid(), po.getId()))
			{
				DsCmsCategory m = service.getCategory(po.getId());
				put("scope", m.getScope());
				put("po", po);
				return "/cms/audit/auditCategory.jsp";
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	@RequestMapping("/auditCategory2")
	public void updCategory2(DsCmsCategoryEdit po)
	{
		try
		{
			DsCmsCategory m = service.getCategory(po.getId());
			DsCmsSite s = service.getSite(m.getSiteid());
			if(checkAudit(s.getId(), m.getId()))
			{
				DsCmsCategoryEdit _po = service.getCategoryEdit(po.getId());
				if(_po.isAudit())
				{
					String action = req.getString("action");
					if("pass".equals(action))
					{
						_po.setAuditstatus(4);
					}
					else if("nopass".equals(action))
					{
						_po.setAuditstatus(2);
					}
					else
					{
						print("0:参数错误");
						return;
					}
					_po.setMsg(po.getMsg());
					_po.setAuditid(getAccount());
					_po.setAuditname(getName());
					_po.setAudittime(TimeUtil.getCurrentTime());
					service.updateCategoryEdit(_po, m, s.isWriteLog());
				}
				print(1);
				return;
			}
			print("0:站点不存在");
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
		try
		{
			Long categoryid = req.getLong("id");
			DsCmsCategory m = service.getCategory(categoryid);
			DsCmsSite s = service.getSite(m.getSiteid());
			if(checkAudit(s.getId(), m.getId()))
			{
				if(m.getScope() == 0)// 列表
				{
					PageRequest pr = getPageRequest();
					pr.getFilters().remove("id");
					pr.getFilters().put("siteid", m.getSiteid());
					pr.getFilters().put("categoryid", m.getId());
					pr.getFilters().put("auditstatus", DsCmsPageEdit.AUDIT);
					Page<DsCmsPageEdit> pageModel = service.queryPagePageEdit(pr);
					put("pageModel", pageModel);
					put("pageNav", new PageNav<DsCmsPageEdit>(request, pageModel));
					put("po", m);
					DsCmsCategoryEdit c = service.getCategoryEdit(categoryid);
					put("audit", c == null ? false : c.isAudit());
					return "/cms/audit/getPage.jsp";
				}
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	// 审核
	@RequestMapping("/auditPage1")
	public String auditPage1()
	{
		try
		{
			Long id = req.getLong("keyIndex");
			DsCmsPageEdit po = service.getPageEdit(id);
			DsCmsSite s = service.getSite(po.getSiteid());
			if(checkAudit(s.getId(), po.getCategoryid()))
			{
				put("po", po);
				put("page", req.getInt("page", 1));
				return "/cms/audit/auditPage.jsp";
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	@RequestMapping("/auditPage2")
	public void auditPage2(DsCmsPageEdit po)
	{
		try
		{
			DsCmsPageEdit _po = service.getPageEdit(po.getId());
			DsCmsSite s = service.getSite(_po.getSiteid());
			if(checkAudit(s.getId(), _po.getCategoryid()))
			{
				if(_po.isAudit())
				{
					String action = req.getString("action");
					if("pass".equals(action))
					{
						_po.setAuditstatus(4);
					}
					else if("nopass".equals(action))
					{
						_po.setAuditstatus(2);
					}
					else
					{
						print("0:参数错误");
						return;
					}
					_po.setMsg(po.getMsg());
					_po.setAuditid(getAccount());
					_po.setAuditname(getName());
					_po.setAudittime(TimeUtil.getCurrentTime());
					if(_po.getStatus() == -1)
					{
						service.deletePageEdit(_po, s.isWriteLog());
					}
					else
					{
						service.updatePageEdit(_po, s.isWriteLog());
					}
				}
				print(1);
				return;
			}
			print("0:站点不存在");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@Override
	public boolean checkCategory(DsCmsCategory category)
	{
		return checkAudit(category.getSiteid(), category.getId());
	}
}
