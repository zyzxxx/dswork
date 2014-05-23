package dswork.android.demo.framework.app.single;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.controller.PersonController;
import dswork.android.lib.ui.OleActionMode;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter.ExpandCtrlMenu;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.ActionModeListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleActivity;
import dswork.android.model.Person;

public class PersonActivity extends OleActivity
{
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	@InjectView(id=R.id.chkAll) CheckBox chkAll;//全选框CheckBox
	private PersonController controller;
	Map params = new HashMap();//查询参数

	@Override
	public void initMainView() {
		setContentView(R.layout.activity_person);
		InjectUtil.injectView(this);//注入控件
		controller = new PersonController(this);
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		//获取列表信息
		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getIntent().getSerializableExtra("params");//获取查询参数
		if(null != rtn_params) params = rtn_params.get(0);
		List<Person> persons = controller.get(params);
		//实列化MultiCheck适配器，并初始化MultiCheck
		MultiCheckAdapter adapter = new MultiCheckAdapter(
				this, controller, persons, listView, R.layout.activity_person_item,
				R.id.itemId, R.id.itemChk, R.id.itemMenu, 0, new String[]{"name","sortkey","phone","amount"},new int[]{R.id.name,R.id.sortkey,R.id.phone,R.id.amount},
				new MyViewCache(),
				"dswork.android", "dswork.android.demo.framework.app.single.PersonUpdActivity",
				new ExpandCtrlMenu()
				{
					@Override
					public void onItemSelected(String id_s, long id_l, int which) {
					}
				},false);
		listView.initMultiCheck(persons, adapter, listView, R.id.itemId, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.single.PersonDetailActivity"));
		listView.setActionModeListener(new ActionModeListener()
		{
			@Override
			public Callback getActionModeCallback() 
			{
				return new OleActionMode(PersonActivity.this, controller, R.menu.context_menu, R.id.menu_upd, 
						R.id.menu_del_confirm, listView,
						"dswork.android", "dswork.android.demo.framework.app.single.PersonUpdActivity");
			}
		});
	}

	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.person, menu);
	}
	
	@Override
	public void initMenuItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(this, PersonAddActivity.class));
				break;
			case R.id.menu_search://搜索
				startActivity(new Intent().setClass(this, PersonSearchActivity.class));
				break;
		}
	}
	
	//扩展视图缓存类
	public class MyViewCache extends ViewCache
	{
		public TextView nameView;
		public TextView phoneView;
		public TextView amountView;
	}
	
}
