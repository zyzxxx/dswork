package dswork.android.demo.component.downloadlist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

import dswork.android.demo.component.downloadlist.model.ThreadInfo;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.QueryParams;

/**
 * Created by ole on 15/5/8.
 */
public class ThreadInfoDao extends BaseDao<ThreadInfo, Long>
{
    //注入DBHelper
    private MyDBHelper helper;
    public ThreadInfoDao(Context context)
    {
        this.helper = new MyDBHelper(context);
    }

    @Override
    public SQLiteOpenHelper getDBHelper()
    {
        return helper.getDBHelper();
    }

    @Override
    public QueryParams getQueryParams(Map map)
    {
        QueryParams params = new QueryParams();
        if(map != null)
        {
            Object tmp;
            tmp = map.get("id");
            if(tmp != null && String.valueOf(tmp).trim().length()>0)
            {
                params.addSelection("and", "id", "=");
                params.addSelectionArgs(String.valueOf(tmp).trim());
            }
            tmp = map.get("thread_id");
            if(tmp != null && String.valueOf(tmp).trim().length()>0)
            {
                params.addSelection("and", "thread_id", "=");
                params.addSelectionArgs(String.valueOf(tmp).trim());
            }
            tmp = map.get("url");
            if(tmp != null && String.valueOf(tmp).trim().length()>0)
            {
                params.addSelection("and", "url", "=");
                params.addSelectionArgs(String.valueOf(tmp).trim());
            }
        }
        return params;
    }
}
