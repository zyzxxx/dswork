package dswork.android.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import dswork.android.R;
import dswork.android.lib.controller.BaseController;
import dswork.android.lib.util.webutil.HttpPostObj;
import dswork.android.lib.util.webutil.HttpUtil;
import dswork.android.model.Demo;

public class DemoController implements BaseController<Demo>
{
	private Context ctx;
	
	public DemoController(Context ctx) 
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public String add(Demo po) 
	{
		String action = "addDemo2.htm";
		Map m = new HashMap();
		m.put("title", po.getTitle());
		m.put("content", po.getContent());
		m.put("foundtime", po.getFoundtime());
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
		String result = "";
		try
		{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String upd(Demo po, Long id) 
	{
		String action = "updDemoForMobile.htm";		
		Map m = new HashMap();
		m.put("id", id);
		m.put("title", po.getTitle());
		m.put("content", po.getContent());
		m.put("foundtime", po.getFoundtime());
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
		String result = "";
		try
		{
			result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Demo> get(Map m)
	{
		String action = "getDemoForMobile.htm";
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
		List<Demo> list = new ArrayList<Demo>();
		try
		{
			String result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			if(!result.equals("error"))
			{
				JSONArray arr = new JSONArray(result);
				for(int i=0; i<arr.length(); i++)
				{
					JSONObject o = arr.getJSONObject(i);
					Demo Demo = new Demo(o.getLong("id"), o.getString("title"), o.getString("content"), o.getString("foundtime"));
					list.add(Demo);
				}
			}
			else
			{
				list = null;
			}
		}
		catch(Exception e)
		{
			list = null;
			e.printStackTrace();
			Log.i("controller Exception",e.getMessage());
		}
		return list;
	}

	@Override
	public Demo getById(Long id)
	{
		String action = "getDemoByIdForMobile.htm";
		Map m = new HashMap();
		m.put("keyIndex", id);
		HttpPostObj postObj = new HttpPostObj(ctx.getString(R.string.projUrl) + ctx.getString(R.string.moduleUrl) + action, m);
		Demo po = new Demo();
		try
		{
			String result = HttpUtil.sendHttpPost(postObj);//发送HttpPost请求
			if(!result.equals("error"))
			{
				JSONObject jsonObject = new JSONObject(result);
				po = new Demo(jsonObject.getLong("id"),jsonObject.getString("title"),jsonObject.getString("content"),jsonObject.getString("foundtime"));
			}
			else
			{
				po = null;
			}
		}
		catch(Exception e)
		{
			po = null;
			e.printStackTrace();
			Log.i("controller Exception",e.getMessage());
		}
		return po;
	}
}
