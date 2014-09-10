/**
 * 企业用户Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.util.EncryptUtil;
import dswork.ep.model.DsEpEnterprise;
import dswork.ep.model.DsEpUser;
import dswork.ep.dao.DsEpEnterpriseDao;
import dswork.ep.dao.DsEpUserDao;

@Service
@SuppressWarnings("all")
public class DsEpUserService extends BaseService<DsEpUser, Long>
{
	@Autowired
	private DsEpUserDao userDao;
	
	@Autowired
	private DsEpEnterpriseDao epDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return userDao;
	}
	public boolean isExists(String account)
	{
		return userDao.isExists(account);
	}
	
	public void updatePassword(long userId, int status, String password) throws Exception
	{
		password = EncryptUtil.encryptMd5(password);
		userDao.updatePassword(userId, status, password);
	}
	
	public DsEpEnterprise getEp(Long id)
	{
		return (DsEpEnterprise) epDao.get(id);
	}
}
