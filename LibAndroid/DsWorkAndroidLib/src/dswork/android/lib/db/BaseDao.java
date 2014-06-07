package dswork.android.lib.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import dswork.android.lib.util.SqlUtil;

public abstract class BaseDao<T, PK extends Serializable>
{
	
	public abstract SQLiteOpenHelper getDBHelper();
	public abstract QueryParams getQueryParams(Map m);
	
	/*获取CRUD的操作对象SQLiteDatabase*/
	public SQLiteDatabase getWritableDb() 
	{
		return getDBHelper().getWritableDatabase();
	}
	public SQLiteDatabase getReadableDb() 
	{
		return getDBHelper().getReadableDatabase();
	}
	/*事务操作方法*/
	/**
	 * 开始事务
	 */
	public void beginTransaction()
	{
		getWritableDb().beginTransaction();
	}
	/**
	 * 调用此方法会在执行到endTransaction() 时提交当前事务，如果不调用此方法会回滚事务	
	 */
	public void setTransactionSuccessful()
	{
		getWritableDb().setTransactionSuccessful();
	}
	/**
	 * 由事务的标志决定是提交事务，还是回滚事务
	 */
	public void endTransaction()
	{
		getWritableDb().endTransaction();
	}
	public void close()
	{
		getDBHelper().close();
	}
	/*基本CRUD操作*/
	/**
	 * 新增记录
	 * @param o model对象
	 */
	public void add(T o)
	{
		getWritableDb().insert(SqlUtil.getTableNameByModel(o), null, SqlUtil.getValues(o));
	}
	/**
	 * 新增记录
	 * @param sql sql语句
	 * @param values insert语句values值数组
	 */
	public void add(String sql, Object...values)
	{
		getWritableDb().execSQL(sql,values);
	}
	/**
	 * 删除记录
	 * @param table 表名
	 * @param primaryKey 主键值
	 */
	public void delete(String table, PK primaryKey)
	{
		getWritableDb().delete(table, "id=?", new String[]{primaryKey.toString()});
	}
	/**
	 * 删除记录(批量)
	 * @param table 表名
	 * @param pks 主键值集合
	 */
	public void deleteBatch(String table, List<PK> pks)
	{
		for(int i=0;i<pks.size();i++)
		{
			this.delete(table, pks.get(i));
		}
	}
	/**
	 * 删除记录(批量)
	 * @param table 表名
	 * @param pks 主键值集合
	 */
	public void deleteBatch(String table, String pks)
	{
		String[] _pks_s = pks.split(",");
		PK[] _pks_l = (PK[])ConvertUtils.convert(_pks_s, Long.class);
		for(int i=0; i<_pks_l.length; i++)
		{
			this.delete(table, _pks_l[i]);
		}
	}
	/**
	 * 修改记录
	 * @param table 表名
	 * @param values 要修改的列的键值对
	 * @param whereClause where条件(如："id=? and name=?")
	 * @param whereArgs where条件参数数组，数组长度需跟where条件的?号个数对应（如：new String[]{"001","张三"}）
	 */
	public void update(String table, ContentValues values, String whereClause, String[] whereArgs)
	{
		getWritableDb().update(table, values, whereClause, whereArgs);
	}
	/**
	 * 修改记录
	 * @param o Model实例
	 * @param id 主键
	 * @param fieldNames 需要修改的属性名
	 */
	public void update(T o, Long id, String[] fieldNames)
	{
		ContentValues cv= new ContentValues();
		for(String n:fieldNames)
		{
			cv.put(n, SqlUtil.getValueByName(o, n));
		}
		getWritableDb().update(SqlUtil.getTableNameByModel(o), cv, "id=?", new String[]{id.toString()});
	}
	/**
	 * 修改记录(批量)
	 * @param o Model实例
	 * @param ids 主键值数组
	 * @param fieldNames 需要修改的属性名
	 */
	public void update(T o, long[] ids, String[] fieldNames)
	{
		ContentValues cv= new ContentValues();
		for(String n:fieldNames)
		{
			cv.put(n, SqlUtil.getValueByName(o, n));
		}
		for(Long id:ids)
		{
			getWritableDb().update(SqlUtil.getTableNameByModel(o), cv, "id=?", new String[]{id.toString()});
		}
	}
	/**
	 * 修改记录
	 * @param o Model实例
	 * @param ids 主键值集合字符串,以逗号隔开
	 * @param fieldNames 需要修改的属性名
	 */
	public void update(T o, String ids, String[] fieldNames)
	{
		ContentValues cv= new ContentValues();
		for(String n:fieldNames)
		{
			cv.put(n, SqlUtil.getValueByName(o, n));
		}
		String[] _ids = ids.split(",");
		for(String id:_ids)
		{
			getWritableDb().update(SqlUtil.getTableNameByModel(o), cv, "id=?", new String[]{id.toString()});
		}
	}
	/**
	 * 修改记录
	 * @param o Model实例
	 * @param id 主键
	 */
	public void update(T o, Long id)
	{
		getWritableDb().update(SqlUtil.getTableNameByModel(o), SqlUtil.getValues(o), "id=?", new String[]{id.toString()});
	}
	/**
	 * 修改记录（批量）
	 * @param o Model实例
	 * @param ids 主键值数组
	 */
	public void update(T o, long[] ids)
	{
		for(Long id:ids)
		{
			getWritableDb().update(SqlUtil.getTableNameByModel(o), SqlUtil.getValues(o), "id=?", new String[]{id.toString()});
		}
	}
	/**
	 * 修改记录
	 * @param o Model实例
	 * @param ids 主键值集合字符串，以逗号隔开
	 */
	public void update(T o, String ids)
	{
		String[] _ids = ids.split(",");
		for(String id:_ids)
		{
			getWritableDb().update(SqlUtil.getTableNameByModel(o), SqlUtil.getValues(o), "id=?", new String[]{id});
		}
	}
	/**
	 * 修改记录
	 * @param sql SQL的update语句
	 * @param values update语句对应的问号值
	 */
	public void update(String sql, Object...values)
	{
		getWritableDb().execSQL(sql,values);
	}
	
