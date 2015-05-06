package dswork.android.demo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dswork.android.R;

public class ViewPagerFragment extends Fragment
{
	private ViewPager mViewPager;
	private List<Fragment> fgms = new ArrayList<Fragment>();
	private SectionsPagerAdapter mSectionsPagerAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View convertView = inflater.inflate(R.layout.activity_viewpager_fgm, container, false);
		//使用ViewPager
		fgms.add(new Fragment1());
		fgms.add(new Fragment2());
		fgms.add(new Fragment3());
		mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager)convertView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
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

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
}
