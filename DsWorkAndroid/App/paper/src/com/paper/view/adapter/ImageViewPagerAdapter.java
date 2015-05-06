package com.paper.view.adapter;

import java.util.List;

import dswork.android.lib.core.util.bmputil.BitmapLoader;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImageViewPagerAdapter extends FragmentPagerAdapter
{
	private Context ctx;
	private List<Fragment> fgms;
	public ImageViewPagerAdapter(Context ctx, FragmentManager fm, List<Fragment> fgms)
	{
		super(fm);
		this.ctx = ctx;
		this.fgms = fgms;
		BitmapLoader.createBitmapCache(ctx, 8);
	}

	@Override
	public Fragment getItem(int pos)
	{
		return fgms.get(pos);
	}

	@Override
	public int getCount()
	{
		return fgms.size();
	}

//	@Override
//	public CharSequence getPageTitle(int pos) {
//		return "STEP "+(++pos);
//	}
}
