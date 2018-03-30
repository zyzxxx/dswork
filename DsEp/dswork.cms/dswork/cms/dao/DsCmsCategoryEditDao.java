/**
 * 内容Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsCategoryEdit;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsCategoryEditDao extends BaseDao<DsCmsCategoryEdit, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsCategoryEdit.class;
	}
}
