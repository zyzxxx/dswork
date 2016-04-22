/**
 * 功能:公共Controller
 */
package dswork.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.web.MyRequest;
import dswork.common.DsFactory;

@Controller
@RequestMapping("/common/share")
public class CommonShareOrgController
{
	@RequestMapping("/getJsonOrg")
	public void getJsonOrg(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		MyRequest req = new MyRequest(request);
		long pid = req.getLong("pid");
		int status = req.getInt("status", -1);
		try
		{
			response.getWriter().print(DsFactory.getOrg().getOrgJson(pid, (status > -1 && status < 3) ? status : null));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
