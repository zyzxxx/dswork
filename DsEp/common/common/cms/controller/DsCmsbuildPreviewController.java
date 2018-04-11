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
		DsCmsPreview cms = new DsCmsPreview(siteid);
		put("cms", cms);
		put("year", TimeUtil.getCurrentTime("yyyy"));
		Map<String, Object> site = cms.getSite();
		put("site", site);
		put("ctx", request.getContextPath() + "/html/" + site.get("folder") + "/html");// 预览时，现在可以不需要运行服务器，即可浏览相对地址
		if(pageid > 0)// 内容页
		{
			Map<String, Object> page = cms.get(pageid.toString());
			Map<String, Object> cate = cms.getCategory((Long)page.get("categoryid"));
			put("category", cate);
			put("id", getString(page.get("id")));
			put("categoryid", getString(page.get("categoryid")));
			put("title", getString(page.get("title")));
			put("summary", getString(page.get("summary")));
			put("metakeywords", getString(page.get("metakeywords")));
			put("metadescription", getString(page.get("metadescription")));
			put("releasetime", getString(page.get("releasetime")));
			put("releasesource", getString(page.get("releasesource")));
			put("releaseuser", getString(page.get("releaseuser")));
			put("img", getString(page.get("img")));
			put("url", getString(page.get("url")));
			put("content", getString(page.get("content")));
			return "/" + site.get("folder") + "/templates/" + cate.get("pageviewsite");
		}
		if(categoryid > 0)// 栏目页
		{
			int page = req.getInt("page", 1);
			int pagesize = req.getInt("pagesize", 25);
			Map<String, Object> cate = cms.getCategory(categoryid);
			if(String.valueOf(cate.get("viewsite")).equals(""))
			{
				return null;
			}
			try
			{
				Map<String, Object> categoryparent = cms.getCategory((Long)cate.get("pid"));
				put("categoryparent", categoryparent);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			put("categoryid", categoryid);
			put("category", cate);
			Map<String, Object> mm = cms.queryPage(page, pagesize, false, false, true, String.valueOf(cate.get("url")), categoryid);
			put("datalist", mm.get("list"));
			put("datapageview", mm.get("datapageview"));
			put("datauri", mm.get("datauri"));
			put("datapage", mm.get("datapage"));
			return "/" + site.get("folder") + "/templates/" + cate.get("viewsite");
		}
		// 首页
		return "/" + site.get("folder") + "/templates/" + site.get("viewsite");
	}
	
	private String getString(Object object)
	{
		return object == null ? "" : String.valueOf(object);
	}
}
