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
import dswork.cms.dao.DsCmsPermissionDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsAuditPage;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;
import dswork.core.page.PageRequest;

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

	@SuppressWarnings("unchecked")
	public List<DsCmsSite> queryListSite(PageRequest rq)
	{
		return siteDao.queryList(rq);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return cateDao.queryList(new PageRequest(map));
	}

	public DsCmsPermission getPermissionByOwnAccount(String own, String account)
	{
		return permissionDao.getByOwnAccount(own, account);
	}

	public DsCmsAuditCategory getAuditCategory(Long id)
	{
		return (DsCmsAuditCategory) auditCateDao.get(id);
	}

	public int updateAuditCategory(DsCmsAuditCategory po)
	{
		return auditCateDao.update(po);
	}
	
	public int updateAuditPage(DsCmsAuditPage po)
	{
		return auditPageDao.update(po);
	}

	public int saveAuditCategoryList(List<DsCmsCategory> cateList)
	{
		int r = 0;
		for(DsCmsCategory cate : cateList)
		{
			if(cate.getStatus() != 0) //非列表
			{
				DsCmsAuditCategory auditCate = (DsCmsAuditCategory) auditCateDao.get(cate.getId());
				if(auditCate == null)
				{
					r++;
					auditCate = new DsCmsAuditCategory();
					auditCate.setId(cate.getId());
					auditCate.setSiteid(cate.getSiteid());
					auditCate.setSummary(cate.getSummary());
					auditCate.setMetakeywords(cate.getMetakeywords());
					auditCate.setMetadescription(cate.getMetadescription());
					auditCate.setReleasesource(cate.getReleasesource());
					auditCate.setReleaseuser(cate.getReleaseuser());
					auditCate.setImg(cate.getImg());
					auditCate.setContent(cate.getContent());
					auditCate.setReleasetime(cate.getReleasetime());
					auditCate.setUrl(cate.getUrl());
					auditCate.setStatus(0); //草稿
					auditCateDao.save(auditCate);
				}
			}
		}
		return r;
	}
}
