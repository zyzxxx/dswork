/**
 * 内容Dao
 */
package dswork.cms.dao;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsPageEdit;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsPageEditDao extends BaseDao<DsCmsPageEdit, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsPageEdit.class;
	}
}
