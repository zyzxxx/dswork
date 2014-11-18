package dswork.android.demo.framework.app.greendao.view;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.demo.framework.app.greendao.dao.CustomDaoUtil;
import dswork.android.demo.framework.app.greendao.dao.Person;
import dswork.android.demo.framework.app.greendao.dao.PersonDao;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.view.v40.base.getbyid.BaseGetByIdOleActivity;

public class PersonGetById_A extends BaseGetByIdOleActivity<Person>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.id) TextView id_v;//id
	@InjectView(id=R.id.name) TextView name_v;//姓名
	@InjectView(id=R.id.sortkey) TextView sortkey_v;//拼音
	@InjectView(id=R.id.phone) TextView phone_v;//电话
	@InjectView(id=R.id.amount) TextView amount_v;//存款
    private PersonDao personDao;
	private Long id;

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
		setContentView(R.layout.person_getbyid_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		personDao = CustomDaoUtil.getDao(this);//获取dao
		id = getIntent().getLongExtra("id", 0);//获取id主键
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
	}

	@Override
	public void initMenu(Menu menu)
	{
//		getMenuInflater().inflate(R.menu.person_detail, menu);		
	}

	@Override
	public FrameLayout getRootLayout()
	{
		return rootLayout;
	}

	@Override
	public Person getDataInBackground() 
	{
		return personDao.loadByRowId(id);
	}

	@Override
	public void executeUI(Person po)
	{
		id_v.setText(po.getId().toString());
		name_v.setText(po.getName());
		sortkey_v.setText(po.getSortkey());
		phone_v.setText(po.getPhone());
		amount_v.setText(po.getAmount());
	}
}
