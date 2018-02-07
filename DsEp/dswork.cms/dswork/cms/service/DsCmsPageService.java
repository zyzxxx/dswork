/**
 * 内容Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsLogDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsLog;
import dswork.cms.model.DsCmsPage;
import dswork.cms.model.DsCmsSite;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import dswork.core.util.TimeUtil;
import dswork.core.util.UniqueId;

@Service
public class DsCmsPageService
{
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao pageDao;
	@Autowired
	private DsCmsLogDao logDao;

	public DsCmsPage getPage(Long id)
	{
		return (DsCmsPage) pageDao.get(id);
	}

	public void savePage(DsCmsPage po, boolean isEnablelog, String editid, String editname)
	{
		long oldid = po.getId();
		pageDao.save(po);
		if(po.getScope() != 2) // 不为外链
		{
			pageDao.updateURL(po.getId(), po.getUrl() + "/" + po.getId() + ".html");
		}
		if(isEnablelog)
		{
			if(oldid == -1 && po.getStatus() == 0)
			{
				po.setStatus(2);// 拷贝新增
			}
			writeLogPage(po, editid, editname);
		}
	}

	public void updatePage(DsCmsPage po, boolean isEnablelog, String editid, String editname)
	{
		pageDao.update(po);
		if(isEnablelog)
		{
			writeLogPage(po, editid, editname);
		}
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public DsCmsCategory getCategory(Long categoryid)
	{
		return (DsCmsCategory) categoryDao.get(categoryid);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return siteDao.queryList(map);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsCategory> queryListCategory(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return categoryDao.queryList(map);
	}

	public void updateCategory(DsCmsCategory po, boolean isEnablelog, String editid, String editname)
	{
		categoryDao.updateContent(po);
		if(isEnablelog)
		{
			writeLogCategory(po, editid, editname);
		}
	}

	public void updatePageStatus(Long id, int status)
	{
		pageDao.updateStatus(id, status);
	}

	public void updateCategoryStatus(Long id, int status)
	{
		categoryDao.updateStatus(id, status);
	}

	public void deleteBatchPage(Long[] ids)
	{
		if(ids != null)
		{
			for(long id : ids)
			{
				pageDao.delete(id);
			}
		}
	}

	public int deletePage(Long id)
	{
		return pageDao.delete(id);
	}

	@SuppressWarnings("unchecked")
	public Page<DsCmsPage> queryPagePage(PageRequest pr)
	{
		return pageDao.queryPage(pr);
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsPage> queryListPage(PageRequest pr)
	{
		return pageDao.queryList(pr);
	}

	private void writeLogPage(DsCmsPage po, String editid, String editname)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getCategoryid());
			log.setPageid(po.getId());
			log.setEditid(editid);
			log.setEditname(editname);
			log.setEdittime(TimeUtil.getCurrentTime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(4);// 直接通过审核
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

	private void writeLogCategory(DsCmsCategory po, String editid, String editname)
	{
		try
		{
			DsCmsLog log = new DsCmsLog();
			log.setId(UniqueId.genId());
			log.setSiteid(po.getSiteid());
			log.setCategoryid(po.getId());
			log.setEditid(editid);
			log.setEditname(editname);
			log.setEdittime(TimeUtil.getCurrentTime());
			log.setStatus(po.getStatus());
			log.setAuditstatus(4);// 直接通过审核
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
