package dswork.android.demo.framework.app.web.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import dswork.android.R;
import dswork.android.demo.framework.app.web.model.Demo;
import dswork.android.lib.core.controller.BaseWebController;
import dswork.android.lib.core.util.webutil.HttpUtil;

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
		return submitHttpAction("addDemo.htm", String.class, getModelMap(po,false), "POST").getData();
	}

	public String deleteBatch(Long[] ids) 
	{
		return submitHttpAction("delDemo.htm", String.class, getKeyIndexMap(HttpUtil.idsConvertToStr(ids)), "POST").getData();
	}

	public String upd(Demo po) 
	{
		return submitHttpAction("updDemo.htm", String.class, getModelMap(po,true), "POST").getData();
	}

	public List<Demo> get(Map<String, String> m)
	{
		List<Demo> list = null;
		try{
			String result = submitHttpAction("get.htm", String.class, m, "POST").getData();//发送HttpPost请求
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
			String result = submitHttpAction("getDemoById.htm", String.class, getKeyIndexMap(String.valueOf(id)), "POST").getData();
			if(!result.equals("error")){
				po = new Gson().fromJson(result, Demo.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return po;
	}
	
	public List<Demo> queryPage(Map<String, String> m, int offset, int maxResult)
	{
		int page = offset/maxResult+1;
		m.put("page", String.valueOf(page));
		m.put("pageSize", String.valueOf(maxResult));
		List<Demo> list = null;
		try{
			String result = submitHttpAction("queryPage.htm", String.class, m, "POST").getData();
			if(!result.equals("error")){
				list = new Gson().fromJson(result, new TypeToken<List<Demo>>(){}.getType());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount(Map<String, String> m)
	{
		int count = 0;
		try{
			count = submitHttpAction("getCount.htm", Integer.class, m, "POST").getData();
		}catch(Exception e){
			count = 0;
			e.printStackTrace();
		}
		return count;
	}
}
