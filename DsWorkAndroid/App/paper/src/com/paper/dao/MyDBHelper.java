package com.paper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.paper.R;

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
		return new DBOpenHelper(ctx, "paper.db", null, 2,
			ctx.getResources().getStringArray(R.array.createTableSql), 
			ctx.getResources().getStringArray(R.array.updateTableSql));
	}
}
