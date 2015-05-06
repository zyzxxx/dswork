package com.paper.view.asynctask;

import com.paper.controller.PaperNavController;
import com.paper.view.adapter.NavDrawerListViewAdapter;
import dswork.android.lib.core.util.webutil.HttpResultObj;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class LoadNavDrawUiTask extends AsyncTask<String, Integer, HttpResultObj<String>>
{
	private Context ctx;
	private SwipeRefreshLayout mSwipeLayout;
	private ExpandableListView mDrawerListView;
	private PaperNavController controller;
	private CallBackFn cb;
	
	public LoadNavDrawUiTask(Context ctx, SwipeRefreshLayout mSwipeLayout, ExpandableListView mDrawerListView, CallBackFn cb)
	{
		super();
		this.ctx = ctx;
		this.mSwipeLayout = mSwipeLayout;
		this.mDrawerListView = mDrawerListView;
		this.controller = new PaperNavController(ctx);
		this.cb = cb;
	}

	@Override
	protected void onPreExecute() 
	{
		mSwipeLayout.setRefreshing(true);
	}
	
	@Override  
    protected HttpResultObj<String> doInBackground(String... _params) 
    {//后台耗时操作，获取列表数据，不能在后台线程操作UI
		HttpResultObj<String> result = controller.getNavJson();
        return result;  
    }

	protected void onPostExecute(HttpResultObj<String> result) 
	{// 后台任务执行完之后被调用，在ui线程执行
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
		mSwipeLayout.scrollTo(0, 0);
		mSwipeLayout.setRefreshing(false);
	}
	
	public static interface CallBackFn
	{
		public void exeUiSuc(ExpandableListView mDrawerListView);
		public void exeUiErr();
	}
}
