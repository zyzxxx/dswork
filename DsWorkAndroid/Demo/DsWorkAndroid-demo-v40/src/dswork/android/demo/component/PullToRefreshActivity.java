package dswork.android.demo.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import dswork.android.R;
import dswork.android.demo.framework.app.single.model.Person;
import dswork.android.demo.framework.app.single.service.PersonService;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.view.base.interfaces.OleFragmentActivity;

public class PullToRefreshActivity extends OleFragmentActivity
{
	@InjectView(id=R.id.pull_refresh_list) PullToRefreshListView mPullRefreshListView;
	private PersonService service;//注入service
	private SimpleAdapter adapter;
	private List<Person> persons;
	private List<HashMap<String,Object>> data;
	private static int maxResult = 10;//每次取n条

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_pulltorefresh);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		service=new PersonService(this);
		data = new ArrayList<HashMap<String,Object>>();
		
		queryPage(null, 0, maxResult);
		adapter=new SimpleAdapter(this, data, R.layout.activity_pulltorefresh_item,
			new String[]{"id","name","sortkey","phone","amount"},new int[]{R.id.id,R.id.name,R.id.sortkey,R.id.phone,R.id.amount});
		mPullRefreshListView.setMode(Mode.BOTH);//设置刷新方式
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);// Update the LastUpdatedLabel
				new GetDataTask().execute();// Do work to refresh the list here.
			}
		});
		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				Toast.makeText(PullToRefreshActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		registerForContextMenu(mPullRefreshListView);// Need to use the Actual ListView when registering for Context Menu
		mPullRefreshListView.setAdapter(adapter);
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<Person>> {
		@Override
		protected List<Person> doInBackground(Void... params) {
			queryPage(null, adapter.getCount(), maxResult);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return persons;
		}

		@Override
		protected void onPostExecute(List<Person> result) {
			adapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();// Call onRefreshComplete when the list has been refreshed.
			super.onPostExecute(result);
		}
	}
    
	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loadmore, menu);//填充菜单布局xml
	}
	
	public void queryPage(Map m, int offset, int maxResult)
	{
		persons = service.queryPage(m, offset, maxResult);
		for(Person p:persons)
		{
			HashMap<String,Object> item=new HashMap<String,Object>();
			item.put("id", p.getId());
			item.put("name", p.getName());
			item.put("sortkey", p.getSortkey());
			item.put("phone",p.getPhone());
			item.put("amount",p.getAmount());
			data.add(item);
		}
	}
}
