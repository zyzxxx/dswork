package dswork.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsPageEdit;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsEditService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;

@Scope("prototype")
@Controller
@RequestMapping("/cms/edit")
public class DsCmsEditController extends DsCmsBaseController
{
	@Autowired
	private DsCmsEditService service;

	@RequestMapping("getCategoryTree")
	public String getCategoryTree()
	{
		try
		{
			Long id = req.getLong("siteid", -1), siteid = -1L;
			List<DsCmsSite> siteList = service.queryListSite(getOwn(), getAccount());
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
				categoryList = DsCmsUtil.categoryAccess(categoryList, getAccount(), this);
				put("siteList", siteList);
				put("categoryList", categoryList);
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
	public void addPage2(DsCmsPageEdit po)
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory c = service.getCategory(categoryid);
			DsCmsSite s = service.getSite(c.getSiteid());
			if(c.getScope() == 0 && checkOwn(s.getOwn()))
			{
				if(checkEditCategory(s.getId(), c.getId()))
				{
					String action = req.getString("action");
					boolean writePage = false;
					if("save".equals(action))
					{
						po.setAuditstatus(0);
					}
					else if("submit".equals(action))
					{
						if(checkCategroy(s.getId(), c.getId()))
						{
							writePage = true;
						}
						po.setAuditstatus(1);
					}
					else
					{
						print("0:参数错误");
						return;
					}
					po.setSiteid(c.getSiteid());
					po.setCategoryid(c.getId());
					po.pushEditidAndEditname(getAccount(), getName());
					po.setEdittime(TimeUtil.getCurrentTime());
					if(po.getReleasetime().trim().equals(""))
					{
						po.setReleasetime(TimeUtil.getCurrentTime());
					}
					po.setStatus(0); // 新增
					service.savePageEdit(po, writePage, s.isWriteLog(), getAccount(), getName());// url拼接/id.html
					print(1);
					return;
				}
			}
			print("0:站点不存在");
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
		try
		{
			Long categoryid = req.getLong("id");
			DsCmsCategory c = service.getCategory(categoryid);
			if(c.getScope() == 0)
			{
				DsCmsSite s = service.getSite(c.getSiteid());
				if(checkOwn(s.getOwn()))
				{
					if(checkEditAll(s.getId(), c.getId()) || checkEditOwn(s.getId(), c.getId()))
					{
						PageRequest pr = getPageRequest();
						pr.getFilters().remove("id");
						pr.getFilters().put("siteid", c.getSiteid());
						pr.getFilters().put("categoryid", c.getId());
						if(checkEditOwn(s.getId(), c.getId()))
						{
							pr.getFilters().put("editid", "," + getAccount() + ",");
						}
						Page<DsCmsPageEdit> pageModel = service.queryPagePageEdit(pr);
						put("pageModel", pageModel);
						put("pageNav", new PageNav<DsCmsPageEdit>(request, pageModel));
						put("po", c);
						return "/cms/edit/getPage.jsp";
					}
				}
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	// 删除页面
	@RequestMapping("/delPage")
	public void delPage()
	{
		try
		{
			DsCmsCategory c = service.getCategory(req.getLong("id"));
			DsCmsSite s = service.getSite(c.getSiteid());
			if(c.getScope() == 0 && checkOwn(s.getOwn()))
			{
				long[] idArray = req.getLongArray("keyIndex", 0);
				if(checkCategroy(s.getId(), c.getId()))
				{
					for(long id : idArray)
					{
						DsCmsPageEdit p = service.getPageEdit(id);
						if(c.getId() == p.getCategoryid())
						{
							if(p.getStatus() == 0)// 新增的数据，直接删除
							{
								service.deletePageEdit(p.getId(), true);
							}
						}
					}
					print(1);
					return;
				}
				else if(checkEditAll(s.getId(), c.getId()) || checkEditOwn(s.getId(), c.getId()))
				{
					boolean editown = checkEditOwn(s.getId(), c.getId());
					for(long id : idArray)
					{
						DsCmsPageEdit p = service.getPageEdit(id);
						if(c.getId() == p.getCategoryid())
						{
							if(editown && checkEditid(p.getEditid()))
							{
								continue;
							}
							if(p.getStatus() == 0)// 新增的数据，直接删除
							{
								service.deletePageEdit(p.getId(), false);
							}
							else// 非新增的数据，需审核后才能删除
							{
								p.setStatus(-1);
								p.setAuditstatus(DsCmsPageEdit.AUDIT);
								service.updatePageEdit(p, false, s.isWriteLog(), getAccount(), getName());
							}
						}
					}
					print(1);
					return;
				}
			}
			print("0:站点不存在");
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
		try
		{
			Long id = req.getLong("keyIndex");
			DsCmsPageEdit po = service.getPageEdit(id);
			DsCmsSite s = service.getSite(po.getSiteid());
			if(checkOwn(s.getOwn()))
			{
				if(
						checkEditAll(s.getId(), po.getCategoryid())
					|| (checkEditOwn(s.getId(), po.getCategoryid()) && checkEditid(po.getEditid()))
				)
				{
					put("po", po);
					return "/cms/edit/updPage.jsp";
				}
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	@RequestMapping("/updPage2")
	public void updPage2(DsCmsPageEdit po)
	{
		try
		{
			DsCmsPageEdit _po = service.getPageEdit(po.getId());
			DsCmsSite s = service.getSite(_po.getSiteid());
			if(checkOwn(s.getOwn()))
			{
				if(
						checkEditAll(s.getId(), _po.getCategoryid())
					|| (checkEditOwn(s.getId(), _po.getCategoryid()) && checkEditid(_po.getEditid()))
				)
				{
					boolean writePage = false;
					String action = req.getString("action");
					if("save".equals(action))
					{
						if(_po.isEdit() || _po.isNopass() || _po.isPass())
						{
							po.setAuditstatus(0);
						}
						else
						{
							print("0:状态错误");
							return;
						}
					}
					else if("submit".equals(action))
					{
						if(checkCategroy(_po.getSiteid(), _po.getCategoryid()))
						{
							writePage = true;
						}
						else
						{
							if(_po.isEdit() || _po.isNopass() || _po.isPass())
							{
								po.setAuditstatus(1);
							}
							else
							{
								print("0:状态错误");
								return;
							}
						}
					}
					else if("revoke".equals(action))
					{
						if(_po.isAudit())
						{
							_po.setAuditstatus(0);
							service.updateRevokePageEdit(_po, s.isWriteLog(), getAccount(), getName());
							print(1);
							return;
						}
						else
						{
							print("0:状态错误");
							return;
						}
					}
					else if("restore".equals(action))
					{
						if(_po.getStatus() > 0 && (_po.isEdit() || _po.isNopass()))
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
							_po.setScope(page.getScope());
							_po.setUrl(page.getUrl());
							// _po.setStatus(page.getStatus());
							_po.setAuditstatus(DsCmsPageEdit.PASS);
							service.updatePageEdit(_po, false, s.isWriteLog(), getAccount(), getName());
						}
						print(1);
						return;
					}
					else
					{
						print("0:参数错误");
						return;
					}
					po.pushEditidAndEditname(getAccount(), getName());
					po.setEdittime(TimeUtil.getCurrentTime());
					service.updatePageEdit(po, writePage, s.isWriteLog(), getAccount(), getName());
					print(1);
					return;
				}
			}
			print("0:站点不存在");
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
		try
		{
			long id = req.getLong("id");
			DsCmsCategoryEdit po = service.getCategoryEdit(id);
			if(po == null)
			{
				po = service.saveCategoryEdit(id);
			}
			DsCmsSite s = service.getSite(po.getSiteid());
			if(checkOwn(s.getOwn()))
			{
				if(checkEditCategory(po.getSiteid(), po.getId()))
				{
					if(po.getReleasetime().isEmpty())
					{
						po.setReleasetime(TimeUtil.getCurrentTime());
					}
					DsCmsCategory c = service.getCategory(po.getId());
					put("scope", c.getScope());
					put("po", po);
					return "/cms/edit/updCategory.jsp";
				}
			}
			return null;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@RequestMapping("/updCategory2")
	public void updCategory2(DsCmsCategoryEdit po)
	{ 
		try
		{
			DsCmsCategory c = service.getCategory(po.getId());
			DsCmsSite s = service.getSite(c.getSiteid());
			if(checkOwn(s.getOwn()))
			{
				if(checkEditCategory(s.getId(), c.getId()))
				{
					DsCmsCategoryEdit _po = service.getCategoryEdit(po.getId());
					String action = req.getString("action");
					boolean writeCategory = false;
					if("save".equals(action))
					{
						if(_po.isEdit() || _po.isNopass() || _po.isPass())
						{
							_po.setAuditstatus(0);
						}
						else
						{
							print("0:状态错误");
							return;
						}
					}
					else if("submit".equals(action))
					{
						if(checkCategroy(_po.getSiteid(), _po.getId()))
						{
							writeCategory = true;
						}
						else
						{
							if(_po.isEdit() || _po.isNopass() || _po.isPass())
							{
								_po.setAuditstatus(1);
							}
							else
							{
								print("0:状态错误");
								return;
							}
						}
					}
					else if("revoke".equals(action))
					{
						if(_po.isAudit())
						{
							_po.setAuditstatus(0);
							service.updateRevokeCategoryEdit(_po, s.isWriteLog(), getAccount(), getName());
							print(1);
							return;
						}
						else
						{
							print("0:状态错误");
							return;
						}
					}
					else if("restore".equals(action))
					{
						if(_po.getStatus() > 0 && (_po.isEdit() || _po.isNopass()))
						{
							_po.setMetakeywords(c.getMetakeywords());
							_po.setMetadescription(c.getMetadescription());
							_po.setSummary(c.getSummary());
							_po.setContent(c.getContent());
							_po.setReleasetime(c.getReleasetime());
							_po.setReleasesource(c.getReleasesource());
							_po.setReleaseuser(c.getReleaseuser());
							_po.setImg(c.getImg());
							_po.setUrl(c.getUrl());
							// _po.setStatus(c.getStatus());
							_po.setAuditstatus(DsCmsCategoryEdit.PASS);
							service.updateCategoryEdit(_po, false, s.isWriteLog(), getAccount(), getName());
						}
						print(1);
						return;
					}
					else
					{
						print("0:参数错误");
						return;
					}
					//save and submit
					_po.setMetakeywords(po.getMetakeywords());
					_po.setMetadescription(po.getMetadescription());
					_po.setSummary(po.getSummary());
					_po.setReleasetime(po.getReleasetime());
					_po.setReleasesource(po.getReleasesource());
					_po.setReleaseuser(po.getReleaseuser());
					_po.setImg(po.getImg());
					_po.setContent(po.getContent());
					_po.setUrl(po.getUrl());
					_po.pushEditidAndEditname(getAccount(), getName());
					_po.setEdittime(TimeUtil.getCurrentTime());
					service.updateCategoryEdit(_po, writeCategory, s.isWriteLog(), getAccount(), getName());
					print(1);
					return;
				}
			}
			print("0:站点不存在");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	private boolean checkEditOwn(long siteid, long categoryid)
	{
		return DsCmsUtil.checkEditown(siteid, categoryid, getAccount());
	}

	private boolean checkEditAll(long siteid, long categoryid)
	{
		return DsCmsUtil.checkEditall(siteid, categoryid, getAccount());
	}

	private boolean checkEditid(String editid)
	{
		return editid.indexOf("," + getAccount() + ",") != -1;
	}

	private boolean checkEditCategory(long siteid, long categoryid)
	{
		return DsCmsUtil.checkEdit(siteid, categoryid, getAccount());
	}

	private boolean checkCategroy(long siteid, long categoryid)
	{
		return DsCmsUtil.checkCategory(siteid, categoryid);
	}

	@Override
	public boolean checkCategory(DsCmsCategory category, String account)
	{
		return DsCmsUtil.checkEdit(category.getSiteid(), category.getId(), account);
	}
}
