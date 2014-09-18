package dswork.android.demo.framework.app.greendao.view;

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.demo.framework.app.greendao.dao.CustomDaoUtil;
import dswork.android.demo.framework.app.greendao.dao.Person;
import dswork.android.demo.framework.app.greendao.dao.PersonDao;
import dswork.android.lib.ui.MultiCheck.MultiCheckActionMode;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter;
import dswork.android.lib.ui.MultiCheck.MultiCheckAdapter.ItemMenuDialog;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.MultiCheckActionModeListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.OnItemClickNotMultiListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.PullUpToRefreshListener;
import dswork.android.lib.ui.MultiCheck.MultiCheckListView.ViewCache;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.base.get.BaseGetOleActivity;

public class PersonGet_A extends BaseGetOleActivity<Person>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.listView) MultiCheckListView listView;//列表视图
    private PersonDao personDao;

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.person_get_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		personDao = CustomDaoUtil.getDao(this);//获取dao
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
				isEmptyParams(true);
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
	}

	@Override
	public FrameLayout getRootLayout() 
	{
		return rootLayout;
	}

	@Override
	public List<Person> getDataInBackground() 
	{
		return queryPage(getParams(), 0, 5);
	}
	@Override
	public void executeUI(List<Person> list)
	{
		MultiCheckAdapter adapter = new MultiCheckAdapter(
				PersonGet_A.this, list, R.layout.person_get_item2,
				new String[]{"name","sortkey"}, new int[]{R.id.name,R.id.sortkey},
				new MyViewCache());
		adapter.setItemMenuDialog(new MyItemMenuDialog());//使用ItemMenuDialog,不用时可删掉
		listView.init(list, adapter);//初始化MultiCheck
//		listView.setFastScrollDrawable(R.drawable.ic_menu_moreoverflow);//修改快速滚动条图片
		listView.setOnItemClickNotMultiListener(new MyOnItemClickNotMultiListener());//列表项单击事件（非多选模式）
		listView.setMultiCheckActionModeListener(new MyMultiCheckActionModeListener());//实例化ActionMode
		//设置PullRefresh属性
		listView.setMaxDataNum(Integer.parseInt(String.valueOf(personDao.count())));//设置数据最大值
		listView.setAvgDataNum(5);//平均每次取n条数据
		listView.setPerDataNum(5);//每秒取n条数据
		listView.setPullUpToRefreshListener(new MyPullUpToRefreshListener());//上拉刷新
	}
	
	@Override
	public void executeDel(Long[] ids)
	{
		try{
			personDao.deleteByKeyInTx(ids);//执行删除
			listView.refreshListView(queryPage(getParams(), 0, listView.getCurDataNum()));//刷新列表
			Toast.makeText(PersonGet_A.this, "删除成功", Toast.LENGTH_SHORT).show(); 
		}catch(Exception e){
			Toast.makeText(PersonGet_A.this, "操作失败，"+e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	//ItemMenu对话框item点击事件，不用时可删掉
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
					PersonGet_A.this.showDeleteDialog(new Long[]{id_long});
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
		public Callback getActionModeCallback(){
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
		        		PersonGet_A.this.showDeleteDialog(listView.getIdArray());
		        		result = true;
		        	}else{
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
	//上拉刷新事件
	private class MyPullUpToRefreshListener implements PullUpToRefreshListener
	{
		@Override
		public void pullUpToRefresh() {
			queryPage(getParams(), listView.getCurDataNum(), listView.getLoadDataNum());//获取下一页数据
		}
	}
	
	//分页查询
	public List<Person> queryPage(Map m, int offset, int maxResult)
	{
		listView.setMaxDataNum(Integer.parseInt(String.valueOf(personDao.count())));//设置数据最大值
		//获取下一页数据
		List<Person> list = CustomDaoUtil.getPersonQueryBuilder(this, m).offset(offset).limit(maxResult).list();
		for(Person po : list) listView.addDataItem(po);
		return list;
	}
}
