package dswork.android.demo.framework.app.web;

import java.util.HashMap;
import java.util.Map;
import dswork.android.R;
import dswork.android.service.DemoController;
import dswork.android.util.InjectUtil;
import dswork.android.util.MyStrictMode;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DemoAddActivity extends OleActivity 
{
	@InjectView(id=R.id.title) EditText title;//标题
	@InjectView(id=R.id.content) EditText content;//内容
	@InjectView(id=R.id.foundtime) EditText foundtime;//创建时间
	@InjectView(id=R.id.btn_save) Button btnSave;//保存按钮
	DemoController controller;

	@Override
	public void initMainView() 
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		setContentView(R.layout.activity_demo_add);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		btnSave.setOnClickListener(new SaveClickListener());
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.demo_add, menu);
	}
	
	private final class SaveClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			save(v);
		}
	}
	
	public void save(View v)
	{
		Map m = new HashMap();
		m.put("title", title.getText().toString().trim());
		m.put("content", content.getText().toString().trim());
		m.put("foundtime", foundtime.getText().toString().trim());
		String result = controller.add(m);
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DemoActivity.class));
	}
}
