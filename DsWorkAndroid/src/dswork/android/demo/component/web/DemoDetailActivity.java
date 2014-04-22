package dswork.android.demo.component.web;

import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.model.Demo;
import dswork.android.service.DemoController;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;

public class DemoDetailActivity extends OleActivity 
{
	@InjectView(id=R.id.id) TextView pk;//id
	@InjectView(id=R.id.title) TextView title;//姓名
	@InjectView(id=R.id.content) TextView content;//拼音
	@InjectView(id=R.id.foundtime) TextView foundtime;//电话
	private DemoController controller;//注入service

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_demo_detail);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		controller = new DemoController(this);
		Intent intent=getIntent();
		Long id = intent.getLongExtra("id",0);
		Demo po = controller.getById(id);
		pk.setText(String.valueOf(po.getId()));
		title.setText(po.getTitle());
		content.setText(po.getContent());
		foundtime.setText(po.getFoundtime());
	}

	@Override
	public void initMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.demo_detail, menu);		
	}
}
