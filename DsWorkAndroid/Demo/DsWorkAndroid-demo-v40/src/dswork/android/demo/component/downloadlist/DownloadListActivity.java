package dswork.android.demo.component.downloadlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dswork.android.R;
import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.lib.core.util.InjectUtil;

@SuppressLint("NewApi")
public class DownloadListActivity extends Activity
{
    @InjectUtil.InjectView(id = R.id.lv_download)ListView lv_download;
    FileListAdapter mAdapter = null;
    List<FileInfo> mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
        getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
        //注入控件
        InjectUtil.injectView(this);
        //创建文件信息对象
        mList = new ArrayList<FileInfo>();
        mList.add(new FileInfo(0,"http://gdown.baidu.com/data/wisegame/8d8165b5bae245b2/wangyigongkaike_20150514.apk","wangyi.apk",0,0));
        mList.add(new FileInfo(1,"http://120.198.243.13:9999/file.liqucn.com/upload/2015/shejiao/com.tencent.mobileqq_5.6.1_liqucn.com.apk","qq.apk",0,0));
        mList.add(new FileInfo(2,"http://filelx.liqucn.com/upload/2015/sheji/ZombieTerminator_liqushichang_2015_5_13_300008955036_2200126314.ptada","game.apk",0,0));
        mList.add(new FileInfo(3,"http://120.198.243.13:9999/file.liqucn.com/upload/2011/liaotian/com.tencent.mm_6.1.0.73_liqucn.com.apk","wx.apk",0,0));
        mList.add(new FileInfo(4,"http://filelx.liqucn.com/upload/2014/shejiao/weibo-2421_0244_5.2.8release_Android.ptada","wb.apk", 0,0));
        mList.add(new FileInfo(5,"http://file.liqucn.com/upload/2011/bangong/cn.wps.moffice_eng_7.2_liqucn.com.apk","office.apk", 0,0));
        mList.add(new FileInfo(6,"http://filelx.liqucn.com/upload/2014/shipin/PPTV_aPhone_5.0.9_265_20150518.ptada","pptv.apk", 0,0));
        mList.add(new FileInfo(7,"http://filelx.liqucn.com/upload/2011/shipin/Youku_Android_4.7.5_liqu.ptada","youku.apk", 0,0));
        mList.add(new FileInfo(8,"http://file.liqucn.com/upload/2014/shipin/com.qiyi.video_6.3_liqucn.com.apk","qiyi.apk", 0,0));
//        mList.add(new FileInfo(0,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog.png",0,0));
//        mList.add(new FileInfo(1,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog1.png",0,0));
//        mList.add(new FileInfo(2,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog2.png",0,0));
//        mList.add(new FileInfo(3,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog3.png",0,0));
//        mList.add(new FileInfo(4,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog4.png",0,0));
//        mList.add(new FileInfo(5,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog5.png",0,0));
//        mList.add(new FileInfo(6,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog6.png",0,0));
//        mList.add(new FileInfo(7,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog7.png",0,0));
//        mList.add(new FileInfo(8,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog8.png",0,0));
//        mList.add(new FileInfo(9,"http://media2.giga.de/2013/05/Opera_Android-robog.png","robog9.png",0,0));
//        mList.add(new FileInfo(1,"http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg","1406383291_6518.jpg",0,0));
//        mList.add(new FileInfo(2,"http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg","1406383291_8239.jpg",0,0));
//        mList.add(new FileInfo(3,"http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg","1406383290_9329.jpg",0,0));
//        mList.add(new FileInfo(4,"http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg","1406383290_1042.jpg",0,0));
//        mList.add(new FileInfo(5,"http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg","1406383275_3977.jpg",0,0));
//        mList.add(new FileInfo(6,"http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg","1406383265_8550.jpg",0,0));
//        mList.add(new FileInfo(7,"http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg","1406383264_3954.jpg",0,0));
//        mList.add(new FileInfo(8,"http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg","1406383264_4787.jpg",0,0));
//        mList.add(new FileInfo(9,"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg","1406383299_1976.jpg",0,0));
        mAdapter = new FileListAdapter(this, mList, lv_download);
        lv_download.setAdapter(mAdapter);
        //注册广播接收器
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(DownloadService.ACTION_UPDATE);
        mIntentFilter.addAction(DownloadService.ACTION_FINISH);
        mIntentFilter.addAction(DownloadService.ACTION_ERROR);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        //停止service
        Intent mIntent = new Intent(DownloadListActivity.this, DownloadService.class);
        stopService(mIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.download_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home://返回
                this.finish();
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(DownloadService.ACTION_UPDATE.equals(intent.getAction()))
            {
                int progress = Integer.valueOf(String.valueOf(intent.getLongExtra("progress", 0l)));
                int id = intent.getIntExtra("id",0);
                float finished = intent.getFloatExtra("finished",0l);
                float len = intent.getFloatExtra("len",0l);
                String status = intent.getStringExtra("status");
                mAdapter.updateProgress(id,progress,finished,len, status);
            }
            else if(DownloadService.ACTION_FINISH.equals(intent.getAction()))
            {
                //下载结束
                FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
                //更新进度为0
                mAdapter.updateProgress(mFileInfo.getId(),0,0,0,"");
                Toast.makeText(DownloadListActivity.this, mList.get(mFileInfo.getId()).getFileName()+"下载完毕",Toast.LENGTH_SHORT).show();
            }
            else if(DownloadService.ACTION_ERROR.equals(intent.getAction()))
            {
                String errMsg = intent.getStringExtra("errMsg");
                Toast.makeText(DownloadListActivity.this, errMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
