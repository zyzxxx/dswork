package dswork.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsCount;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.cms.service.DsCmsPublishService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.FileUtil;
import dswork.core.util.TimeUtil;
import dswork.http.HttpUtil;

@Scope("prototype")
@Controller
@RequestMapping("/cms/publish")
public class DsCmsPublishController extends DsCmsBaseController
{
	@Autowired
	private DsCmsPublishService service;

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
			return "/cms/publish/getCategoryTree.jsp";
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@RequestMapping("getCategoryPublish")
	public String getCategoryPublish()
	{
		try
		{
			long siteid = req.getLong("siteid", -1);
			if(siteid == -1)
			{
				return null;
			}
			List<DsCmsCategory> list = service.queryListCategory(siteid);
			Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
			list = categoryAccess(list, this);
			List<Long> idList = new ArrayList<Long>();
			for(int i = 0; i < list.size(); i++)
			{
				DsCmsCategory c = list.get(i);
				if(c.getScope() == 1 || c.getScope() == 2)
				{
					if(c.getScope() == 1
						&&(c.getStatus() == -1
						|| c.getStatus() == 0
						|| c.getStatus() == 1)
					)
					{
						c.setCount(1);
					}
					continue;
				}
				idList.add(c.getId());
				map.put(c.getId(), c);
			}
			List<Long> xList = new ArrayList<Long>();
			List<DsCmsCount> clist = service.queryCountForPublish(siteid, idList, xList);
			for(DsCmsCount c : clist)
			{
				DsCmsCategory x = map.get(c.getId());
				x.setCount(x.getCount() + c.getCount());
			}
			put("list", list);
			return "/cms/publish/getCategoryPublish.jsp";
		}
		catch(Exception e)
		{
		}
		return null;
	}

	// 获得page分页
	@RequestMapping("/getPage")
	public String getPage()
	{
		Long categoryid = req.getLong("id");
		DsCmsCategory m = service.getCategory(categoryid);
		if(m.getScope() == 0)
		{
			DsCmsSite s = service.getSite(m.getSiteid());
			if(checkPublish(s.getId(), m.getId()))
			{
				PageRequest pr = getPageRequest();
				pr.getFilters().remove("id");
				pr.getFilters().put("siteid", m.getSiteid());
				pr.getFilters().put("categoryid", m.getId());
				Page<DsCmsPage> pageModel = service.queryPage(pr);
				put("pageModel", pageModel);
				put("pageNav", new PageNav<DsCmsPage>(request, pageModel));
				put("po", m);
				return "/cms/publish/getPage.jsp";
			}
		}
		return null;
	}

