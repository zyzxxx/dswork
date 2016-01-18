package dswork.core.data;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;

/**
 * 单模块服务类
 * @author skey
 * @version 1.0
 */
@SuppressWarnings("all")
public class MyService
{
	protected Log log = LogFactory.getLog(getClass());
	private MyDao dao;
	
	/**
	 * 注入MyDao
	 * @param dao MyDao
	 */
	public void setMyDao(MyDao dao)
	{
		this.dao = dao;
	}
	
	/**
	 * 获取MyDao
	 * @return MyDao
	 */
	public MyDao getMyDao()
	{
		return dao;
	}

	/**
	 * 新增对象
	 * @param namespace SQL的namespace
	 * @param entity 需要新增的对象模型
	 * @return int
	 */
	public int save(String namespace, Object entity)
	{
		return dao.executeInsert(namespace + ".insert", entity);
	}

	/**
	 * 根据主键删除对象
	 * @param namespace SQL的namespace
	 * @param p 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return int
	 */
	public int delete(String namespace, Long p)
	{
		return dao.executeDelete(namespace + ".delete", p);
	}

	/**
	 * 根据主键批量删除对象
	 * @param namespace SQL的namespace
	 * @param primaryKeys 主键数组(如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map)
	 */
	public void deleteBatch(String namespace, Long[] primaryKeys)
	{
		if(primaryKeys != null && primaryKeys.length > 0)
		{
			for(Long p : primaryKeys)
			{
				dao.executeDelete(namespace + ".delete", p);
			}
		}
	}

	/**
	 * 查询对象
	 * @param namespace SQL的namespace
	 * @param entity 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return int
	 */
	public int update(String namespace, Object entity)
	{
		return dao.executeUpdate(namespace + ".update", entity);
	}

	/**
	 * 查询对象
	 * @param namespace SQL的namespace
	 * @param primaryKey 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return T对象模型
	 */
	public Object get(String namespace, Long primaryKey)
	{
		return (Object) dao.executeSelect(namespace + ".select", primaryKey);
	}

	/**
	 * 取得全部数据
	 * @param namespace SQL的namespace
	 * @param map 查询参数和条件数据
	 * @return List
	 */
	public List<Object> queryList(String namespace, Map map)
	{
		PageRequest request = new PageRequest();
		request.setFilters(map);
		return (List<Object>) dao.executeSelectList(namespace + ".query", request);
	}

	/**
	 * 默认列表方法
	 * @param namespace SQL的namespace
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @return List
	 */
	public List<Object> queryList(String namespace, PageRequest pageRequest)
	{
		return (List<Object>) dao.executeSelectList(namespace + ".query", pageRequest.getFilters());
	}

	/**
	 * 得到分页模型
	 * @param namespace SQL的namespace
	 * @param currentPage 当前页码
	 * @param pageSize 一页显示的条数
	 * @param map 查询参数和条件数据
	 * @return Page
	 */
	public Page<Object> queryPage(String namespace, int currentPage, int pageSize, Map map)
	{
		PageRequest pageRequest = new PageRequest(currentPage, pageSize, map);
		Page<Object> page = dao.queryPage(namespace + ".query", pageRequest, namespace + ".queryCount", pageRequest);
		return page;
	}

	/**
	 * 分页查询数据
	 * @param namespace SQL的namespace
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @return Page
	 */
	public Page<Object> queryPage(String namespace, PageRequest pageRequest)
	{
		return (Page<Object>)dao.queryPage(namespace + ".query", pageRequest, namespace + ".queryCount", pageRequest);
	}

	/**
	 * 执行查询操作取得数据条数
	 * @param namespace SQL的namespace
	 * @param pageRequestCount PageRequest.getFilters()查询参数和条件数据
	 * @return int
	 */
	public int queryCount(String namespace, PageRequest pageRequest)
	{
		return dao.queryCount(namespace + ".queryCount", pageRequest);
	}
}
