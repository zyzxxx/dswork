package com.paper.view.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paper.controller.PaperImageController;
import com.paper.model.PaperImage;
import com.paper.view.adapter.ImageGridViewAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.lib.core.ui.MoreGridView;

public class DownRefreshImageGridViewAsyncTask extends AsyncTask<String, Integer, Map>
{
	private Context ctx;
	private ProgressBar pb;
	private SwipeRefreshLayout mSwipeLayout;
	private MoreGridView mGridView;
	private TextView mPageNumView;
	private long pid;
	private PaperImageController controller;
	private String refreshMode = "auto";//刷新方式：auto自动，manual手动
	
	public DownRefreshImageGridViewAsyncTask(Context ctx, SwipeRefreshLayout mSwipeLayout, MoreGridView mGridView, TextView mPageNumView, long pid, String refreshMode)
	{
		super();
		this.ctx = ctx;
		this.pb = new ProgressBar(ctx);
		this.mSwipeLayout = mSwipeLayout;
		this.mGridView = mGridView;
		this.mPageNumView = mPageNumView;
		this.pid = pid;
		this.controller = new PaperImageController(ctx);
		this.refreshMode = refreshMode;
	}

	@Override
	protected void onPreExecute() 
	{
		if(refreshMode.equals("auto"))
		{
			mSwipeLayout.setProgressViewOffset(false, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, ctx.getResources().getDisplayMetrics()));
			mSwipeLayout.setRefreshing(true);
		}

		//创建ProgressBar
//		pb.setVisibility(ProgressBar.VISIBLE);
//		FrameLayout.LayoutParams pbViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//		pbViewParams.gravity = Gravity.CENTER;
//		((ViewGroup) mGridView.getRootView()).addView(pb,pbViewParams);
	}
	
	@Override  
    protected Map doInBackground(String... _params) 
    {//后台耗时操作，获取列表数据，不能在后台线程操作UI
		Map<String,String> params = new HashMap<String,String>();
		params.put("pid", String.valueOf(pid));
		params.put("page", String.valueOf(Integer.valueOf(_params[0])));
        return controller.getPaperImagePage(params);
    }

	protected void onPostExecute(final Map m)
	{// 后台任务执行完之后被调用，在ui线程执行
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (m != null)
				{
					List list = (List<PaperImage>) m.get("list");
					mGridView.setAdapter(new ImageGridViewAdapter(ctx, list));
					mPageNumView.setText("1");
				}
				else
				{
					Toast.makeText(ctx, "加载失败，网络异常", Toast.LENGTH_SHORT).show();
				}
				mSwipeLayout.setRefreshing(false);
//				pb.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
			}
		}, 2000);
	}
}