package dswork.android.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.R;
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
		return new DBOpenHelper(context, "demo.db", null, 
				1, 
				context.getResources().getStringArray(R.array.createTableSql), 
				context.getResources().getStringArray(R.array.updateTableSql));
	}

}
