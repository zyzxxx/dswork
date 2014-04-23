package dswork.android.demo.framework.app.single;

import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.controller.PersonController;
import dswork.android.model.Person;
import dswork.android.ui.OleActionMode;
import dswork.android.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.ui.MultiCheck.MultiCheckListView;
import dswork.android.ui.MultiCheck.MultiCheckListView.ActionModeListener;
import dswork.android.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;

public class DbActivity extends OleActivity
{
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	@InjectView(id=R.id.chkAll) CheckBox chkAll;//全选框CheckBox
	private PersonController controller;

	@Override
	public void initMainView() {
		setContentView(R.layout.activity_db);
		InjectUtil.injectView(this);//注入控件
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		controller = new PersonController(this);
		List<Person> persons = controller.get(new HashMap());
		//实列化MultiCheck适配器，并初始化MultiCheck
		MultiCheckAdapter adapter = new MultiCheckAdapter(this, persons, R.layout.activity_db_item,
				R.id.id, R.id.chk, new String[]{"name","sortkey","phone","amount"},new int[]{R.id.name,R.id.sortkey,R.id.phone,R.id.amount},
				new MyViewCache());
		listView.getMultiCheck(persons, adapter, listView, R.id.id, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.single.DbDetailActivity"));
		listView.setActionModeListener(new ActionModeListener()
		{
			@Override
			public Callback getActionModeCallback() 
			{
				return new OleActionMode(DbActivity.this, controller, R.menu.context_menu, R.id.menu_upd, 
						R.id.menu_del_confirm, listView,
						"dswork.android", "dswork.android.demo.framework.app.single.DbEditActivity");
			}
		});
	}

	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.db, menu);
	}
	
	@Override
	public void initMenuItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(this, DbAddActivity.class));
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
