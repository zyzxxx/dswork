/**
 * 内容Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsAuditPage;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsAuditPageDao extends BaseDao<DsCmsAuditPage, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsAuditPage.class;
	}
}
