package dswork.android.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import dswork.android.dao.PersonDao;
import dswork.android.db.BaseDao;
import dswork.android.db.BaseService;
import dswork.android.model.Person;

public class PersonService extends BaseService<Person, Long>
{
	//注入dao
	private PersonDao dao;
	public PersonService(Context context) 
	{
		this.dao = new PersonDao(context);
	}

	@Override
	public BaseDao getEntityDao() {
		return dao;
	}
	
	public List<Person> query()
	{
		Cursor c = dao.queryCursor("person", null, null, null, null, null, null);
		List<Person> persons=new ArrayList<Person>();
		while(c.moveToNext())
		{
			Person p = new Person();
			p.setId(c.getLong(c.getColumnIndex("id")));
			p.setName(c.getString(c.getColumnIndex("name")));
			p.setPhone(c.getString(c.getColumnIndex("phone")));
			p.setAmount(c.getString(c.getColumnIndex("amount")));
			p.setSortkey(c.getString(c.getColumnIndex("sortkey")));
			persons.add(p);
		}
		c.close();
		return persons;
	}
	
	public List<Person> query(Map<String, Object> m)
	{
		String selection = "";
		String[] selectionArgs = {};
		List<String> values = new ArrayList<String>();
		if(m.size()>0)
		{
			for (Map.Entry<String, Object> e : m.entrySet())
			{
				selection += (e.getKey()) + "=?" + " and ";
				values.add(String.valueOf(e.getValue()));
			}
			selection = selection.substring(0, selection.length()-4);
			Log.i("selection",selection);
			Log.i("selection.length",selection.length()+"");
			selectionArgs = values.toArray(selectionArgs);
		}
		for(String v:selectionArgs)
		{
			Log.i("v",v);
		}
		Cursor c = dao.queryCursor("person", null, selection, selectionArgs, null, null, null);
		
		List<Person> persons=new ArrayList<Person>();
		while(c.moveToNext())
		{
			Person p = new Person();
			p.setId(c.getLong(c.getColumnIndex("id")));
			p.setName(c.getString(c.getColumnIndex("name")));
			p.setPhone(c.getString(c.getColumnIndex("phone")));
			p.setAmount(c.getString(c.getColumnIndex("amount")));
			p.setSortkey(c.getString(c.getColumnIndex("sortkey")));
			persons.add(p);
		}
		c.close();
		return persons;
	}
	
	//分页加载
	public List<Person> queryScroll(int offset, int maxResult)
	{
		List<Person> persons=new ArrayList<Person>();
		Cursor c = dao.queryCursorScrollData(new Person(), "", offset, maxResult);
		while(c.moveToNext())
		{
			Person p = new Person();
			p.setId(c.getLong(c.getColumnIndex("_id")));
			p.setName(c.getString(c.getColumnIndex("name")));
			p.setPhone(c.getString(c.getColumnIndex("phone")));
			p.setAmount(c.getString(c.getColumnIndex("amount")));
			p.setSortkey(c.getString(c.getColumnIndex("sortkey")));
			persons.add(p);
		}
		c.close();
		return persons;
	}
	
	public Person getById(Long id)
	{
		Person p = new Person();
		Cursor c = dao.queryCursor("person", null, "id=?", new String[]{id.toString()}, null, null, null);
		if(c.moveToFirst())
		{
			p.setId(c.getLong(c.getColumnIndex("id")));
			p.setName(c.getString(c.getColumnIndex("name")));
			p.setPhone(c.getString(c.getColumnIndex("phone")));
			p.setAmount(c.getString(c.getColumnIndex("amount")));
			p.setSortkey(c.getString(c.getColumnIndex("sortkey")));
		}
		c.close();
		return p;
	}
}
