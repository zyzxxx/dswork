package dswork.android.demo.component.alphabet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import dswork.android.R;
import dswork.android.lib.ui.AlphaBar;
import dswork.android.lib.ui.AlphaBar.OnAlphaBarListener;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleFragmentActivity;
import dswork.android.model.Person;
import dswork.android.service.PersonService;

public class AlphabetActivity extends OleFragmentActivity 
{
	@InjectView(id=R.id.listView) ListView listView;
	@InjectView(id=R.id.alphabar) AlphaBar alphaBar;//字母表
	private PersonService service;//注入service
	
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_alphabet);
		InjectUtil.injectView(this);//注入控件
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		service=new PersonService(this);
		List<Person> persons = service.query();
		
		//初始化字母表
		alphaBar.initAlphaMap(persons, 1, "name", null);//初始化首字母位置映射
		alphaBar.setAlphaBarListener(new MyOnAlphaBarListener());
		//初始化列表
		List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
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
		//采用简单适配器完成界面数据绑定
		SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.activity_alphabet_item,
				new String[]{"id","name","sortkey","phone","amount"},new int[]{R.id.id,R.id.name,R.id.sortkey,R.id.phone,R.id.amount});
		listView.setAdapter(adapter);
		
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.alphabet, menu);//填充菜单布局xml
	}
	
	private class MyOnAlphaBarListener implements OnAlphaBarListener
	{
		@Override
		public void getStr(String str) {
        	System.out.println("str:"+str);
			listView.setSelection(alphaBar.getAlphaPos(str));
		}
	}

}
