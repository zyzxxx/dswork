package dswork.common.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dswork.web.MyRequest;
import dswork.common.model.DsCommonFunc;
import dswork.common.model.DsCommonRole;
import dswork.common.model.DsCommonRoleFunc;
import dswork.common.model.DsCommonSystem;
import dswork.common.service.DsCommonRoleChooseService;
import dswork.core.page.Page;
import dswork.core.page.PageNav;

//角色
@Controller
@RequestMapping("/common/rolechoose")
public class DsCommonRoleChooseController
{
	@Autowired
	private DsCommonRoleChooseService service;

	// 获得分页
	@RequestMapping("/getRoleChoose")
	public String getChoose(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		Page<DsCommonSystem> pageModel = service.querySystemPage(req.getInt("page", 1), req.getInt("pageSize", 10), req.getParameterValueMap(false, false));
		request.setAttribute("pageModel", pageModel);
		request.setAttribute("pageNav", new PageNav<DsCommonSystem>(request, pageModel));
		return "/common/rolechoose/getRoleChoose.jsp";
	}

	// 树形管理
	@RequestMapping("/getRoleTree")
	public String getRoleTree(HttpServletRequest request)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		request.setAttribute("po", service.getSystem(systemid));
		return "/common/rolechoose/getRoleTree.jsp";
	}
	// 获得树形管理时的json数据
	@RequestMapping("/getRoleJson")// BySystemidAndPid
	public void getRoleJson(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		long systemid = req.getLong("systemid");
		long pid = req.getLong("pid");
		out.print(service.queryRoleList(systemid, pid));
	}

	// 获得功能和被分配到角色的功能
	@RequestMapping("/getRoleFuncJson")// BySystemidAndRoleid
	public void getRoleFuncJson(HttpServletRequest request, PrintWriter out)
	{
		MyRequest req = new MyRequest(request);
		Long roleid = req.getLong("roleid");
		DsCommonRole po = service.get(roleid);
		List<DsCommonFunc> list = service.queryFuncList(po.getSystemid());
		if(null != list && 0 < list.size())
		{
			Map<Long, DsCommonFunc> m = new HashMap<Long, DsCommonFunc>();
			for(DsCommonFunc i : list)
			{
				m.put(i.getId(), i);
			}
			if(0 < roleid)
			{
				List<DsCommonRoleFunc> flist = service.queryFuncListByRoleid(roleid);
				for(DsCommonRoleFunc i : flist)
				{
					m.get(i.getFuncid()).setChecked(true);
				}
			}
			out.print(list);
		}
		else
		{
			out.print("[]");
		}
	}
}
