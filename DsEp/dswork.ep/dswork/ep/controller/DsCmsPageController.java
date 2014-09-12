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
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.CollectionUtil;
import dswork.core.util.FileUtil;
import dswork.core.util.TimeUtil;
import dswork.ep.model.DsCmsCategory;
import dswork.ep.model.DsCmsPage;
import dswork.ep.model.DsCmsSite;
import dswork.ep.service.DsCmsPageService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Scope("prototype")
@Controller
@RequestMapping("/cms/page")
public class DsCmsPageController extends BaseController
{
	@Autowired
	private DsCmsPageService service;

	private String getCmsRoot()
	{
		return request.getSession().getServletContext().getRealPath("/html/") + "/";
	}

	// 添加
	@RequestMapping("/addPage1")
	public String addPage1()
	{
		return "/cms/page/addPage.jsp";
	}

	@RequestMapping("/addPage2")
	public void addPage2(DsCmsPage po)
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory m = service.getCategory(categoryid);
			if(m.getStatus() == 0 && checkSite(m.getSiteid()))
			{
				po.setSiteid(m.getSiteid());
				po.setCategoryid(m.getId());
				po.setReleasetime(TimeUtil.getCurrentTime());
				po.setFolder(TimeUtil.getCurrentTime("yyyyMM"));
				service.save(po);
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

	// 删除
	@RequestMapping("/delPage")
	public void delPage()
	{
		try
		{
			Long categoryid = req.getLong("id");
			DsCmsCategory po = service.getCategory(categoryid);
			if(po.getStatus() == 0 && checkSite(po.getSiteid()))
			{
				service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
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

	// 修改
	@RequestMapping("/updPage1")
	public String updPage1()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("page", req.getInt("page", 1));
		return "/cms/page/updPage.jsp";
	}

	@RequestMapping("/updPage2")
	public void updPage2(DsCmsPage po)
	{
		try
		{
			service.update(po);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	// 修改
	@RequestMapping("/updCategory1")
	public String updCategory1()
	{
		Long id = req.getLong("id");
		DsCmsCategory po = service.getCategory(id);
		if(po.getStatus() == 1)// 单页栏目
		{
			put("po", po);
			return "/cms/page/updCategory.jsp";
		}
		else
		{
			return null;
		}
	}

	@RequestMapping("/updCategory2")
	public void updCategory2()
	{
		try
		{
			Long id = req.getLong("id");
			String keywords = req.getString("keywords");
			String content = req.getString("content");
			DsCmsCategory m = service.getCategory(id);
			if(m.getStatus() == 1 && checkSite(m.getSiteid()))
			{
				service.updateCategory(m.getId(), keywords, content);
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

	// 获得栏目列表
	@RequestMapping("/getCategoryTree")
	public String getCategoryTree()
	{
		Long id = req.getLong("siteid"), siteid = 0L;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qybm", common.auth.AuthLogin.getLoginUser(request, response).getQybm());
		PageRequest rq = new PageRequest(map);
		List<DsCmsSite> siteList = service.queryListSite(rq);
		if(siteList != null && siteList.size() > 0)
		{
			put("siteList", siteList);
			if(id > 0)
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
			if(siteid == 0)
			{
				siteid = siteList.get(0).getId();
			}
		}
		put("siteid", siteid);
		put("list", queryCategory(siteid));
		return "/cms/page/getCategoryTree.jsp";
	}

	// 获得分页
	@RequestMapping("/getPage")
	public String getPage()
	{
		Long categoryid = req.getLong("id");
		DsCmsCategory m = service.getCategory(categoryid);
		if(m.getStatus() == 0 && checkSite(m.getSiteid()))// 列表
		{
			PageRequest rq = getPageRequest();
			rq.getFilters().put("siteid", m.getSiteid());
			rq.getFilters().put("categoryid", m.getId());
			rq.getFilters().put("keyvalue", req.getString("keyvalue"));
			Page<DsCmsPage> pageModel = service.queryPage(rq);
			put("pageModel", pageModel);
			put("pageNav", new PageNav<DsCmsPage>(request, pageModel));
			put("po", m);
			return "/cms/page/getPage.jsp";
		}
		return null;
	}

	/**
	 * 取出可处理数据的栏目
	 * @return List
	 */
	private List<DsCmsCategory> queryCategory(Long siteid)
	{
		List<DsCmsCategory> clist = service.queryListCategory(siteid);
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		for(DsCmsCategory m : clist)
		{
			map.put(m.getId(), m);
		}
		List<DsCmsCategory> tlist = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory m : clist)
		{
			if(m.getPid() > 0)
			{
				try
				{
					if(m.getStatus() == 0 || m.getStatus() == 1)// 过滤外链栏目
					{
						map.get(m.getPid()).add(m);// 放入其余节点对应的父节点
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();// 找不到对应的父栏目
				}
			}
			else if(m.getPid() == 0)
			{
				if(m.getStatus() == 0 || m.getStatus() == 1)// 过滤外链栏目
				{
					tlist.add(m);// 只把根节点放入list
				}
			}
		}
		List<DsCmsCategory> list = new ArrayList<DsCmsCategory>();// 按顺序放入
		for(int i = 0; i < tlist.size(); i++)
		{
			DsCmsCategory m = tlist.get(i);
			m.setLevel(0);
			m.setLabel("");
			list.add(m);
			categorySettingList(m, list);
		}
		return list;
	}

	private void categorySettingList(DsCmsCategory m, List<DsCmsCategory> list)
	{
		int size = m.getList().size();
		for(int i = 0; i < size; i++)
		{
			DsCmsCategory n = m.getList().get(i);
			n.setLevel(m.getLevel() + 1);
			n.setLabel(m.getLabel());
			if(m.getLabel().endsWith("├"))
			{
				n.setLabel(m.getLabel().substring(0, m.getLabel().length() - 2) + "│");
			}
			else if(m.getLabel().endsWith("└"))
			{
				n.setLabel(m.getLabel().substring(0, m.getLabel().length() - 2) + "　");
			}
			n.setLabel(n.getLabel() + "&nbsp;&nbsp;");
			n.setLabel(n.getLabel() + (i == size - 1 ? "└" : "├"));
			list.add(n);
			categorySettingList(n, list);
		}
	}

	private boolean checkSite(Long siteid)
	{
		try
		{
			return service.getSite(siteid).getQybm().equals(common.auth.AuthLogin.getLoginUser(request, response).getQybm());
		}
		catch(Exception ex)
		{
		}
		return false;
	}

	private boolean checkSite(String qybm)
	{
		try
		{
			return qybm.equals(common.auth.AuthLogin.getLoginUser(request, response).getQybm());
		}
		catch(Exception ex)
		{
		}
		return false;
	}

	@RequestMapping("/uploadImage")
	public void uploadImage()
	{
		try
		{
			Long categoryid = req.getLong("categoryid");
			DsCmsCategory m = service.getCategory(categoryid);
			DsCmsSite site = service.getSite(m.getSiteid());
			if(checkSite(site.getQybm()))
			{
				String ext = "";
				boolean isHTML5 = "application/octet-stream".equals(request.getContentType());
				byte[] byteArray = null;
				if(isHTML5)
				{
					String header = request.getHeader("Content-Disposition");
					int iStart = header.indexOf("filename=\"") + 10;
					int iEnd = header.indexOf("\"", iStart);
					String fileName = header.substring(iStart, iEnd);
					int len = fileName.lastIndexOf(".");
					ext = (len != -1) ? fileName.substring(len + 1) : "";
					int i = request.getContentLength();
					byteArray = new byte[i];
					int j = 0;
					while(j < i)// 获取表单的上传文件
					{
						int k = request.getInputStream().read(byteArray, j, i - j);
						j += k;
					}
				}
				else
				{
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					MultipartFile file = multipartRequest.getFile("filedata");
					String fileName = file.getOriginalFilename();
					int len = fileName.lastIndexOf(".");
					ext = (len != -1) ? fileName.substring(len + 1) : "";
					byteArray = file.getBytes();
				}
				if(!ext.equals("") && "jpg,jpeg,gif,png".indexOf(ext) != -1)
				{
					String root = getCmsRoot();
					String path = site.getFolder() + "/themes/" + TimeUtil.getCurrentTime("yyyyMM") + "/";
					FileUtil.createFolder(root + path);
					String webpath = request.getContextPath() + "/html/" + path;
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
}
