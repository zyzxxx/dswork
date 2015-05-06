package com.paper.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Window;

import com.paper.R;
import com.paper.model.PaperImage;
import com.paper.view.adapter.ImageViewPagerAdapter;
import com.paper.view.fragment.ImageViewPagerFragment;
import com.paper.view.listeners.ImageViewPagerOnPageChangeListener;

import dswork.android.lib.core.ui.PosPoint;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.MyStrictMode;
import dswork.android.lib.core.util.InjectUtil.InjectView;

public class ImageViewPagerActivity extends ActionBarActivity
{
	//标记要注入的控件
	@InjectView(id=R.id.image_view_pager) ViewPager vp;
	@InjectView(id=R.id.points) PosPoint pp;
	//变量
	private List<Fragment> fgms = new ArrayList<Fragment>();
	private ImageViewPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_viewpager_activity);
		MyStrictMode.setPolicy();
		InjectUtil.injectView(this);//注入控件

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示左上角返回键
//		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
		
		Bundle b = getIntent().getBundleExtra("info");
		long id = b.getLong("id");
		long pid = b.getLong("pid");
		List dataList =  (List) b.getSerializable("dataList");
		
		for(PaperImage o : (List<PaperImage>)dataList)
		{
			fgms.add(new ImageViewPagerFragment(o.getId()));
		}
		
		pp.initPosPoint(fgms.size(), "●", R.color.white, R.color.gray_dark, 255, 1);
		adapter = new ImageViewPagerAdapter(this, getSupportFragmentManager(), fgms);
		vp.setAdapter(adapter);
		vp.setCurrentItem(getCurPageIndex(id, dataList));
		vp.setOnPageChangeListener(new ImageViewPagerOnPageChangeListener(this, pid, pp, fgms));
		pp.setCurPoint(getCurPageIndex(id, dataList));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 获取点击图片的页索引
	 * @param id
	 * @param dataList
	 * @return
	 */
	private int getCurPageIndex(long id, List dataList)
	{
		int index = 0;
		for(int i=0; i<dataList.size(); i++)
		{
			Long curFgmtImgId = ((PaperImage)dataList.get(i)).getId();
			if(id == curFgmtImgId)
			{
				index = i;
				break;
			}
		}
		return index;
	}
}
