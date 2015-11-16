/**
 * 缓存实现类的接口
 */
package dswork.sso.service.cache;

import java.util.Date;
import java.util.Map;

public interface BaseCache
{
	/**
	 * 删除缓存内的数据
	 * @return
	 */
	public boolean clear();
	
	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * 释放Cache占用的资源
	 */
	public void destroy();

	/**
	 * 获取对象
	 * @param key
	 * @return
	 */
	public Object get(String key);

	/**
	 * 获取多个key对应的key&value
	 * @param keys
	 * @return
	 */
	public Map<String, Object> getMulti(String[] key);

	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(String key, Object value);

	/**
	 * 降低memcache的交互频繁造成的性能损失，因此采用本地cache结合memcache的方式
	 * @param key
	 * @param value
	 * @param 有效期(取的是客户端时间)
	 * @return
	 */
	public Object put(String key, Object value, Date expiry);

	/**
	 * 保降低memcache的交互频繁造成的性能损失，因此采用本地cache结合memcache的方式
	 * @param key
	 * @param value
	 * @param 设置有效期为距离当前时间后TTL秒。
	 * @return
	 */
	public Object put(String key, Object value, int TTL);

	/**
	 * 移出缓存数据
	 * @param key
	 * @return
	 */
	public Object remove(String key);
}
