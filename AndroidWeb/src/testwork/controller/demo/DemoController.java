/**
 * 功能:DEMOController
 * 开发人员:你是谁
 * 创建时间:2013-9-13 23:06:34
 */
package testwork.controller.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import testwork.model.demo.Demo;
import testwork.service.demo.DemoService;
import dswork.core.db.BaseService;
import dswork.mvc.BaseController;
import dswork.web.MyRequest;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/manage/demo")
public class DemoController extends BaseController<Demo>
{
	@Autowired
	private DemoService service;
	protected BaseService getService() {return service;};
	protected String getViewAdd() {return "/WEB-INF/view/manage/demo/addDemo.jsp";};
	protected String getViewUpd() {return "/WEB-INF/view/manage/demo/updDemo.jsp";};
	protected String getViewGet() {return "/WEB-INF/view/manage/demo/getDemo.jsp";};
	protected String getViewGetById() {return "/WEB-INF/view/manage/demo/getDemoById.jsp";};
	
	
	@RequestMapping("/addDemo1")
	public String add1(HttpServletRequest request, HttpServletResponse response)
	{
		return super.add1(request, response);
	}
	@RequestMapping("/addDemo2")
	public void add2(HttpServletRequest request, HttpServletResponse response, Demo po) throws IOException
	{
		super.add2(request, response, po);
	}
	@RequestMapping("/delDemo")
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		super.del(request, response);
	}
	@RequestMapping("/delJSONDemo")
	public void delJSON(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		PrintWriter out = null;
		String result = "";
		try
		{
			out = response.getWriter();
			service.deleteBatch((Long[])ConvertUtils.convert(req.getString("keyIndex").split(","), Long.class));
			result = "1";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		out.print(result);
	}
	@RequestMapping("/updDemo1")
	public String upd1(HttpServletRequest request, HttpServletResponse response)
	{
		return super.upd1(request, response);
	}
	@RequestMapping("/updDemo2")
	public void upd2(HttpServletRequest request, HttpServletResponse response, Demo po) throws IOException
	{
		super.upd2(request, response, po);
	}
	@RequestMapping("/updBatchForMobile")
	public void updBatch(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MyRequest req = new MyRequest(request);
		Map m = req.getParameterValueMap(false, true);
		m.put("ids", req.getString("ids"));
		PrintWriter out = null;
		String result = "";
		try
		{
			out = response.getWriter();
			service.updBatch(m);
			result = "1";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		out.print(result);
	}
	@RequestMapping("/getDemo")
	public String get(HttpServletRequest request, HttpServletResponse response)
	{
		return super.get(request, response);
	}
	@RequestMapping("/getDemoForMobile")
	public void getForMobile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		MyRequest req = new MyRequest(request);
		List<Demo> list = service.queryList(req.getParameterValueMap(false, true));
		out.print(list);
	}
	@RequestMapping("/getDemoById")
	public String getById(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0")Long keyIndex)
	{
		return super.getById(request, response, keyIndex);
	}
	@RequestMapping("/getDemoByIdForMobile")
	public void getByIdForMobile(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0")Long keyIndex) throws IOException
	{
		PrintWriter out = response.getWriter();
		Demo po = service.get(keyIndex);
		out.print(po);
	}
}
