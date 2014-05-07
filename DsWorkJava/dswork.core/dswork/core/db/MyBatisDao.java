package dswork.core.db;

import java.util.List;
//import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;
import org.mybatis.spring.SqlSessionTemplate;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;

/**
 * MyBatis抽象类
 * @author skey
 */
@SuppressWarnings("all")
public abstract class MyBatisDao extends DaoSupport
{
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 用于返回命名空间的全路径Class.getName()
	 * @return Class
	 */
	protected abstract Class getEntityClass();
	private String _statement = null;
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 实现abstract
	 */
	protected void checkDaoConfig() throws IllegalArgumentException
	{
		Assert.notNull(sqlSessionTemplate, "sqlSessionTemplate must be not null");
	}

	/**
	 * 注入SqlSessionTemplate
	 * @param sqlSessionTemplate SqlSessionTemplate
	 */
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 不建议使用，使用execute*和query*方法代替
	 * @return SqlSessionTemplate
	 */
	protected SqlSessionTemplate getSqlSessionTemplate()
	{
		return sqlSessionTemplate;
	}

	/**
	 * 返回命名空间的值
	 * @return String
	 */
	private String getSqlNamespace()
	{
		return getEntityClass().getName();
	}

	/**
	 * 获取需要操作sql的id，当getEntityClass().getName()无法满足时，可以重载此方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @return String
	 */
	protected String getStatementName(String statementName)
	{
		if(_statement == null)
		{
			_statement = getSqlNamespace() + ".";
		}
		return _statement + statementName;
	}

	/**
	 * 封装sqlSessionTemplate.insert方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @param parameter 参数
	 * @return int
	 */
	protected int executeInsert(String statementName, Object parameter)
	{
		return getSqlSessionTemplate().insert(getStatementName(statementName), parameter);
	}

	/**
	 * 封装sqlSessionTemplate.delete方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @param parameter 参数
	 * @return int
	 */
	protected int executeDelete(String statementName, Object parameter)
	{
		return getSqlSessionTemplate().delete(getStatementName(statementName), parameter);
	}

	/**
	 * 封装sqlSessionTemplate.update方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @param parameter 参数
	 * @return int
	 */
	protected int executeUpdate(String statementName, Object parameter)
	{
		return getSqlSessionTemplate().update(getStatementName(statementName), parameter);
	}

	/**
	 * 封装sqlSessionTemplate.selectOne方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @param parameter 参数
	 * @return Object
	 */
	protected Object executeSelect(String statementName, Object parameter)
	{
		return getSqlSessionTemplate().selectOne(getStatementName(statementName), parameter);
	}

	/**
	 * 封装sqlSessionTemplate.selectList方法
	 * @param statementName SQL的ID(不包含namespace)
	 * @param parameter 参数
	 * @return List
	 */
	protected List executeSelectList(String statementName, Object parameter)
	{
		return getSqlSessionTemplate().selectList(getStatementName(statementName), parameter);
	}

//	/**
//	 * 封装sqlSessionTemplate.selectMap方法
//	 * @param statementName SQL的ID(不包含namespace)
//	 * @param parameter 参数
//	 * @param mapKey
//	 * @return Map
//	 */
//	protected Map executeSelectMap(String statementName, Object parameter, String mapKey)
//	{
//		return getSqlSessionTemplate().selectMap(getStatementName(statementName), parameter, mapKey);
//	}

	/**
	 * 不分页查询数据
	 * @param statementName SQL的ID(不包含namespace)
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @return List
	 */
	protected List queryList(String statementName, PageRequest pageRequest)
	{
		return getSqlSessionTemplate().selectList(getStatementName(statementName), pageRequest.getFilters());
	}

	/**
	 * 分页查询数据
	 * @param statementName 查询SQL的ID(不包含namespace)
	 * @param pageRequest PageRequest.getFilters()查询参数和条件数据
	 * @param statementNameCount 查询总数SQL的ID(不包含namespace)
	 * @param pageRequestCount PageRequest.getFilters()查询参数和条件数据
	 * @return Page
	 */
	protected Page queryPage(String statementName, PageRequest pageRequest, String statementNameCount, PageRequest pageRequestCount)
	{
//		if (!(pageRequest.getFilters() instanceof Map))
//		{
//			Map parameterObject = BeanUtils.describe(pageRequest.getFilters());
//			pageRequest.setFilters(parameterObject);
//		}
//		if (!(countPageRequest.getFilters() instanceof Map))
//		{
//			Map parameterObject = BeanUtils.describe(countPageRequest.getFilters());
//			countPageRequest.setFilters(parameterObject);
//		}
		int totalCount = queryCount(statementNameCount, pageRequestCount);
		Page page = new Page(pageRequest.getCurrentPage(), pageRequest.getPageSize(), totalCount);
		// if(page.getTotalCount() <= 0){page.setResult(new ArrayList(0));}else{}//没数据的话不影响性能，而实际上又不可能没有数据
		List list = getSqlSessionTemplate().selectList(getStatementName(statementName), pageRequest.getFilters(), new RowBounds(page.getFirstResultIndex(), page.getPageSize()));
		page.setResult(list);
		return page;
	}

	/**
	 * 执行查询操作取得数据条数
	 * @param statementNameCount SQL的ID(不包含namespace)
	 * @param pageRequestCount PageRequest.getFilters()查询参数和条件数据
	 * @return int
	 */
	protected int queryCount(String statementNameCount, PageRequest pageRequestCount)
	{
		Object o = this.getSqlSessionTemplate().selectOne(getStatementName(statementNameCount), pageRequestCount.getFilters());
		return MyBatisDao.queryCountProcess(o);// xml里配置是int或long都可以
	}
	
	private final static int queryCountProcess(Object o)
	{
		int totalCount = 0;
		try
		{
			totalCount = ((Integer) o).intValue();
		}
		catch(Exception e)
		{
			try
			{
				totalCount = ((Long) o).intValue();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				totalCount = 0;
			}
		}
		return totalCount;
	}
}
