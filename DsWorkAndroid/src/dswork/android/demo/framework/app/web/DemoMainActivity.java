package dswork.android.demo.framework.app.web;

import android.content.Context;
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
import dswork.android.lib.view.OleListFragment;

public class DemoMainActivity extends SlidingFragmentActivity //OleActivity
{
	private static SlidingMenu sdMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		/**初始化SlidingMenu视图**/
		// 设置主界面视图
		setContentView(R.layout.activity_demo_main);//奇怪，此句如果放在onCreate()方法中会报错？
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		//嵌套DemoListFragment
		getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoListFragment()).commit();
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
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoListFragment()).commit();
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
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.demo_main_frame, new DemoListFragment()).commit();
					//关闭左菜单
					sdMenu.toggle();break;
			}
		}
	}
/**采用activity作为列表页，即不使用SlidingMenu且不嵌套列表页于fragment中，采用以下代码**/
//	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
//	@InjectView(id=R.id.chkAll) CheckBox chkAll;//全选框CheckBox
//	@InjectView(id=R.id.waitingBar) ProgressBar waitingBar;//进度条
//	DemoController controller;
//	Map params = new HashMap();//查询参数
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void initMainView()
//	{
//		MyStrictMode.setPolicy();//webapp需要调用此方法
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
//		setContentView(R.layout.activity_demo);
//		InjectUtil.injectView(this);//注入控件
//		controller = new DemoController(this);
//		
//		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
//		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
//
//		//异步获取后台数据，并更新UI
//		new GetBgDataTask().execute();
//	}
//
//	@Override
//	public void initMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.demo, menu);
//	}
//	
//	@Override
//	public void initMenuItemSelected(MenuItem item) {
//		switch(item.getItemId())
//		{
//			case android.R.id.home://返回
//				this.finish();
//				break;
//			case R.id.menu_add://添加
//				startActivity(new Intent().setClass(this, DemoAddActivity.class));
//				break;
//			case R.id.menu_search://搜索
//				startActivity(new Intent().setClass(this, DemoSearchActivity.class));
//				break;
//			case R.id.menu_refresh://刷新
//				new GetBgDataTask().execute();
//				break;
//		}
//	}
//
//	//扩展视图缓存类
//	public class MyViewCache extends ViewCache
//	{
//		public TextView titleView;
//		public TextView contentView;
//		public TextView foundtimeView;
//	}
//	
//	/**
//	 * 异步获取后台数据类
//	 * @author ole
//	 *
//	 */
//	class GetBgDataTask extends AsyncTask<String, Integer, List<Demo>>
//	{//继承AsyncTask
//		@Override
//		protected void onPreExecute() 
//		{
//			waitingBar.setVisibility(ProgressBar.VISIBLE);//显示圆形进度条
//		}
//		
//        @SuppressWarnings("unchecked")
//		@Override  
//        protected List<Demo> doInBackground(String... _params) 
//        {//后台耗时操作，不能在后台线程操作UI
//    		//获取列表信息
//    		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getIntent().getSerializableExtra("params");//获取查询参数
//    		if(null != rtn_params) params = rtn_params.get(0);
//    		List<Demo> list = controller.get(params);
//    		try {
//    			Thread.sleep(100 * (list!=null?list.size():5));
//    		} catch (InterruptedException e) {
//    			e.printStackTrace();
//    		}  
//            return list;  
//        }
//
//		protected void onPostExecute(List<Demo> list) 
//		{// 后台任务执行完之后被调用，在ui线程执行
//			if (list != null)
//			{
//				Toast.makeText(DemoActivity.this, "加载成功",Toast.LENGTH_LONG).show();
//				//实列化MultiCheck适配器，并初始化MultiCheck
//				MultiCheckAdapter adapter = new MultiCheckAdapter(
//						DemoActivity.this, controller, list, listView, R.layout.activity_demo_item,
//						R.id.id, R.id.chk, R.id.ctrl_menu, R.array.ctrl_menu_items, new String[]{"title","foundtime"},new int[]{R.id.title,R.id.foundtime},
//						new MyViewCache(),
//						"dswork.android", "dswork.android.demo.framework.app.web.DemoUpdActivity",
//						new ExpandCtrlMenu()
//						{//列表项扩展菜单
//							@Override
//							public void onItemSelected(String id_s, long id_l, int which) 
//							{
//								Toast.makeText(DemoActivity.this, id_s, Toast.LENGTH_SHORT).show();
//							}
//						}, false);
//				listView.initMultiCheck(list, adapter, listView, R.id.id, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.web.DemoDetailActivity"));
//				listView.setActionModeListener(new ActionModeListener()
//				{
//					@Override
//					public Callback getActionModeCallback() 
//					{
//						return new OleActionMode(DemoActivity.this, controller, R.menu.context_menu, R.id.menu_upd, 
//								R.id.menu_del_confirm, listView,
//								"dswork.android", "dswork.android.demo.framework.app.web.DemoUpdActivity");
//					}
//				});
//			} 
//			else 
//			{
//				Toast.makeText(DemoActivity.this, "加载失败，网络异常", Toast.LENGTH_LONG).show();
//			}
//			waitingBar.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
//		}
//    } 
}
