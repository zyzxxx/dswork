package com.paper.view.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

import com.paper.controller.PaperImageController;
import com.paper.model.PaperImage;
import com.paper.view.adapter.ImageGridViewAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.lib.core.ui.MoreGridView;

public class UpRefreshImageGridViewAsyncTask extends AsyncTask<String, Integer, Map>
{
	private Context ctx;
	private ProgressBar pb;
	private MoreGridView mGridView;
	private TextView mPageNumView;
	private long pid;
	private PaperImageController controller;
	
	public UpRefreshImageGridViewAsyncTask(Context ctx, MoreGridView mGridView, TextView mPageNumView, long pid)
	{
		super();
		this.ctx = ctx;
		this.pb = new ProgressBar(ctx);
		this.mGridView = mGridView;
		this.mPageNumView = mPageNumView;
		this.pid = pid;
		this.controller = new PaperImageController(ctx);
	}

	@Override
	protected void onPreExecute() 
	{
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

	protected void onPostExecute(Map m) 
	{// 后台任务执行完之后被调用，在ui线程执行
		if (m != null)
		{
			List list = (List<PaperImage>)m.get("list");
			
			ImageGridViewAdapter adapter = null;
			if(mGridView.getAdapter() instanceof WrapperListAdapter) {  
				WrapperListAdapter gridAdapter=(WrapperListAdapter)mGridView.getAdapter();  
		        adapter = (ImageGridViewAdapter) gridAdapter.getWrappedAdapter();  
		     }else{  
		        adapter = (ImageGridViewAdapter) mGridView.getAdapter();   
		     }
			adapter.getDataList().addAll(list);
			adapter.notifyDataSetChanged();
			
			if (list.size()>0) mPageNumView.setText(m.get("page").toString());
		} 
		else 
		{
			Toast.makeText(ctx, "加载失败，网络异常", Toast.LENGTH_SHORT).show();
		}
		((MoreGridView)mGridView).OnLoadComplete();
	}
}