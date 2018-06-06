/**
 * ${table.comment}Dao
 */
package ${namespace}.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;
import ${frame}.core.db.HibernateBaseDao;
import ${namespace}.model.${model};

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@Repository
@SuppressWarnings("all")
public class ${model}Dao extends HibernateBaseDao<${model}, Long>
{
	@Override
	public Class getEntityClass()
	{
		return ${model}.class;
	}
	
	@Override
	protected void queryParam(Map map, Criteria criteria)
	{
		Object tmp;
<#list table.column as c>
		tmp = map.get("${c.nameLowerCamel}");
		if(tmp != null)
		{
	<#if c.key>
			criteria.add(Restrictions.eq("${c.nameLowerCamel}", String.valueOf(tmp).trim()));
	</#if>
	<#if !c.key>
			criteria.add(Restrictions.like("${c.nameLowerCamel}", String.valueOf(tmp).trim(), MatchMode.ANYWHERE));
	</#if>
		}
</#list>
	}
}