package dswork.android.demo.framework.app.web;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.DemoController;
import dswork.android.lib.util.InjectUtil;
import dswork.android.lib.util.InjectUtil.InjectView;
import dswork.android.lib.view.OleActivity;
import dswork.android.model.Demo;

public class DemoGetByIdA extends OleActivity 
{
	@InjectView(id=R.id.id) TextView pk;//id
	@InjectView(id=R.id.title) TextView title;//姓名
	@InjectView(id=R.id.content) TextView content;//拼音
	@InjectView(id=R.id.foundtime) TextView foundtime;//电话
	private DemoController controller;//注入service

	@Override
	public void initMainView() 
	{
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//允许标题栏显示圆形进度条
		setContentView(R.layout.demo_getbyid_a);
		InjectUtil.injectView(this);//注入控件
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
		controller = new DemoController(this);
		//异步获取后台数据，并更新UI
		new GetBgDataTask().execute();  
	}

	@Override
	public void initMenu(Menu menu)
	{
//		getMenuInflater().inflate(R.menu.demo_detail, menu);		
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
            Intent intent=getIntent();
    		Long id = intent.getLongExtra("id",0);
    		Demo po = controller.getById(id);
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
				Toast.makeText(DemoGetByIdA.this, "加载成功",Toast.LENGTH_LONG).show();
				pk.setText(String.valueOf(result.getId()));
				title.setText(result.getTitle());
				content.setText(result.getContent());
				foundtime.setText(result.getFoundtime());
			} 
			else 
			{
				Toast.makeText(DemoGetByIdA.this, "加载失败，网络异常", Toast.LENGTH_LONG).show();
			}
			setProgressBarIndeterminateVisibility(false);
		}
    } 
}
