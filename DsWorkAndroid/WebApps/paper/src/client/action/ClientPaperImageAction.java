package client.action;

import gwen.devwork.BaseAction;
import gwen.devwork.PageNav;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import client.entity.PaperImage;
import client.entity.PaperImage1;
import client.service.PaperImage1Service;
import client.service.PaperImageService;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/client/paperimage")
public class ClientPaperImageAction extends BaseAction<PaperImage>
{
	@Autowired
	private PaperImageService service;
	@Autowired
	private PaperImage1Service service1;
	//Model
	private PaperImage po;
	public PaperImage getPaperImage() { return po; }
	public void setPaperImage(PaperImage po) { this.po = po; }
	private PaperImage1 po1;
	public PaperImage1 getPaperImage1() { return po1; }
	public void setPaperImage1(PaperImage1 po1) { this.po1 = po1; }
	
	@Action(value="getPaperImageById")
	public void getPaperImageById() throws IOException 
	{
		po1 = service1.getById(getReq().getLong("id"));
		ServletOutputStream sender = getBaseResp().getOutputStream();
		getBaseResp().setContentType("application/octet-stream");
		sender.write(po1.getImage());
		sender.flush();
		sender.close();
	}
	
	@Action(value="getPaperImagePage")
	public void getPaperImagePage() 
	{
		Map m = getReq().getParameterValueMap(false, true);
		m.put("page", getReq().getInt("page", 1));
		m.put("pageSize", getReq().getInt("pageSize", 5));
		page = service.getPage(5, m);
		pageNav = new PageNav<PaperImage>(getReq(), page);
		resultList = pageNav.getResult();
		Gson gson = new Gson();
		print("json="+gson.toJson(resultList)+"&page="+page.getCurPage());
	}
}