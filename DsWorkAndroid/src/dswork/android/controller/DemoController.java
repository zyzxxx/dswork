package dswork.android.controller;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import dswork.android.R;
import dswork.android.lib.controller.BaseWebController;
import dswork.android.lib.util.webutil.HttpUtil;
import dswork.android.model.Demo;

public class DemoController extends BaseWebController<Demo>
{
	private Context ctx;
	
	public DemoController(Context ctx) 
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public String getModulePath() {
		return ctx.getString(R.string.projPath)+"/mobile/demo/";
	}

	@Override
	public String add(Demo po) 
	{
		return sendRequest("addDemo.htm", getModelMap(po,false));
	}

	@Override
	public String deleteBatch(Long[] ids) 
	{
		return sendRequest("delDemo.htm", getKeyIndexMap(HttpUtil.idsConvertToStr(ids)));
	}

	@Override
	public String upd(Demo po) 
	{
		return sendRequest("updDemo.htm", getModelMap(po,true));
	}

	@Override
	public List<Demo> get(Map m)
	{
		List<Demo> list = null;
		try{
			String result = sendRequest("get.htm", m);//发送HttpPost请求
			if(!result.equals("error")){
				list = new Gson().fromJson(result, new TypeToken<List<Demo>>(){}.getType());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Demo getById(Long id)
	{
		Demo po = null;
		try{
			String result = sendRequest("getDemoById.htm", getKeyIndexMap(id));
			if(!result.equals("error")){
				po = new Gson().fromJson(result, Demo.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return po;
	}
	
	public List<Demo> queryPage(Map<String, Integer> m, int offset, int maxResult)
	{
		int page = offset/maxResult+1;
		m.put("page", page);
		m.put("pageSize", maxResult);
		System.out.println("【页码】:"+page+"|offset:"+offset+"|pageSize:"+maxResult);
		List<Demo> list = null;
		try{
			String result = sendRequest("queryPage.htm", m);
			if(!result.equals("error")){
				list = new Gson().fromJson(result, new TypeToken<List<Demo>>(){}.getType());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}
