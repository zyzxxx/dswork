package com.paper.dao;

import com.paper.R;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.lib.core.db.BaseDBHelper;
import dswork.android.lib.core.db.DBOpenHelper;

public class MyDBHelper extends BaseDBHelper
{
	private Context ctx;

	public MyDBHelper(Context ctx)
	{
		this.ctx = ctx;
	}

	@Override
	protected SQLiteOpenHelper initDBHelper()
	{
		return new DBOpenHelper(ctx, "paper.db", null, 1, 
			ctx.getResources().getStringArray(R.array.createTableSql), 
			ctx.getResources().getStringArray(R.array.updateTableSql));
	}
}
