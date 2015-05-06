package dswork.android.lib.view.base.template.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import dswork.android.lib.view.base.R;
import dswork.android.lib.view.base.interfaces.OleFragmentActivity;

public abstract class BaseGetOleFragmentActivity<T> extends OleFragmentActivity
{
	private Boolean isEmptyParams = false;
	private Map<String,Object> params;
	/**
	 * 获取查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getParams()
	{
		params = new HashMap<String,Object>();
		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getIntent().getSerializableExtra("params");//获取查询参数
		if(null != rtn_params && !isEmptyParams) params = rtn_params.get(0);
		return params;
	}
	/**
	 * 是否设空查询参数
	 */
	public void isEmptyParams(Boolean b)
	{
		isEmptyParams = b;
	}
	
	/**
	 * 执行删除
	 * @param ids Long类型数组
	 */
	public void executeDel(Long[] ids){};
	/**
	 * 显示删除对话�?
	 * @param ids Long类型数组
	 */
	public void showDeleteDialog(final Long[] ids)
	{
		new AlertDialog.Builder(this)
		.setTitle(R.string.confirm_del)
		.setIcon(android.R.drawable.ic_delete)
		.setNegativeButton(R.string.no, null)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				executeDel(ids);
			}
		})
		.show();
	}
	
	/**
	 * 获取列表页根布局FrameLayout对象
	 * @return FrameLayout
	 */
	public abstract FrameLayout getRootLayout();
	/**
	 * 获取后台数据集合
	 * @return List<T>
	 */
	public abstract List<T> getDataInBackground();
	/**
	 * 更新UI
	 * @param list
	 */
	public abstract void executeUI(List<T> list);
	
	/*异步获取后台数据更新UI*/
	public class BaseGetDataTask extends AsyncTask<String, Integer, List<T>>
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
        protected List<T> doInBackground(String... _params) 
        {//后台耗时操作，获取列表数据，不能在后台线程操作UI
            return getDataInBackground();  
        }

		protected void onPostExecute(List<T> list) 
		{// 后台任务执行完之后被调用，在ui线程执行
			if (list != null)
			{
				executeUI(list);
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
