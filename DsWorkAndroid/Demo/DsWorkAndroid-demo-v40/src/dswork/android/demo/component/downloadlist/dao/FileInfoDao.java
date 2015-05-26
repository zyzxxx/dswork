package dswork.android.demo.component.downloadlist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.lib.core.db.BaseDao;
import dswork.android.lib.core.db.QueryParams;

/**
 * Created by ole on 15/5/8.
 */
public class FileInfoDao extends BaseDao<FileInfo, Long>
{
    //注入DBHelper
    private MyDBHelper helper;
    public FileInfoDao(Context context)
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
            tmp = map.get("file_id");
            if(tmp != null && String.valueOf(tmp).trim().length()>0)
            {
                params.addSelection("and", "file_id", "=");
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

    /**
     * 将所有文件下载状态'start'设为'stop'
     */
    public void updateAllStartToStop()
    {
        this.update("update file_info set status = ? where status = ?", "stop", "start");
    }
}
