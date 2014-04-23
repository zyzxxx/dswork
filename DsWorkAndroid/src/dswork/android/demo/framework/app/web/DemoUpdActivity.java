package dswork.android.demo.framework.app.web;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.model.Demo;
import dswork.android.util.InjectUtil;
import dswork.android.util.InjectUtil.InjectView;
import dswork.android.view.OleActivity;

public class DemoUpdActivity extends OleActivity
{
	@InjectView(id=R.id.id) TextView id;//主键
	@InjectView(id=R.id.title) EditText title;//标题
	@InjectView(id=R.id.content) EditText content;//内容
	@InjectView(id=R.id.foundtime) EditText foundtime;//创建时间
	private long[] idsArr = {};
	private String ids = "";
	DemoController controller;

	@Override
	public void initMainView() 
	{
		setContentView(R.layout.activity_demo_upd);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		ids = b.getString("ids");//获取id集合字符串
		idsArr = b.getLongArray("idsArr");//获取id集合数组
		Log.i("idsArr's length:",idsArr.length+"");
		//修改一条数据，就把旧数据读出
		if(idsArr.length == 1)
		{
			Demo po = controller.getById(idsArr[0]);
			id.setText(String.valueOf(po.getId()));
			title.setText(po.getTitle());
			content.setText(po.getContent());
			foundtime.setText(po.getFoundtime());
		}
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.demo_edit, menu);
	}

	public void save(View v)
	{
		Demo po = new Demo();
		po.setTitle(title.getText().toString().trim());
		po.setContent(content.getText().toString().trim());
		po.setFoundtime(foundtime.getText().toString().trim());
		String result = controller.upd(po, ids);
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DemoActivity.class));
	}
}
