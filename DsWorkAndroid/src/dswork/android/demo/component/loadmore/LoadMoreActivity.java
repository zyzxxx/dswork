package dswork.android.demo.component.loadmore;

import java.util.HashMap;
import java.util.List;
import dswork.android.R;
import dswork.android.model.Person;
import dswork.android.service.PersonService;
import dswork.android.ui.MoreListView;
import dswork.android.ui.MoreListView.OnLoadMoreListener;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleFragmentActivity;
import android.view.Menu;
import android.widget.SimpleAdapter;

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
		mListView.setAvgDataNum(5);//平均每次取5条数据
		mListView.setPerDataNum(10);//每秒取10条数据
//		mListView.setLoadScroll(true);//是否需要下拉刷新效果，默认false
		//初始化首页数据
		persons = service.queryScroll(0, mListView.getAvgDataNum());//获取首页数据
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
		//采用简单适配器完成界面数据绑定
		adapter=new SimpleAdapter(this, mListView.getData(), R.layout.activity_loadmore_item,
				new String[]{"id","name","sortkey","phone","amount"},new int[]{R.id.id,R.id.name,R.id.sortkey,R.id.phone,R.id.amount});
		mListView.setAdapter(adapter);
		//由使用者通过实现OnLoadMoreListener接口获取下一页数据
		mListView.setOnLoadMoreListener(new OnLoadMoreListener()
		{
			@Override
			public void loadMoreData() 
			{
				persons = service.queryScroll(mListView.getCurDataNum(), mListView.getLoadDataNum());//获取首页数据
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
		});
	}
    
	@Override
	public void initMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loadmore, menu);//填充菜单布局xml
	}
}
