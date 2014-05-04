package dswork.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dswork.core.db.BaseService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;
import dswork.core.util.CollectionUtil;
import dswork.web.MyRequest;

@SuppressWarnings("unchecked")
public abstract class BaseController<T>
{
	protected abstract BaseService getService();
	protected abstract String getViewAdd();
	protected abstract String getViewUpd();
	protected abstract String getViewGet();
	protected abstract String getViewGetById();
	
	// 添加
	@RequestMapping("/add1")
	public String add1(HttpServletRequest request, HttpServletResponse response)
	{
		return getViewAdd();
	}
	@RequestMapping("/add2")
	public void add2(HttpServletRequest request, HttpServletResponse response, T po) throws IOException
	{
		PrintWriter out = response.getWriter();
		try
		{
			getService().save(po);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 删除
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MyRequest req = new MyRequest(request);
		getService().deleteBatch(CollectionUtil.toLongArray(req.getLongArray("keyIndex", 0)));
		response.sendRedirect(req.getRefererURL());
	}

	// 修改
	@RequestMapping("/upd1")
	public String upd1(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		Long id = req.getLong("keyIndex");
		request.setAttribute("po", getService().get(id));
		request.setAttribute("page", req.getInt("page", 1));
		return getViewUpd();
	}
	@RequestMapping("/upd2")
	public void upd2(HttpServletRequest request, HttpServletResponse response, T po) throws IOException
	{
		PrintWriter out = response.getWriter();
		try
		{
			getService().update(po);
			out.print(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.print("0:" + e.getMessage());
		}
	}

	// 获得分页
	@RequestMapping("/get")
	public String get(HttpServletRequest request, HttpServletResponse response)
	{
		MyRequest req = new MyRequest(request);
		int page = req.getInt("page", 1);
		int pageSize = req.getInt("pageSize", 10);
		Map map = req.getParameterValueMap(false, false);
		Page<T> pageModel = getService().queryPage(page, pageSize, map);
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav(request, pageModel));
		return getViewGet();
	}

	// 明细
	@RequestMapping("/getById")
	public String getById(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "0")Long keyIndex)
	{
		request.setAttribute("po", getService().get(keyIndex));
		return getViewGetById();
	}
}
