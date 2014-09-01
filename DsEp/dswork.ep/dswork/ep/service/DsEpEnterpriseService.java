/**
 * 企业Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsEpEnterprise;
import dswork.ep.model.DsEpUser;
import dswork.ep.dao.DsEpEnterpriseDao;
import dswork.ep.dao.DsEpUserDao;

@Service
@SuppressWarnings("all")
public class DsEpEnterpriseService extends BaseService<DsEpEnterprise, Long>
{
	@Autowired
	private DsEpEnterpriseDao entDao;
	@Autowired
	private DsEpUserDao userDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return entDao;
	}

	@Override
	@Deprecated
	public int save(DsEpEnterprise ent)
	{
		return 0;
	}

	public int save(DsEpEnterprise ent, DsEpUser user)
	{
		entDao.save(ent);
		userDao.save(user);
		return 1;
	}
}
