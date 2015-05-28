package admin.action;

import gwen.devwork.BaseAction;
import gwen.devwork.PageNav;
import gwen.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import admin.entity.PaperCategory;
import admin.service.PaperCategoryService;


@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/admin/papercategory")
//@InterceptorRefs(value={ @InterceptorRef("myStack") })
public class PaperCategoryAction extends BaseAction<PaperCategory>
{
	@Autowired
	private PaperCategoryService service;
	//实体类
	private PaperCategory po;
	public PaperCategory getPo() {return po;}
	public void setPo(PaperCategory po) {this.po = po;}
	
	@Action(value="addPaperCategory1",results={ @Result(name = "success", location = "/view/admin/papercategory/addPaperCategory.jsp")})
	public String addPaperCategory1() 
	{
		return "success";
	}
	@Action(value="addPaperCategory2")
	public void addPaperCategory2() 
	{
		service.add(po);
		print("1");
	}
	
	@Action(value="delPaperCategory")
	public void delPaperCategory() 
	{
		service.del(CollectionUtil.toLongArray(getReq().getLongArray("keyIndex", 0l)));
		print("1");
	}
	
	@Action(value="updPaperCategory1",results={ @Result(name = "success", location = "/view/admin/papercategory/updPaperCategory.jsp")})
	public String updPaperCategory1() 
	{
		po = service.getById(getReq().getLong("id"));
		return "success";
	}
	@Action(value="updPaperCategory2")
	public void updPaperCategory2() 
	{
		service.upd(po);
		print("1");
	}

	@Action(value="getPaperCategoryById",results={ @Result(name = "success", location = "/view/admin/papercategory/getPaperCategoryById.jsp")})
	public String getPaperCategoryById() 
	{
		po = service.getById(getReq().getLong("id"));
		return "success";
	}

	@Action(value="getPaperCategorys",results={ @Result(name = "success", location = "/view/admin/papercategory/getPaperCategorys.jsp")})
	public String getPaperCategorys() 
	{
		Map m = getReq().getParameterValueMap(false, true);
		m.put("page", getReq().getInt("page", 1));
		m.put("pageSize", getReq().getInt("pageSize", 10));
		
		page = service.getPage(5, m);
		pageNav = new PageNav<PaperCategory>(getReq(), page);
		resultList = pageNav.getResult();
		return "success";
	}
}