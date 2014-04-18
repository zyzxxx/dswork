package dswork.android.demo.component.db;

import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.model.Person;
import dswork.android.service.PersonService;
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
	private PersonService service;//注入service

	@Override
	public void initMainView() {
		setContentView(R.layout.activity_db);
		InjectUtil.injectView(this);//注入控件
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		service=new PersonService(this);
		List<Person> persons = service.query();
		//实列化MultiCheck适配器，并初始化MultiCheck
		MultiCheckAdapter adapter = new MultiCheckAdapter(this, persons, R.layout.activity_db_item,
				R.id.id, R.id.chk, new String[]{"name","sortkey","phone","amount"},new int[]{R.id.name,R.id.sortkey,R.id.phone,R.id.amount},
				new MyViewCache());
		listView.getMultiCheck(persons, adapter, listView, R.id.id, chkAll, new Intent().setClassName("dswork.android", "dswork.android.demo.component.db.DbDetailActivity"));
		listView.setActionModeListener(new ActionModeListener()
		{
			@Override
			public Callback getActionModeCallback() {
				return new MyActionMode();
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
	
	//自定义ActionMode，进入多选模式下启用
	private final class MyActionMode implements ActionMode.Callback 
	{
		@Override
		public boolean onCreateActionMode(ActionMode mode,Menu menu) 
		{
			//填充ActionMode的menu
			MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_menu, menu);
			return true;
		}
		@Override
		public boolean onPrepareActionMode(ActionMode mode,Menu menu)
		{
			return false;
		}
		@Override
		public boolean onActionItemClicked(ActionMode mode,MenuItem item) 
		{
			boolean result = false;
			switch (item.getItemId()) 
			{
				case R.id.menu_edit://修改
		        	if(listView.getIdList().size() > 0)
		        	{
		        		if(listView.getIdList().size() == 1)
		        		{//一条
		        			startActivity(new Intent().setClassName("dswork.android", "dswork.android.demo.component.db.DbEditActivity").putExtra("ids", listView.getIdArray()));
		        		}
		        		else
		        		{//多条
			        		new AlertDialog.Builder(DbActivity.this)
			        		.setTitle(R.string.confirm_upd)
			        		.setIcon(android.R.drawable.ic_dialog_info)
			        		.setNegativeButton(R.string.no, null)
			        		.setPositiveButton(R.string.yes, new updateListener())
			        		.show();
		        		}
		        		result = true;
		        	}
		        	else
		        	{
		        		Toast.makeText(getApplicationContext(), "未选中 ！", Toast.LENGTH_SHORT).show();  
		                result = false;
		        	}
					break;
		        case R.id.menu_confirm://删除
		        	if(listView.getIdList().size()>0)
		        	{
		        		new AlertDialog.Builder(DbActivity.this)
		        		.setTitle(R.string.confirm_del)
		        		.setIcon(android.R.drawable.ic_delete)
		        		.setNegativeButton(R.string.no, null)
		        		.setPositiveButton(R.string.yes, new deleteListener())
		        		.show();
		        		result = true;
		        	}
		        	else
		        	{
		        		Toast.makeText(getApplicationContext(), "未选中 ！", Toast.LENGTH_SHORT).show();  
		                result = false;
		        	}
		        default:
		        	result = false;
			}
			return result;
		}
		@Override
		//左上角的勾
		public void onDestroyActionMode(ActionMode mode) 
		{
			listView.isMultiMode(false);//关闭多选模式
		}
	}
	
	//删除操作监听类
	private class deleteListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
    		service.deleteBatch("person", listView.getIdList());//执行删除
    		listView.refreshListView(service.query());//刷新列表
    		Toast.makeText(getApplicationContext(), "删除成功 ！", Toast.LENGTH_SHORT).show(); 
		}
	}
	//修改操作监听类
	private class updateListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			startActivity(new Intent().setClassName("dswork.android", "dswork.android.demo.component.db.DbEditActivity").putExtra("ids", listView.getIdArray()));
		}
	}
}
