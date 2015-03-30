/**
 * 站点Service
 */
package dswork.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;

@Service
@SuppressWarnings("all")
public class DsCmsSiteService extends BaseService<DsCmsSite, Long>
{
	@Autowired
	private DsCmsSiteDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
}
