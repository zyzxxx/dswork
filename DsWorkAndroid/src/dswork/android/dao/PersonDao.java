package dswork.android.dao;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dswork.android.db.BaseDao;
import dswork.android.model.Person;

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
}
