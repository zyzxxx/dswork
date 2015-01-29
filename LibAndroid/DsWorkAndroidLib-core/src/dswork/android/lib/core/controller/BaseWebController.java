package dswork.android.lib.core.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

import dswork.android.lib.core.util.webutil.HttpActionObj;
import dswork.android.lib.core.util.webutil.HttpUtil;

public abstract class BaseWebController
{
	/**
	 * 设置模块路径
	 * @param path
	 */
	public abstract String getModulePath();
	
	/**
	 * 发送Http请求
	 * @param actionPath 请求路径
	 * @param m 查询参数
	 * @return String
	 */
	public String sendHttpAction(String actionPath, Map m)
	{
		HttpActionObj actionObj = new HttpActionObj(getModulePath()+actionPath, m);
		return HttpUtil.sendHttpAction(actionObj);
	}
	
	/**
	 * 获取服务器图片
	 * @param action
	 * @param m
	 * @return
	 */
	public Bitmap sendHttpActionBitmap(String action, Map m)
	{
		HttpActionObj actionObj = new HttpActionObj(getModulePath()+action, m);
		return HttpUtil.sendHttpActionBitmap(actionObj);
	}
	
	/**
	 * 获取model转换成的Map
	 * @param o
	 * @param isNeedPk 是否需要获取主键
	 * @return Map<String, String>
	 */
	public Map<String, String> getModelMap(Object o, Boolean isNeedPk)
	{
		Map<String, String> m = new HashMap<String, String>();
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
	 * @return Map<String, String>
	 */
	public Map<String, String> getKeyIndexMap(String keyIndex)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("keyIndex", keyIndex);
		return m;
	}
}
