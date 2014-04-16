/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.util.List;
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
import testwork.service.demo.DemoService;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/demo")
public class DemoController
{
	@Autowired
	private DemoService service;

	// 添加
	@RequestMapping("/addDemo1")
	public String addDemo1()
	{
		return "/manage/demo/addDemo.jsp";
	}

	@RequestMapping("/addDemo2")
	public @ResponseBody String addDemo2(Demo po)
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

	// 删除
	@RequestMapping("/delDemo")
	public String delDemo(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		service.deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		return "redirect:" + req.getRefererURL();
	}

	// 修改
	@RequestMapping("/updDemo1")
	public String updDemo1(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", service.get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return "/manage/demo/updDemo.jsp";
	}

	@RequestMapping("/updDemo2")
	public @ResponseBody String updDemo2(Demo po)
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

	// 获得分页
	@RequestMapping("/getDemo")
	public String getDemo(HttpServletRequest request, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int pageSize)
	{
		MyRequest req = new MyRequest(request);
		Map map = req.getParameterValueMap(false, false);
		Page<Demo> pageModel = service.queryPage(page, pageSize, map);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav(request, pageModel));
		return "/manage/demo/getDemo.jsp";
	}

	// 明细
	@RequestMapping("/getDemoById")
	public String getDemoById(HttpServletRequest request, @RequestParam(defaultValue = "0")Long keyIndex)
	{
		request.setAttribute("po", service.get(keyIndex));
		return "/manage/demo/getDemoById.jsp";
	}

	@RequestMapping("/getJSONDemo")
	public @ResponseBody String getJSONDemo(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Map map = req.getParameterValueMap(false, true);
		List<Demo> demos = service.queryList(map);
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(Demo d : demos)
		{
			builder.append("{");
			builder.append("\"id\":" + d.getId() + ",");
			builder.append("\"title\":\"" + d.getTitle() + "\",");
			builder.append("\"content\":\"" + d.getContent() + "\",");
			builder.append("\"foundtime\":\"" + d.getFoundtime());
			builder.append("\"},");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		System.out.println(builder.toString());
		return builder.toString();
	}
}