	/**
	 * 查询
	 * @param table 表名
	 * @param columns 列名数组
	 * @param selection where条件(如："id=? and name=?")
	 * @param selectionArgs where条件参数数组，数组长度需跟where条件的?号个数对应（如：new String[]{"001","张三"}）
	 * @param groupBy 分组
	 * @param having 分组条件
	 * @param orderBy 排序类
	 * @return
	 */
	public Cursor queryCursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
	{
		Log.i("sql's selection",selection);
		for(String args:selectionArgs)
		{
			Log.i("sql's selectionArgs",args);
		}
		return getReadableDb().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	/**
	 * 分页查询
	 * @param o model对象
	 * @param orderBy 排序
	 * @param offet 记录索引
	 * @param maxResult 需要显示的记录数
	 * @return
	 */
	public Cursor queryCursorScrollData(T o, String orderBy, int offset, int maxResult)
	{
		return getReadableDb().rawQuery(
				"select "+SqlUtil.getColumnsStr(o, true)+" from "+SqlUtil.getTableNameByModel(o)+" "+orderBy+" limit ?,?", 
				new String[]{String.valueOf(offset),String.valueOf(maxResult)});
	}
	
	/**
	 * 获取记录数
	 * @param sql sql count语句
	 * @param values where条件参数数组，数组长度需跟where条件的?号个数对应（如：new String[]{"001","张三"}）
	 * @return
	 */
	public int getCount(String table)
	{
//		Cursor cursor = getReadableDb().rawQuery(sql, (String[]) values);
		Cursor cursor = getReadableDb().rawQuery("select count(1) from "+table, null);
		cursor.moveToFirst();
		int count=cursor.getInt(0);
		return count;
	}
}
