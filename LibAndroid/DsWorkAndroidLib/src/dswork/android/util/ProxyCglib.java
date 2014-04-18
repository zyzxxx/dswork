package dswork.android.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import dswork.android.db.BaseDao;




public class ProxyCglib implements InvocationHandler
{
	//要代理的原始service对象
	private Object serviceObj;
	//基类dao
	private BaseDao dao;
	
	public ProxyCglib(Object serviceObj, BaseDao dao)
	{
		this.serviceObj = serviceObj;
		this.dao = dao;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable 
	{
		Object result = null;
		//事务开始
		dao.beginTransaction();
		try
		{
			//调用原始对象的方法
			result = method.invoke(serviceObj, args);
			//设置事务的标志为true	
			dao.setTransactionSuccessful();
		}
		//事务结束
		finally
		{
			dao.endTransaction();
		}
		dao.close();
		return result;
	}

}
