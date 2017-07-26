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
import dswork.core.page.PageRequest;

@Service
public class DsCmsAuditService extends BaseService<DsCmsAuditPage, Long>
{
	@Autowired
	private DsCmsAuditPageDao auditPageDao;
	@Autowired
	private DsCmsAuditCategoryDao auditCateDao;
	@Autowired
	private DsCmsPermissionDao permissionDao;

	@Autowired
	private DsCmsPageDao pageDao;
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

	public int updateAuditCategory(DsCmsAuditCategory po, DsCmsCategory cate)
	{
		if(po.getStatus() == 4)//通过
		{
			if(cate.getStatus() == 1)
			{
				cate.setSummary(po.getSummary());
				cate.setMetakeywords(po.getMetakeywords());
				cate.setMetadescription(po.getMetadescription());
				cate.setReleasesource(po.getReleasesource());
				cate.setReleaseuser(po.getReleaseuser());
				cate.setImg(po.getImg());
				cate.setContent(po.getContent());
				cate.setReleasetime(po.getReleasetime());
				cateDao.updateContent(cate);
			}
			else
			{
				cate.setUrl(po.getUrl());
				cateDao.update(cate);
			}
			if(cate.getPublishstatus() != 0)
			{
				cateDao.updateStatus(cate.getId(), 1); //更改至更新未发布状态
			}
		}
		return auditCateDao.update(po);
	}
	
	public int updateAuditPage(DsCmsAuditPage po)
	{
		if(po.getStatus() == 4)//通过
		{
			DsCmsPage page = (DsCmsPage) pageDao.get(po.getId());
			boolean isSave = false;
			if(page == null)
			{
				page = new DsCmsPage();
				page.setId(po.getId());
				isSave = true;
			}
			page.setSiteid(po.getSiteid());
			page.setCategoryid(po.getCategoryid());
			page.setStatus(po.getStatus());
			page.setTitle(po.getTitle());
			page.setMetakeywords(po.getMetakeywords());
			page.setMetadescription(po.getMetadescription());
			page.setSummary(po.getSummary());
			page.setContent(po.getContent());
			page.setReleasetime(po.getReleasetime());
			page.setReleasesource(po.getReleasesource());
			page.setReleaseuser(po.getReleaseuser());
			page.setImg(po.getImg());
			page.setImgtop(po.getImgtop());
			page.setPagetop(po.getPagetop());
//			page.setUrl(po.getUrl());
			if(isSave)
			{
				page.setStatus(0); //更新至新建未发布状态
				pageDao.save(page);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(page.getStatus() != 0)
				{
					page.setStatus(1); //更新至更新未发布状态
				}
				pageDao.update(page);
			}
		}
		return auditPageDao.update(po);
	}
}
