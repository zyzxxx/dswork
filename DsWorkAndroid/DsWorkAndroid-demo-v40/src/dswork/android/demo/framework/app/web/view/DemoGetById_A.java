package dswork.android.demo.framework.app.web.view;

import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.demo.framework.app.web.controller.DemoController;
import dswork.android.demo.framework.app.web.model.Demo;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.v40.base.getbyid.BaseGetByIdOleActivity;

public class DemoGetById_A extends BaseGetByIdOleActivity<Demo>
{
	@InjectView(id=R.id.rootLayout) FrameLayout rootLayout;//根布局
	@InjectView(id=R.id.id) TextView id_v;//id
	@InjectView(id=R.id.title) TextView title_v;//姓名
	@InjectView(id=R.id.content) TextView content_v;//拼音
	@InjectView(id=R.id.foundtime) TextView foundtime_v;//电话
	private DemoController controller;
	private Long id;

	@Override
	public void initMainView() 
	{
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
		setContentView(R.layout.demo_getbyid_a);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		id = getIntent().getLongExtra("id", 0);//获取id主键
		new BaseGetDataTask().execute();//异步获取后台数据，并更新UI
	}

	@Override
	public void initMenu(Menu menu)
	{
//		getMenuInflater().inflate(R.menu.demo_detail, menu);		
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
}
