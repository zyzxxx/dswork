package dswork.android.demo.ui;

import java.util.ArrayList;
import java.util.List;
import dswork.android.R;
import dswork.android.ui.OleTab;
import dswork.android.ui.OleTab.OnOleTabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OleTabFragment extends Fragment
{
	private ViewPager mViewPager;
	private List<Fragment> fgms = new ArrayList<Fragment>();
	private SectionsPagerAdapter mSectionsPagerAdapter;
    private OleTab ot;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View convertView = inflater.inflate(R.layout.activity_oletab_fgm, container, false);
		//使用OleTab
		ot = (OleTab) convertView.findViewById(R.id.tab);
//		ot.setOleTabBgColor(getResources().getColor(android.R.color.holo_orange_dark));
//		ot.setOleTabTitleBarBgColor(getResources().getColor(android.R.color.holo_blue_light));
//		ot.setOleTabCurTitleColor(getResources().getColor(android.R.color.black));
//		ot.setOleTabTitleColor(getResources().getColor(android.R.color.white));
//		ot.setOleTabTitleSize(30);
//		ot.setOleTabScrollLineColor(getResources().getDrawable(android.R.color.black));
//		ot.setOleTabTitleHeight(150);
		ot.addTabTitle(new String[]{"s1","s2","s3"});
		ot.setOnOleTabListener(new OnOleTabListener() 
		{
			@Override
			public void onTabSelected(int pos) 
			{
				mViewPager.setCurrentItem(pos);
			}
		});
		//使用ViewPager
		fgms.add(new Fragment1());
		fgms.add(new Fragment2());
		fgms.add(new Fragment3());
		mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager)convertView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int pageNum) 
			{
				ot.setScrollPos(pageNum);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}
			@Override
			public void onPageScrollStateChanged(int arg0) 
			{
			}
		});
		return convertView;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fgms.get(position);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}
	}
}
