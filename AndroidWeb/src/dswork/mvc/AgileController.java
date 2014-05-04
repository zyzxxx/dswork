package dswork.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import dswork.core.page.PageRequest;
import dswork.web.MyRequest;

public abstract class AgileController
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	//protected HttpSession session;
	protected MyRequest req;
	private PrintWriter out;
	
	@org.springframework.web.bind.annotation.ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		//this.session = request.getSession();
		this.req = new MyRequest(request);
	}

	protected void print(Object msg)
	{
		try
		{
			//if(out == null)
			//{
				out = response.getWriter();
			//}
			out.print(msg);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected PageRequest getPageRequest()
	{
		PageRequest pageRequest = new PageRequest();
		pageRequest.setFilters(req.getParameterValueMap(false, false));
		pageRequest.setCurrentPage(req.getInt("page", 1));
		pageRequest.setPageSize(req.getInt("pageSize", 10));
		return pageRequest;
	}
}
