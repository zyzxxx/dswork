package dswork.android.lib.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public abstract class BaseGetOleSherlockFragment<T> extends OleSherlockFragment
{
	private Map<String,Object> params = new HashMap<String,Object>();//查询参数
	@SuppressWarnings("unchecked")
	/**
	 * 获取请求参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getParams()
	{
		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getActivity().getIntent().getSerializableExtra("params");//获取查询参数
		if(null != rtn_params) params = rtn_params.get(0);
		return params;
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
		ProgressBar pb = new ProgressBar(getActivity());
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
        {//后台耗时操作，不能在后台线程操作UI
    		//获取列表信息
            return getDataInBackground();  
        }

		protected void onPostExecute(List<T> list) 
		{// 后台任务执行完之后被调用，在ui线程执行
			if (list != null)
			{
				executeUI(list);
				Toast.makeText(getActivity(), "加载成功",Toast.LENGTH_SHORT).show();
			} 
			else 
			{
				Toast.makeText(getActivity(), "加载失败，网络异常", Toast.LENGTH_SHORT).show();
			}
			pb.setVisibility(ProgressBar.GONE);//隐藏圆形进度条
		}
    }
}
