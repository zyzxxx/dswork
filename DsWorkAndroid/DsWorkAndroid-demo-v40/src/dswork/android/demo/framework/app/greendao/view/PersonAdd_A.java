package dswork.android.demo.framework.app.greendao.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.demo.framework.app.greendao.dao.CustomDaoUtil;
import dswork.android.demo.framework.app.greendao.dao.Person;
import dswork.android.demo.framework.app.greendao.dao.PersonDao;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.v40.OleActivity;

public class PersonAdd_A extends OleActivity 
{
	@InjectView(id=R.id.name) EditText nameText;//姓名
	@InjectView(id=R.id.phone) EditText phoneText;//电话
	@InjectView(id=R.id.amount) EditText amountText;//存款
	@InjectView(id=R.id.btn_save) Button btnSave;//保存按钮
	private PersonDao personDao;

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.person_add_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		personDao = CustomDaoUtil.getDao(this);//获取dao
		btnSave.setOnClickListener(new SaveClickListener());
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.person_add, menu);
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
		Person p = new Person(
				null,
				nameText.getText().toString().trim(), 
				phoneText.getText().toString().trim(), 
				amountText.getText().toString().trim(),
				null);
		personDao.insert(p);
		Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, PersonGet_A.class));
	}
}
