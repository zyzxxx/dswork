package dswork.android.demo.framework.app.web;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleActivity;
import dswork.android.model.Demo;

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
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
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
			//异步获取后台数据，并更新UI
			new GetBgDataTask().execute();  
		}
	}

	@Override
	public void initMenu(Menu menu) 
	{
//		getMenuInflater().inflate(R.menu.demo_edit, menu);
	}

	public void save(View v)
	{
		Demo po = new Demo();
		po.setTitle(title.getText().toString().trim());
		po.setContent(content.getText().toString().trim());
		po.setFoundtime(foundtime.getText().toString().trim());
		String result = controller.upd(po, ids);
		if(!result.equals("1"))
		{
			Toast.makeText(this, "操作失败，网络异常", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
			this.finish();
			startActivity(new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setClass(this, DemoMainActivity.class));
		}
	}
	
	/**
	 * 异步获取后台数据类
	 * @author ole
	 *
	 */
	class GetBgDataTask extends AsyncTask<String, Integer, Demo>
	{//继承AsyncTask  
        protected void onPreExecute () 
        {//在 doInBackground(Params...)之前被调用，在ui线程执行  
        	setProgressBarIndeterminateVisibility(true);
        }
        
        @Override  
        protected Demo doInBackground(String... params) 
        {//后台耗时操作 ，不能在后台线程操作UI
			Demo po = controller.getById(idsArr[0]);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
            return po;  
        }  
          
		protected void onPostExecute(Demo result) 
		{// 后台任务执行完之后被调用，在ui线程执行
			if (result != null)
			{
				Toast.makeText(DemoUpdActivity.this, "加载成功",Toast.LENGTH_LONG).show();
				id.setText(String.valueOf(result.getId()));
				title.setText(result.getTitle());
				content.setText(result.getContent());
				foundtime.setText(result.getFoundtime());
			} 
			else 
			{
				Toast.makeText(DemoUpdActivity.this, "加载失败，网络异常", Toast.LENGTH_LONG).show();
			}
			setProgressBarIndeterminateVisibility(false);
		}
    } 
}
