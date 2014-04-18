package dswork.android.demo.component.db;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.model.Person;
import dswork.android.service.PersonService;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;

public class DbAddActivity extends OleActivity 
{
	@InjectView(id=R.id.btn_save) Button btnSave;//保存按钮
	private PersonService service;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_db_add);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		service = new PersonService(this);
		btnSave.setOnClickListener(new SaveClickListener());
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.db_add, menu);
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
		EditText nameText = (EditText) findViewById(R.id.name);
		EditText phoneText = (EditText) findViewById(R.id.phone);
		EditText amountText = (EditText) findViewById(R.id.amount);
		Person p = new Person(
				nameText.getText().toString().trim(), 
				phoneText.getText().toString().trim(), 
				amountText.getText().toString().trim());
		Log.i("model值","name:"+p.getName()+",phone:"+p.getPhone()+",amount:"+p.getAmount());
		service.add(p);
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DbActivity.class));
	}
}
