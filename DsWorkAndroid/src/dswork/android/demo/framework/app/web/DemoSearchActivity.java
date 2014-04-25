package dswork.android.demo.framework.app.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.util.MyStrictMode;
import dswork.android.view.OleActivity;

public class DemoSearchActivity extends OleActivity
{
	@InjectView(id=R.id.title) EditText title;//标题
	@InjectView(id=R.id.content) EditText content;//内容
	@InjectView(id=R.id.foundtime) EditText foundtime;//创建时间
	@InjectView(id=R.id.btn_search) Button btnSearch;//搜索按钮
	DemoController controller;
	
	@Override
	public void initMainView() 
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		setContentView(R.layout.activity_demo_search);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		btnSearch.setOnClickListener(new SearchClickListener());
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.demo_search, menu);
	}
	
	private final class SearchClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			search(v);
		}
	}
	
	public void search(View v)
	{
		Map m = new HashMap();
		m.put("title", title.getText().toString().trim());
		m.put("content", content.getText().toString().trim());
		m.put("foundtime", foundtime.getText().toString().trim());
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
		params.add(m);
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DemoActivity.class).putExtra("params", (Serializable)params));
	}
}
