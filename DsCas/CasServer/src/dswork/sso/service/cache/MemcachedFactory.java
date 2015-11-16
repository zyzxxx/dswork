/**
 * memcached缓存管理实现抽象类
 */
package dswork.sso.service.cache;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

public class MemcachedFactory
{
	static ICacheManager<IMemcachedCache> manager;

	private MemcachedFactory()
	{
	}

	public static IMemcachedCache getInstance(String xmlFile, String param)
	{
		if (manager == null)
		{
			init(xmlFile);
		}
		return manager.getCache(param);
	}

	public static void init(String xmlFile)
	{
		// System.out.println("--init--");
		manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());// manager初始化，可以通过配置来替换CacheManager实现
		// manager.setConfigFile("memcached.xml");//设置Cache Client配置文件
		manager.setConfigFile(xmlFile);// 设置Cache Client配置文件
		// manager.setResponseStatInterval(5 * 1000);// 设置Cache响应统计间隔时间，不设置则不进行统计
		manager.start();// Manager启动
	}

	public void destroy()
	{
		manager.stop();// manager结束
		// System.out.println("--ICacheManager-->end--");
	}
}
