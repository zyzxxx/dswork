package dswork.android.demo.framework.app.web.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.demo.framework.app.web.controller.DemoController;
import dswork.android.demo.framework.app.web.model.Demo;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.v40.base.upd.BaseUpdOleActivity;

public class DemoUpd_A extends BaseUpdOleActivity<Demo>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.id) TextView id_v;//主键
	@InjectView(id=R.id.title) EditText title_v;//标题
	@InjectView(id=R.id.content) EditText content_v;//内容
	@InjectView(id=R.id.foundtime) EditText foundtime_v;//创建时间
	private DemoController controller;
	private Long id;

	@SuppressLint("NewApi")
	@Override
	public void initMainView() 
	{
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
		setContentView(R.layout.demo_upd_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);//注入Controller
		id = getIntent().getLongExtra("id", 0);//获取id主键
		System.out.println("主键值："+id);
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
	}

	@Override
	public void initMenu(Menu menu) 
	{
//		getMenuInflater().inflate(R.menu.demo_edit, menu);
	}
	
	@Override
	public FrameLayout getRootLayout()
	{
		return rootLayout;
	}
	@Override
	public Demo getDataInBackground()
	{
		return controller.getById(id);
	}
	@Override
	public void executeUI(Demo po)
	{
		id_v.setText(String.valueOf(po.getId()));
		title_v.setText(po.getTitle());
		content_v.setText(po.getContent());
		foundtime_v.setText(po.getFoundtime());
	}

	public void save(View v)
	{
		Demo po = new Demo();
		po.setId(id);
		po.setTitle(title_v.getText().toString().trim());
		po.setContent(content_v.getText().toString().trim());
		po.setFoundtime(foundtime_v.getText().toString().trim());
		String result = controller.upd(po);
		if(!result.equals("1")){
			Toast.makeText(this, "操作失败，网络异常", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
			this.finish();
			startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, Main_A.class));
		}
	}
}
