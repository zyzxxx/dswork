/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsAuditCategoryDao;
import dswork.cms.dao.DsCmsAuditPageDao;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsPermissionDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsAuditPage;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;

@Service
public class DsCmsEditService extends BaseService<DsCmsAuditPage, Long>
{
	@Autowired
	private DsCmsAuditPageDao auditPageDao;
	@Autowired
	private DsCmsAuditCategoryDao auditCateDao;
	@Autowired
	private DsCmsPermissionDao permissionDao;

	@Autowired
	private DsCmsCategoryDao cateDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao pageDao;

	@Override
	protected EntityDao<DsCmsAuditPage, Long> getEntityDao()
	{
		return auditPageDao;
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

	public DsCmsAuditCategory getAuditCategory(Long id)
	{
		return (DsCmsAuditCategory) auditCateDao.get(id);
	}

	public int updateAuditCategory(DsCmsAuditCategory po)
	{
		return auditCateDao.update(po);
	}

	public int saveAuditCategoryList(List<DsCmsCategory> cateList)
	{
		return auditCateDao.saveFromCategoryList(cateList);
	}

	public void updateAndDeleteList(List<DsCmsAuditPage> updList, List<DsCmsAuditPage> delList)
	{
		if(updList != null)
		{
			for(DsCmsAuditPage p : updList)
			{
				auditPageDao.update(p);
			}
		}
		if(delList != null)
		{
			for(DsCmsAuditPage p : delList)
			{
				auditPageDao.delete(p.getId());
			}
		}
	}

	public DsCmsPage getPage(Long id)
	{
		return (DsCmsPage) pageDao.get(id);
	}
}
