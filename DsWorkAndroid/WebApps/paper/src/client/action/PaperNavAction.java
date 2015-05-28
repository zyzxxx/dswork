package client.action;

import java.util.List;

import gwen.devwork.BaseAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import client.entity.PaperCategory;
import client.entity.PaperModel;
import client.service.PaperNavService;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@ParentPackage(value="json-default")
@Namespace("/client/papernav")
@Action(results={ @Result(name = "json", type = "json", params={"ignoreHierarchy","false"})})
public class PaperNavAction extends BaseAction<PaperCategory>
{
	@Autowired
	private PaperNavService paperNavService;

	@Override
	public String execute() throws Exception {
		getBaseResp().setContentType("application/json");
		String navJsonStr = paperNavService.getNavJson();
		System.out.println(navJsonStr);
		print(navJsonStr);
		return NONE;
	}
	
	public static void main(String[] args)
	{
		String jsonStr = "[{\"id\":3,\"name\":\"动物\",\"sort\":1,\"paperModelList\":[{\"id\":12,\"pid\":3,\"name\":\"牛\",\"sort\":2}]},{\"id\":2,\"name\":\"军事\",\"sort\":2,\"paperModelList\":[{\"id\":7,\"pid\":2,\"name\":\"飞机\",\"sort\":1},{\"id\":8,\"pid\":2,\"name\":\"船\",\"sort\":2}]},{\"id\":4,\"name\":\"工具\",\"sort\":3,\"paperModelList\":[]}]";
		PaperCategory[] mGroups = null;
		PaperModel[][] mChilds = null;
		
		Gson gson = new Gson();
		List<PaperCategory> groupList = (List<PaperCategory>)gson.fromJson(jsonStr,  new TypeToken<List<PaperCategory>>(){}.getType());
		mGroups = new PaperCategory[groupList.size()];
		mChilds = new PaperModel[groupList.size()][];
		for(int i=0; i<mGroups.length; i++)
		{
			mGroups[i] = groupList.get(i);
//			mChilds[i] = new PaperModel[];
			System.out.println(mGroups[i].getName());
			for(int j=0; j<mGroups[i].getPaperModelList().size(); j++)
			{
				System.out.println("-"+mGroups[i].getPaperModelList().get(j).getName());
			}
		}
	
	}
}