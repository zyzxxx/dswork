/**
 * 栏目Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsCmsCategory;
import dswork.ep.dao.DsCmsCategoryDao;

@Service
@SuppressWarnings("all")
public class DsCmsCategoryService extends BaseService<DsCmsCategory, Long>
{
	@Autowired
	private DsCmsCategoryDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
}
