package dswork.android.demo.framework.app.web;

import java.util.HashMap;
import java.util.List;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.model.Demo;
import dswork.android.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.ui.MultiCheck.MultiCheckListView;
import dswork.android.ui.MultiCheck.MultiCheckListView.ActionModeListener;
import dswork.android.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.ui.OleActionMode;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.util.MyStrictMode;
import dswork.android.view.OleActivity;

public class DemoActivity extends OleActivity
{
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	@InjectView(id=R.id.chkAll) CheckBox chkAll;//全选框CheckBox
	DemoController controller;

	@Override
	public void initMainView()
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		setContentView(R.layout.activity_demo);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		//获取列表信息
		List<Demo> list = controller.get(new HashMap());
		//实列化MultiCheck适配器，并初始化MultiCheck
		MultiCheckAdapter adapter = new MultiCheckAdapter(this, list, R.layout.activity_demo_item,
				R.id.id, R.id.chk, new String[]{"title","foundtime"},new int[]{R.id.title,R.id.foundtime},
				new MyViewCache());
		listView.getMultiCheck(list, adapter, listView, R.id.id, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.web.DemoDetailActivity"));
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
		}
	}
	
	//扩展视图缓存类
	public class MyViewCache extends ViewCache
	{
		public TextView nameView;
		public TextView phoneView;
		public TextView amountView;
	}
	
//	//删除操作监听类
//	private class deleteListener implements DialogInterface.OnClickListener
//	{
//		@Override
//		public void onClick(DialogInterface dialog, int which) 
//		{
//    		String result = controller.deleteBatch(listView.getIds());//执行删除
//    		listView.refreshListView(controller.get(new HashMap()));//刷新列表
//    		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show(); 
//		}
//	}
//	//修改操作监听类
//	private class updateListener implements DialogInterface.OnClickListener
//	{
//		@Override
//		public void onClick(DialogInterface dialog, int which) 
//		{
//			Bundle b = new Bundle();
//			b.putString("ids", listView.getIds());
//			b.putLongArray("idsArr", listView.getIdArray());
//			startActivity(new Intent().setClassName("dswork.android", "dswork.android.demo.framework.app.web.DemoUpdActivity").putExtras(b));
//		}
//	}
}
