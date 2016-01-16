package dswork.core.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;

/**
 * 服务基础类
 * @author sille
 * @version 1.0
 * @param &lt;T&gt; 对象模型
 * @param &lt;PK&gt; 主键类
 */
@SuppressWarnings("all")
public abstract class MyDataService<T, PK extends Serializable>
{
	private static final long serialVersionUID = 1L;
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * 需要被子类覆盖
	 * @return EntityDao
	 */
	protected abstract MyEntityDao getEntityDao();

	protected abstract String getSqlNamespace();

	/**
	 * 新增对象
	 * @param entity 需要新增的对象模型
	 * @return int
	 */
	public int save(Object entity)
	{
		return getEntityDao().executeInsert(getSqlNamespace() + ".insert", entity);
	}

	/**
	 * 根据主键删除对象
	 * @param p 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return int
	 */
	public int delete(PK p)
	{
		return getEntityDao().executeDelete(getSqlNamespace() + ".delete", p);
	}

	/**
	 * 根据主键批量删除对象
	 * @param primaryKeys 主键数组(如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map)
	 */
	public void deleteBatch(PK[] primaryKeys)
	{
		if(primaryKeys != null && primaryKeys.length > 0)
		{
			for(PK p : primaryKeys)
			{
				delete(p);
			}
		}
	}

	/**
	 * 查询对象
	 * @param entity 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return int
	 */
	public int update(Object entity)
	{
		return getEntityDao().executeUpdate(getSqlNamespace() + ".update", entity);
	}

	/**
	 * 查询对象
	 * @param primaryKey 如果是单主键的，传入主键数据类型，如果为多主键的，可以用主键类或map
	 * @return T对象模型
	 */
	public T get(PK primaryKey)
	{
		return (T) getEntityDao().executeSelect(getSqlNamespace() + ".select", primaryKey);
	}

	/**
	 * 取得全部数据
	 * @param map 查询参数和条件数据
	 * @return List
	 */
	public List<T> queryList(Map map)
	{
		PageRequest request = new PageRequest();
		request.setFilters(map);
		return (List<T>) getEntityDao().executeSelectList(getSqlNamespace() + ".query", request);
	}

	/**
	 * 默认列表方法
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @return List
	 */
	public List<T> queryList(PageRequest pageRequest)
	{
		return (List<T>) getEntityDao().executeSelectList(getSqlNamespace() + ".query", pageRequest);
	}

	/**
	 * 得到分页模型
	 * @param currentPage 当前页码
	 * @param pageSize 一页显示的条数
	 * @param map 查询参数和条件数据
	 * @return Page
	 */
	public Page<T> queryPage(int currentPage, int pageSize, Map map)
	{
		PageRequest pageRequest = new PageRequest();
		pageRequest.setFilters(map);
		pageRequest.setCurrentPage(currentPage);
		pageRequest.setPageSize(pageSize);
		Page<T> page = getEntityDao().queryPage(getSqlNamespace() + ".query", pageRequest, getSqlNamespace() + ".queryCount", pageRequest);
		return page;
	}

	/**
	 * 分页查询数据
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @return Page
	 */
	public Page<T> queryPage(PageRequest pageRequest)
	{
		return getEntityDao().queryPage(getSqlNamespace() + ".query", pageRequest, getSqlNamespace() + ".queryCount", pageRequest);
	}

	/**
	 * 执行查询操作取得数据条数
	 * @param statementNameCount SQL的ID(包含namespace)
	 * @param pageRequestCount PageRequest.getFilters()查询参数和条件数据
	 * @return int
	 */
	public int queryCount(PageRequest pageRequest)
	{
		return getEntityDao().queryCount(getSqlNamespace() + ".queryCount", pageRequest);
	}
}
