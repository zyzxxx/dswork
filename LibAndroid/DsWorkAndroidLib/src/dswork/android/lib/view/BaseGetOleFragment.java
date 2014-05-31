package dswork.android.lib.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseGetOleFragment<T> extends OleFragment
{
	private Map params = new HashMap();//查询参数
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
	
	public void showList()
	{
		
	}
}
