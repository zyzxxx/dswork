/**
 * 站点Dao
 */
package dswork.ep.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.ep.model.DsCmsSite;

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