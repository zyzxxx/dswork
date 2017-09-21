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

	@Override
	public int save(DsCmsAuditPage po)
	{
		auditPageDao.save(po);
		if(po.getScope() != 2) //不为外链
		{
			po.setUrl(po.getUrl() + "/" + po.getId() + ".html");
			auditPageDao.update(po);
		}
		return 1;
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
		DsCmsAuditCategory po = (DsCmsAuditCategory) auditCateDao.get(id);
		if(po == null)
		{
			po = new DsCmsAuditCategory();
			DsCmsCategory _po = (DsCmsCategory) cateDao.get(id);
			if(_po.getScope() != 0) //非列表
			{
				po.setId(_po.getId());
				po.setSiteid(_po.getSiteid());
				po.setSummary(_po.getSummary());
				po.setMetakeywords(_po.getMetakeywords());
				po.setMetadescription(_po.getMetadescription());
				po.setReleasesource(_po.getReleasesource());
				po.setReleaseuser(_po.getReleaseuser());
				po.setImg(_po.getImg());
				po.setContent(_po.getContent());
				po.setReleasetime(_po.getReleasetime());
				po.setUrl(_po.getUrl());
				po.setAuditstatus(DsCmsAuditCategory.DRAFT);// 草稿
				po.setStatus(0);// 初始设置为新增状态
				auditCateDao.save(po);
			}
		}
		return po;
	}

	public int updateAuditCategory(DsCmsAuditCategory po)
	{
		return auditCateDao.update(po);
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
