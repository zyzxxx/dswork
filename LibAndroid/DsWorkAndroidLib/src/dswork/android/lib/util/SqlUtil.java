package dswork.android.lib.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.util.Log;

public class SqlUtil 
{
	/**
	 * 根据对象名获取表名
	 * @param o model对象
	 * @return
	 */
	public static String getTableNameByModel(Object o)
	{
		Log.i("表名",o.getClass().getSimpleName());
		return o.getClass().getSimpleName();
	}
	
	/**
	 * 获取列名
	 * @param o model对象
	 * @param isNeedId 是否需要主键Id
	 * @return 字符串（如："id,name,age"）
	 */
	public static String getColumnsStr(Object o, boolean isNeedId)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		String columns = "";
		if(isNeedId)
		{
			columns += "id as _id,";
		}
		for(int i=0;i<fields.length;i++)
		{
			columns += fields[i].getName();
			if(i+1<fields.length) columns += ",";
		}
		Log.i("列名串",columns);
		return columns;
	}

	/**
	 * 获取列名
	 * @param o model对象
	 * @param isNeedId 是否需要主键Id
	 * @return 字符串数组对象（如：new String[]{"id","name","age"}）
	 */
	public static String[] getColumnsArr(Object o,boolean isNeedId)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		//将列名放到list中
		List<String> columnList = new ArrayList<String>();
		if(isNeedId)
		{
			columnList.add("id");
		}
		for(int i=0;i<fields.length;i++)
		{
			columnList.add(fields[i].getName());
		}
		//list转换成string[]
		String[] columns = new String[columnList.size()];
		for(int i=0;i<columnList.size();i++)
		{
			columns[i] = columnList.get(i);
			Log.i("列名"+i,columns[i]);
		}
		return columns;
	}
	
	//获取?号串，以逗号分割
	public static String getQuesMarks(Object o)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		String quesmarks = "";
		for(int i=1;i<fields.length;i++)
		{
			quesmarks += "?";
			if(i+1<fields.length) quesmarks += ",";
		}
		Log.i("问号串",quesmarks);
		return quesmarks;
	}
	
	/**
	 * 获取列值
	 * @param o model对象
	 * @return ContentValues
	 */
	public static ContentValues getValues(Object o)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		ContentValues v =  new ContentValues();
		for(int i=0;i<fields.length;i++)
		{
			if(!fields[i].getName().equals("id")) 
			{
				try
				{
					String _key = fields[i].getName();
					Method m = o.getClass().getMethod("get"+fields[i].getName().substring(0,1).toUpperCase()+fields[i].getName().substring(1));
					String _value = String.valueOf(m.invoke(o));
					v.put(_key, _value);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return v;
	}
	
	/**
	 * 根据属性名获取属性值
	 * @param o Model
	 * @param name 属性名
	 * @return String
	 */
	public static String getValueByName(Object o, String name)
	{
		String v = "";
		try 
		{
			Method m = o.getClass().getMethod("get"+name.substring(0,1).toUpperCase()+name.substring(1));
			v = String.valueOf(m.invoke(o));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return v;
	}
}
