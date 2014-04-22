package dswork.android.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import dswork.android.R;
import dswork.android.model.Demo;
import dswork.android.util.webutil.HttpPostObj;
import dswork.android.util.webutil.HttpUtil;


public class DemoController 
{
	private Context context;
	
	public DemoController(Context context) 
	{
		super();
		this.context = context;
	}

	//添加
	public String add(Map m) 
	{
		HttpPostObj postObj = new HttpPostObj(context.getString(R.string.projUrl)+context.getString(R.string.moduleUrl)+"addDemo2.htm", m);
		String result = "";
		try
		{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			if(result.equals("1")) result = "保存成功！";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	//删除（批量）
	public String deleteBatch(String ids) {
		Map m = new HashMap();
		m.put("keyIndex", ids);
		HttpPostObj postObj = new HttpPostObj(context.getString(R.string.projUrl)+context.getString(R.string.moduleUrl)+"delJSONDemo.htm", m);
		String result = "";
		try
		{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			if(result.equals("1")) result = "删除成功！";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	//修改(可批量)
	public String upd(Map m) 
	{
		System.out.println("批量修改："+m.toString());
		HttpPostObj postObj = new HttpPostObj(context.getString(R.string.projUrl)+context.getString(R.string.moduleUrl)+"updBatchForMobile.htm", m);
		String result = "";
		try
		{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			if(result.equals("1")) result = "修改成功！";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}
	//获取列表
	public List<Demo> get(Map m)
	{
		HttpPostObj postObj = new HttpPostObj(context.getString(R.string.projUrl)+context.getString(R.string.moduleUrl)+"getDemoForMobile.htm", m);
		List<Demo> list = new ArrayList<Demo>();
		try
		{
			String result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			JSONArray arr = new JSONArray(result);
			for(int i=0; i<arr.length(); i++)
			{
				JSONObject o = arr.getJSONObject(i);
				Demo Demo = new Demo(o.getLong("id"), o.getString("title"), o.getString("content"), o.getString("foundtime"));
				list.add(Demo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	//明细
	public Demo getById(Long id) 
	{
		Map m = new HashMap();
		m.put("keyIndex", id);
		HttpPostObj postObj = new HttpPostObj(context.getString(R.string.projUrl)+context.getString(R.string.moduleUrl)+"getDemoByIdForMobile.htm", m);
		String result = "";
		Demo po = null;
		try{
			Log.i("path", postObj.getUrl());
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			JSONObject jsonObject = new JSONObject(result);
			po = new Demo(jsonObject.getLong("id"),jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("foundtime"));
			Log.i("result", result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		return po;
	}
}
