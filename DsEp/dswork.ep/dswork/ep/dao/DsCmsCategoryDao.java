/**
 * 栏目Dao
 */
package dswork.ep.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.ep.model.DsCmsCategory;

@Repository
@SuppressWarnings("all")
public class DsCmsCategoryDao extends BaseDao<DsCmsCategory, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsCategory.class;
	}
}