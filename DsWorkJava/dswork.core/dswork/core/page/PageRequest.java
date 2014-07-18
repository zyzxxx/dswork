package dswork.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页请求信息
 */
public class PageRequest implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Object filters = null;
	private int currentPage;
	private int pageSize;
	private String pageName = "page";
	private String pageSizeName = "pageSize";

	/**
	 * 构造函数
	 */
	public PageRequest()
	{
		this(0, 0);
	}

	/**
	 * 构造函数
	 * @param filters 条件，用MyBatis时使用Map，用Hibernate时使用List
	 */
	public PageRequest(Object filters)
	{
		this(0, 0, filters);
	}

	/**
	 * 构造函数
	 * @param currentPage 当前页码
	 * @param pageSize 一页显示的条数
	 */
	public PageRequest(int currentPage, int pageSize)
	{
		this(currentPage, pageSize, null);
	}

	/**
	 * 构造函数
	 * @param currentPage 当前页码
	 * @param pageSize 一页显示的条数
	 * @param filters 条件，用MyBatis时使用Map，用Hibernate时使用List
	 */
	public PageRequest(int currentPage, int pageSize, Object filters)
	{
		setCurrentPage(currentPage);
		setPageSize(pageSize);
		setFilters(filters);
	}

	/**
	 * 取得当前页
	 * @return int
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * 设置当前页
	 * @param currentPage int
	 */
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage < 0 ? 1 : currentPage;
	}

	/**
	 * 取得泛型参数，用MyBatis时使用Map，用Hibernate时使用List
	 * @return Object
	 */
	public Object getFilters()
	{
		return filters;
	}

	/**
	 * 根据泛型取得参数
	 * @param filters
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFilters(T t)
	{
		try
		{
			t = (T)filters;
		}
		catch(Exception ex)
		{
		}
		return t;
	}

	/**
	 * 取得Map参数
	 * @return Map&lt;String, Object&gt;
	 */
	public Map<String, Object> getFiltersMap()
	{
		return getFilters(new HashMap<String, Object>());
	}

	/**
	 * 取得List参数
	 * @return List&lt;Object&gt;
	 */
	public List<Object> getFiltersList()
	{
		return getFilters(new ArrayList<Object>());
	}
	

	/**
	 * 设置泛型参数，用MyBatis时使用Map，用Hibernate时使用List
	 * @param filters
	 */
	public void setFilters(Object filters)
	{
		this.filters = filters;
	}

	/**
	 * 取得当前页码的参数名
	 * @return String，默认值是page
	 */
	public String getPageName()
	{
		return String.valueOf(pageName);
	}

	/**
	 * 设置当前页码的参数名
	 * @param pageName 默认值是page
	 */
	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}

	/**
	 * 取得一页显示的条数
	 * @return int
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * 设置一页显示的条数
	 * @param pageSize int
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize < 0 ? 10 : pageSize;
	}

	/**
	 * 取得一页显示的条数的参数名
	 * @return String，默认值是pageSize
	 */
	public String getPageSizeName()
	{
		return String.valueOf(pageSizeName);
	}

	/**
	 * 设置一页显示的条数的参数名
	 * @param pageSizeName 默认值是pageSize
	 */
	public void setPageSizeName(String pageSizeName)
	{
		this.pageSizeName = pageSizeName;
	}
}
