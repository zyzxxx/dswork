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
import dswork.core.util.FileUtil;
import dswork.core.util.TimeUtil;
import dswork.web.MyFile;

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
			if(c.getScope() == 0)
			{
				if(checkEdit(s.getId(), c.getId()))
				{
					String action = req.getString("action");
					boolean writePage = false;
					if("save".equals(action))
					{
						po.setAuditstatus(0);
					}
					else if("submit".equals(action))
					{
						if(checkCategory(s.getId(), c.getId()))
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

	// 拷贝
	@RequestMapping("/copyPage1")
	public String copyPage1()
	{
		try
		{
			long categoryid = req.getLong("id");
			DsCmsCategory po = service.getCategory(categoryid);
			if(po.getScope() == 0)
			{
				DsCmsSite s = service.getSite(po.getSiteid());
				if(checkOwn(s.getId()))
				{
					put("list", queryCategory(po.getSiteid(), false, categoryid));
					return "/cms/edit/copyPage.jsp";
				}
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	@RequestMapping("/copyPage2")
	public void copyPage2()
	{
		try
		{
			long categoryid = req.getLong("id");
			DsCmsCategory po = service.getCategory(categoryid);
			if(po.getScope() == 0)
			{
				DsCmsSite s = service.getSite(po.getSiteid());
				if(checkOwn(s.getId()))
				{
					DsCmsPageEdit page = service.getPageEdit(req.getLong("keyIndex"));
					if(page.getCategoryid() != categoryid)
					{
						page.setCategoryid(categoryid);
						page.setStatus(0);// 拷贝新增
						page.setAuditstatus(0);// 草稿状态
						service.savePageEdit(page, false, s.isWriteLog(), getAccount(), getName());
						print("1:拷贝成功");
						return;
					}
				}
			}
		}
		catch(Exception e)
		{
		}
		print("0:拷贝失败");
	}

	private List<DsCmsCategory> queryCategory(long siteid, boolean exclude, long excludeId)
	{
		List<DsCmsCategory> list = service.queryListCategory(siteid);
		return queryCategory(list, exclude, excludeId);
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
				if(checkEditall(s.getId(), c.getId()) || checkEditown(s.getId(), c.getId()))
				{
					PageRequest pr = getPageRequest();
					pr.getFilters().remove("id");
					pr.getFilters().put("siteid", c.getSiteid());
					pr.getFilters().put("categoryid", c.getId());
					if(checkEditown(s.getId(), c.getId()))
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
			if(c.getScope() == 0)
			{
				long[] idArray = req.getLongArray("keyIndex", 0);
				if(checkCategory(s.getId(), c.getId()))
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
				else if(checkEditall(s.getId(), c.getId()) || checkEditown(s.getId(), c.getId()))
				{
					boolean editown = checkEditown(s.getId(), c.getId());
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
			if(
					checkEditall(s.getId(), po.getCategoryid())
				|| (checkEditown(s.getId(), po.getCategoryid()) && checkEditid(po.getEditid()))
			)
			{
				put("po", po);
				return "/cms/edit/updPage.jsp";
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
			DsCmsPageEdit p = service.getPageEdit(po.getId());
			DsCmsSite s = service.getSite(p.getSiteid());
			if(
					checkEditall(s.getId(), p.getCategoryid())
				|| (checkEditown(s.getId(), p.getCategoryid()) && checkEditid(p.getEditid()))
			)
			{
				String action = req.getString("action");
				if("save".equals(action))
				{
					if(p.isEdit() || p.isNopass() || p.isPass())
					{
						p.setAuditstatus(0);
						p.pushEditidAndEditname(getAccount(), getName());
						p.setEdittime(TimeUtil.getCurrentTime());
						service.updatePageEdit(p, false, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				if("submit".equals(action))
				{
					if(checkCategory(p.getSiteid(), p.getCategoryid()))
					{
						p.pushEditidAndEditname(getAccount(), getName());
						p.setEdittime(TimeUtil.getCurrentTime());
						service.updatePageEdit(p, true, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					if(p.isEdit() || p.isNopass() || p.isPass())
					{
						p.setAuditstatus(1);
						p.pushEditidAndEditname(getAccount(), getName());
						p.setEdittime(TimeUtil.getCurrentTime());
						service.updatePageEdit(p, false, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				if("revoke".equals(action))
				{
					if(p.isAudit())
					{
						p.setAuditstatus(0);
						service.updateRevokePageEdit(p, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				if("restore".equals(action))
				{
					if(p.getStatus() > 0 && (p.isEdit() || p.isNopass()))
					{
						DsCmsPage page = service.getPage(po.getId());
						p.setTitle(page.getTitle());
						p.setMetakeywords(page.getMetakeywords());
						p.setMetadescription(page.getMetadescription());
						p.setSummary(page.getSummary());
						p.setContent(page.getContent());
						p.setReleasetime(page.getReleasetime());
						p.setReleasesource(page.getReleasesource());
						p.setReleaseuser(page.getReleaseuser());
						p.setImg(page.getImg());
						p.setImgtop(page.getImgtop());
						p.setPagetop(page.getPagetop());
						p.setScope(page.getScope());
						p.setUrl(page.getUrl());
						// p.setStatus(page.getStatus());
						p.setAuditstatus(DsCmsPageEdit.PASS);
						service.updatePageEdit(p, false, s.isWriteLog(), getAccount(), getName());
					}
					print(1);
					return;
				}
				print("0:参数错误");
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
			if(checkEdit(po.getSiteid(), po.getId()))
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
			if(checkEdit(s.getId(), c.getId()))
			{
				DsCmsCategoryEdit p = service.getCategoryEdit(po.getId());
				String action = req.getString("action");
				if("revoke".equals(action))
				{
					if(p.isAudit())
					{
						p.setAuditstatus(0);
						service.updateRevokeCategoryEdit(p, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				if("restore".equals(action))
				{
					if(p.getStatus() > 0 && (p.isEdit() || p.isNopass()))
					{
						p.setMetakeywords(c.getMetakeywords());
						p.setMetadescription(c.getMetadescription());
						p.setSummary(c.getSummary());
						p.setContent(c.getContent());
						p.setReleasetime(c.getReleasetime());
						p.setReleasesource(c.getReleasesource());
						p.setReleaseuser(c.getReleaseuser());
						p.setImg(c.getImg());
						p.setUrl(c.getUrl());
						p.setAuditstatus(DsCmsCategoryEdit.PASS);
						service.updateCategoryEdit(p, false, s.isWriteLog(), getAccount(), getName());
					}
					print(1);
					return;
				}

				p.setMetakeywords(po.getMetakeywords());
				p.setMetadescription(po.getMetadescription());
				p.setSummary(po.getSummary());
				p.setReleasetime(po.getReleasetime());
				p.setReleasesource(po.getReleasesource());
				p.setReleaseuser(po.getReleaseuser());
				p.setImg(po.getImg());
				p.setContent(po.getContent());
				p.setUrl(po.getUrl());
				p.pushEditidAndEditname(getAccount(), getName());
				p.setEdittime(TimeUtil.getCurrentTime());

				if("save".equals(action))
				{
					if(p.isEdit() || p.isNopass() || p.isPass())
					{
						p.setAuditstatus(0);
						service.updateCategoryEdit(p, false, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				if("submit".equals(action))
				{
					if(checkCategory(p.getSiteid(), p.getId()))
					{
						service.updateCategoryEdit(p, true, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					if(p.isEdit() || p.isNopass() || p.isPass())
					{
						p.setAuditstatus(1);
						service.updateCategoryEdit(p, false, s.isWriteLog(), getAccount(), getName());
						print(1);
						return;
					}
					print("0:状态错误");
					return;
				}
				print("0:参数错误");
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

	@RequestMapping("/uploadImage")
	public void uploadImage()
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory m = service.getCategory(categoryid);
			DsCmsSite site = service.getSite(m.getSiteid());
			if(checkOwn(site.getId()))
			{
				String ext = "";
				byte[] byteArray = null;
				if(req.getFileArray().length > 0)
				{
					MyFile file = req.getFileArray()[0];
					byteArray = file.getFileData();
					ext = file.getFileExt();
				}
				if(!ext.equals("") && "jpg,jpeg,gif,png".indexOf(ext) != -1)
				{
					String zoom = req.getString("zoom", "true");
					String root = getCmsRoot();
					String ym = TimeUtil.getCurrentTime("yyyyMM");
					String path = "/html/" + site.getFolder() + "/html/f/img/" + ym + "/";
					FileUtil.createFolder(root + path);
					String webpath = site.getUrl() + "/f/img/" + ym + "/";
					String v = System.currentTimeMillis() + "." + ext.toLowerCase();
					try
					{
						if("true".equals(zoom) && "jpg,jpeg,png".indexOf(ext) != -1)
						{
							// 压缩图片使尺寸最多不超过1000*1000
							byte[] arr = dswork.core.util.ImageUtil.resize(FileUtil.getToInputStream(byteArray), 1000, 1000);
							if(arr == null)
							{
								arr = byteArray;
							}
							FileUtil.writeFile(root + path + v, FileUtil.getToInputStream(arr), true);
						}
						else// gif
						{
							FileUtil.writeFile(root + path + v, FileUtil.getToInputStream(byteArray), true);
						}
						print("{\"err\":\"\",\"msg\":\"!" + webpath + v + "\"}");
						return;
					}
					catch(Exception e)
					{
						e.printStackTrace();
						print("{\"err\":\"上传失败\",\"msg\":\"\"}");
						return;
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		print("{\"err\":\"上传失败！\",\"msg\":\"\"}");
	}

	@RequestMapping("/uploadFile")
	public void uploadFile()
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory m = service.getCategory(categoryid);
			DsCmsSite site = service.getSite(m.getSiteid());
			if(checkOwn(site.getId()))
			{
				String ext = "";
				byte[] byteArray = null;
				if(req.getFileArray().length > 0)
				{
					MyFile file = req.getFileArray()[0];
					byteArray = file.getFileData();
					ext = file.getFileExt();
				}
				if(!ext.equals("") && "bmp,doc,docx,gif,jpeg,jpg,pdf,png,ppt,pptx,rar,rtf,txt,xls,xlsx,zip,7z".indexOf(ext) != -1)
				{
					String root = getCmsRoot();
					String ym = TimeUtil.getCurrentTime("yyyyMM");
					String path = "/html/" + site.getFolder() + "/html/f/file/" + ym + "/";
					FileUtil.createFolder(root + path);
					String webpath = site.getUrl() + "/f/file/" + ym + "/";
					String v = System.currentTimeMillis() + "." + ext.toLowerCase();
					try
					{
						FileUtil.writeFile(root + path + v, FileUtil.getToInputStream(byteArray), true);
						print("{\"err\":\"\",\"msg\":\"!" + webpath + v + "\"}");
						return;
					}
					catch(Exception e)
					{
						e.printStackTrace();
						print("{\"err\":\"上传失败\",\"msg\":\"\"}");
						return;
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		print("{\"err\":\"上传失败！\",\"msg\":\"\"}");
	}

	private String getCmsRoot()
	{
		return request.getSession().getServletContext().getRealPath("/") + "/";
	}

	private boolean checkEditid(String editid)
	{
		return editid.indexOf("," + getAccount() + ",") != -1;
	}

	@Override
	public boolean checkCategory(DsCmsCategory category)
	{
		return checkEdit(category.getSiteid(), category.getId());
	}
}
