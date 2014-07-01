package dswork.android.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
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
		return ctx.getString(R.string.projUrl)+"/mobile/demo/";
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
		List<Demo> list = new ArrayList<Demo>();
		try{
			String result = sendRequest("get.htm", m);//发送HttpPost请求
			if(!result.equals("error")){
				JSONArray arr = new JSONArray(result);
				for(int i=0; i<arr.length(); i++)
				{
					JSONObject o = arr.getJSONObject(i);
					Demo Demo = new Demo(o.getLong("id"), o.getString("title"), o.getString("content"), o.getString("foundtime"));
					list.add(Demo);
				}
			}else{
				list = null;
			}
		}catch(Exception e){
			list = null;
			e.printStackTrace();
			Log.i("controller Exception",e.getMessage());
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
				JSONObject jsonObject = new JSONObject(result);
				po = new Demo(jsonObject.getLong("id"),jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("foundtime"));
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("controller Exception",e.getMessage());
		}
		return po;
	}
	
	public List<Demo> queryPage(Map m, int offset, int maxResult)
	{
		int page = offset/maxResult+1;
		m.put("page", page);
		m.put("pageSize", maxResult);
		System.out.println("【页码】:"+page+"|offset:"+offset+"|pageSize:"+maxResult);
		List<Demo> list = null;
		try{
			String result = sendRequest("queryPage.htm", m);
			if(!result.equals("error")){
				list = new ArrayList<Demo>();
				JSONArray arr = new JSONArray(result);
				for(int i=0; i<arr.length(); i++){
					JSONObject o = arr.getJSONObject(i);
					Demo Demo = new Demo(o.getLong("id"), o.getString("title"), o.getString("content"), o.getString("foundtime"));
					list.add(Demo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.i("controller Exception",e.getMessage());
		}
		return list;
	}
}
