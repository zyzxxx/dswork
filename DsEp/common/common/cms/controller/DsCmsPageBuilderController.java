package common.cms.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.cms.CmsFactory;
import dswork.mvc.BaseController;

@Scope("prototype")
@Controller
public class DsCmsPageBuilderController extends BaseController
{
	@RequestMapping("/cms/page/buildHTML")
	public String buildHTML()
	{
		//Long siteid = req.getLong("siteid");
		Long categoryid = req.getLong("categoryid", -1);
		Long pageid = req.getLong("pageid", -1);
		common.cms.CmsFactory cms = new CmsFactory(request);
		put("cms", cms);
		Map<String, Object> s = cms.getSite();
		put("site", s);
		put("ctx", getString(s.get("url")));
		if(pageid > 0)// 内容页
		{
			Map<String, Object> p = cms.get(pageid + "");
			Map<String, Object> c = cms.getCategory(String.valueOf(p.get("categoryid")));
			put("category", c);
			put("id", getString(p.get("id")));
			put("categoryid", getString(p.get("categoryid")));
			put("title", getString(p.get("title")));
			put("metakeywords", getString(p.get("metakeywords")));
			put("metadescription", getString(p.get("metadescription")));
			put("summary", getString(p.get("summary")));
			put("content", getString(p.get("content")));
			put("releasetime", getString(p.get("releasetime")));
			put("img", getString(p.get("img")));
			put("url", getString(p.get("url")));
			return "/" + s.get("folder") + "/templates/" + c.get("pageviewsite");
		}
		if(categoryid > 0)// 栏目页
		{
			int page = req.getInt("page", 1);
			int pagesize = req.getInt("pagesize", 25);
			Map<String, Object> c = cms.getCategory(categoryid + "");
			if(String.valueOf(c.get("status")).equals("2"))
			{
				return null;// 外链
			}
			if(String.valueOf(c.get("viewsite")).equals(""))
			{
				return null;// 外链
			}
			put("categoryid", categoryid);
			put("category", c);
			Map<String, Object> mm = cms.queryPage(page, pagesize, false, false, true, String.valueOf(c.get("url")), categoryid);
			put("datalist", mm.get("list"));
			put("datapageview", mm.get("datapageview"));
			put("datauri", mm.get("datauri"));
			put("datapage", mm.get("datapage"));
			return "/" + s.get("folder") + "/templates/" + c.get("viewsite");
		}
		return "/" + s.get("folder") + "/templates/" + s.get("viewsite");
	}
	
	private String getString(Object object)
	{
		return object == null ? "" : String.valueOf(object);
	}
}
