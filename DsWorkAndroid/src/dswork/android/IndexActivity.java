package dswork.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import dswork.android.ui.PosPoint;
import dswork.android.util.WorkUtil;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class IndexActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private ActionBar actionBar;
	private PosPoint posPoint;
	private ViewPager mViewPager;
	private List<Fragment> fgms;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initIndexView();//初始化主界面
		initPoints();//初始化指示小圆点
	}
	
	//初始化主界面
	private void initIndexView()
	{
		setContentView(R.layout.activity_index);		
		
		//Set up the ViewPager
		fgms = new ArrayList<Fragment>();
		fgms.add(new Fragment1());
		fgms.add(new Fragment2());
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		// Set up the action bar.
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) 
		{
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}
	
	//初始化指示小圆点
	private void initPoints()
	{
		posPoint = (PosPoint) findViewById(R.id.points);
		posPoint.initPosPoint(fgms.size());
//		posPoint.initPosPoint(fgms.size(),"●","#ff0000","#DDDDDD",255,1);
	}

	//自定义ViewPager适配器
	private class SectionsPagerAdapter extends FragmentPagerAdapter
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
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_ui).toUpperCase(l);
			case 1:
				return getString(R.string.title_component).toUpperCase(l);
			}
			return null;
		}
	}
	//适配器翻页监听类
	private class MyOnPageChangeListener extends SimpleOnPageChangeListener
	{
		@Override
		public void onPageSelected(int pos) 
		{
			//撤换action bar tab
			actionBar.setSelectedNavigationItem(pos);
			//撤换小圆点
			posPoint.setCurPoint(pos);
		}
	}
	
	//重写 ActionBar Tab 方法
	@Override
	public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) 
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	// ****************定义Fragment主界面布局****************
	public static class Fragment1 extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_index_1, container, false);
			WorkUtil.addShortcut(getActivity(), rootView, R.id.ViewPager, R.drawable.ic_launcher, "ViewPager", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.ViewPagerActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.FragmentTabHost, R.drawable.ic_launcher, "FragmentTabHost", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.FragmentTabHostActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.OleTab, R.drawable.ic_launcher, "OleTab", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.OleTabActivity", false,null,null,null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.web, R.drawable.ic_3, "web", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.WebActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.SlidingMenu, R.drawable.ic_map, "SlidingMenu", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.SlidingMenuActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.FragmentDemo, R.drawable.ic_map, "FragmentDemo", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.FragmentDemoActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.html_tab, R.drawable.ic_config, "html_tab", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.ui.HtmlTabActivity", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.other, R.drawable.ic_2, "other", WorkUtil.getAppIntent("dswork.android", "dswork.android.IndexActivity", true, "android.intent.action.MAIN", null, null));
			return rootView;
		}
	}
	public static class Fragment2 extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_index_2, container, false);
			WorkUtil.addShortcut(getActivity(), rootView, R.id.db, R.drawable.ic_launcher, "db", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.db.DbActivity",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.alphabet, R.drawable.ic_launcher, "alphabet", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.alphabet.AlphabetActivity",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.loadmore, R.drawable.ic_launcher, "loadmore", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.loadmore.LoadMoreActivity",false, null, null, null));
			return rootView;
		}
	}
}
