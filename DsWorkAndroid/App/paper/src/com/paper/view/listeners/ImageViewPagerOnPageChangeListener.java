package com.paper.view.listeners;

import java.util.List;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import dswork.android.lib.core.ui.PosPoint;

public class ImageViewPagerOnPageChangeListener extends SimpleOnPageChangeListener
{
	private Context ctx;
	private long imgPid;
	private PosPoint pp;
	private List<Fragment> fgms;
	
	public ImageViewPagerOnPageChangeListener(Context ctx, long imgPid, PosPoint pp, List<Fragment> fgms)
	{
		super();
		this.ctx = ctx;
		this.imgPid = imgPid;
		this.pp = pp;
		this.fgms = fgms;
	}

	@Override
	public void onPageSelected(int pos) 
	{
		pp.setCurPoint(pos);
	}
}
