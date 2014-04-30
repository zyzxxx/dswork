package dswork.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import dswork.core.page.PageRequest;
import dswork.web.MyRequest;

public class BaseController
{
	protected static void print(HttpServletResponse response, Object msg)
	{
		try
		{
			PrintWriter out = response.getWriter();
			out.print(msg);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	protected static void sendRedirect(HttpServletResponse response, String url)
	{
		try
		{
			response.sendRedirect(url);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected static PageRequest getPageRequest(MyRequest req)
	{
		PageRequest pageRequest = new PageRequest();
		pageRequest.setFilters(req.getParameterValueMap(false, false));
		pageRequest.setCurrentPage(req.getInt("page", 1));
		pageRequest.setPageSize(req.getInt("pageSize", 10));
		return pageRequest;
	}
}
