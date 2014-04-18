package dswork.android.demo.component.db;

import dswork.android.R;
import dswork.android.model.Person;
import dswork.android.service.PersonService;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DbEditActivity extends OleActivity 
{
	@InjectView(id=R.id.name) EditText nameText;//姓名
	@InjectView(id=R.id.phone) EditText phoneText;//电话
	@InjectView(id=R.id.amount) EditText amountText;//存款
	private PersonService service;//注入service
	private long[] ids;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_db_edit);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		service=new PersonService(this);
		Intent intent=getIntent();
		ids = intent.getLongArrayExtra("ids");
		//修改一条数据，就把旧数据读出
		if(ids.length == 1)
		{
			Person p = service.getById(ids[0]);
			nameText.setText(p.getName());
			phoneText.setText(p.getPhone());
			amountText.setText(p.getAmount());
		}
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.db_edit, menu);
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
		Log.i("model值",p.toString());
		service.update(p,ids);
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DbActivity.class));
	}
}
