package dswork.android.demo.framework.app.greendao.dao;

import java.util.Map;

import android.content.Context;
import de.greenrobot.dao.query.QueryBuilder;
import dswork.android.demo.framework.app.greendao.dao.DaoMaster.OpenHelper;
import dswork.android.demo.framework.app.greendao.dao.PersonDao.Properties;

/**
 * Dao工具类（greendao生成代码后，为简化页面调用dao，每次必须自定义）
 * @author ole
 *
 */
public class CustomDaoUtil
{
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	
	/**
	 * 取得DaoMaster
	 *
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context)
	{
	    if (daoMaster == null)
	    {
	        OpenHelper helper = new DaoMaster.DevOpenHelper(context, "persons-db", null);
	        daoMaster = new DaoMaster(helper.getWritableDatabase());
	    }
	    return daoMaster;
	}
	/**
	 * 取得DaoSession
	 *
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context)
	{
	    if (daoSession == null)
	    {
	        if (daoMaster == null)
	        {
	            daoMaster = getDaoMaster(context);
	        }
	        daoSession = daoMaster.newSession();
	    }
	    return daoSession;
	}
	/**
	 * 取得Dao
	 *
	 * @param context
	 * @return
	 */
	public static PersonDao getDao(Context context)
	{
		QueryBuilder.LOG_SQL = true;
		QueryBuilder.LOG_VALUES = true;
		return getDaoSession(context).getPersonDao();
	}
	/**
	 * 取得包含查询调价的QueryBuilder
	 * @param context
	 * @param map
	 * @return
	 */
	public static QueryBuilder<Person> getQueryBuilder(Context context, Map map)
	{
		QueryBuilder<Person> qb = getDao(context).queryBuilder();
		if(map != null)
		{
			Object tmp;
			tmp = map.get("name");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				qb = qb.where(Properties.Name.like(String.valueOf(map.get("name"))));
			}
			tmp = map.get("phone");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				qb = qb.where(Properties.Phone.like(String.valueOf(map.get("phone"))));
			}
			tmp = map.get("amount");
			if(tmp != null && String.valueOf(tmp).trim().length()>0)
			{
				qb = qb.where(Properties.Amount.eq(String.valueOf(map.get("amount"))));
			}
		}
		return qb;
	}
}
