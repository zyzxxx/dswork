package dswork.cms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.cms.model.DsCmsSite;
import dswork.cms.model.DsCmsSpecial;
import dswork.cms.service.DsCmsSpecialService;
import dswork.core.util.CollectionUtil;
import dswork.core.util.UniqueId;

@Scope("prototype")
@Controller
@RequestMapping("/cms/special")
public class DsCmsSpecialController extends DsCmsBaseController
{
	@Autowired
	private DsCmsSpecialService service;

	@RequestMapping("/getSpecial")
	public String getSpecial()
	{
		try
		{
			Long id = req.getLong("siteid");
			DsCmsSite s = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("own", getOwn());
			List<DsCmsSite> siteList = service.querySiteList(map);
			if(siteList != null && siteList.size() > 0)
			{
				put("siteList", siteList);
				for(DsCmsSite m : siteList)
				{
					if(m.getId() == id)
					{
						s = m;
						break;
					}
				}
				if(s == null)
				{
					s = siteList.get(0);
				}
				put("site", s);
				put("siteid", s.getId());
				put("list", service.querySpecialList(s.getId()));
				put("enablemobile", s.getEnablemobile() == 1);
				put("templates", getTemplateName(s.getFolder(), false));
				put("mtemplates", getTemplateName(s.getFolder(), true));
			}
			else
			{
				put("siteid", -1);
			}
			return "/cms/special/getSpecial.jsp";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/updSpecial")
	public void updSpecial()
	{
		try
		{
			long siteid = req.getLong("siteid", -1);
			DsCmsSite s = service.getSite(siteid);
			if(checkOwn(s.getId()))
			{
				long [] ids = req.getLongArray("id", -1);
				String[] titles = req.getStringArray("title");
				String[] viewsites = req.getStringArray("viewsite");
				String[] mviewsites = req.getStringArray("mviewsite");
				String[] urls = req.getStringArray("url");
				List<DsCmsSpecial> updList = new ArrayList<DsCmsSpecial>();
				List<DsCmsSpecial> addList = new ArrayList<DsCmsSpecial>();
				List<Long> delList = Arrays.asList(CollectionUtil.toLongArray(req.getLongArray("deleteId", -1)));
				for(int i = 0; i < ids.length; i++)
				{
					DsCmsSpecial m = new DsCmsSpecial();
					m.setId(ids[i]);
					m.setSiteid(siteid);
					m.setTitle(titles[i]);
					m.setViewsite(viewsites[i]);
					m.setMviewsite(mviewsites[i]);
					m.setUrl("/" + urls[i].replace("/", "").replace("\\", ""));
					updList.add(m);
				}
				for(int i = ids.length; i < titles.length; i++)
				{
					DsCmsSpecial m = new DsCmsSpecial();
					m.setId(UniqueId.genId());
					m.setSiteid(siteid);
					m.setTitle(titles[i]);
					m.setViewsite(viewsites[i]);
					m.setMviewsite(mviewsites[i]);
					m.setUrl("/" + urls[i].replace("/", "").replace("\\", ""));
					addList.add(m);
				}
				service.saveUpdateDelete(addList, updList, delList);
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

	private List<String> getTemplateName(String sitename, boolean mobile)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			File file = new File(getCmsRoot() + sitename + (mobile ? "/templates/m" : "/templates"));
			for(File f : file.listFiles())
			{
				if(f.isFile() && !f.isHidden() && f.getPath().endsWith(".jsp"))
				{
					list.add(f.getName());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	private String getCmsRoot()
	{
		return request.getSession().getServletContext().getRealPath("/html") + "/";
	}
}
