/**
 * 字典分类Dao
 */
package ds.dict.dao;

import org.springframework.stereotype.Repository;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("unchecked")
public class CommonDictDao extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return null;
	}
}