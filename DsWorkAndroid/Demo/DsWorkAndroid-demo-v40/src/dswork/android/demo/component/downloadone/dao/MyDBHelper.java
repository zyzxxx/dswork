package dswork.android.demo.component.downloadone.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import dswork.android.R;
import dswork.android.lib.core.db.BaseDBHelper;
import dswork.android.lib.core.db.DBOpenHelper;

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
		return new DBOpenHelper(context, "downloadone.db", null, 1,
			context.getResources().getStringArray(R.array.downloadone_createTableSql),
			context.getResources().getStringArray(R.array.downloadone_upgradeTableSql));
	}
}
