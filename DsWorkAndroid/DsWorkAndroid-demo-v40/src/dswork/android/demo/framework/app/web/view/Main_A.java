package dswork.android.demo.framework.app.web.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import dswork.android.R;
import dswork.android.demo.ui.Fragment1;
import dswork.android.demo.ui.Fragment2;
import dswork.android.demo.ui.Fragment3;
import dswork.android.lib.view.v40.OleListFragment;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class Main_A extends SlidingFragmentActivity //OleActivity
{
	private static SlidingMenu sdMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/**初始化SlidingMenu视图**/
		// 设置主界面视图
		setContentView(R.layout.main_a);//奇怪，此句如果放在onCreate()方法中会报错？
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		//嵌套DemoListFragment
		getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoGet_F()).commit();
		// 设置滑动菜单的属性值
		sdMenu = getSlidingMenu();
		sdMenu.setMode(SlidingMenu.LEFT);
		sdMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sdMenu.setShadowWidthRes(R.dimen.shadow_width);	
		sdMenu.setShadowDrawable(R.drawable.shadow);
		sdMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sdMenu.setFadeDegree(0.35f);
		// 设置左边菜单打开后的视图界面
	    setBehindContentView(R.layout.left_slidingmenu_frame);	
	    getSupportFragmentManager().beginTransaction().replace(R.id.left_slidingmenu_frame, new LeftSlidingMenu()).commit();
		 // 设置右边菜单打开后的视图界面
//		getSlidingMenu().setSecondaryMenu(R.layout.rightmenu_frame);
//		getSupportFragmentManager().beginTransaction().replace(R.id.rightmenu_frame, new SlidingMenuListFragment()).commit();
	}
	
	/**
	 * 左边SlidingMenu内部类
	 * @author ole
	 *
	 */
	public static class LeftSlidingMenu extends OleListFragment 
	{
		public LeftSlidingMenu() {
			super();
		}

		@Override
		public View initMainView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			return inflater.inflate(R.layout.left_slidingmenu_list, null);
		}

		@Override
		public void initMenu(Menu menu, MenuInflater inflater)
		{
		}

		public void onActivityCreated(Bundle savedInstanceState) 
		{
			super.onActivityCreated(savedInstanceState);
			SampleAdapter adapter = new SampleAdapter(getActivity());
			adapter.add(new SampleItem("Demo List", android.R.drawable.ic_menu_search));
			adapter.add(new SampleItem("Fragment1", android.R.drawable.ic_menu_search));
			adapter.add(new SampleItem("Fragment2", android.R.drawable.ic_menu_search));
			adapter.add(new SampleItem("Fragment13", android.R.drawable.ic_menu_search));
			setListAdapter(adapter);
		}

		private class SampleItem {
			public String tag;
			public int iconRes;
			public SampleItem(String tag, int iconRes) {
				this.tag = tag; 
				this.iconRes = iconRes;
			}
		}

		//适配器
		public class SampleAdapter extends ArrayAdapter<SampleItem> {

			public SampleAdapter(Context context) {
				super(context, 0);
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_slidingmenu_row, null);
				}
				ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
				icon.setImageResource(getItem(position).iconRes);
				TextView title = (TextView) convertView.findViewById(R.id.row_title);
				title.setText(getItem(position).tag);

				return convertView;
			}
		}

		//响应SlidingMenu菜单项点击事件
		@Override
		public void onListItemClick(ListView l, View v, int pos, long id) 
		{
			switch(pos)
			{
				case 0:
					//切换主页的fragment
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoGet_F()).commit();
					//关闭左菜单
					sdMenu.toggle();
					break;
				case 1:
					//切换主页的fragment
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new Fragment1()).commit();
					//关闭左菜单
					sdMenu.toggle();
					break;
				case 2:
					//切换主页的fragment
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new Fragment2()).commit();
					//关闭左菜单
					sdMenu.toggle();
					break;
				case 3:
					//切换主页的fragment
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new Fragment3()).commit();
					//关闭左菜单
					sdMenu.toggle();
					break;
				default :
					//切换主页的fragment
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoGet_F()).commit();
					//关闭左菜单
					sdMenu.toggle();break;
			}
		}
	}
}
