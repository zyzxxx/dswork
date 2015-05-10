package dswork.android.demo.component.downloadone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.demo.component.downloadone.model.FileInfo;
import dswork.android.demo.component.downloadone.model.ThreadInfo;
import dswork.android.demo.component.downloadone.service.ThreadInfoService;

/**
 * Created by ole on 15/5/8.
 */
public class DownloadTask
{
    private Context ctx = null;
    private FileInfo mFileInfo = null;
    private ThreadInfoService service = null;
    private long mFinished = 0;
    public boolean isPause = false;

    public DownloadTask(Context ctx, FileInfo mFileInfo)
    {
        this.ctx = ctx;
        this.mFileInfo = mFileInfo;
        this.service = new ThreadInfoService(ctx);
    }

    public void download()
    {
        //读取数据库的线程信息
        Map m = new HashMap();
        m.put("url", mFileInfo.getUrl());
        List<ThreadInfo> list = service.query(m);
        ThreadInfo po = null;
        if(list.size() == 0)
        {
            //初始化线程信息对象
            po = new ThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0);
            Log.i("test","get from new "+po.toString());
        }
        else
        {
            po = list.get(0);
            Log.i("test","get from db "+po.toString());
        }
        //创建子线程进行下载
        new DownloadThread(po).start();
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread
    {
        private ThreadInfo mThreadInfo = null;

        public DownloadThread(ThreadInfo mThreadInfo)
        {
            this.mThreadInfo = mThreadInfo;
        }

        public void run()
        {
            //向数据库插入线程信息
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id",mThreadInfo.getId());
            m.put("url", mThreadInfo.getUrl());
            Log.i("test","isExists:"+service.isExists(m));
            if(!service.isExists(m))
            {
                service.add("thread_info",mThreadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try
            {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                long start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                Log.i("test", "Range:"+conn.getRequestProperty("Range"));
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                //在读写的时候跳过设置好的字节数，从下一个字节数开始读写
                //例如：seek(100),则跳过100个字节，从第101个字节开始读写
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                mFinished += mThreadInfo.getFinished();
                //开始下载
                if(conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT)
                {
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while((len = input.read(buffer)) != -1)
                    {
                        //写入文件
                        raf.write(buffer,0,len);
                        //把下载进度发送广播给Activity, 每隔500毫秒发送一次广播
                        mFinished += len;
                        if(System.currentTimeMillis() - time > 500)
                        {
                            intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
                            ctx.sendBroadcast(intent);
                        }
                        //在下载暂停时，保存下载进度
                        if(isPause)
                        {
                            mThreadInfo.setFinished(mFinished);
                            Log.i("test", "暂停："+mThreadInfo.toString());
                            service.updateFinished(mThreadInfo);
                            return;
                        }
                    }
                    //删除线程信息
                    service.deleteByIdAndUrl(mThreadInfo.getId(), mThreadInfo.getUrl());

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
                    conn.disconnect();
                    raf.close();
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
