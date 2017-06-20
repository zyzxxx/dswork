/**
 * 样例信息Service
 */
package testwork.service.demo;

import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import testwork.model.Demo;
import testwork.dao.DemoDao;

@Service
@SuppressWarnings(value={"all"})
public class DemoService extends BaseService<Demo, java.lang.Long>
{
	private DemoDao dao;
	
	public void setDemoDao(DemoDao dao)
	{
		this.dao = dao;
	}

	@Override
	protected EntityDao getEntityDao()
	{
		return this.dao;
	}
//	public Demo get(Long primaryKey)
//	{
//		System.out.println(org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive());
//		Demo x = (Demo) dao.get(primaryKey);
//		x.setId(dswork.core.util.UniqueId.genId());
//		dao.save(x);
//		return (Demo) dao.get(primaryKey);
//	}
}
