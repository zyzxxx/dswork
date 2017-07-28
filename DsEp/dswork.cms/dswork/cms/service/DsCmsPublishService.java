/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsPermissionDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;

@Service
public class DsCmsPublishService extends BaseService<DsCmsPage, Long>
{
	@Autowired
	private DsCmsPageDao pageDao;
	@Autowired
	private DsCmsCategoryDao cateDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPermissionDao permissionDao;

	@Override
	protected EntityDao<DsCmsPage, Long> getEntityDao()
	{
		return pageDao;
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) cateDao.get(categoryid);
	}

	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return permissionDao.queryListSite(map);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return cateDao.queryList(map);
	}

	public DsCmsPermission getPermission(Long siteid, String account)
	{
		return permissionDao.get(siteid, account);
	}

	public void updatePageStatus(Long id, int status)
	{
		pageDao.updateStatus(id, status);
	}

	public void updateCategoryStatus(Long id, int status)
	{
		cateDao.updateStatus(id, status);
	}
}
