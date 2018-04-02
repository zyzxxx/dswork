/**
 * CMSDao
 */
package common.cms.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.MyBatisDao;

@Repository
@SuppressWarnings("all")
public class CmsPermissionDao extends MyBatisDao
{
	@Override
	public Class<?> getEntityClass()
	{
		return CmsPermissionDao.class;
	}

	public List<DsCmsSite> queryListSite()
	{
		return queryList("queryListSite", new HashMap<String, Object>());
	}

	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return queryList("queryListCategory", map);
	}

	public List<DsCmsPermission> queryListPermission(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return queryList("queryListPermission", map);
	}
}
