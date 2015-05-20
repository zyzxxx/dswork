package dswork.android.demo.component.downloadlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import dswork.android.R;
import dswork.android.demo.component.downloadlist.model.FileInfo;

/**
 * Created by ole on 15/5/18.
 */
public class FileListAdapter extends BaseAdapter
{
    private Context ctx = null;
    private List<FileInfo> mList = null;

    public FileListAdapter(Context ctx, List<FileInfo> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override

    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final FileInfo mFileInfo = mList.get(position);
        ViewHolder holder = null;
//        if(view == null)
//        {
            view = LayoutInflater.from(ctx).inflate(R.layout.activity_download_list_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
            //设置视图中的控件
            holder.tv_file.setText(mFileInfo.getFileName());
            holder.pgb_download.setMax(100);
            holder.btn_download_start.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent mIntent = new Intent(ctx, DownloadService.class);
                    mIntent.setAction(DownloadService.ACTION_START);
                    mIntent.putExtra("fileinfo", mFileInfo);
                    ctx.startService(mIntent);
                }
            });
            holder.btn_download_stop.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent mIntent = new Intent(ctx, DownloadService.class);
                    mIntent.setAction(DownloadService.ACTION_STOP);
                    mIntent.putExtra("fileinfo", mFileInfo);
                    ctx.startService(mIntent);
                }
            });
//        }
//        else
//        {
//            holder = (ViewHolder)view.getTag();
//        }
//        System.out.println("progress : "+mFileInfo.getFinished());
        holder.pgb_download.setProgress(mFileInfo.getFinishedInt());
//        holder.btn_download_start.setOnClickListener(new DownLoadStartListener(ctx, mFileInfo));
//        holder.btn_download_stop.setOnClickListener(new DownloadStopListener(ctx, mFileInfo));
        return view;
    }

    /**
     * 更新列表中的进度条
     */
    public void updateProgress(int id, int progress)
    {
        FileInfo mFileInfo = mList.get(id);
        mFileInfo.setFinished(progress);
        notifyDataSetChanged();
    }


    static class ViewHolder
    {
        private TextView tv_file;
        private Button btn_download_start,btn_download_stop;
        private ProgressBar pgb_download;

        public ViewHolder(View v)
        {
            this.tv_file = (TextView)v.findViewById(R.id.tv_file);
            this.btn_download_start = (Button)v.findViewById(R.id.btn_download_start);
            this.btn_download_stop = (Button)v.findViewById(R.id.btn_download_stop);
            this.pgb_download = (ProgressBar)v.findViewById(R.id.pgb_download);
        }
    }
}
//Listeners/////////////////////////////////////////////////////////////////////////////////////
// class DownLoadStartListener implements View.OnClickListener
//{
//    private Context ctx;
//    private FileInfo mFileInfo;
//
//    public DownLoadStartListener(Context ctx, FileInfo mFileInfo)
//    {
//        this.ctx = ctx;
//        this.mFileInfo = mFileInfo;
//    }
//
//    @Override
//    public void onClick(View v)
//    {
//        Toast.makeText(ctx, "下载", Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(ctx, DownloadService.class);
//        mIntent.setAction(DownloadService.ACTION_START);
//        mIntent.putExtra("fileinfo", mFileInfo);
//        ctx.startService(mIntent);
//    }
//}
//
// class DownloadStopListener implements View.OnClickListener
//{
//    private Context ctx;
//    private FileInfo mFileInfo;
//
//    public DownloadStopListener(Context ctx, FileInfo mFileInfo)
//    {
//        this.ctx = ctx;
//        this.mFileInfo = mFileInfo;
//    }
//
//    @Override
//    public void onClick(View v)
//    {
//        Toast.makeText(ctx, "暂停", Toast.LENGTH_SHORT).show();
//        Intent mIntent = new Intent(ctx, DownloadService.class);
//        mIntent.setAction(DownloadService.ACTION_STOP);
//        mIntent.putExtra("fileinfo", mFileInfo);
//        ctx.startService(mIntent);
//    }
//}