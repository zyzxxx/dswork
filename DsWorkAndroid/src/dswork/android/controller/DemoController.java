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

public class DemoController extends BaseWebController
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

	public String add(Demo po) 
	{
		return sendRequest("addDemo.htm", getModelMap(po,false));
	}

	public String deleteBatch(Long[] ids) 
	{
		return sendRequest("delDemo.htm", getKeyIndexMap(HttpUtil.idsConvertToStr(ids)));
	}

	public String upd(Demo po) 
	{
		return sendRequest("updDemo.htm", getModelMap(po,true));
	}

	public List<Demo> get(Map<String, Object> m)
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
	
	public List<Demo> queryPage(Map<String, Object> m, int offset, int maxResult)
	{
		int page = offset/maxResult+1;
		m.put("page", page);
		m.put("pageSize", maxResult);
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
	
	public int getCount(Map<String, Object> m)
	{
		int count = 0;
		try{
			count = Integer.valueOf(sendRequest("getCount.htm", m));
		}catch(Exception e){
			count = 0;
			e.printStackTrace();
		}
		return count;
	}
}
