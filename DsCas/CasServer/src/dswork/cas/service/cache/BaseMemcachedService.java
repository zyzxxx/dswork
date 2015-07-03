/**
 * Memcached缓存的实现
 */
package dswork.cas.service.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

public abstract class BaseMemcachedService implements BaseCache
{
	/**
	 * key前缀
	 * @param prefix
	 * @return
	 */
	protected abstract String getPrefix();
	
	private IMemcachedCache cache;
	private String encodeKey(String key)
	{
		return this.getPrefix() + String.valueOf(key);
	}
	private String decodeKey(String key)
	{
		return String.valueOf(key).replaceFirst(this.getPrefix(), "");
	}

	public void setMemcachedFactory(IMemcachedCache cache)
	{
		this.cache = cache;
	}

	public boolean clear()
	{
		return cache.clear();
	}

	public boolean containsKey(String key)
	{
		return cache.containsKey(this.encodeKey(key));
	}

	public void destroy()
	{
		cache.destroy();
	}

	public Object get(String key)
	{
		return cache.get(this.encodeKey(key));
	}

	public Map<String, Object> getMulti(String[] key)
	{
		for (int i = 0; i < key.length; i++)
		{
			key[i] = this.encodeKey(key[i]);
		}
		Map<String, Object> _map = cache.getMulti(key);
		Map<String, Object> map = new HashMap<String, Object>();
		for(Entry<String,Object> obj : _map.entrySet())
		{
			map.put(this.decodeKey(obj.getKey()), obj.getValue());
		}
		_map = null;
		return map;
	}

	public Object put(String key, Object value)
	{
		return cache.put(this.encodeKey(key), value);
	}

	public Object put(String key, Object value, Date expiry)
	{
		return cache.put(this.encodeKey(key), value, expiry);
	}

	public Object put(String key, Object value, int TTL)
	{
		return cache.put(this.encodeKey(key), value, TTL);
	}

	public Object remove(String key)
	{
		return cache.remove(this.encodeKey(key));
	}
}
