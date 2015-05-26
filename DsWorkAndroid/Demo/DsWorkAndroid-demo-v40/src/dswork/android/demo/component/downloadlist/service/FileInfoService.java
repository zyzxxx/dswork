package dswork.android.demo.component.downloadlist.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.demo.component.downloadlist.dao.FileInfoDao;
import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.BaseService;
import dswork.android.lib.core.db.QueryParams;

/**
 * Created by ole on 15/5/8.
 */
public class FileInfoService extends BaseService<FileInfo, Long>
{
    //注入dao
    private FileInfoDao dao;
    public FileInfoService(Context context)
    {
        this.dao = new FileInfoDao(context);
    }

    @Override
    public BaseDao getEntityDao() {
        return dao;
    }

    public synchronized void deleteByUrl(String url)
    {
        getEntityDao().beginTransaction();
        try
        {
            dao.delete("file_info", "url=?", new String[]{url});
            getEntityDao().setTransactionSuccessful();
        }
        finally
        {
            getEntityDao().endTransaction();
        }
        getEntityDao().close();
    }

    public synchronized void updateProgress(FileInfo po)
    {
        getEntityDao().beginTransaction();
        try
        {
            ContentValues cv= new ContentValues();
            cv.put("length", po.getLength());
            cv.put("finished", po.getFinished());
            cv.put("progress", po.getProgress());
            cv.put("status", po.getStatus());
            dao.update("file_info", cv, "file_id=? and url=?", new String[]{String.valueOf(po.getFile_id()), po.getUrl()});
            getEntityDao().setTransactionSuccessful();
        }
        finally
        {
            getEntityDao().endTransaction();
        }
        getEntityDao().close();
    }

    public synchronized boolean isExists(Map<String, Object> m)
    {
        QueryParams p = dao.getQueryParams(m);
        Cursor c = dao.queryCursor("file_info", null, p.getSelection(), p.getSelectionArgs(), null, null, null);
        if(c.getCount()>0)
            return true;
        else
            return false;
    }

    public List<FileInfo> query(Map<String, Object> m)
    {
        QueryParams p = dao.getQueryParams(m);
        Cursor c = dao.queryCursor("file_info", null, p.getSelection(), p.getSelectionArgs(), null, null, null);
        List<FileInfo> list = new ArrayList<FileInfo>();
        while(c.moveToNext())
        {
            FileInfo po = new FileInfo();
            po.setFile_id(c.getInt(c.getColumnIndex("file_id")));
            po.setUrl(c.getString(c.getColumnIndex("url")));
            po.setFilename(c.getString(c.getColumnIndex("filename")));
            po.setLength(c.getInt(c.getColumnIndex("length")));
            po.setFinished(c.getInt(c.getColumnIndex("finished")));
            po.setProgress(c.getInt(c.getColumnIndex("progress")));
            po.setStatus(c.getString(c.getColumnIndex("status")));
            list.add(po);
        }
        c.close();
        return list;
    }

    public List<FileInfo> refreshList(List<FileInfo> mList)
    {
        getEntityDao().beginTransaction();
        try
        {
            for(FileInfo po : mList)
            {
                //数据库有则获取，无则添加
                Map m = new HashMap();
                m.put("file_id", po.getFile_id());
                m.put("url", po.getUrl());
                if(this.isExists(m))
                {
                    po = (FileInfo)this.query(m).get(0);
                    mList.set(po.getFile_id(), po);
                }
                else
                {
                    dao.add("file_info", po);
                }
            }
            getEntityDao().setTransactionSuccessful();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            getEntityDao().endTransaction();
        }
        getEntityDao().close();
        return mList;
    }

    /**
     * 将所有文件下载状态'start'设为'stop'
     */
    public synchronized void updateAllStartToStop()
    {
        getEntityDao().beginTransaction();
        try
        {
            dao.updateAllStartToStop();
            getEntityDao().setTransactionSuccessful();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            getEntityDao().endTransaction();
        }
        getEntityDao().close();
    }
}
