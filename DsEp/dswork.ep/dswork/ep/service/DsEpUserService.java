/**
 * 企业用户Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsEpUser;
import dswork.ep.dao.DsEpUserDao;

@Service
@SuppressWarnings("all")
public class DsEpUserService extends BaseService<DsEpUser, Long>
{
	@Autowired
	private DsEpUserDao userDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return userDao;
	}
	public boolean isExists(String account)
	{
		return userDao.isExists(account);
	}
}
