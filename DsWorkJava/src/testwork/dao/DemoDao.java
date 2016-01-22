/**
 * 样例信息Dao
 */
package testwork.dao;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import testwork.model.Demo;

@Repository
@SuppressWarnings(value={"all"})
public class DemoDao extends BaseDao<Demo, Long>
{
	@Override
	public Class getEntityClass()
	{
		return Demo.class;
	}
}