package admin.action;

import gwen.devwork.BaseAction;
import gwen.devwork.PageNav;
import gwen.util.CollectionUtil;
import gwen.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

import admin.entity.PaperImage;
import admin.service.PaperImageService;


@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="struts-default")
@Namespace("/admin/paperimage")
@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })
public class PaperImageAction extends BaseAction<PaperImage>
{
	@Autowired
	private PaperImageService service;
	//Model
	private PaperImage po;
	public PaperImage getPaperImage() { return po; }
	public void setPaperImage(PaperImage po) { this.po = po; }
	
	//上传文件file属性（多个）
	private List<File> myFile;
	public List<File> getMyFile() { return myFile; }
	public void setMyFile(List<File> myFile) { this.myFile = myFile; }
	//封装上传文件名的属性 （包含后缀名，如：hello.jpg）
	private List<String> myFileFileName;
	public List<String> getMyFileFileName() { return myFileFileName; }
	public void setMyFileFileName(List<String> myFileFileName) { this.myFileFileName = myFileFileName; }
    //封装上传文件类型的属性  （如：image/jpeg）
    private List<String> myFileContentType;
    public List<String> getMyFileContentType() { return myFileContentType; }
	public void setMyFileContentType(List<String> myFileContentType) { this.myFileContentType = myFileContentType; }
	
	@Action(value="addPaperImage1",results={ @Result(name = "success", location = "/view/admin/paperimage/addPaperImage.jsp")})
	public String addPaperImage1() 
	{
		put("pid",getReq().getLong("pid"));
		put("ppid",getReq().getLong("ppid"));
		return "success";
	}
	@Action(value="addPaperImage2")
	public void addPaperImage2() throws Exception
	{
		List<PaperImage> poList = new ArrayList<PaperImage>();
		for(int i=0; i<myFile.size(); i++){
			//保存到硬盘
//			FileUtils.copyFile(myFile.get(i), new File("d:/uploadtemp/", myFileFileName.get(i)));
			//保存到db
			po = new PaperImage();
			po.setPid(getReq().getLong("pid"));
			po.setImage(FileUtil.getToByte(myFile.get(i)));
			poList.add(po);
		}
		service.addBatch(poList);
		print("1");
	}
	
	@Action(value="updSortById")
	public void updSortById() 
	{
		long id = getReq().getLong("id");
		int sort = getReq().getInt("sort", 0);
		po = new PaperImage();
		po.setId(id);
		po.setSort(sort);
		service.updSortById(po);
		print("1");
	}
	
	@Action(value="updSortBatch")
	public void updSortBatch() 
	{
		service.updSortBatch(CollectionUtil.toLongArray(getReq().getLongArray("id", 0l)), 
							 CollectionUtil.toIntegerArray(getReq().getIntArray("sort", 0)));
		print("1");
	}
	
	@Action(value="delPaperImage")
	public void delPaperImage() 
	{
		service.del(CollectionUtil.toLongArray(getReq().getLongArray("keyIndex", 0l)));
		print("1");
	}

	@Action(value="getPaperImageById",results={ @Result(name = "success", location = "/view/admin/paperimage/getPaperImageById.jsp")})
	public String getPaperImageById() 
	{
		po = service.getById(getReq().getLong("id"));
		put("po",po);
		return "success";
	}

	@Action(value="getPaperImages",results={ @Result(name = "success", location = "/view/admin/paperimage/getPaperImages.jsp")})
	public String getPaperImages() 
	{
		Map m = getReq().getParameterValueMap(false, true);
		m.put("page", getReq().getInt("page", 1));
		m.put("pageSize", getReq().getInt("pageSize", 5));
		
		page = service.getPage(5, m);
		pageNav = new PageNav<PaperImage>(getReq(), page, "5,10,20");
		resultList = pageNav.getResult();
		put("pid", getReq().getLong("pid"));
		put("ppid", getReq().getLong("ppid"));
		return "success";
	}
}