package dswork.android.demo.framework.app.web.view;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.demo.framework.app.web.controller.DemoController;
import dswork.android.demo.framework.app.web.model.Demo;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.util.MyStrictMode;
import dswork.android.lib.view.v40.OleActivity;

public class DemoAdd_A extends OleActivity 
{
	@InjectView(id=R.id.title) EditText title;//标题
	@InjectView(id=R.id.content) EditText content;//内容
	@InjectView(id=R.id.foundtime) EditText foundtime;//创建时间
	@InjectView(id=R.id.btn_save) Button btnSave;//保存按钮
	DemoController controller;

	@Override
	public void initMainView() 
	{
		MyStrictMode.setPolicy();//webapp需要调用此方法
		setContentView(R.layout.demo_add_a);
		InjectUtil.injectView(this);//注入控件
		controller = new DemoController(this);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		btnSave.setOnClickListener(new SaveClickListener());
	}

	@Override
	public void initMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.demo_add, menu);
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
		Demo po = new Demo();
		po.setTitle(title.getText().toString().trim());
		po.setContent(content.getText().toString().trim());
		po.setFoundtime(foundtime.getText().toString().trim());
		String result = controller.add(po);
		if(!result.equals("1"))
		{
			Toast.makeText(this, "操作失败，网络异常", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
			this.finish();
			startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, Main_A.class));
		}
	}
}
