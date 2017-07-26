/**
 * 内容Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsAuditCategory;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsAuditCategoryDao extends BaseDao<DsCmsAuditCategory, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsAuditCategory.class;
	}
}
