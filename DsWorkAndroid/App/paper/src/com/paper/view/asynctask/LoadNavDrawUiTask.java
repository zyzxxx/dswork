package com.paper.view.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.paper.controller.PaperNavController;
import com.paper.view.adapter.NavDrawerListViewAdapter;

import dswork.android.lib.core.util.webutil.HttpResultObj;

public class LoadNavDrawUiTask extends AsyncTask<String, Integer, HttpResultObj<String>>
{
	private Context ctx;
	private SwipeRefreshLayout mSwipeLayout;
	private ExpandableListView mDrawerListView;
	private PaperNavController controller;
	private CallBackFn cb;
	private String refreshMode = "auto";//刷新方式：auto自动，manual手动

	public LoadNavDrawUiTask(Context ctx, SwipeRefreshLayout mSwipeLayout, ExpandableListView mDrawerListView, String refreshMode, CallBackFn cb)
	{
		super();
		this.ctx = ctx;
		this.mSwipeLayout = mSwipeLayout;
		this.mDrawerListView = mDrawerListView;
		this.controller = new PaperNavController(ctx);
		this.refreshMode = refreshMode;
		this.cb = cb;
	}

	@Override
	protected void onPreExecute()
	{
		if(refreshMode.equals("auto"))
		{
			mSwipeLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, ctx.getResources().getDisplayMetrics()));
			mSwipeLayout.setRefreshing(true);
		}
	}

	@Override
    protected HttpResultObj<String> doInBackground(String... _params)
    {//后台耗时操作，获取列表数据，不能在后台线程操作UI
		HttpResultObj<String> result = controller.getNavJson();
        return result;
    }

	protected void onPostExecute(final HttpResultObj<String> result)
	{// 后台任务执行完之后被调用，在ui线程执行
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
                System.out.println("navJson -----> "+result.getData());
                if(result.isSuc())
                {
                    mDrawerListView.setAdapter(new NavDrawerListViewAdapter(ctx, result.getData()));
                    cb.exeUiSuc(mDrawerListView);
                }
                else
                {
                    Toast.makeText(ctx, "加载菜单失败，请稍后重试。"+result.getErrMsg(), Toast.LENGTH_LONG).show();
                    cb.exeUiErr();
                }
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
	}

	public static interface CallBackFn
	{
		public void exeUiSuc(ExpandableListView mDrawerListView);
		public void exeUiErr();
	}
}
