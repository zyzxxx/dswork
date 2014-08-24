/**
 * 网页文章Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsCmsPage;
import dswork.ep.dao.DsCmsPageDao;

@Service
@SuppressWarnings("all")
public class DsCmsPageService extends BaseService<DsCmsPage, Long>
{
	@Autowired
	private DsCmsPageDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
}
