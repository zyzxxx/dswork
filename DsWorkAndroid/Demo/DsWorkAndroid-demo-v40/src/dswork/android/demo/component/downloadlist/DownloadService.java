package dswork.android.demo.component.downloadlist;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

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
    private DownloadTask mDownloadTask = null;
    //下载任务的集合
    private Map<Integer, DownloadTask> mTasks = new LinkedHashMap<Integer, DownloadTask>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //获取activity传来的参数
        if(ACTION_START.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            //启动初始化线程
            DownloadTask.sExecutorService.execute(new InitThread(mFileInfo));
        }
        else if(ACTION_STOP.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            //从集合中取出下载任务
            DownloadTask task = mTasks.get(mFileInfo.getFile_id());
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
            FileInfo mFileInfo = null;
            switch (msg.what)
            {
                case MSG_INIT:
                    mFileInfo = (FileInfo)msg.obj;
                    //启动下载任务
                    mDownloadTask = new DownloadTask(DownloadService.this, mFileInfo, 1);
                    mDownloadTask.download();
                    //把下载任务添加到集合中
                    mTasks.put(mFileInfo.getFile_id(),mDownloadTask);
                    break;
                case MSG_ERROR:
                    mFileInfo = (FileInfo)msg.obj;
                    //发送错误广播
                    Intent intent = new Intent(DownloadService.ACTION_ERROR);
//                    intent.putExtra("errMsg", msg.obj.toString());
                    intent.putExtra("fileinfo", mFileInfo);
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
                    mFileInfo.setStatus("error");
                    mHandler.obtainMessage(MSG_ERROR, mFileInfo).sendToTarget();
//                    mHandler.obtainMessage(MSG_ERROR, mHttpResultObj.getErrMsg()).sendToTarget();
                }
                else
                {
                    //在本地创建文件
                    File dir = new File(DOWNLOAD_PATH);
                    if(!dir.exists()) dir.mkdir();
                    File mFile = new File(dir, mFileInfo.getFilename());
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
