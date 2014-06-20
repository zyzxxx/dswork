package dswork.mvc;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import dswork.core.page.PageRequest;
import dswork.web.MyRequest;

public abstract class BaseController
{
	protected static String PageSize_SessionName = "dswork_session_pagesize";
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected MyRequest req;
	private PrintWriter out;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		this.response.setCharacterEncoding("UTF-8");
		this.session = request.getSession();
		this.req = new MyRequest(request);
	}
	
	protected void print(Object msg)
	{
		try
		{
			if(out == null)
			{
				out = response.getWriter();
			}
			out.print(msg);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected void put(String key, Object obj)
	{
		request.setAttribute(key, obj);
	}
	
	protected PageRequest getPageRequest()
	{
		PageRequest pr = new PageRequest();
		pr.setFilters(req.getParameterValueMap(false, false));
		pr.setCurrentPage(req.getInt("page", 1));
		int pagesize = 10;
		try
		{
			pagesize = Integer.parseInt(String.valueOf(session.getAttribute(PageSize_SessionName)).trim());
		}
		catch(Exception ex)
		{
			pagesize = 10;
		}
		pagesize = req.getInt("pageSize", pagesize);
		session.setAttribute(PageSize_SessionName, pagesize);
		pr.setPageSize(pagesize);
		return pr;
	}
}
