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

	public int updateAuditCategory(DsCmsAuditCategory po, DsCmsCategory cate)
	{
		if(po.isPass())//通过
		{
			if(cate.getScope() == 1)
			{
				cate.setSummary(po.getSummary());
				cate.setMetakeywords(po.getMetakeywords());
				cate.setMetadescription(po.getMetadescription());
				cate.setReleasesource(po.getReleasesource());
				cate.setReleaseuser(po.getReleaseuser());
				cate.setImg(po.getImg());
				cate.setContent(po.getContent());
				cate.setReleasetime(po.getReleasetime());
				if(cate.getStatus() != 0)
				{
					cate.setStatus(1);;
				}
				cateDao.updateContent(cate);
			}
			else
			{
				cate.setUrl(po.getUrl());
				cate.setStatus(8);// 更改至已发布状态（因为外链不需要发布操作）
				cateDao.update(cate);
			}
			po.setStatus(1);// 审核栏目设置为修改状态
		}
		return auditCateDao.update(po);
	}
	
	public int updateAuditPage(DsCmsAuditPage po)
	{
		if(po.isPass())// 通过
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
			if(isSave)
			{
				page.setStatus(0);// 内容设置为新建未发布状态
				pageDao.save(page);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(page.getStatus() != 0)
				{
					page.setStatus(1);// 更新至更新未发布状态
				}
				pageDao.update(page);
			}
			po.setStatus(1);// 审核内容设置为修改状态
		}
		return auditPageDao.update(po);
	}

	public int deleteAuditPage(DsCmsAuditPage po)
	{
		if(po.isPass())// 通过
		{
			DsCmsPage page = (DsCmsPage) pageDao.get(po.getId());
			if(page != null)
			{
				page.setStatus(-1);
				pageDao.update(page);
			}
			return auditPageDao.delete(po.getId());
		}
		else
		{
			po.setStatus(1);// 更新为修改状态
			return auditPageDao.update(po);
		}
	}

	public int saveAuditCategoryList(List<DsCmsCategory> cateList)
	{
		return auditCateDao.saveFromCategoryList(cateList);
	}
}
