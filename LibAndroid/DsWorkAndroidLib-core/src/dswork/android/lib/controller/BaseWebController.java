package dswork.android.lib.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import dswork.android.lib.util.webutil.HttpPostObj;
import dswork.android.lib.util.webutil.HttpUtil;

public abstract class BaseWebController
{
	/**
	 * 设置模块路径
	 * @param path
	 */
	public abstract String getModulePath();
	
	/**
	 * 发送Http请求
	 * @param action 请求路径
	 * @param m 查询参数
	 * @return String
	 */
	public String sendRequest(String action, Map m)
	{
		HttpPostObj postObj = new HttpPostObj(getModulePath()+action, m);
		return HttpUtil.sendHttpPost(postObj);
	}
	
	/**
	 * 获取model转换成的Map
	 * @param o
	 * @param isNeedPk 是否需要获取主键
	 * @return
	 */
	public Map<String, Object> getModelMap(Object o, Boolean isNeedPk)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		if(isNeedPk)
		{
			try{
				m.put("id", String.valueOf(o.getClass().getSuperclass().getMethod("getId").invoke(o)));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//当前类fields
		Field[] fields = o.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			try
			{
				String _key = fields[i].getName();
				Method mth = o.getClass().getMethod("get"+fields[i].getName().substring(0,1).toUpperCase()+fields[i].getName().substring(1));
				String _value = String.valueOf(mth.invoke(o));
				m.put(_key, _value);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return m;
	}

	/**
	 * 获取keyIndex的map
	 * @param keyIndex
	 * @return
	 */
	public Map<String, Object> getKeyIndexMap(Object keyIndex)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("keyIndex", keyIndex);
		return m;
	}
}
