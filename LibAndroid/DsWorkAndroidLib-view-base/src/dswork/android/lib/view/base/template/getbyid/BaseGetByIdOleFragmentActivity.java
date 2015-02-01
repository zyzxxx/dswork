package dswork.android.lib.view.base.template.getbyid;

import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import dswork.android.lib.view.base.interfaces.OleFragmentActivity;
import dswork.android.lib.view.v40.R;

public abstract class BaseGetByIdOleFragmentActivity<T> extends OleFragmentActivity
{
	/**
	 * 获取列表页根布局FrameLayout对象
	 * @return FrameLayout
	 */
	public abstract FrameLayout getRootLayout();
	/**
	 * 获取后台数据对象
	 * @return T
	 */
	public abstract T getDataInBackground();
	/**
	 * 更新UI
	 * @param T po对象
	 */
	public abstract void executeUI(T po);
	
	/*异步获取后台数据更新UI*/
	public class BaseGetDataTask extends AsyncTask<String, Integer, T>
	{
		ProgressBar pb = new ProgressBar(getApplicationContext());
		@Override
		protected void onPreExecute() 
		{
			//创建ProgressBar
			pb.setVisibility(ProgressBar.VISIBLE);
			FrameLayout.LayoutParams pbViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			pbViewParams.gravity = Gravity.CENTER;
			getRootLayout().addView(pb,pbViewParams);
		}
		
		@Override  
        protected T doInBackground(String... _params) 
        {//后台耗时操作，获取列表数据，不能在后台线程操作UI
            return getDataInBackground();  
        }

		protected void onPostExecute(T po) 
		{// 后台任务执行完之后被调用，在ui线程执行
			if (po != null)
			{
				executeUI(po);
				Toast.makeText(getApplicationContext(), "加载成功",Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				Toast.makeText(getApplicationContext(), "加载失败，网络异常", Toast.LENGTH_SHORT).show();
			}
			pb.setVisibility(ProgressBar.GONE);//隐藏圆形进度�?
		}
    }
}
