package common.lucene;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息, 第一页从1开始
 * @author skey
 */
public class MyPage
{
	private List<MyDocument> result = new ArrayList<MyDocument>(0);
	private int pageSize;
	private int currentPage;
	private int lastPage;
	private int totalCount = 0;
	private int searchSize = 0;

	/**
	 * 构造函数
	 * @param currentPage 当前页码
	 * @param pageSize 一页显示的条数
	 * @param totalCount 数据总条数
	 * @param searchSize 搜索命中总条数
	 */
	public MyPage(int currentPage, int pageSize, int totalCount, int searchSize)
	{
		if(pageSize <= 0)
		{
			throw new IllegalArgumentException("[pageSize] must great than zero");
		}
		this.lastPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		if(this.lastPage < 1)
		{
			this.lastPage = 1;
		}
		this.pageSize = pageSize;
		if(currentPage <= 1)
		{
			currentPage = 1;
		}
		else if(Integer.MAX_VALUE == currentPage || currentPage > this.lastPage)
		{
			currentPage = this.lastPage;
		}
		this.searchSize = searchSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
	}

	/**
	 * 取得当前页页码
	 * @return int
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * 得到结果集第一条数据的行编码
	 * @return int
	 */
	public int getFirstResultIndex()
	{
		if (pageSize <= 0)
		{
			throw new IllegalArgumentException("[pageSize] must great than zero");
		}
		return (currentPage - 1) * pageSize;
	}
	
	/**
	 * 取得数据总页数，也就是最后一页页码
	 * @return int
	 */
	public int getLastPage()
	{
		return lastPage;
	}

	/**
	 * 取得下一页页码
	 * @return int
	 */
	public int getNextPage()
	{
		return currentPage + 1;
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
	 * 取得上一页页码
	 * @return int
	 */
	public int getPreviousPage()
	{
		return currentPage - 1;
	}

	/**
	 * 取得结果集
	 * @return 结果集List&lt;T&gt;
	 */
	public List<MyDocument> getResult()
	{
		return result;
	}

	/**
	 * 设置结果集，其值与长度不影响计数
	 * @param result 结果集List&lt;T&gt;
	 */
	public void setResult(List<MyDocument> result)
	{
		if(result == null)
		{
			throw new IllegalArgumentException("'result' must be not null");
		}
		this.result = result;
	}

	/**
	 * 取得数据搜索总条数，0表示没有数据
	 * @return int
	 */
	public int getSearchSize()
	{
		return searchSize <= 0 ? 0 : searchSize;
	}

	/**
	 * 取得数据总条数，0表示没有数据
	 * @return int
	 */
	public int getTotalCount()
	{
		return totalCount;
	}

	/**
	 * 取得数据总页数
	 * @return int
	 */
	public int getTotalPage()
	{
		return lastPage;
	}

	/**
	 * 是否是首页（第一页），第一页页码为1
	 * @return boolean
	 */
	public boolean isFirstPage()
	{
		return currentPage == 1;
	}

	/**
	 * 是否有下一页
	 * @return boolean
	 */
	public boolean isHasNextPage()
	{
		return lastPage > currentPage;
	}

	/**
	 * 是否有上一页
	 * @return boolean
	 */
	public boolean isHasPreviousPage()
	{
		return currentPage > 1;
	}

	/**
	 * 是否是最后一页
	 * @return boolean
	 */
	public boolean isLastPage()
	{
		return currentPage >= lastPage;
	}
}
