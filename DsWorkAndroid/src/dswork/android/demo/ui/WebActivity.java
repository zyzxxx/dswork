package dswork.android.demo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.android.R;
import dswork.android.R.id;
import dswork.android.R.layout;
import dswork.android.R.menu;
import dswork.android.model.Demo;
import dswork.android.service.DemoService;
import dswork.android.util.MyStrictMode;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WebActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		MyStrictMode.setPolicy();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
		
		// 获取activity_main.xml中的listView对象
		ListView listView = (ListView) this.findViewById(R.id.listView);
		// 通过adapter填充数据到listView中
		try
		{
			Log.i("dswork", "yes");
			List<Demo> demos = DemoService.getJSONDemo();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for(Demo d : demos)
			{
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("id", "ID:" + d.getId());
				item.put("title", "标题：" + d.getTitle());
				item.put("content", "内容:" + d.getContent());
				item.put("foundtime", "创建时间：" + d.getFoundtime());
				data.add(item);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.activity_web_item, new String[]
			{
					"title", "foundtime"
			}, new int[]
			{
					R.id.title, R.id.foundtime
			});
			listView.setAdapter(adapter);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alphabet, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
