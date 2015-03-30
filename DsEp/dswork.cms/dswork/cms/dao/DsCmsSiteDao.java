/**
 * 站点Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseDao;

@Repository
@SuppressWarnings("all")
public class DsCmsSiteDao extends BaseDao<DsCmsSite, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsSite.class;
	}
}
