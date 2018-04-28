/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryEditDao;
import dswork.cms.dao.DsCmsCountDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsPageEditDao;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsCount;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPageEdit;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.UniqueId;

@SuppressWarnings("unchecked")
@Service
public class DsCmsAuditService
{
	@Autowired
	private DsCmsPageEditDao pageEditDao;
	@Autowired
	private DsCmsCategoryEditDao categoryEditDao;
	@Autowired
	private DsCmsLogDao logDao;
	@Autowired
	private DsCmsPageDao pageDao;
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsCountDao countDao;

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) categoryDao.get(categoryid);
	}

	public List<DsCmsSite> queryListSite(String own)
	{
		return siteDao.queryList(own);
	}

	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("publishstatus", "true");
		return categoryDao.queryList(map);
	}

	public DsCmsPageEdit getPage(Long pageid)
	{
		return (DsCmsPageEdit) pageDao.get(pageid);
	}

	public DsCmsCategoryEdit saveCategoryEdit(Long id)
	{
		DsCmsCategoryEdit po = new DsCmsCategoryEdit();
		DsCmsCategory c = (DsCmsCategory) categoryDao.get(id);
		po.setId(c.getId());
		po.setSiteid(c.getSiteid());
		po.setSummary(c.getSummary());
		po.setMetakeywords(c.getMetakeywords());
		po.setMetadescription(c.getMetadescription());
		po.setReleasesource(c.getReleasesource());
		po.setReleaseuser(c.getReleaseuser());
		po.setImg(c.getImg());
		po.setContent(c.getContent());
		po.setReleasetime(c.getReleasetime());
		po.setUrl(c.getUrl());
		po.setAuditstatus(DsCmsCategoryEdit.EDIT);// 草稿
		po.setStatus(0);// page设置为新增状态
		categoryEditDao.save(po);
		return po;
	}

	public DsCmsCategoryEdit getCategoryEdit(Long id)
	{
		return (DsCmsCategoryEdit) categoryEditDao.get(id);
	}

	public void updateCategoryEdit(DsCmsCategoryEdit po, boolean writeCategory, boolean enablelog)
	{
		if(writeCategory)
		{
			DsCmsCategory c = (DsCmsCategory) categoryDao.get(po.getId());
			if(c.getScope() == 0 || c.getScope() == 1)
			{
				c.setSummary(po.getSummary());
				c.setMetakeywords(po.getMetakeywords());
				c.setMetadescription(po.getMetadescription());
				c.setReleasesource(po.getReleasesource());
				c.setReleaseuser(po.getReleaseuser());
				c.setImg(po.getImg());
				c.setContent(po.getContent());
				c.setReleasetime(po.getReleasetime());
				if(c.getStatus() != 0)
				{
					c.setStatus(1);
				}
				categoryDao.updateContent(c);
			}
			else if(c.getScope() == 2)
			{
				c.setUrl(po.getUrl());
				if(c.getStatus() != 0)
				{
					c.setStatus(1);
				}
				categoryDao.update(c);
			}
			po.setStatus(1);// categoryEdit设置为待更新状态
		}
		categoryEditDao.update(po);
		if(enablelog)
		{
			writeLogCategory(po, po.isPass() ? 5 : 4);
		}
	}

	public void updatePageEdit(DsCmsPageEdit po, boolean writePage, boolean enablelog)
	{
		if(writePage)
		{
			DsCmsPage p = (DsCmsPage) pageDao.get(po.getId());
			boolean isSave = false;
			if(p == null)
			{
				p = new DsCmsPage();
				p.setId(po.getId());
				isSave = true;
			}
			p.setSiteid(po.getSiteid());
			p.setCategoryid(po.getCategoryid());
			p.setStatus(po.getStatus());
			p.setTitle(po.getTitle());
			p.setMetakeywords(po.getMetakeywords());
			p.setMetadescription(po.getMetadescription());
			p.setSummary(po.getSummary());
			p.setContent(po.getContent());
			p.setReleasetime(po.getReleasetime());
			p.setReleasesource(po.getReleasesource());
			p.setReleaseuser(po.getReleaseuser());
			p.setImg(po.getImg());
			p.setImgtop(po.getImgtop());
			p.setPagetop(po.getPagetop());
			p.setScope(po.getScope());
			if(isSave)
			{
				p.setStatus(0);// page设置为新建未发布状态
				pageDao.save(p);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(p.getStatus() != 0)
				{
					p.setStatus(1);// page设置为更新未发布状态
				}
				pageDao.update(p);
			}
			po.setStatus(1);// pageEdit设置为待更新状态
		}
		pageEditDao.update(po);
		if(enablelog)
		{
			writeLogPage(po, po.isPass() ? 5 : 4);
		}
	}

	public void updateDeletePageEdit(DsCmsPageEdit po, boolean enablelog)
	{
		DsCmsPage p = (DsCmsPage) pageDao.get(po.getId());
		if(p != null)
		{
			if(p.getStatus() == 1)
			{
				pageDao.delete(p.getId());
				pageEditDao.delete(p.getId());
			}
			else// 逻辑删除
			{
				p.setStatus(-1);
				po.setStatus(-1);
				po.setAuditstatus(4);
				pageDao.update(p);
				pageEditDao.update(po);
			}
		}
		else
		{
			pageEditDao.delete(po.getId());
		}
		if(enablelog)
		{
			writeLogPage(po, 5);
		}
	}

	public DsCmsPageEdit getPageEdit(Long id)
	{
		return (DsCmsPageEdit) pageEditDao.get(id);
	}

	public Page<DsCmsPageEdit> queryPagePageEdit(PageRequest pr)
	{
		return pageEditDao.queryPage(pr);
	}

	private void writeLogPage(DsCmsPageEdit po, int action)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getCategoryid());
			log.setPageid(po.getId());
			log.setAuditid(po.getAuditid());
			log.setAuditmsg(po.getMsg());
			log.setAuditname(po.getAuditname());
			log.setAudittime(po.getAudittime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(action);
			log.setTitle(po.getTitle());
			log.setScope(po.getScope());
			log.setUrl(po.getUrl());
			log.setMetakeywords(po.getMetakeywords());
			log.setMetadescription(po.getMetadescription());
			log.setSummary(po.getSummary());
			log.setReleasetime(po.getReleasetime());
			log.setReleasesource(po.getReleasesource());
			log.setReleaseuser(po.getReleaseuser());
			log.setImg(po.getImg());
			log.setContent(po.getContent());
			log.setImgtop(po.getImgtop());
			log.setPagetop(po.getPagetop());
			logDao.save(log);
		}
		catch(Exception e)
		{
		}
	}

	private void writeLogCategory(DsCmsCategoryEdit po, int action)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getId());
			log.setAuditid(po.getAuditid());
			log.setAuditmsg(po.getMsg());
			log.setAuditname(po.getAuditname());
			log.setAudittime(po.getAudittime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(action);
			log.setTitle(po.getName());
			log.setScope(po.getScope());
			log.setUrl(po.getUrl());
			log.setMetakeywords(po.getMetakeywords());
			log.setMetadescription(po.getMetadescription());
			log.setSummary(po.getSummary());
			log.setReleasetime(po.getReleasetime());
			log.setReleasesource(po.getReleasesource());
			log.setReleaseuser(po.getReleaseuser());
			log.setImg(po.getImg());
			log.setContent(po.getContent());
			logDao.save(log);
		}
		catch(Exception e)
		{
		}
	}

	public List<DsCmsCount> queryCountForAudit(long siteid, List<Long> idsForPageList, List<Long> idsForCategoryList)
	{
		String idsForPage = "", idsForCategory = "" ;
		for(int i = 0; i < idsForPageList.size(); i++)
		{
			idsForPage += idsForPageList.get(i);
			if(i < idsForPageList.size() - 1)
			{
				idsForPage += ",";
			}
		}
		for(int i = 0; i < idsForCategoryList.size(); i++)
		{
			idsForCategory += idsForCategoryList.get(i);
			if(i < idsForCategoryList.size() - 1)
			{
				idsForCategory += ",";
			}
		}
		List<DsCmsCount> resultList;
		if(idsForPage.length() > 0 || idsForCategory.length() > 0)
		{
			resultList = countDao.queryCountForAudit(siteid, idsForPage, idsForCategory);
		}
		else
		{
			resultList = new ArrayList<DsCmsCount>();
		}
		return resultList;
	}
}
