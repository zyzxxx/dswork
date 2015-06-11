/**
 * 功能:MyBatis样例Controller
 * 开发人员:skey
 * 创建时间:2014-07-01 12:01:01
 */
package testwork.controller.manage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.mvc.BaseController;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;
import testwork.model.Demo;
import testwork.model.Mark;
import testwork.service.demo.MarkService;

@Scope("prototype")
@Controller
@RequestMapping("/manage/mark")
public class MarkController extends BaseController
{
	@Autowired
	private MarkService service;

	@RequestMapping("/addMark2")
	public void addMark2()
	{
		try
		{
			Long demoid = req.getLong("demoid");
			List<Mark> list = new ArrayList<Mark>();
			String[] contentArr = req.getStringArray("content", false);
			for(int i = 0; i < contentArr.length; i++)
			{
				Mark m = new Mark();
				m.setDemoid(demoid);
				m.setContent(contentArr[i]);
				m.setFoundtime(TimeUtil.getCurrentDate());
				list.add(m);
			}
			service.save(list);
			print(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			print("0:" + e.getMessage());
		}
	}

	@RequestMapping("/getDemo")
	public String getDemo()
	{
		PageRequest pr = getPageRequest();
		pr.setFilters(req.getParameterValueMap(false, false));
		Page<Demo> pageModel = service.queryPage(pr);
		put("pageModel", pageModel);
		put("pageNav", new PageNav<Demo>(request, pageModel));
		return "/manage/mark/getDemo.jsp";
	}

	@RequestMapping("/getMark")
	public String getMark()
	{
		Long id = req.getLong("keyIndex");
		put("po", service.get(id));
		put("list", service.queryListMark(id));
		return "/manage/mark/getMark.jsp";
	}
}
