/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dswork.web.MyRequest;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import testwork.model.demo.Demo;
import testwork.service.demo.HbmDemoService;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/hbmdemo")
public class HbmDemoController
{
	@Autowired
	private HbmDemoService service;

	//添加
	@RequestMapping("/addHbmDemo1")
	public String addHbmDemo1()
	{
		return "/manage/hbmdemo/addHbmDemo.jsp";
	}
	
	@RequestMapping("/addHbmDemo2")
	public @ResponseBody String addHbmDemo2(Demo po)
	{
		try
		{
			service.save(po);
			return "1";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "0:" + e.getMessage();
		}
	}

	//删除
	@RequestMapping("/delHbmDemo")
	public String delHbmDemo(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		return "redirect:" + req.getRefererURL();
	}

	//修改
	@RequestMapping("/updHbmDemo1")
	public String updHbmDemo1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return "/manage/hbmdemo/updHbmDemo.jsp";
	}
	
	@RequestMapping("/updHbmDemo2")
	public @ResponseBody String updHbmDemo2(Demo po)
	{
		try
		{
			service.update(po);
			return "1";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "0:" + e.getMessage();
		}
	}

	//获得分页
	@RequestMapping("/getHbmDemo")
	public String getHbmDemo(HttpServletRequest request, @RequestParam(defaultValue="1")int page, @RequestParam(defaultValue="10")int pageSize)
	{
		MyRequest req = new MyRequest(request);
		Map map = req.getParameterValueMap(false, false);
		Page<Demo> pageModel = service.queryPage(page, pageSize, map);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav(request, pageModel));
		return "/manage/hbmdemo/getHbmDemo.jsp";
	}

	//明细
	@RequestMapping("/getHbmDemoById")
	public String getHbmDemoById(HttpServletRequest request, @RequestParam(defaultValue="0")Long keyIndex)
	{
		request.setAttribute("po", service.get(keyIndex));
		return "/manage/hbmdemo/getHbmDemoById.jsp";
	}
}
