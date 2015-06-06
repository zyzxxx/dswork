package dswork.android.demo.component.downloadlist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
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

        ViewHolder holder = ViewHolder.get(ctx, view, parent, R.layout.activity_download_list_item, position);
        ((TextView)holder.getView(R.id.tv_file)).setText(mFileInfo.getFilename());
        ((ProgressBar)holder.getView(R.id.pgb_download)).setMax(100);
        holder.getView(R.id.btn_download_start).setOnClickListener(new BtnDownloadStartOnClickListener(mFileInfo, holder));
        holder.getView(R.id.btn_download_stop).setOnClickListener(new BtnDownloadStopOnClickListener(mFileInfo, holder));
        setProgressUI(mFileInfo, holder);

        DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
        String _finished = df.format((float)mFileInfo.getFinished()/1024/1024);
        System.out.println("in getView():"+mFileInfo.getFilename()+"->"+_finished);
        return holder.getConvertView();
    }

    /**
     * 刷新列表中指定item的进度条
     */
    public void updateItemProgress(FileInfo mFileInfo)
    {
        //刷新指定item，主要用到ListView的getChildAt(int position)方法，该方法是获取ListView众多可视的item中位置处于position的view。
        //例如：getChildAt(1)，获取可视的第一个item，
        int _visible_pos = mFileInfo.getFile_id() - mListView.getFirstVisiblePosition();
        //获取指定itemIndex在屏幕中的view
        View mView = mListView.getChildAt(_visible_pos);
        //滑动ListView时，若item view在可视范围内，刷新UI
        if(mView != null)
        {
//            ViewHolder holder = (ViewHolder) mView.getTag();
            ViewHolder holder = ViewHolder.get(ctx, mView, null, R.layout.activity_download_list_item, mFileInfo.getFile_id());
            System.out.println("pos:" + mFileInfo.getFile_id() + ",文件名:" + mFileInfo.getFilename() + ",updateItemProgress()中执行setProgressUI（）");
            setProgressUI(mFileInfo, holder);
        }
    }

    private void setProgressUI(FileInfo mFileInfo, ViewHolder holder)
    {
        DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
        String _finished = df.format((float)mFileInfo.getFinished()/1024/1024);
        String _length = df.format((float)mFileInfo.getLength()/1024/1024);
        ((ProgressBar)holder.getView(R.id.pgb_download)).setProgress(mFileInfo.getProgress());
        ((TextView)holder.getView(R.id.tv_file_finished)).setText(_finished + " MB/");
        ((TextView)holder.getView(R.id.tv_file_len)).setText(_length + " MB");
        if(mFileInfo.getStatus().equals("start"))
        {
            holder.getView(R.id.btn_download_start).setVisibility(View.GONE);
            holder.getView(R.id.btn_download_stop).setVisibility(View.VISIBLE);
        }
        else//stop finished error

        {
            holder.getView(R.id.btn_download_start).setVisibility(View.VISIBLE);
            holder.getView(R.id.btn_download_stop).setVisibility(View.GONE);
        }
        mList.set(mFileInfo.getFile_id(), mFileInfo);
    }

    //listeners/////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class BtnDownloadStartOnClickListener implements View.OnClickListener
    {
        private FileInfo mFileInfo;
        private ViewHolder holder;

        public BtnDownloadStartOnClickListener(FileInfo mFileInfo, ViewHolder holder)
        {
            this.mFileInfo = mFileInfo;
            this.holder = holder;
        }

        @Override
        public void onClick(View v)
        {
//            holder.getView(R.id.btn_download_start).setVisibility(View.GONE);
//            holder.getView(R.id.btn_download_stop).setVisibility(View.VISIBLE);
//            mFileInfo.setStatus("start");
            Intent mIntent = new Intent(ctx, DownloadService.class);
            mIntent.setAction(DownloadService.ACTION_START);
            mIntent.putExtra("fileinfo", mFileInfo);
            ctx.startService(mIntent);
        }
    }

    private class BtnDownloadStopOnClickListener implements View.OnClickListener
    {
        private FileInfo mFileInfo;
        private ViewHolder holder;

        public BtnDownloadStopOnClickListener(FileInfo mFileInfo, ViewHolder holder)
        {
            this.mFileInfo = mFileInfo;
            this.holder = holder;
        }

        @Override
        public void onClick(View v)
        {
//            holder.getView(R.id.btn_download_start).setVisibility(View.VISIBLE);
//            holder.getView(R.id.btn_download_start).setVisibility(View.GONE);
//            mFileInfo.setStatus("stop");
//            setProgressUI(mFileInfo,holder);
            Intent mIntent = new Intent(ctx, DownloadService.class);
            mIntent.setAction(DownloadService.ACTION_STOP);
            mIntent.putExtra("fileinfo", mFileInfo);
            ctx.startService(mIntent);
        }
    }
}