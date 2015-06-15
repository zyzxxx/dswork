package com.paper.controller;

import android.content.Context;

import com.paper.R;
import com.paper.service.PaperNavService;

import java.util.HashMap;
import java.util.Map;

import dswork.android.lib.core.controller.BaseWebController;
import dswork.android.lib.core.util.webutil.HttpResultObj;

public class PaperNavController extends BaseWebController
{
	private Context ctx;
	private PaperNavService service;//注入service
	
	public PaperNavController(Context ctx) 
	{
		super();
		this.ctx = ctx;
		this.service = new PaperNavService(ctx);
	}

	@Override
	public String getModulePath() 
	{
		return ctx.getString(R.string.app_path);
	}

	public HttpResultObj<String> getNavJson() 
	{
		Map<String,String> m = new HashMap<String,String>();
		HttpResultObj<String> r = submitHttpAction("client/papernav/paper-nav.action", String.class, m, 5000, 5000, "POST");
		r = service.getNavJson(r);
		return r;
	}
}
