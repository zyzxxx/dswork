package admin.action;

import gwen.devwork.BaseAction;
import gwen.devwork.PageNav;
import gwen.util.CollectionUtil;

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
import admin.entity.PaperModel;
import admin.service.PaperModelService;


@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/admin/papermodel")
//@InterceptorRefs(value={ @InterceptorRef("myStack") })
public class PaperModelAction extends BaseAction<PaperModel>
{
	@Autowired
	private PaperModelService service;
	//实体类
	private PaperModel po;
	public PaperModel getPo() {return po;}
	public void setPo(PaperModel po) {this.po = po;}
	
	@Action(value = "addPaperModel1", results={@Result(name = "success", location = "/view/admin/papermodel/addPaperModel.jsp")})
	public String addPaperModel1() 
	{
		put("pid",getReq().getLong("pid"));
		return "success";
	}
	@Action(value = "addPaperModel2")
	public void addPaperModel2() 
	{
		service.add(po);
		print("1");
	}
	
	@Action(value="delPaperModel")
	public void delPaperModel()
	{
		service.del(CollectionUtil.toLongArray(getReq().getLongArray("keyIndex", 0l)));
		print("1");
	}
	
	@Action(value="updPaperModel1",results={@Result(name = "success", location = "/view/admin/papermodel/updPaperModel.jsp")})
	public String updPaperModel1() 
	{
		po = service.getById(getReq().getLong("id"));
		return "success";
	}
	@Action(value="updPaperModel2")
	public void updPaperModel2() 
	{
		service.upd(po);
		print("1");
	}

	@Action(value="getPaperModelById",results={@Result(name = "success", location = "/view/admin/papermodel/getPaperModelById.jsp")})
	public String getPaperModelById() 
	{
		po = service.getById(getReq().getLong("id"));
		return "success";
	}

	@Action(value="getPaperModels", results={@Result(name = "success", location = "/view/admin/papermodel/getPaperModels.jsp")})
	public String getPaperModels() 
	{
		Map m = getReq().getParameterValueMap(false, true);
		m.put("page", getReq().getInt("page", 1));
		m.put("pageSize", getReq().getInt("pageSize", 10));
		
		page = service.getPage(5, m);
		pageNav = new PageNav<PaperModel>(getReq(), page);
		resultList = pageNav.getResult();
		put("pid",getReq().getLong("pid"));
		return "success";
	}
}