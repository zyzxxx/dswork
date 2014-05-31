package dswork.android.lib.view;

import java.util.List;
import java.util.Map;

public abstract class BaseGetF<T> extends OleSherlockFragment
{
	@SuppressWarnings("unchecked")
	/**
	 * 获取请求参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getParams()
	{
		List<Map<String,Object>> rtn_params = (List<Map<String, Object>>) getActivity().getIntent().getSerializableExtra("params");//获取查询参数
		return rtn_params.get(0);
	}
	
	public void showList()
	{
		
	}
}
