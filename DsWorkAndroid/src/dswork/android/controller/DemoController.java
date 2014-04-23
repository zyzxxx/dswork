package dswork.android.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import dswork.android.R;
import dswork.android.model.Demo;
import dswork.android.util.webutil.HttpPostObj;
import dswork.android.util.webutil.HttpUtil;


public class DemoController
{
	private Context ctx;
	
	public DemoController(Context ctx) 
	{
		super();
		this.ctx = ctx;
	}

	//添加
	public String add(Map m) 
	{
		String action = "addDemo2.htm";
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
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
	public String deleteBatch(String ids)
	{
		String action = "delJSONDemo.htm";
		Map m = new HashMap();
		m.put("keyIndex", ids);
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
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
		String action = "updBatchForMobile.htm";
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
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
		String action = "getDemoForMobile.htm";
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
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
		String action = "getDemoByIdForMobile.htm";
		Map m = new HashMap();
		m.put("keyIndex", id);
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
		String result = "";
		Demo po = null;
		try{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			JSONObject jsonObject = new JSONObject(result);
			po = new Demo(jsonObject.getLong("id"),jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("foundtime"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = e.getMessage();
		}
		return po;
	}
}
