/**
 * 个人用户Service
 */
package dswork.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.util.EncryptUtil;
import dswork.person.model.DsPersonUser;
import dswork.person.dao.DsPersonUserDao;

@Service
@SuppressWarnings("all")
public class DsPersonUserService extends BaseService<DsPersonUser, Long>
{
	@Autowired
	private DsPersonUserDao userDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return userDao;
	}

	public boolean isExistsAccount(String account)
	{
		return userDao.isExistsAccount(account);
	}
	
	public boolean isExistsIdcard(String idcard)
	{
		return userDao.isExistsIdcard(idcard);
	}

	public void updatePassword(long userId, String password) throws Exception
	{
		password = EncryptUtil.encryptMd5(password);
		userDao.updatePassword(userId, password);
	}

	public void updateStatus(long id, int status)
	{
		userDao.updateStatus(id, status);
	}
}
