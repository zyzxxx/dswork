package dswork.android.demo.framework.app.web;

import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.lib.ui.MultiCheck.MultiCheckActionMode;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter.ItemMenuDialog;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.MultiCheckActionModeListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.OnItemClickNotMultiListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.util.MyStrictMode;
import dswork.android.lib.view.base.get.BaseGetOleSherlockFragment;
import dswork.android.model.Demo;

public class DemoGet_F extends BaseGetOleSherlockFragment<Demo>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
	private DemoController controller;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);//使fragment可以控制Activity中的menu
	}

	@Override
	public View initMainView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		View convertView = inflater.inflate(R.layout.demo_get_f, container, false);//填充布局
		InjectUtil.injectView(this, convertView);//注入控件
		controller = new DemoController(getActivity());//注入Controller
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
		return convertView;
	}

	@Override
	public void initMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.demo_get, menu);
	} 
	
	@Override
	public boolean initMenuItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				getActivity().finish();
				break;
			case R.id.menu_refresh://刷新
				new BaseGetDataTask().execute();
				break;
			case R.id.menu_search://搜索
				startActivity(new Intent().setClass(getActivity(), DemoSearch_A.class));
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(getActivity(), DemoAdd_A.class));
				break;
		}
		return true;
	}

	//扩展视图缓存类
	public class MyViewCache extends ViewCache
	{
		public TextView titleView;
		public TextView foundtimeView;
		public ImageButton itemMenu;
	}

	@Override
	public FrameLayout getRootLayout()
	{
		return rootLayout;
	}
	
	@Override
	public List<Demo> getDataInBackground() 
	{
		return controller.get(getParams());
	}
	
	@Override
	public void executeUI(List<Demo> list)
	{
		MultiCheckAdapter adapter = new MultiCheckAdapter(
				getActivity(), list, R.layout.demo_get_item,
				new String[]{"title","foundtime"},new int[]{R.id.title,R.id.foundtime},
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
			Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show(); 
		}
		else
		{
			Toast.makeText(getActivity(), "操作失败，网络异常", Toast.LENGTH_LONG).show();
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
					getActivity().startActivity(new Intent().setClass(getActivity(), DemoUpd_A.class).putExtra("id", id_long));
					break;
				case 1://删除
					DemoGet_F.this.showDeleteDialog(id_str);
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
            getActivity().startActivity(new Intent().setClass(getActivity(), DemoGetById_A.class).putExtra("id", id));
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
		        		DemoGet_F.this.showDeleteDialog(listView.getIds());
		        		result = true;
		        	}
		        	else
		        	{
		        		Toast.makeText(getActivity(), "未选中 ！", Toast.LENGTH_SHORT).show();  
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

