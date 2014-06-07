package dswork.android.demo.framework.app.single;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.PersonController;
import dswork.android.lib.ui.MultiCheck.MultiCheckActionMode;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter.ItemMenuDialog;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.MultiCheckActionModeListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.OnItemClickNotMultiListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.base.get.BaseGetOleActivity;
import dswork.android.model.Person;

public class PersonGet_A extends BaseGetOleActivity<Person>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	private PersonController controller;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.person_get_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		controller = new PersonController(this);
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
	}

	@Override
	public void initMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.person_get, menu);
	}
	
	@Override
	public void initMenuItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
			case R.id.menu_refresh://刷新
				new BaseGetDataTask().execute();
				break;
			case R.id.menu_search://搜索
				startActivity(new Intent().setClass(this, PersonSearch_A.class));
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(this, PersonAdd_A.class));
				break;
		}
	}
	
	//扩展视图缓存类
	public class MyViewCache extends ViewCache
	{
		public TextView nameView;
		public TextView sortkeyView;
		public ImageButton itemMenu;
	}

	@Override
	public FrameLayout getRootLayout() 
	{
		return rootLayout;
	}

	@Override
	public List<Person> getDataInBackground() 
	{
		return controller.get(getParams());
	}

	@Override
	public void executeUI(List<Person> list)
	{
		MultiCheckAdapter adapter = new MultiCheckAdapter(
				PersonGet_A.this, list, R.layout.person_get_item,
				new String[]{"name","sortkey"}, new int[]{R.id.name,R.id.sortkey},
				new MyViewCache());
		adapter.setItemMenuDialog(new MyItemMenuDialog());//实例化ItemMenuDialog
		listView.initMultiCheck(list, adapter);//初始化MultiCheck
		listView.setOnItemClickNotMultiListener(new MyOnItemClickNotMultiListener());//列表项单击事件（非多选模式）
		listView.setMultiCheckActionModeListener(new MyMultiCheckActionModeListener());//实例化ActionMode
	}
	@Override
	public void executeDel(String id_str)
	{
		String result = controller.deleteBatch(id_str);//执行删除
		if(result.equals("1"))
		{
			listView.refreshListView(controller.get(new HashMap()));//刷新列表
			Toast.makeText(PersonGet_A.this, "删除成功", Toast.LENGTH_SHORT).show(); 
		}
		else
		{
			Toast.makeText(PersonGet_A.this, "操作失败，网络异常", Toast.LENGTH_LONG).show();
		}
	}

	//ItemMenu对话框item点击事件
	private class MyItemMenuDialog implements ItemMenuDialog
	{
		@Override
		public int setItemMenuDialogTitleRes()
		{
			return R.string.item_menu_title;
		}
		@Override
		public int setItemMenuDialogItemsRes() 
		{
			return R.array.my_item_menu;
		}
		@Override
		public void onItemClick(final String id_str, Long id_long, int which) 
		{
			switch (which) 
			{
				case 0://修改
					startActivity(new Intent().setClass(PersonGet_A.this, PersonUpd_A.class).putExtra("id", id_long));
					break;
				case 1://删除
					PersonGet_A.this.showDeleteDialog(id_str);
		    		break;
			}
		}
	}
	//列表项单击事件（非多选模式）
	private class MyOnItemClickNotMultiListener implements OnItemClickNotMultiListener
	{
		@Override
		public void onClick(View v) 
		{
			TextView _itemId = (TextView)v.findViewById(R.id.itemId);
        	long id = Long.parseLong(_itemId.getText().toString());
            startActivity(new Intent().setClass(PersonGet_A.this, PersonGetById_A.class).putExtra("id", id));
		}
	}
	//ActionMode事件
	private class MyMultiCheckActionModeListener implements MultiCheckActionModeListener
	{
		@Override
		public Callback getActionModeCallback()
		{
			return new MultiCheckActionMode(this, R.menu.context_menu, listView);
		}
		@Override
		public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item)
		{
			boolean result = false;
			switch(item.getItemId())
			{
				case R.id.menu_del_confirm://删除
		        	if(listView.getIdList().size()>0){
		        		PersonGet_A.this.showDeleteDialog(listView.getIds());
		        		result = true;
		        	}
		        	else
		        	{
		        		Toast.makeText(PersonGet_A.this, "未选中 ！", Toast.LENGTH_SHORT).show();  
		        		result = false;
		        	}
					break;
				case R.id.menu_chkall://切换全/不选
					listView.toggleChecked(item);
					result = true;
					break;
			}
			return result;
		}
	}
}
