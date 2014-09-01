/**
 * 企业Dao
 */
package dswork.ep.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.ep.model.DsEpEnterprise;

@Repository
@SuppressWarnings("all")
public class DsEpEnterpriseDao extends BaseDao<DsEpEnterprise, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsEpEnterprise.class;
	}
}