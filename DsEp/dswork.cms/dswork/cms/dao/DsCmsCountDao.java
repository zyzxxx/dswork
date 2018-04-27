/**
 * 日志Dao
 */
package dswork.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import dswork.core.db.MyBatisDao;
import dswork.cms.model.DsCmsCount;

@Repository
@SuppressWarnings("all")
public class DsCmsCountDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCmsCount.class;
	}

	public List<DsCmsCount> queryCountForAudit(long siteid, String idsForPage, String idsForPageCategory)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("idsForPage", idsForPage.length() > 0 ? idsForPage : "0");
		map.put("idsForPageCategory", idsForPageCategory.length() > 0 ? idsForPageCategory : "0");
		return queryList("queryCountForAudit", map);
	}

	public List<DsCmsCount> queryCountForPublish(long siteid, String idsForPage, String idsForPageCategory)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("idsForPage", idsForPage.length() > 0 ? idsForPage : "0");
		map.put("idsForPageCategory", idsForPageCategory.length() > 0 ? idsForPageCategory : "0");
		return queryList("queryCountForPublish", map);
	}
}