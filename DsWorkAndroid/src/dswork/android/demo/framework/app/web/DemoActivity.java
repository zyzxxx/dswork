package dswork.android.demo.framework.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.model.Demo;
import dswork.android.ui.OleActionMode;
import dswork.android.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.ui.MultiCheck.MultiCheckAdapter.ExpandCtrlMenu;
import dswork.android.ui.MultiCheck.MultiCheckListView;
import dswork.android.ui.MultiCheck.MultiCheckListView.ActionModeListener;
import dswork.android.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.util.MyStrictMode;
import dswork.android.view.OleActivity;

public class DemoActivity extends OleActivity
{
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	@InjectView(id=R.id.chkAll) CheckBox chkAll;//全选框CheckBox
	@InjectView(id=R.id.waitingBar) ProgressBar waitingBar;//进度条
	DemoController controller;
	Map params = new HashMap();//查询参数

	@SuppressWarnings("unchecked")
	@Override
	public void initMainView()
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
		setContentView(R.layout.activity_demo);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标

		//异步获取后台数据，并更新UI
		new GetBgDataTask().execute();
	}

	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.demo, menu);
	}
	
	@Override
	public void initMenuItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(this, DemoAddActivity.class));
				break;
			case R.id.menu_search://搜索
				startActivity(new Intent().setClass(this, DemoSearchActivity.class));
				break;
			case R.id.menu_refresh://刷新
				new GetBgDataTask().execute();
				break;
		}
	}

	//扩展视图缓存类
	public class MyViewCache extends ViewCache
	{
		public TextView titleView;
		public TextView contentView;
		public TextView foundtimeView;
	}
	
	/**
	 * 异步获取后台数据类
	 * @author ole
	 *
	 */
	class GetBgDataTask extends AsyncTask<String, Integer, List<Demo>>
	{//继承AsyncTask
		@Override
		protected void onPreExecute() 
		{
			waitingBar.setVisibility(ProgressBar.VISIBLE);//显示圆形进度条
		}
		
        @SuppressWarnings("unchecked")
		@Override  
        protected List<Demo> doInBackground(String... _params) 
        {//后台耗时操作，不能在后台线程操作UI
    		//获取列表信息
    		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getIntent().getSerializableExtra("params");//获取查询参数
    		if(null != rtn_params) params = rtn_params.get(0);
    		List<Demo> list = controller.get(params);
    		try {
    			Thread.sleep(100 * list.size());
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}  
            return list;  
        }

		protected void onPostExecute(List<Demo> list) 
		{// 后台任务执行完之后被调用，在ui线程执行
			if (list != null)
			{
				Toast.makeText(DemoActivity.this, "加载成功",Toast.LENGTH_LONG).show();
				//实列化MultiCheck适配器，并初始化MultiCheck
				MultiCheckAdapter adapter = new MultiCheckAdapter(
						DemoActivity.this, controller, list, listView, R.layout.activity_demo_item,
						R.id.id, R.id.chk, R.id.ctrl_menu, R.array.ctrl_menu_items, new String[]{"title","foundtime"},new int[]{R.id.title,R.id.foundtime},
						new MyViewCache(),
						"dswork.android", "dswork.android.demo.framework.app.web.DemoUpdActivity",
						new ExpandCtrlMenu()
						{//列表项扩展菜单
							@Override
							public void onItemSelected(String id_s, long id_l, int which) 
							{
								Toast.makeText(DemoActivity.this, id_s, Toast.LENGTH_SHORT).show();
							}
						}, false);
				listView.initMultiCheck(list, adapter, listView, R.id.id, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.web.DemoDetailActivity"));
				listView.setActionModeListener(new ActionModeListener()
				{
					@Override
					public Callback getActionModeCallback() 
					{
						return new OleActionMode(DemoActivity.this, controller, R.menu.context_menu, R.id.menu_upd, 
								R.id.menu_del_confirm, listView,
								"dswork.android", "dswork.android.demo.framework.app.web.DemoUpdActivity");
					}
				});
			} 
			else 
			{
				Toast.makeText(DemoActivity.this, "加载失败，网络异常", Toast.LENGTH_LONG).show();
			}
			waitingBar.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
		}
    } 
}
