/**
 * DEMOService
 */
package testwork.service.hbmdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import testwork.model.hbmdemo.HbmDemo;
import testwork.dao.hbmdemo.HbmDemoDao;

@Service
@SuppressWarnings("unchecked")
public class HbmDemoService extends BaseService<HbmDemo, Long>
{
	@Autowired
	private HbmDemoDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
}
