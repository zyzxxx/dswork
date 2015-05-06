package com.paper.service;

import com.paper.dao.PaperNavDao;
import com.paper.model.PaperNav;
import android.content.Context;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.BaseService;
import dswork.android.lib.core.util.webutil.HttpResultObj;

public class PaperNavService extends BaseService<PaperNav, Long>
{
	//注入dao
	private PaperNavDao dao;
	public PaperNavService(Context context) 
	{
		this.dao = new PaperNavDao(context);
	}

	@Override
	public BaseDao getEntityDao() {
		return dao;
	}
	
//	public List<PaperMenu> query()
//	{
//		Cursor c = dao.queryCursor("paper_menu", null, null, null, null, null, null);
//		List<PaperMenu> list=new ArrayList<PaperMenu>();
//		while(c.moveToNext())
//		{
//			PaperMenu p = new PaperMenu();
//			p.setId(c.getLong(c.getColumnIndex("id")));
//			p.setJson(c.getString(c.getColumnIndex("json")));
//			list.add(p);
//		}
//		c.close();
//		return list;
//	}
	
	public HttpResultObj<String> getNavJson(HttpResultObj<String> r)
	{
		if(!r.isSuc())
		{
			if(dao.getCount("paper_nav")>0)
			{
				r.setData(dao.query().get(0).getJson());
				r.setSuc(true);
			}
		}
		else
		{
			if(dao.getCount("paper_nav")>0)
			{
				dao.delete("paper_nav", dao.query().get(0).getId());
			}
			PaperNav o = new PaperNav();
			o.setJson(r.getData());
			dao.add("paper_nav", o);
		}
		return r;
	}
}
