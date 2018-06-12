/**
 * 站点Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseDao;

@Repository
@SuppressWarnings("all")
public class DsCmsSiteDao extends BaseDao<DsCmsSite, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsSiteDao.class;
	}

	public List<DsCmsSite> queryList(String own)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("own", own);
		return queryList(map);
	}
}
