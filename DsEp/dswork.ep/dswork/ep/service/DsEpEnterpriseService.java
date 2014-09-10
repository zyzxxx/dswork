/**
 * 企业Service
 */
package dswork.ep.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.PageRequest;
import dswork.core.util.EncryptUtil;
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

	public int update(DsEpEnterprise ent, DsEpUser user)
	{
		entDao.update(ent);
		userDao.update(user);
		return 1;
	}

	public List<DsEpUser> queryListUser(Map map)
	{
		PageRequest rq = new PageRequest();
		rq.setFilters(map);
		return userDao.queryList(rq);
	}

	public DsEpUser getUser(Long id)
	{
		return (DsEpUser) userDao.get(id);
	}

	public void updatePassword(long userId, int status, String password) throws Exception
	{
		password = EncryptUtil.encryptMd5(password);
		userDao.updatePassword(userId, status, password);
	}
}
