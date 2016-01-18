/**
 * 样例信息Service
 */
package testwork.service.demo;

import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import testwork.model.Demo;
import testwork.dao.demo.DemoDao;

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
}
