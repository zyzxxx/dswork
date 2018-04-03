/**
 * 采编审核Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryEditDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsPageEditDao;
import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategoryEdit;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPageEdit;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.UniqueId;

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

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) categoryDao.get(categoryid);
	}

	public List<DsCmsSite> queryListSite(String own, String accout)
	{
		return siteDao.queryList(own, accout);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	public DsCmsPageEdit getPage(Long pageid)
	{
		return (DsCmsPageEdit) pageDao.get(pageid);
	}

	public DsCmsCategoryEdit saveCategoryEdit(Long id)
	{
		DsCmsCategoryEdit po = new DsCmsCategoryEdit();
		DsCmsCategory _po = (DsCmsCategory) categoryDao.get(id);
		if(_po.getScope() != 0) // 非列表
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
			po.setAuditstatus(DsCmsCategoryEdit.EDIT);// 草稿
			po.setStatus(0);// page设置为新增状态
			categoryEditDao.save(po);
		}
		return po;
	}

	public DsCmsCategoryEdit getCategoryEdit(Long id)
	{
		return (DsCmsCategoryEdit) categoryEditDao.get(id);
	}

	public int updateCategoryEdit(DsCmsCategoryEdit po, DsCmsCategory c, boolean isEnablelog)
	{
		if(po.isPass())// 通过
		{
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
				c.setStatus(8);// category设置为已发布状态（因为外链不需要发布操作）
				categoryDao.update(c);
			}
			po.setStatus(1);// categoryEdit设置为待更新状态
		}
		if(isEnablelog)
		{
			writeLogCategory(po);
		}
		return categoryEditDao.update(po);
	}

	public int updatePageEdit(DsCmsPageEdit po, boolean isEnablelog)
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
			page.setScope(po.getScope());
			if(isSave)
			{
				page.setStatus(0);// page设置为新建未发布状态
				pageDao.save(page);
				pageDao.updateURL(po.getId(), po.getUrl());
			}
			else
			{
				if(page.getStatus() != 0)
				{
					page.setStatus(1);// page设置为更新未发布状态
				}
				pageDao.update(page);
			}
			po.setStatus(1);// pageEdit设置为待更新状态
		}
		if(isEnablelog)
		{
			writeLogPage(po);
		}
		return pageEditDao.update(po);
	}

	public int deletePageEdit(DsCmsPageEdit po, boolean isEnablelog)
	{
		if(po.isPass())// 通过
		{
			DsCmsPage page = (DsCmsPage) pageDao.get(po.getId());
			if(page != null)
			{
				page.setStatus(-1);
				pageDao.update(page);
			}
			pageEditDao.delete(po.getId());
		}
		else
		{
			po.setStatus(1);// pageEdit为待更新状态
			pageEditDao.update(po);
		}
		if(isEnablelog)
		{
			writeLogPage(po);
		}
		return 1;
	}

	public DsCmsPageEdit getPageEdit(Long id)
	{
		return (DsCmsPageEdit) pageEditDao.get(id);
	}

	@SuppressWarnings("unchecked")
	public Page<DsCmsPageEdit> queryPagePageEdit(PageRequest pr)
	{
		return pageEditDao.queryPage(pr);
	}

	private void writeLogPage(DsCmsPageEdit po)
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
			log.setAuditstatus(po.getAuditstatus());
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

	private void writeLogCategory(DsCmsCategoryEdit po)
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
			log.setAuditstatus(po.getAuditstatus());
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
}
