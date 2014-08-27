/**
 * 站点Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsCmsSite;
import dswork.ep.dao.DsCmsSiteDao;

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
