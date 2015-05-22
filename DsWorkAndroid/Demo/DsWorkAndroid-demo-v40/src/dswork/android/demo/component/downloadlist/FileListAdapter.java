package dswork.android.demo.component.downloadlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    private ListView mListView = null;

    public FileListAdapter(Context ctx, List<FileInfo> mList, ListView mListView)
    {
        this.ctx = ctx;
        this.mList = mList;
        this.mListView = mListView;
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
        if(view == null)
        {
            view = LayoutInflater.from(ctx).inflate(R.layout.activity_download_list_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        //设置视图中的控件
        holder.tv_file.setText(mFileInfo.getFileName());
        holder.pgb_download.setMax(100);
        holder.btn_download_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ctx, DownloadService.class);
                mIntent.setAction(DownloadService.ACTION_START);
                mIntent.putExtra("fileinfo", mFileInfo);
                ctx.startService(mIntent);
            }
        });
        holder.btn_download_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ctx, DownloadService.class);
                mIntent.setAction(DownloadService.ACTION_STOP);
                mIntent.putExtra("fileinfo", mFileInfo);
                ctx.startService(mIntent);
            }
        });
        holder.pgb_download.setProgress(mFileInfo.getFinishedInt());
        return view;
    }

    /**
     * 刷新列表中指定item的进度条
     */
    public void updateProgress(int id, int progress)
    {
        FileInfo mFileInfo = mList.get(id);
        mFileInfo.setFinished(progress);

//        notifyDataSetChanged();//刷新整个listview, 影响性能, 导致item按钮响应慢

        //刷新指定item，主要用到ListView的getChildAt(int position)方法，该方法是获取ListView众多可视的item中位置处于position的view。
        //例如：getChildAt(1)，获取可视的第一个item，
        int _visible_pos = id - mListView.getFirstVisiblePosition();
        //获取指定itemIndex在屏幕中的view
        View mView = mListView.getChildAt(_visible_pos);
        //滑动ListView时，若item view在可视范围内，刷新UI
        if(mView != null)
        {
            ViewHolder holder = (ViewHolder) mView.getTag();
            holder.pgb_download.setProgress(mFileInfo.getFinishedInt());
        }
    }


    static class ViewHolder
    {
        public TextView tv_file;
        public Button btn_download_start,btn_download_stop;
        public ProgressBar pgb_download;

        public ViewHolder(View v)
        {
            this.tv_file = (TextView)v.findViewById(R.id.tv_file);
            this.btn_download_start = (Button)v.findViewById(R.id.btn_download_start);
            this.btn_download_stop = (Button)v.findViewById(R.id.btn_download_stop);
            this.pgb_download = (ProgressBar)v.findViewById(R.id.pgb_download);
        }
    }
}