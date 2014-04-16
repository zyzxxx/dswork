package dswork.core.db;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

import dswork.core.page.Page;
import dswork.core.page.PageRequest;

/**
 * Hibernate基础Dao类
 * @author skey
 * @version 3.0
 * @param &lt;T&gt; 对象模型
 * @param &lt;PK&gt; 主键类
 */
@SuppressWarnings("unchecked")
public abstract class HibernateBaseDao<E, PK extends Serializable> extends HibernateDao implements EntityDao<E, PK>
{
	private static final long serialVersionUID = 1L;
	/**
	 * 保存对象
	 */
	public int save(E entity)
	{
		getHibernateTemplate().save(entity);// .saveOrUpdate(entity);
		return 1;
	}

	/**
	 * 保存全部对象
	 */
	public void saveAll(Collection<E> entities)
	{
		for (Iterator<E> it = entities.iterator(); it.hasNext();)
		{
			save(it.next());
		}
	}
	
	/**
	 * 删除对象
	 */
	public int delete(PK primaryKey)
	{
		final String hql = "delete " + getEntityClass().getName() + " where id=" + primaryKey;
		createQuery(hql).executeUpdate();
		return 1;
	}

	/**
	 * 删除对象
	 */
	public int delete(E entity)
	{
		getHibernateTemplate().delete(entity);
		return 1;
	}

	/**
	 * 删除全部对象
	 */
	public void deleteAll(Collection entities)
	{
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 更新对象
	 */
	public int update(E entity)
	{
		getHibernateTemplate().update(entity);
		return 1;
	}

	/**
	 * 保存或更新对象
	 */
	public void saveOrUpdate(E entity)
	{
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 取得实体数据
	 * session.get()方法返回实体，会立刻访问数据库，如果没有对应的记录，返回null
	 */
	public E get(PK id)
	{
		return (E) getHibernateTemplate().get(getEntityClass(), id);
	}
	
	/**
	 * 根据ID获取对象
	 * session.load()方法返回实体或其proxy对象，如果对象不存在，抛出异常
	 */
	public E load(PK id)
	{
		return (E) getHibernateTemplate().load(getEntityClass(), id);
	}
	
	/**
	 * 不分页查询数据
	 * @param pageRequest <Map>PageRequest.getFilters()查询参数和条件数据<br />
	 * &nbsp; &nbsp; 需要重写queryParam(Criteria criteria)方法
	 */
	public List<E> queryList(final PageRequest pageRequest)
	{
		return (List<E>) getHibernateTemplate().executeFind(new HibernateCallback()
		{
			public List<E> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Criteria criteria = session.createCriteria(getEntityClass());
				if (pageRequest.getFilters() instanceof Map)
				{
					queryParam((Map)pageRequest.getFilters(), criteria);
				}
				return criteria.list();
			}
		});
//		Criteria criteria = createCriteria();
//		if (pageRequest.getFilters() instanceof Map)
//		{
//			queryParam((Map)pageRequest.getFilters(), criteria);
//		}
//		return criteria.list();
	}

	/**
	 * 分页查询数据
	 * @param pageRequest <Map>PageRequest.getFilters()查询参数和条件数据<br />
	 * &nbsp; &nbsp; 需要重写queryParam(Criteria criteria)方法
	 */
	public Page queryPage(final PageRequest pageRequest)
	{
		return getHibernateTemplate().execute(new HibernateCallback()
		{
			public Page doInHibernate(Session session) throws HibernateException, SQLException
			{
				Criteria criteria = session.createCriteria(getEntityClass());
				if (pageRequest.getFilters() instanceof Map)
				{
					queryParam((Map)pageRequest.getFilters(), criteria);
				}
				return queryPage(criteria, pageRequest);
			}
		});
//		Criteria criteria = createCriteria();
//		if (pageRequest.getFilters() instanceof Map)
//		{
//			queryParam((Map)pageRequest.getFilters(), criteria);
//		}
//		return queryPage(criteria, pageRequest);
	}

	/**
	 * 设置查询条件<br />
	 * &nbsp; &nbsp; criteria.add(Restrictions.eq(propertyName, value))<br />
	 * &nbsp; &nbsp; criteria.add(Restrictions.like(propertyName, "%" + value + "%"))<br />
	 * &nbsp; &nbsp; ……<br />
	 * Base类中仅用于queryList(PageRequest)和queryPage(PageRequest)
	 * @param map 查询参数和条件数据
	 * @param criteria
	 */
	protected void queryParam(Map map, Criteria criteria)
	{
	}
	
	protected Order addOrder(String orderBy, boolean isAsc)
	{
		if(isAsc)
		{
			return Order.asc(orderBy);
		}
		else
		{
			return Order.desc(orderBy);
		}
	}
}
