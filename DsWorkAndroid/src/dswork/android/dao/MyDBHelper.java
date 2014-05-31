package dswork.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.lib.db.BaseDBHelper;
import dswork.android.lib.db.DBOpenHelper;

public class MyDBHelper extends BaseDBHelper
{
	private Context context;
	public MyDBHelper(Context context)
	{
		this.context = context;
	}
	
	@Override
	protected SQLiteOpenHelper initDBHelper()
	{
		return new DBOpenHelper(context, "demo.db", null, 3, 
				new String[]{"CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(2),phone VARCHAR(12) NULL, amount VARCHAR(20) NULL)"}, 
				new String[]{"ALTER TABLE person ADD sortkey VARCHAR"});
	}

}
