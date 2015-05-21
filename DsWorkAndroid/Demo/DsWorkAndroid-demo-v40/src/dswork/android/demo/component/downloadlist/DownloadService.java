package dswork.android.demo.component.downloadlist;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.lib.core.util.webutil.HttpActionObj;
import dswork.android.lib.core.util.webutil.HttpResultObj;
import dswork.android.lib.core.util.webutil.HttpUtil;

/**
 * Created by ole on 15/5/8.
 */
public class DownloadService extends Service
{
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/downloads/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    public static final String ACTION_ERROR = "ACTION_ERROR";
    public static final int MSG_INIT = 0;
    public static final int MSG_ERROR = -1;
    DownloadTask mDownloadTask = null;
    //下载任务的集合
    private Map<Integer, DownloadTask> mTasks = new LinkedHashMap<Integer, DownloadTask>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        System.out.println("in onStartCommand..."+intent.getAction());
        //获取activity传来的参数
        if(ACTION_START.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            Log.i("<* ACTION_START *>", mFileInfo.toString());
            //启动初始化线程
            DownloadTask.sExecutorService.execute(new InitThread(mFileInfo));
        }
        else if(ACTION_STOP.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            Log.i("<* ACTION_STOP *>", mFileInfo.toString());
            //从集合中取出下载任务
            DownloadTask task = mTasks.get(mFileInfo.getId());
            if(task != null)
            {
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_INIT:
                    FileInfo mFileInfo = (FileInfo)msg.obj;
                    Log.i("<* Handler启动下载任务 *>", mFileInfo.toString());
                    //启动下载任务
                    mDownloadTask = new DownloadTask(DownloadService.this, mFileInfo, 3);
                    mDownloadTask.download();
                    //把下载任务添加到集合中
                    mTasks.put(mFileInfo.getId(),mDownloadTask);
                    break;
                case MSG_ERROR:
                    Log.i("<* Handler网络异常 *>", msg.obj.toString());
                    //发送错误广播
                    Intent intent = new Intent(DownloadService.ACTION_ERROR);
                    intent.putExtra("errMsg", msg.obj.toString());
                    DownloadService.this.sendBroadcast(intent);
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    private class InitThread extends Thread
    {
        private FileInfo mFileInfo = null;

        public InitThread(FileInfo mFileInfo)
        {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run()
        {
            RandomAccessFile raf = null;
            try
            {
                //连接网络文件
                HttpActionObj mHttpActionObj = new HttpActionObj(mFileInfo.getUrl(), new HashMap());
                HttpResultObj<InputStream> mHttpResultObj = HttpUtil.submitHttpAction(mHttpActionObj, InputStream.class, "GET");
                if(!mHttpResultObj.isSuc())
                {
                    mHandler.obtainMessage(MSG_ERROR, mHttpResultObj.getErrMsg()).sendToTarget();
                }
                else
                {
                    //在本地创建文件
                    File dir = new File(DOWNLOAD_PATH);
                    if(!dir.exists()) dir.mkdir();
                    File mFile = new File(dir, mFileInfo.getFileName());
                    raf = new RandomAccessFile(mFile,"rwd");
                    //设置文件长度
                    raf.setLength(mHttpResultObj.getContentLength());
                    mFileInfo.setLength(mHttpResultObj.getContentLength());
                    mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if(raf!=null) raf.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
