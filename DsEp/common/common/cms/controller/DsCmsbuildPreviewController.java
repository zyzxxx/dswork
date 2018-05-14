package common.cms.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.cms.DsCmsPreview;
import dswork.core.util.TimeUtil;
import dswork.mvc.BaseController;
@Scope("prototype")
@Controller
public class DsCmsbuildPreviewController extends BaseController
{
	@RequestMapping("/cmsbuild/preview")
	public String preview()
	{
		Long siteid = req.getLong("siteid", -1);
		Long categoryid = req.getLong("categoryid", -1);
		Long pageid = req.getLong("pageid", -1);
		boolean mobile = req.getString("mobile", "false").equals("true");

		DsCmsPreview cms = new DsCmsPreview(siteid);
		put("cms", cms);
		put("year", TimeUtil.getCurrentTime("yyyy"));
		Map<String, Object> s = cms.getSite();
		put("site", s);
		put("ctx", request.getContextPath() + "/html/" + s.get("folder") + (mobile ? "/html/m" : "/html"));// 预览时，现在可以不需要运行服务器，即可浏览相对地址
		if(pageid > 0)// 内容页
		{
			Map<String, Object> p = cms.get(pageid.toString());
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
			if(String.valueOf(c.get("viewsite")).equals(""))
			{
				return null;
			}
			put("categoryparent", cms.getCategory(c.get("pid")));
			put("categorylist", cms.queryCategory("0"));
			put("categoryid", categoryid);
			put("category", c);
			String url = String.valueOf(c.get("url"));
			Map<String, Object> mm = cms.queryPage(page, pagesize, false, false, true, url, categoryid);
			put("datalist", mm.get("list"));
			String datapageview = String.valueOf(mm.get("datapageview"));
			if(mobile)// ugly hack
			{
				url = url.replace(".html", "");
				datapageview = datapageview.replace(url, "/m" + url);
			}
			put("datapageview", datapageview);
			put("datauri", mm.get("datauri"));
			put("datapage", mm.get("datapage"));
			return "/" + s.get("folder") + (mobile ? "/templates/m/"+c.get("mviewsite") : "/templates/"+c.get("viewsite"));
		}
		// 首页
		return "/" + s.get("folder") + (mobile ? "/templates/m/"+s.get("mviewsite") : "/templates/"+s.get("viewsite"));
	}
	
	private String getString(Object object)
	{
		return object == null ? "" : String.valueOf(object);
	}
}
