/**
 * DEMODao
 */
package testwork.dao.demo;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import testwork.model.demo.Demo;

@Repository
@SuppressWarnings("unchecked")
public class DemoDao extends BaseDao<Demo, Long>
{
	@Override
	public Class getEntityClass()
	{
		return Demo.class;
	}
}