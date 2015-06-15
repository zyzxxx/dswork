package com.paper.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paper.R;
import com.paper.model.PaperImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.lib.core.controller.BaseWebController;
import dswork.android.lib.core.util.webutil.HttpResultObj;
 
public class PaperImageController extends BaseWebController
{
	private Context ctx;
	
	public PaperImageController(Context ctx) 
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public String getModulePath() 
	{
		return ctx.getString(R.string.app_path);
	}

	public Map getPaperImagePage(Map<String,String> params) 
	{
		HttpResultObj<Map> o = submitHttpAction("client/paperimage/getPaperImagePage.action", Map.class, params, 5000, 5000, "POST");
		Map<String,Object> result = null;
		if(o.isSuc())
		{
			String _json = String.valueOf(o.getData().get("json"));
			int _page = Integer.valueOf(String.valueOf(o.getData().get("page")));
			Gson gson = new Gson();
			result = new HashMap<String,Object>();
			result.put("list", (List<PaperImage>)gson.fromJson(_json, new TypeToken<List<PaperImage>>(){}.getType()));
			result.put("page", _page);
		}
		return result;
	}

	public List getPaperImagesByPid(Map<String, String> params)
	{
		HttpResultObj<Map> o = submitHttpAction("client/paperimage/getPaperImagesByPid.action", Map.class, params, 5000, 5000, "POST");
		List result = null;
		if(o.isSuc())
		{
			String _json = String.valueOf(o.getData().get("json"));
			Gson gson = new Gson();
			result =  (List<PaperImage>)gson.fromJson(_json, new TypeToken<List<PaperImage>>(){}.getType());
		}
		return result;
	}

}
