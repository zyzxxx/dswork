package dswork.android.demo.component.downloadone;

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

import dswork.android.demo.component.downloadone.model.FileInfo;
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
    public static final int MSG_INIT = 0;
    DownloadTask mDownloadTask = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        System.out.println("in onStartCommand...");
        //获取activity传来的参数
        if(ACTION_START.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            Log.i("test","start:"+mFileInfo.toString());
            //启动初始化线程
            new InitThread(mFileInfo).start();
        }
        else if(ACTION_STOP.equals(intent.getAction()))
        {
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            Log.i("test","stop:"+mFileInfo.toString());
            if(mDownloadTask != null)
            {
                mDownloadTask.isPause = true;
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
                    Log.i("test","Init:"+mFileInfo.toString());
                    //启动下载任务
                    mDownloadTask = new DownloadTask(DownloadService.this, mFileInfo);
                    mDownloadTask.download();
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
                HttpActionObj mHttpActionObj = new HttpActionObj("http://media2.giga.de/2013/05/Opera_Android-robog.png", new HashMap());
                HttpResultObj<InputStream> mHttpResultObj = HttpUtil.submitHttpAction(mHttpActionObj, InputStream.class, "GET");
                if(!mHttpResultObj.isSuc()) return;
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
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    raf.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
