package com.paper.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paper.R;
import com.paper.view.asynctask.DownRefreshImageGridViewAsyncTask;
import com.paper.view.asynctask.UpRefreshImageGridViewAsyncTask;

import dswork.android.lib.core.ui.MoreGridView;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.core.util.MyStrictMode;

public class ImageGridViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MoreGridView.OnLoadListener
{
	//标记要注入的控件
	@InjectView(id=R.id.swipe_ly) SwipeRefreshLayout mSwipeLayout;
	@InjectView(id=R.id.image_wall) MoreGridView mMoreGridView;
	@InjectView(id=R.id.page) TextView mPageNumView;
	
	//定义当前实例fragment所属的导航项number、导航项id、导航项name
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ARG_SECTION_ID = "section_id";
	private static final String ARG_SECTION_NAME = "section_name";
	
	//定义变量
	private Callbacks mCallbacks;

	public ImageGridViewFragment(int sectionNumber, long sectionId, CharSequence sectionName, Callbacks mCallbacks)
	{
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putLong(ARG_SECTION_ID, sectionId);
		args.putCharSequence(ARG_SECTION_NAME, sectionName);
		this.setArguments(args);//保存当前导航菜单信息
		this.mCallbacks = mCallbacks;
	}

	@Override
	public void onAttach(Activity activity)
	{
		//关联Activity后，修改actionbar title
		super.onAttach(activity);
		if (null == getArguments())return;
		mCallbacks.getNavSectionName(getArguments().getCharSequence(ARG_SECTION_NAME));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		MyStrictMode.setPolicy();
		View rootView = inflater.inflate(R.layout.image_gridview_fragment, container, false);
		InjectUtil.injectView(this, rootView);//注入控件
		if (null == getArguments())return null;
		
		mSwipeLayout.setOnRefreshListener(this);  
		mSwipeLayout.setColorSchemeResources(R.color.red,R.color.blue_light,R.color.orange,R.color.green);
		mMoreGridView.setOnLoadListener(this);
        //下拉异步加载ImageWall UI
		downRefresh("auto");
		return rootView;
	}
	
	//新建AsyncTask对象
	private void upRefresh(String pageNum)
	{
		new UpRefreshImageGridViewAsyncTask(getActivity(), mMoreGridView, mPageNumView, getArguments().getLong(ARG_SECTION_ID)).execute(pageNum);
	}
	private void downRefresh(String refreshMode)
	{
		new DownRefreshImageGridViewAsyncTask(getActivity(), mSwipeLayout, mMoreGridView, mPageNumView, getArguments().getLong(ARG_SECTION_ID), refreshMode).execute("1");
	}
	
	public static interface Callbacks
	{
		public void getNavSectionName(CharSequence sectionName);
	}

	@Override
	public void onLoad()
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
			@Override
			public void run()
			{
				Integer _page = Integer.valueOf((String)mPageNumView.getText());
				_page++;
				upRefresh(_page.toString());
			}}, 2000);
	}

	@Override
	public void onRefresh()
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				downRefresh("manual");
			}}, 2000);
	}
}
