/**
 * 网页文章Dao
 */
package dswork.ep.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.ep.model.DsCmsPage;

@Repository
@SuppressWarnings("all")
public class DsCmsPageDao extends BaseDao<DsCmsPage, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsPage.class;
	}
}