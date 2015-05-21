package dswork.android.demo.component.downloadone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import dswork.android.R;
import dswork.android.demo.component.downloadone.model.FileInfo;
import dswork.android.lib.core.util.InjectUtil;

@SuppressLint("NewApi")
public class DownloadOneActivity extends Activity
{
    @InjectUtil.InjectView(id = R.id.tv_file) TextView tv_file;//文件名
    @InjectUtil.InjectView(id = R.id.pgb_download) ProgressBar pgb_download;//进度条
    @InjectUtil.InjectView(id = R.id.btn_download_start) Button btn_download_start;//下载开始按钮
    @InjectUtil.InjectView(id = R.id.btn_download_stop) Button btn_download_stop;//下载暂停按钮
    @InjectUtil.InjectView(id = R.id.btn_download) Button btn_download;//下载按钮
    FileInfo mFileInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_one);
        getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
        getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
        //注入控件
        InjectUtil.injectView(this);
        pgb_download.setMax(100);
        //创建文件信息对象
        mFileInfo = new FileInfo(0,"http://gdown.baidu.com/data/wisegame/8d8165b5bae245b2/wangyigongkaike_20150514.apk","wangyi.apk",0,0);
        tv_file.setText(mFileInfo.getFileName());
        //注册按钮监听
        btn_download_start.setOnClickListener(new DownLoadStartListener());
        btn_download_stop.setOnClickListener(new DownloadStopListener());
        //注册广播接收器
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        //停止service
//        Intent mIntent = new Intent(DownloadOneActivity.this, DownloadService.class);
//        stopService(mIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.download_one, menu);
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

    //Listeners/////////////////////////////////////////////////////////////////////////////////////
    private class DownLoadStartListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(DownloadOneActivity.this, "下载", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(DownloadOneActivity.this, DownloadService.class);
            mIntent.setAction(DownloadService.ACTION_START);
            mIntent.putExtra("fileinfo", mFileInfo);
            startService(mIntent);
        }
    }

    private class DownloadStopListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(DownloadOneActivity.this, "暂停", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(DownloadOneActivity.this, DownloadService.class);
            mIntent.setAction(DownloadService.ACTION_STOP);
            mIntent.putExtra("fileinfo", mFileInfo);
            startService(mIntent);
        }
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
                int finished = Integer.valueOf(String.valueOf(intent.getLongExtra("finished", 0l)));
                pgb_download.setProgress(finished);
            }
        }
    };
}
