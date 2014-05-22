package dswork.mvc;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import dswork.core.page.PageRequest;
import dswork.web.MyRequest;

public class BaseController
{
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

	@Deprecated
	protected void sendRedirect(String url)
	{
		try
		{
			response.sendRedirect(url);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected PageRequest getPageRequest()
	{
		PageRequest pr = new PageRequest();
		pr.setFilters(req.getParameterValueMap(false, false));
		pr.setCurrentPage(req.getInt("page", 1));
		pr.setPageSize(req.getInt("pageSize", 10));
		return pr;
	}
}
