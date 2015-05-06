package com.paper.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.paper.model.PaperNav;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.QueryParams;

public class PaperNavDao extends BaseDao<PaperNav, Long>
{
	//注入DBHelper
	private MyDBHelper helper;
	public PaperNavDao(Context ctx)
	{
		this.helper = new MyDBHelper(ctx);
	}

	@Override
	public SQLiteOpenHelper getDBHelper() 
	{
		return helper.getDBHelper();
	}

	@Override
	public QueryParams getQueryParams(Map map) 
	{
		QueryParams params = new QueryParams();
		if(map != null)
		{
			Object tmp;
			tmp = map.get("id");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				params.addSelection("and", "id", "=");
				params.addSelectionArgs(String.valueOf(tmp).trim());
			}
			tmp = map.get("json");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				params.addSelection("and", "json", "like");
				params.addSelectionArgs("%"+String.valueOf(tmp).trim()+"%");
			}
		}
		return params;
	}
	
	public List<PaperNav> query()
	{
		Cursor c = this.queryCursor("paper_nav", null, null, null, null, null, null);
		List<PaperNav> list=new ArrayList<PaperNav>();
		while(c.moveToNext())
		{
			PaperNav p = new PaperNav();
			p.setId(c.getLong(c.getColumnIndex("id")));
			p.setJson(c.getString(c.getColumnIndex("json")));
			list.add(p);
		}
		c.close();
		return list;
	}
}
