package dswork.ep.cms;

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
		if(pageid > 0)// 内容页
		{
			Map<String, Object> p = cms.get(pageid + "");
			Map<String, Object> c = cms.getCategory(String.valueOf(p.get("categoryid")));
			put("category", c);
			put("id", p.get("id"));
			put("categoryid", p.get("categoryid"));
			put("title", p.get("title"));
			put("keywords", p.get("keywords"));
			put("summary", p.get("summary"));
			put("content", p.get("content"));
			put("releasetime", p.get("releasetime"));
			put("img", p.get("img"));
			put("url", p.get("url"));
			return "/" + s.get("folder") + "/templates/" + c.get("pageviewsite");
		}
		if(categoryid > 0)// 栏目页
		{
			int page = req.getInt("page", 1);
			Map<String, Object> c = cms.getCategory(categoryid + "");
			put("category", c);
			Map<String, Object> mm = cms.queryPage(page, 50, false, false, true, String.valueOf(c.get("url")), categoryid);
			put("datalist", mm.get("list"));
			put("datapage", mm.get("page"));
			return "/" + s.get("folder") + "/templates/" + c.get("viewsite");
		}
		return "/" + s.get("folder") + "/templates/" + s.get("viewsite");
	}
}
