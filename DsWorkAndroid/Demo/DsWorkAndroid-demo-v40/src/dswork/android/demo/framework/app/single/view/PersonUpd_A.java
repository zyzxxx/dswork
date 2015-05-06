package dswork.android.demo.framework.app.single.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.demo.framework.app.single.controller.PersonController;
import dswork.android.demo.framework.app.single.model.Person;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.view.base.template.upd.BaseUpdOleActivity;

public class PersonUpd_A extends BaseUpdOleActivity<Person>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.name) EditText name_v;//姓名
	@InjectView(id=R.id.phone) EditText phone_v;//电话
	@InjectView(id=R.id.amount) EditText amount_v;//存款
	private PersonController controller;
	private Long id;

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.person_upd_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		controller = new PersonController(this);
		id = getIntent().getLongExtra("id", 0);//获取id主键
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
	}

	@Override
	public void initMenu(Menu menu) 
	{
//		getMenuInflater().inflate(R.menu.person_edit, menu);
	}

	public void save(View v)
	{
		Person p = new Person(
				name_v.getText().toString().trim(), 
				phone_v.getText().toString().trim(), 
				amount_v.getText().toString().trim());
		p.setId(id);
		controller.upd(p);
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, PersonGet_A.class));
	}

	@Override
	public FrameLayout getRootLayout()
	{
		return rootLayout;
	}

	@Override
	public Person getDataInBackground()
	{
		return controller.getById(id);
	}

	@Override
	public void executeUI(Person po) 
	{
		name_v.setText(po.getName());
		phone_v.setText(po.getPhone());
		amount_v.setText(po.getAmount());
	}
}
