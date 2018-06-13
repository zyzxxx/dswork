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
		return DsEpEnterpriseDao.class;
	}
	
	public DsEpEnterprise getByQybm(String qybm)
	{
		return (DsEpEnterprise) this.executeSelect("getByQybm", qybm);
	}
	
	public boolean isExists(String qybm)
	{
		DsEpEnterprise ep = getByQybm(qybm);
		if(ep != null && ep.getQybm().length() > 0 && ep.getQybm() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
