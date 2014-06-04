package dswork.android.demo.framework.app.web;

import java.util.HashMap;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.lib.ui.MultiCheck.MultiCheckActionMode2;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter2;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter2.ItemMenuDialog;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView2;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView2.MultiCheckActionModeListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView2.OnItemClickNotMultiListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView2.ViewCache;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.util.MyStrictMode;
import dswork.android.lib.view.BaseGetOleSherlockFragment;
import dswork.android.model.Demo;

public class DemoGetF extends BaseGetOleSherlockFragment<Demo>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.listView) MultiCheckListView2 listView;//列表视图
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
		View convertView = inflater.inflate(R.layout.demo_get_f, container, false);
		InjectUtil.injectView(this, convertView);//注入控件
		controller = new DemoController(getActivity());
		//异步获取后台数据，并更新UI
		new BaseGetDataTask().execute();
		return convertView;
	}

	@Override
	public void initMenu(Menu menu, MenuInflater inflater) 
	{
		inflater.inflate(R.menu.demo_list, menu);
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
				startActivity(new Intent().setClass(getActivity(), DemoSearchA.class));
				break;
			case R.id.menu_add://添加
				startActivity(new Intent().setClass(getActivity(), DemoAddA.class));
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
	public FrameLayout getRootLayout() {
		return rootLayout;
	}
	
	@Override
	public List<Demo> getDataInBackground() {
		return controller.get(getParams());
	}
	
	@Override
	public void executeUI(List<Demo> list) {
		MultiCheckAdapter2 adapter = new MultiCheckAdapter2(
				getActivity(), list, R.layout.demo_get_item,
				new String[]{"title","foundtime"},new int[]{R.id.title,R.id.foundtime},
				new MyViewCache());
		adapter.setItemMenuDialog(new MyItemMenuDialog());//实例化ItemMenuDialog
		listView.initMultiCheck(list, adapter);//初始化MultiCheck
		listView.setOnItemClickNotMultiListener(new MyOnItemClickNotMultiListener());//列表项单击事件（非多选模式）
		listView.setMultiCheckActionModeListener(new MyMultiCheckActionModeListener());//实例化ActionMode
	}
	
	//列表项单击事件（非多选模式）
	private class MyOnItemClickNotMultiListener implements OnItemClickNotMultiListener
	{
		@Override
		public void onClick(View v) 
		{
			TextView _itemId = (TextView)v.findViewById(R.id.itemId);
        	long id = Long.parseLong(_itemId.getText().toString());
            getActivity().startActivity(new Intent().setClass(getActivity(), DemoGetByIdA.class).putExtra("id", id));
		}
	}
	//ActionMode事件
	private class MyMultiCheckActionModeListener implements MultiCheckActionModeListener
	{
		@Override
		public Callback getActionModeCallback() 
		{
			return new MultiCheckActionMode2(this, R.menu.context_menu, listView);
		}
		@Override
		public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item)
		{
			boolean result = false;
			switch(item.getItemId())
			{
				case R.id.menu_del_confirm:
					result = delete();//删除
					break;
				case R.id.menu_chkall:
					listView.toggleChecked(item);//切换全/不选
					result = true;
					break;
			}
			return result;
		}
		private boolean delete()
		{
        	if(listView.getIdList().size()>0)
        	{
        		new AlertDialog.Builder(getActivity())
        		.setTitle(R.string.confirm_del)
        		.setIcon(android.R.drawable.ic_delete)
        		.setNegativeButton(R.string.no, null)
        		.setPositiveButton(R.string.yes, new delListener())
        		.show();
        		return true;
        	}
        	else
        	{
        		Toast.makeText(getActivity(), "未选中 ！", Toast.LENGTH_SHORT).show();  
                return false;
        	}
		}
	}
	//删除操作监听类
	private class delListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
    		String result = controller.deleteBatch(listView.getIds());//执行删除
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
	}
	//修改操作监听类
	private class updListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			Bundle b = new Bundle();
			b.putString("ids", listView.getIds());
			b.putLongArray("idsArr", listView.getIdArray());
			getActivity().startActivity(new Intent().setClass(getActivity(), DemoUpdA.class).putExtras(b));
		}
	}
	//ItemMenu对话框item点击事件
	private class MyItemMenuDialog implements ItemMenuDialog
	{
		@Override
		public int setItemMenuDialogTitleRes() {
			return R.string.item_menu_title;
		}

		@Override
		public int setItemMenuDialogItemsRes() {
			return R.array.my_item_menu;
		}
		@Override
		public void onItemClick(final String id_s, long id_l, int which) 
		{
			switch (which) 
			{
				case 0://修改
					Bundle b = new Bundle();
					b.putString("ids", id_s);
					long[] idsArr = {id_l};
					b.putLongArray("idsArr", idsArr);
					getActivity().startActivity(new Intent().setClass(getActivity(), DemoUpdA.class).putExtras(b));
					break;
				case 1://删除
	        		new AlertDialog.Builder(getActivity())
	        		.setTitle(R.string.confirm_del)
	        		.setIcon(android.R.drawable.ic_delete)
	        		.setNegativeButton(R.string.no, null)
	        		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
	        		{
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							String result = controller.deleteBatch(id_s);//执行删除
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
					})
	        		.show();
		    		break;
			}
		}
	}
}

