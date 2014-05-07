package dswork.core.db;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;

/**
 * Hibernate抽象类
 * @author skey
 */
@SuppressWarnings("all")
public abstract class HibernateDao extends HibernateDaoSupport
{
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(getClass());
	protected abstract Class getEntityClass();

	/**
	 * HibernateTemplate的find函数
	 * @param hql
	 * @param values 可变参数
	 * @return List
	 */
	protected List find(String hql, Object... values)
	{
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 创建Query对象
	 * @param hql
	 * @param values 可变条件
	 * @return Query
	 */
	protected Query createQuery(final String hql, final Object... values)
	{
		Assert.hasText(hql);
		Query query = getHibernateTemplate().execute(new HibernateCallback()
		{
			public Query doInHibernate(Session session) throws HibernateException, SQLException
			{
				return session.createQuery(hql);
			}
		});
		for(int i = 0; i < values.length; i++)
		{
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象
	 * @param criterions 可变条件Restrictions
	 *        criteria.add(Restrictions.eq(propertyName, value));
	 * @return Criteria
	 */
	protected Criteria createCriteria(Criterion... criterions)
	{
		//final Class _class = getEntityClass();
		Criteria criteria = getHibernateTemplate().execute(new HibernateCallback()
		{
			public Criteria doInHibernate(Session session) throws HibernateException, SQLException
			{
				//return session.createCriteria(_class);
				return session.createCriteria(getEntityClass());
			}
		});// QBC方式
		for(Criterion c : criterions)
		{
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 分页查询函数
	 * @param hql
	 * @param hqlCount 与hql“?”个数、顺序一致
	 * @param pageRequest 仅使用page和pageSize属性，不使用Filters属性
	 * @param values 条件参数，个数与hql“?”个数、顺序一致
	 * @return Page
	 */
	public Page queryPage(final String hql, final String hqlCount, final PageRequest pageRequest, final Object... values)
	{
		Assert.hasText(hql);
		Assert.hasText(hqlCount);
		return getHibernateTemplate().execute(new HibernateCallback()
		{
			public Page doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query  = session.createQuery(hqlCount);
				for(int i = 0; i < values.length; i++)
				{
					query.setParameter(i, values[i]);
				}
				int totalCount = HibernateDao.queryCountProcess(query.uniqueResult());
				Page page = new Page(pageRequest.getCurrentPage(), pageRequest.getPageSize(), totalCount);
				query = session.createQuery(hql);
				for(int i = 0; i < values.length; i++)
				{
					query.setParameter(i, values[i]);
				}
				List results = query.setFirstResult(page.getFirstResultIndex()).setMaxResults(page.getPageSize()).list();
				page.setResult(results);
				return page;
			}
		});
//		int totalCount = HibernateDao.queryCountProcess(createQuery(hqlCount, values).uniqueResult());
//		Page page = new Page(pageRequest, totalCount);
//		// if(page.getTotalCount() <= 0){page.setResult(new ArrayList(0));}else{}//没数据的话不影响性能，而实际上又不可能没有数据
//		Query query = createQuery(hql, values);
//		List results = query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list();
//		page.setResult(results);
//		return page;
	}

	/**
	 * QBC方式分页查询数据
	 * @param criteria Criteria
	 * @param pageRequest 仅使用page和pageSize属性，不使用Filters属性
	 * @return Page
	 */
	protected Page queryPage(Criteria criteria, PageRequest pageRequest)
	{
	    CriteriaImpl impl = (CriteriaImpl) criteria;
	    List orderEntrys = new ArrayList();
	    Field field;
	    try
		{
	    	field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			orderEntrys = (List) field.get(impl);
			field.set(impl, new ArrayList());
		}
		catch(Exception e)
		{
		}
		int totalCount = HibernateDao.queryCountProcess(criteria.setProjection(Projections.rowCount()).uniqueResult());
		Page page = new Page(pageRequest.getCurrentPage(), pageRequest.getPageSize(), totalCount);
		try
		{
	    	field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			for(int i = 0; i < orderEntrys.size(); i++)
			{
				List innerOrderEntries = (List) field.get(criteria);
				innerOrderEntries.add(orderEntrys.get(i));
			}
		}
		catch(Exception e)
		{
		}
		List results = criteria.setProjection(null).setFirstResult(page.getFirstResultIndex()).setMaxResults(page.getPageSize()).list();
		page.setResult(results);
		return page;
	}
	
	/**
	 * 用于将count值转化为int，转换失败则默认为0
	 * @param obj Object
	 * @return int
	 */
	protected final static int queryCountProcess(Object obj)
	{
		int totalCount = 0;
		try
		{
			totalCount = ((Integer) obj).intValue();
		}
		catch(Exception e)
		{
			try
			{
				totalCount = ((Long) obj).intValue();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				totalCount = 0;
			}
		}
		return totalCount;
	}

//	/**
//	 * QBC方式分页查询数据
//	 * @param Criteria criteria
//	 * @param Criteria criteriaCount
//	 * @param pageRequest 仅使用page和pageSize属性，不使用Filters属性
//	 * @return Page
//	 */
//	protected Page queryPage(Criteria criteria, Criteria criteriaCount, PageRequest pageRequest)
//	{
//		int totalCount = ((Integer) (criteriaCount.setProjection(Projections.rowCount()).uniqueResult())).intValue();
//		Page page = new Page(pageRequest, totalCount);
//		List results = criteria.setProjection(null).setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list();
//		page.setResult(results);
//		return page;
//	}
}
