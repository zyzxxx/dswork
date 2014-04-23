package dswork.android.demo.framework.app.single;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.PersonController;
import dswork.android.model.Person;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;

public class PersonEditActivity extends OleActivity 
{
	@InjectView(id=R.id.name) EditText nameText;//姓名
	@InjectView(id=R.id.phone) EditText phoneText;//电话
	@InjectView(id=R.id.amount) EditText amountText;//存款
	private PersonController controller;
	private long[] idsArr = {};
	private String ids = "";

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_person_edit);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		controller = new PersonController(this);
		Intent intent=getIntent();
		Bundle b = intent.getExtras();
		ids = b.getString("ids");//获取id集合字符串
		idsArr = b.getLongArray("idsArr");//获取id集合数组
		Log.i("idsArr's length:",idsArr.length+"");
		//修改一条数据，就把旧数据读出
		if(idsArr.length == 1)
		{
			Person p = controller.getById(idsArr[0]);
			nameText.setText(p.getName());
			phoneText.setText(p.getPhone());
			amountText.setText(p.getAmount());
		}
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.person_edit, menu);
	}

	public void save(View v)
	{
		Person p = new Person(
				nameText.getText().toString().trim(), 
				phoneText.getText().toString().trim(), 
				amountText.getText().toString().trim());
		controller.upd(p, ids);
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, PersonActivity.class));
	}
}
