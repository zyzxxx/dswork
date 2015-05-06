package com.paper.view.asynctask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paper.controller.PaperImageController;
import com.paper.model.PaperImage;
import com.paper.view.adapter.ImageGridViewAdapter;

import dswork.android.lib.core.ui.MoreGridView;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownRefreshImageGridViewAsyncTask extends AsyncTask<String, Integer, Map>
{
	private Context ctx;
	private ProgressBar pb;
	private SwipeRefreshLayout mSwipeLayout;
	private MoreGridView mGridView;
	private TextView mPageNumView;
	private long pid;
	private PaperImageController controller;
	
	public DownRefreshImageGridViewAsyncTask(Context ctx, SwipeRefreshLayout mSwipeLayout, MoreGridView mGridView, TextView mPageNumView, long pid)
	{
		super();
		this.ctx = ctx;
		this.pb = new ProgressBar(ctx);
		this.mSwipeLayout = mSwipeLayout;
		this.mGridView = mGridView;
		this.mPageNumView = mPageNumView;
		this.pid = pid;
		this.controller = new PaperImageController(ctx);
	}

	@Override
	protected void onPreExecute() 
	{
		//创建ProgressBar
		pb.setVisibility(ProgressBar.VISIBLE);
		FrameLayout.LayoutParams pbViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		pbViewParams.gravity = Gravity.CENTER;
		((ViewGroup) mGridView.getRootView()).addView(pb,pbViewParams);
	}
	
	@Override  
    protected Map doInBackground(String... _params) 
    {//后台耗时操作，获取列表数据，不能在后台线程操作UI
		Map<String,String> params = new HashMap<String,String>();
		params.put("pid", String.valueOf(pid));
		params.put("page", String.valueOf(Integer.valueOf(_params[0])));
        return controller.getPaperImagePage(params);
    }

	protected void onPostExecute(Map m) 
	{// 后台任务执行完之后被调用，在ui线程执行
		if (m != null)
		{
			List list = (List<PaperImage>)m.get("list");
			mGridView.setAdapter(new ImageGridViewAdapter(ctx, list));
			mPageNumView.setText("1");
		} 
		else 
		{
			Toast.makeText(ctx, "加载失败，网络异常", Toast.LENGTH_SHORT).show();
		}
		mSwipeLayout.setRefreshing(false);
		pb.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
	}
}