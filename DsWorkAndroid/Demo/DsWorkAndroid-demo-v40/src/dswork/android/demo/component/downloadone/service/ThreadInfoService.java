package dswork.android.demo.component.downloadone.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dswork.android.demo.component.downloadone.dao.ThreadInfoDao;
import dswork.android.demo.component.downloadone.model.ThreadInfo;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.BaseService;
import dswork.android.lib.core.db.QueryParams;

/**
 * Created by ole on 15/5/8.
 */
public class ThreadInfoService extends BaseService<ThreadInfo, Long>
{
    //注入dao
    private ThreadInfoDao dao;
    public ThreadInfoService(Context context)
    {
        this.dao = new ThreadInfoDao(context);
    }

    @Override
    public BaseDao getEntityDao() {
        return dao;
    }

    public void deleteByIdAndUrl(long thread_id, String url)
    {
        dao.delete("thread_info", "id=? and url=?", new String[]{String.valueOf(thread_id), url});
    }

    public void updateFinished(ThreadInfo po)
    {
        getEntityDao().beginTransaction();
        try
        {
            ContentValues cv= new ContentValues();
            cv.put("finished", po.getFinished());
            dao.update("thread_info", cv, "id=? and url=?", new String[]{String.valueOf(po.getId()), po.getUrl()});
            getEntityDao().setTransactionSuccessful();
        }
        finally
        {
            getEntityDao().endTransaction();
        }
        getEntityDao().close();
    }

    public boolean isExists(Map<String, Object> m)
    {
        QueryParams p = dao.getQueryParams(m);
        Cursor c = dao.queryCursor("thread_info", null, p.getSelection(), p.getSelectionArgs(), null, null, null);
        if(c.getCount()>0)
            return true;
        else
            return false;
    }

    public List<ThreadInfo> query(Map<String, Object> m)
    {
        QueryParams p = dao.getQueryParams(m);
        Cursor c = dao.queryCursor("thread_info", null, p.getSelection(), p.getSelectionArgs(), null, null, null);
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        while(c.moveToNext())
        {
            ThreadInfo po = new ThreadInfo();
            po.setId(c.getLong(c.getColumnIndex("id")));
            po.setUrl(c.getString(c.getColumnIndex("url")));
            po.setStart(c.getInt(c.getColumnIndex("start")));
            po.setEnd(c.getInt(c.getColumnIndex("end")));
            po.setFinished(c.getInt(c.getColumnIndex("finished")));
            list.add(po);
        }
        c.close();
        return list;
    }
}
