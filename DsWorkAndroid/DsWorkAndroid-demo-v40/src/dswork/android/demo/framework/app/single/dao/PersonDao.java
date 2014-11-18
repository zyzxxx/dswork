package dswork.android.demo.framework.app.single.dao;


import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.demo.framework.app.single.model.Person;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.QueryParams;

public class PersonDao extends BaseDao<Person, Long>
{
	//注入DBHelper
	private MyDBHelper helper;
	public PersonDao(Context context)
	{
		this.helper = new MyDBHelper(context);
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
			tmp = map.get("name");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				params.addSelection("and", "name", "like");
				params.addSelectionArgs("%"+String.valueOf(tmp).trim()+"%");
			}
			tmp = map.get("phone");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				params.addSelection("and", "phone", "like");
				params.addSelectionArgs("%"+String.valueOf(tmp).trim()+"%");
			}
			tmp = map.get("amount");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				params.addSelection("and", "amount", "=");
				params.addSelectionArgs(String.valueOf(tmp).trim());
			}
		}
		return params;
	}
}
