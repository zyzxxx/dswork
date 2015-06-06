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

import java.util.List;

import dswork.android.R;
import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.demo.component.downloadlist.service.FileInfoService;
import dswork.android.lib.core.util.InjectUtil;

@SuppressLint("NewApi")
public class DownloadListActivity extends Activity
{
    @InjectUtil.InjectView(id = R.id.lv_download)ListView lv_download;
    private Context ctx = DownloadListActivity.this;
    private List<FileInfo> mList = null;
    private FileListAdapter mAdapter = null;
    private FileInfoService mFileInfoService = null;

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
        mFileInfoService = new FileInfoService(this);
        //标记数据库所有任务为STOP...
        mFileInfoService.updateAllStartToStop();
        //接受其它Activity传来的List<FileInfo>
        mList = mFileInfoService.refreshList((List<FileInfo>) getIntent().getSerializableExtra("fileList"));
        //初始化ListView的Adapter
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
        //注销广播接收器
        unregisterReceiver(mReceiver);
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
            FileInfo mFileInfo = (FileInfo)intent.getSerializableExtra("fileinfo");
            mAdapter.updateItemProgress(mFileInfo);
            if(DownloadService.ACTION_UPDATE.equals(intent.getAction()))
            {
            }
            else if(DownloadService.ACTION_FINISH.equals(intent.getAction()))
            {
                Toast.makeText(ctx, mList.get(mFileInfo.getFile_id()).getFilename()+"下载完毕",Toast.LENGTH_SHORT).show();
            }
            else if(DownloadService.ACTION_ERROR.equals(intent.getAction()))
            {
                Toast.makeText(ctx, "资源或网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
