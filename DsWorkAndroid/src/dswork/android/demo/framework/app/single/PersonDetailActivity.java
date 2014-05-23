package dswork.android.demo.framework.app.single;

import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.controller.PersonController;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleActivity;
import dswork.android.model.Person;

public class PersonDetailActivity extends OleActivity 
{
	@InjectView(id=R.id.id) TextView idText;//id
	@InjectView(id=R.id.name) TextView nameText;//姓名
	@InjectView(id=R.id.sortkey) TextView sortkeyText;//拼音
	@InjectView(id=R.id.phone) TextView phoneText;//电话
	@InjectView(id=R.id.amount) TextView amountText;//存款
	private PersonController controller;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_person_detail);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		controller = new PersonController(this);
		Intent intent=getIntent();
		long id = intent.getLongExtra("id",0);
		Person p = controller.getById(id);
		idText.setText(p.getId().toString());
		nameText.setText(p.getName());
		sortkeyText.setText(p.getSortkey());
		phoneText.setText(p.getPhone());
		amountText.setText(p.getAmount());
	}

	@Override
	public void initMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.person_detail, menu);		
	}
}
