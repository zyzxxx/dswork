package dswork.android.demo.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import dswork.android.R;

public class SlidingMenuActivity extends SlidingFragmentActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		
		//设置标题
		setTitle("左右SlidingMenu");
		//初始化ActionBar
		initActionBar();
		//初始化滑动菜单
		initSlidingMenu();
	}
	
	/**
	 * 初始化ActionBar
	 */
	private void initActionBar()
	{
		getSupportActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);//隐藏向左的图标
		setSlidingActionBarEnabled(true);//设置actionbar是否跟随拖动
		
		// 为ActionBar设置下拉菜单和监听器
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_dropdown_item);
        getSupportActionBar().setListNavigationCallbacks(adapter, null);
	}
	
	/**
	 * 初始化SlidingMenu视图
	 */
	private void initSlidingMenu()
	{
		// 设置主界面视图
		setContentView(R.layout.activity_slidingmenu);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ViewPagerFragment()).commit();

		// 设置滑动菜单的属性值
	    getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);	
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		// 设置左边菜单打开后的视图界面
	    setBehindContentView(R.layout.leftmenu_frame);	
	    getSupportFragmentManager().beginTransaction().replace(R.id.leftmenu_frame, new SlidingMenuListFragment()).commit();
		 // 设置右边菜单打开后的视图界面
		getSlidingMenu().setSecondaryMenu(R.layout.rightmenu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.rightmenu_frame, new SlidingMenuListFragment()).commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
        menu.add("Search")
        .setIcon(R.drawable.abs__ic_search)
        .setActionView(R.layout.searchtext)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		getSupportMenuInflater().inflate(R.menu.slidingmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://打开左SlidingMenu
				toggle();
				break;
			case R.id.r_menu://打开右SlidngMenu
				getSlidingMenu().showSecondaryMenu();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