	// 获取page明细
	@RequestMapping("/getPageById")
	public String getPageById()
	{
		try
		{
			Long id = req.getLong("keyIndex");
			DsCmsPage po = service.get(id);
			if(checkPublish(po.getSiteid(), po.getCategoryid()))
			{
				put("po", po);
				return "/cms/publish/getPageById.jsp";
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	// 获取category明细
	@RequestMapping("/getCategoryById")
	public String getCategoryById()
	{
		try
		{
			Long id = req.getLong("id");
			DsCmsCategory po = service.getCategory(id);
			if(checkPublish(po.getSiteid(), po.getId()))
			{
				DsCmsCategory m = service.getCategory(po.getId());
				put("scope", m.getScope());
				put("po", po);
				return "/cms/publish/getCategoryById.jsp";
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}

	// 发布指定的信息
	@RequestMapping("/build")
	public void build()
	{
		Long siteid = req.getLong("siteid", -1);
		Long categoryid = req.getLong("categoryid", -1);
		Long pageid = req.getLong("pageid", -1);
		int pagesize = req.getInt("pagesize", 25);
		_building(true, siteid, categoryid, pageid, pagesize);
	}

	// 删除指定的信息
	@RequestMapping("/unbuild")
	public void unbuild()
	{
		Long siteid = req.getLong("siteid", -1);
		Long categoryid = req.getLong("categoryid", -1);
		Long pageid = req.getLong("pageid", -1);
		int pagesize = req.getInt("pagesize", 25);
		_building(false, siteid, categoryid, pageid, pagesize);
	}
	private HttpUtil httpUtil = new HttpUtil();

	/**
	 * 生成或删除信息
	 * @param isCreateOrDelete true生成，false删除
	 * @param siteid
	 * @param categoryid
	 * @param pageid
	 * @param pagesize
	 */
	private void _building(boolean isCreateOrDelete, long siteid, long categoryid, long pageid, int pagesize)
	{
		pagesize = (pagesize <= 0) ? 25 : pagesize;
		try
		{
			if(siteid >= 0)
			{
				DsCmsSite site = service.getSite(siteid);
				if(site != null)
				{
					site.setFolder(String.valueOf(site.getFolder()).replace("\\", "").replace("/", ""));
				}
				if(site != null && site.getFolder().trim().length() > 0 && checkOwn(site.getId()))
				{
					String path = "http://" + getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/cmsbuild/buildHTML.chtml?siteid=" + siteid;
					// 首页：categoryid==-1，pageid==-1
					// 全部栏目：categoryid==0，pageid==-1
					// 指定栏目：categoryid>0，pageid==-1
					// 全部内容：categoryid==0，pageid==0
					// 栏目内容：categoryid>0，pageid==0
					// 指定内容：pageid>0
					if(pageid > 0)// 指定内容
					{
						DsCmsPage p = service.get(pageid);
						if(p.getSiteid() == siteid && checkPublish(p.getSiteid(), p.getCategoryid()))
						{
							try
							{
								if(p.getStatus() == -1)
								{
									_buildFile(null, p.getUrl(), site.getFolder());
									service.delete(p.getId());
								}
								else if(p.getScope() == 2)
								{
									_buildFile(null, "/a/" + p.getCategoryid() + "/" + p.getId() + ".html", site.getFolder());
									service.updatePageStatus(p.getId(), isCreateOrDelete ? 8 : 0);
								}
								else
								{
									_buildFile(isCreateOrDelete ? path + "&pageid=" + p.getId() : null, p.getUrl(), site.getFolder());
									service.updatePageStatus(p.getId(), isCreateOrDelete ? 8 : 0);
								}
								print("1");
							}
							catch(Exception e)
							{
								print("0");
							}
						}
						else
						{
							print("0:无权操作");
						}
					}
					else if(pageid == -1)// 栏目首页
					{
						List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();
						if(categoryid == 0)// 全部栏目首页
						{
							List<DsCmsCategory> clist = service.queryListCategory(siteid);
							for(DsCmsCategory c : clist)
							{
								if(checkPublish(c.getSiteid(), c.getId()))
								{
									list.add(c);
								}
							}
						}
						else if(categoryid > 0)// 指定栏目首页
						{
							DsCmsCategory c = service.getCategory(categoryid);
							if(c.getSiteid() == siteid && checkPublish(c.getSiteid(), c.getId()))
							{
								list.add(c);
							}
						}
						if(categoryid >= 0)// 栏目首页，这里不能用list.size，因为可能长度就是0
						{
							for(DsCmsCategory c : list)
							{
								try
								{
									if(c.getScope() == 2)// 外链没有东西生成的
									{
										_deleteFile(site.getFolder(), c.getId() + "", true, true);
										service.updateCategoryStatus(c.getId(), 8);
										continue;
									}
									_deleteFile(site.getFolder(), c.getId() + "", true, false);// 删除栏目首页
									if(isCreateOrDelete)
									{
										_buildFile(path + "&categoryid=" + c.getId() + "&page=1&pagesize=" + pagesize, c.getUrl(), site.getFolder());
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("siteid", site.getId());
										map.put("categoryid", c.getId());
										map.put("releasetime", TimeUtil.getCurrentTime());
										PageRequest rq = new PageRequest(map);
										rq.setPageSize(pagesize);
										rq.setCurrentPage(1);
										Page<DsCmsPage> pageModel = service.queryPage(rq);
										for(int i = 2; i <= pageModel.getLastPage(); i++)
										{
											_buildFile(path + "&categoryid=" + c.getId() + "&page=" + i + "&pagesize=" + pagesize, c.getUrl().replaceAll("\\.html", "_" + i + ".html"), site.getFolder());
										}
									}
									service.updateCategoryStatus(c.getId(), isCreateOrDelete ? 8 : 0);
								}
								catch(Exception e)
								{
								}
							}
						}
						else// 首页
						{
							_buildFile(isCreateOrDelete ? path : null, "/index.html", site.getFolder());
						}
						print("1");
					}
					else if(pageid == 0)// 栏目内容
					{
						List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();
						if(categoryid == 0)// 全部栏目内容
						{
							List<DsCmsCategory> clist = service.queryListCategory(siteid);
							for(DsCmsCategory c : clist)
							{
								if(checkPublish(c.getSiteid(), c.getId()))
								{
									list.add(c);
								}
							}
						}
						else if(categoryid > 0)// 指定栏目内容
						{
							DsCmsCategory c = service.getCategory(categoryid);
							if(c.getSiteid() == siteid && checkPublish(c.getSiteid(), c.getId()))
							{
								list.add(c);
							}
						}
						for(DsCmsCategory c : list)
						{
							if(c.getScope() == 2)// 外链没有东西生成的
							{
								_deleteFile(site.getFolder(), c.getId() + "", true, true);
								service.updateCategoryStatus(c.getId(), 8);
								continue;
							}
							_deleteFile(site.getFolder(), c.getId() + "", false, true);// 删除栏目内容
							try
							{
								// 先删除栏目下待删除的数据
								service.deletePage(siteid, categoryid);
							}
							catch(Exception e)
							{
							}
							if(isCreateOrDelete)
							{
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("siteid", site.getId());
								map.put("releasetime", TimeUtil.getCurrentTime());
								map.put("categoryid", c.getId());
								PageRequest rq = new PageRequest(1, pagesize, map);
								Page<DsCmsPage> pageModel = service.queryPage(rq);
								for(DsCmsPage p : pageModel.getResult())
								{
									try
									{
										_buildFile(isCreateOrDelete ? path + "&pageid=" + p.getId() : null, p.getUrl(), site.getFolder());
										service.updatePageStatus(p.getId(), isCreateOrDelete ? 8 : 0);
									}
									catch(Exception e)
									{
									}
								}
								for(int i = 2; i <= pageModel.getLastPage(); i++)
								{
									map.clear();
									map.put("siteid", site.getId());
									map.put("releasetime", TimeUtil.getCurrentTime());
									map.put("categoryid", c.getId());
									rq.setFilters(map);
									rq.setPageSize(pagesize);
									rq.setCurrentPage(i);
									List<DsCmsPage> pageList = service.queryList(rq);
									for(DsCmsPage p : pageList)
									{
										try
										{
											_buildFile(isCreateOrDelete ? path + "&pageid=" + p.getId() : null, p.getUrl(), site.getFolder());
											service.updatePageStatus(p.getId(), isCreateOrDelete ? 8 : 0);
										}
										catch(Exception e)
										{
										}
									}
								}
							}
						}
						print("1");
					}
					else
					{
						print("0");
					}
				}
				else
				{
					print("0");
				}
			}
			else
			{
				print("0");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print(isCreateOrDelete ? "0:生成失败" : "0:删除失败");
		}
	}

	private void _deleteFile(String siteFolder, String categoryFolder, boolean deleteCategory, boolean deletePage)
	{
		// 这部分处理不当，整个站点的都会被删除
		if(siteFolder != null && siteFolder.trim().length() > 0 && categoryFolder != null && categoryFolder.trim().length() > 0)
		{
			java.io.File file = new java.io.File(getCmsRoot() + "/html/" + siteFolder + "/html/a/" + categoryFolder);
			if(file.exists())
			{
				for(java.io.File f : file.listFiles())
				{
					if(f.isDirectory())
					{
						FileUtil.delete(f.getPath());// 不应该存在目录
					}
					if(f.isFile())
					{
						if(f.getName().startsWith("index"))
						{
							if(deleteCategory)
							{
								f.delete();// 删除栏目首页
							}
						}
						else
						{
							if(deletePage)
							{
								f.delete();// 删除栏目内容
							}
						}
					}
				}
			}
		}
	}

	private void _buildFile(String path, String urlpath, String siteFolder)
	{
		try
		{
			String p = getCmsRoot().replaceAll("\\\\", "/") + "html/" + siteFolder + "/html/" + urlpath;
			if(path == null)
			{
				FileUtil.delete(p);
			}
			else
			{
				FileUtil.writeFile(p, httpUtil.create(path).connectStream(), true);
			}
		}
		catch(Exception e)
		{
		}
	}

	private String getCmsRoot()
	{
		return request.getSession().getServletContext().getRealPath("/") + "/";
	}

	private String getLocalAddr()
	{
		String addr = request.getLocalAddr();
		if(addr != null && addr.indexOf(":") != -1)
		{
			addr = "[" + addr + "]";
		}
		return addr;
	}

	@Override
	public boolean checkCategory(DsCmsCategory category)
	{
		return checkPublish(category.getSiteid(), category.getId());
	}
}
