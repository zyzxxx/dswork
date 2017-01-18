package testwork.dao;

import java.util.Map;

import dswork.core.db.HibernateBaseDao;
import testwork.model.Demo;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings(value={"all"})
public class HbmDemoDao extends HibernateBaseDao<Demo, java.lang.Long>
{
	public Class getEntityClass()
	{
		return Demo.class;
	}

	@Override
	protected void queryParam(Map map, Criteria criteria)
	{
		Object tmp;
		tmp = map.get("title");
		if(tmp != null)
		{
			criteria.add(Restrictions.like("title", String.valueOf(tmp).trim(), MatchMode.ANYWHERE));
		}
		tmp = map.get("content");
		if(tmp != null)
		{
			criteria.add(Restrictions.like("content", String.valueOf(tmp).trim(), MatchMode.ANYWHERE));
		}
		tmp = map.get("foundtime");
		if(tmp != null)
		{
			criteria.add(Restrictions.like("foundtime", String.valueOf(tmp).trim(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.desc("foundtime"));
		criteria.addOrder(Order.asc("id"));
	}
}
