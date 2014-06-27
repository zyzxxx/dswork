package dswork.android.demo.component.loadmore;

import java.util.HashMap;
import java.util.List;
import android.view.Menu;
import android.widget.SimpleAdapter;
import dswork.android.R;
import dswork.android.lib.ui.MoreListView;
import dswork.android.lib.ui.MoreListView.PullDownToRefreshListener;
import dswork.android.lib.ui.MoreListView.PullUpToRefreshListener;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleFragmentActivity;
import dswork.android.model.Person;
import dswork.android.service.PersonService;

public class LoadMoreActivity extends OleFragmentActivity
{
	@InjectView(id=R.id.mListView) MoreListView mListView;
	private PersonService service;//注入service
	private SimpleAdapter adapter;
	private List<Person> persons;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_loadmore);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		service=new PersonService(this);
		mListView.setMaxDataNum(service.getCount("person"));//设置数据最大值
		mListView.setAvgDataNum(10);//平均每次取10条数据
		mListView.setPerDataNum(10);//每秒取10条数据
		//获取首页数据
		queryScroll(0, mListView.getAvgDataNum());
		//采用简单适配器完成界面数据绑定
		adapter=new SimpleAdapter(this, mListView.getData(), R.layout.activity_loadmore_item,
				new String[]{"id","name","sortkey","phone","amount"},new int[]{R.id.id,R.id.name,R.id.sortkey,R.id.phone,R.id.amount});
		mListView.setAdapter(adapter);
		//上拉刷新
		mListView.setPullUpToRefreshListener(new PullUpToRefreshListener(){
			@Override
			public void pullUpToRefresh() {
				queryScroll(mListView.getCurDataNum(), mListView.getLoadDataNum());//获取首页数据
			}
		});
		//下拉刷新
		mListView.setPullDownToRefreshListener(new PullDownToRefreshListener(){
			@Override
			public void pullDownToRefresh() {
				queryScroll(0, mListView.getAvgDataNum());
			}
		});
	}
    
	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loadmore, menu);//填充菜单布局xml
	}
	
	public void queryScroll(int offset, int maxResult)
	{
		persons = service.queryPage(null, offset, maxResult);
		for(Person p:persons)
		{
			HashMap<String,Object> item=new HashMap<String,Object>();
			item.put("id", p.getId());
			item.put("name", p.getName());
			item.put("sortkey", p.getSortkey());
			item.put("phone",p.getPhone());
			item.put("amount",p.getAmount());
			mListView.addDataItem(item);
		}
	}
}
