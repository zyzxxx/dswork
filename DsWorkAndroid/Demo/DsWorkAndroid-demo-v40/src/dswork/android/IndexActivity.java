package dswork.android;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dswork.android.demo.component.downloadlist.DownloadService;
import dswork.android.demo.component.downloadlist.model.FileInfo;
import dswork.android.lib.core.ui.PosPoint;
import dswork.android.lib.core.util.WorkUtil;

public class IndexActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private ActionBar actionBar;
	private PosPoint posPoint;
	private ViewPager mViewPager;
	private List<Fragment> fgms;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initIndexView();//初始化主界面
		initPoints();//初始化指示小圆点
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//主Activity销毁后，停止后台Service
        Intent mIntent = new Intent(IndexActivity.this, DownloadService.class);
        stopService(mIntent);
	}

	//初始化主界面
	private void initIndexView()
	{
		setContentView(R.layout.activity_index);		
		
		//Set up the ViewPager
		fgms = new ArrayList<Fragment>();
		fgms.add(new Fragment1());
		fgms.add(new Fragment2());
		fgms.add(new Fragment3());
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
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_ui).toUpperCase(l);
			case 1:
				return getString(R.string.title_component).toUpperCase(l);
			case 2:
				return getString(R.string.title_framework).toUpperCase(l);
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
			WorkUtil.addShortcut(getActivity(), rootView, R.id.alphabet, R.drawable.ic_launcher, "alphabet", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.AlphabetActivity",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.PullToRefresh, R.drawable.ic_launcher, "PullToRefresh", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.PullToRefreshActivity",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.SwipeRefreshLayout, R.drawable.ic_launcher, "SwipeRefreshLayout", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.SwipeRefreshActivity",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.DownloadOne, R.drawable.ic_launcher, "DownloadOne", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.downloadone.DownloadOneActivity",false, null, null, null));
			//创建文件信息对象
			List<FileInfo> mList = new ArrayList<FileInfo>();
			mList.add(new FileInfo(0, "http://120.198.243.13:9999/file.liqucn.com/upload/2015/shejiao/com.tencent.mobileqq_5.6.1_liqucn.com.apk","qq.apk",0,0,0,"stop"));
			mList.add(new FileInfo(1, "http://gdown.baidu.com/data/wisegame/8d8165b5bae245b2/wangyigongkaike_20150514.apk","wangyi.apk",0,0,0,"stop"));
			mList.add(new FileInfo(2, "http://filelx.liqucn.com/upload/2015/sheji/ZombieTerminator_liqushichang_2015_5_13_300008955036_2200126314.ptada","game.apk",0,0,0,"stop"));
			mList.add(new FileInfo(3, "http://120.198.243.13:9999/file.liqucn.com/upload/2011/liaotian/com.tencent.mm_6.1.0.73_liqucn.com.apk","wx.apk",0,0,0,"stop"));
			mList.add(new FileInfo(4, "http://filelx.liqucn.com/upload/2014/shejiao/weibo-2421_0244_5.2.8release_Android.ptada","wb.apk", 0,0,0,"stop"));
			mList.add(new FileInfo(5, "http://file.liqucn.com/upload/2011/bangong/cn.wps.moffice_eng_7.2_liqucn.com.apk","office.apk", 0,0,0,"stop"));
			mList.add(new FileInfo(6, "http://filelx.liqucn.com/upload/2014/shipin/PPTV_aPhone_5.0.9_265_20150518.ptada", "pptv.apk", 0, 0, 0, "stop"));
			mList.add(new FileInfo(7, "http://filelx.liqucn.com/upload/2011/shipin/Youku_Android_4.7.5_liqu.ptada","youku.apk", 0,0,0,"stop"));
			mList.add(new FileInfo(8, "http://file.liqucn.com/upload/2014/shipin/com.qiyi.video_6.3_liqucn.com.apk", "qiyi.apk", 0, 0, 0, "stop"));
			Intent intent = WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.component.downloadlist.DownloadListActivity", false, null, null, null);
			intent.putExtra("fileList", (Serializable)mList);
			WorkUtil.addShortcut(getActivity(), rootView, R.id.DownloadList, R.drawable.ic_launcher, "DownloadList", intent);
			return rootView;
		}
	}
	public static class Fragment3 extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_index_3, container, false);
			WorkUtil.addShortcut(getActivity(), rootView, R.id.webapp, R.drawable.ic_launcher, "webApp", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.framework.app.web.view.Main_A", false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.singleapp, R.drawable.ic_launcher, "singleApp", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.framework.app.single.view.PersonGet_A",false, null, null, null));
			WorkUtil.addShortcut(getActivity(), rootView, R.id.greendao, R.drawable.ic_launcher, "greendaoApp", WorkUtil.getAppIntent("dswork.android", "dswork.android.demo.framework.app.greendao.view.PersonGet_A",false, null, null, null));
			return rootView;
		}
	}
}
