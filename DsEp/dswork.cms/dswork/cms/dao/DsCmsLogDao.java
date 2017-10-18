/**
 * 日志Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.cms.model.DsCmsLog;

@Repository
@SuppressWarnings("all")
public class DsCmsLogDao extends BaseDao<DsCmsLog, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsLog.class;
	}

	@Override
	@Deprecated
	public int delete(Long id)
	{
		return 0;
	}

	@Override
	@Deprecated
	public int update(DsCmsLog id)
	{
		return 0;
	}
}