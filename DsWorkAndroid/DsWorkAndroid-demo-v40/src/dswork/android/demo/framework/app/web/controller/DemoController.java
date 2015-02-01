package dswork.android.demo.framework.app.web.controller;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		return sendHttpAction("addDemo.htm", getModelMap(po,false));
	}

	public String deleteBatch(Long[] ids) 
	{
		return sendHttpAction("delDemo.htm", getKeyIndexMap(HttpUtil.idsConvertToStr(ids)));
	}

	public String upd(Demo po) 
	{
		return sendHttpAction("updDemo.htm", getModelMap(po,true));
	}

	public List<Demo> get(Map<String, Object> m)
	{
		List<Demo> list = null;
		try{
			String result = sendHttpAction("get.htm", m);//发送HttpPost请求
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
			String result = sendHttpAction("getDemoById.htm", getKeyIndexMap(String.valueOf(id)));
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
			String result = sendHttpAction("queryPage.htm", m);
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
			count = Integer.valueOf(sendHttpAction("getCount.htm", m));
		}catch(Exception e){
			count = 0;
			e.printStackTrace();
		}
		return count;
	}
}
