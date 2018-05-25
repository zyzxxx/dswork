package common.cms.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.cms.CmsFactory;
import common.cms.CmsFactoryMobile;
import dswork.core.util.TimeUtil;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
public class DsCmsbuildController extends BaseController
{
	private static final String CMS_FACTORY_KEY = "CMS_FACTORY_KEY";
//	private static final String CMS_FACTORY_KEY_SITEID = "CMS_FACTORY_KEY_SITEID";

	@RequestMapping("/cmsbuild/buildHTML")
	public String buildHTML()
	{
		Long siteid = req.getLong("siteid", -1);
		Long categoryid = req.getLong("categoryid", -1);
		Long pageid = req.getLong("pageid", -1);
		boolean mobile = req.getString("mobile", "false").equals("true");
		
		CmsFactory cms = (CmsFactory) request.getSession().getAttribute(CMS_FACTORY_KEY);
		if(cms == null)
		{
			cms = new CmsFactory(siteid);
			request.getSession().setAttribute(CMS_FACTORY_KEY, cms);
//			request.getSession().setAttribute(CMS_FACTORY_KEY_SITEID, siteid + "");
		}
//		else
//		{
//			String siteidstr = String.valueOf(request.getSession().getAttribute(CMS_FACTORY_KEY_SITEID));
//			if(!siteidstr.equals(String.valueOf(cms.getSite().get("id"))))
//			{
//				cms = new CmsFactory(siteid);
//				request.getSession().setAttribute(CMS_FACTORY_KEY, cms);
//				request.getSession().setAttribute(CMS_FACTORY_KEY_SITEID, siteid + "");
//			}
//		}
		if(mobile)
		{
			cms = new CmsFactoryMobile(cms);
		}

		put("cms", cms);
		put("year", TimeUtil.getCurrentTime("yyyy"));
		Map<String, Object> s = cms.getSite();
		put("site", s);
		if(req.getString("view").equals("true"))
		{
			put("ctx", request.getContextPath() + "/html/" + s.get("folder") + (mobile ? "/html/m" : "/html"));// 预览时，现在可以不需要运行服务器，即可浏览相对地址
		}
		else
		{
			put("ctx", getString(s.get("url")) + (mobile ? "/m" : ""));
		}
		if(pageid > 0)// 内容页
		{
			Map<String, Object> p = cms.get(pageid + "");
			Map<String, Object> c = cms.getCategory(p.get("categoryid"));
			put("category", c);
			put("id", getString(p.get("id")));
			put("categoryid", getString(p.get("categoryid")));
			put("title", getString(p.get("title")));
			put("summary", getString(p.get("summary")));
			put("metakeywords", getString(p.get("metakeywords")));
			put("metadescription", getString(p.get("metadescription")));
			put("releasetime", getString(p.get("releasetime")));
			put("releasesource", getString(p.get("releasesource")));
			put("releaseuser", getString(p.get("releaseuser")));
			put("img", getString(p.get("img")));
			put("url", getString(p.get("url")));
			put("content", getString(p.get("content")));
			return "/" + s.get("folder") + (mobile ? "/templates/m/"+c.get("mpageviewsite") : "/templates/"+c.get("pageviewsite"));
		}
		if(categoryid > 0)// 栏目页
		{
			int page = req.getInt("page", 1);
			int pagesize = req.getInt("pagesize", 25);
			Map<String, Object> c = cms.getCategory(categoryid + "");
			if(String.valueOf(c.get("scope")).equals("2"))
			{
				return null;// 外链
			}
			if(String.valueOf(c.get("viewsite")).equals(""))
			{
				return null;// 兼容模板为空
			}
			put("categoryparent", cms.getCategory(c.get("pid")));
			put("categorylist", cms.queryCategory("0"));
			put("categoryid", categoryid);
			put("category", c);
			Map<String, Object> mm = cms.queryPage(page, pagesize, false, false, true, String.valueOf(c.get("url")), categoryid);
			put("datalist", mm.get("list"));
			put("datapageview", String.valueOf(mm.get("datapageview")));
			put("datauri", mm.get("datauri"));
			put("datapage", mm.get("datapage"));
			return "/" + s.get("folder") + (mobile ? "/templates/m/"+c.get("mviewsite") : "/templates/"+c.get("viewsite"));
		}
		return "/" + s.get("folder") + (mobile ? "/templates/m/"+s.get("mviewsite") : "/templates/"+s.get("viewsite"));
	}
	
	private String getString(Object object)
	{
		return object == null ? "" : String.valueOf(object);
	}
}
