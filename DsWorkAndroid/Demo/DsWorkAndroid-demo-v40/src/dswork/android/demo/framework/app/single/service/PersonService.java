package dswork.android.demo.framework.app.single.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import dswork.android.demo.framework.app.single.dao.PersonDao;
import dswork.android.demo.framework.app.single.model.Person;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.BaseService;
import dswork.android.lib.core.db.QueryParams;

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
		QueryParams p = dao.getQueryParams(m);
		Cursor c = dao.queryCursor("person", null, p.getSelection(), p.getSelectionArgs(), null, null, null);
		List<Person> persons=new ArrayList<Person>();
		while(c.moveToNext())
		{
			Person po = new Person();
			po.setId(c.getLong(c.getColumnIndex("id")));
			po.setName(c.getString(c.getColumnIndex("name")));
			po.setPhone(c.getString(c.getColumnIndex("phone")));
			po.setAmount(c.getString(c.getColumnIndex("amount")));
			po.setSortkey(c.getString(c.getColumnIndex("sortkey")));
			persons.add(po);
		}
		c.close();
		return persons;
	}
	
	public List<Person> queryPage(Map m, int offset, int maxResult)
	{
		List<Person> persons=new ArrayList<Person>();
		Cursor c = dao.queryPage(new Person(), m, null, null, null, offset, maxResult);
		while(c.moveToNext())
		{
			Person p = new Person();
			System.out.println("Long id:"+c.getLong(c.getColumnIndex("id")));
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
