/**
 * 主题Service
 */
package dswork.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.bbs.model.DsBbsPage;
import dswork.bbs.dao.DsBbsPageDao;

@Service
@SuppressWarnings("all")
public class DsBbsPageService extends BaseService<DsBbsPage, Long>
{
	@Autowired
	private DsBbsPageDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
}
